package com.rui.encrypt

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.annotation.RequiresApi
import java.lang.RuntimeException
import java.security.*
import java.security.interfaces.RSAPublicKey
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * 使用RSA对AES的密钥进行加密，然后使用AES在应用范围内加密
 */
@RequiresApi(Build.VERSION_CODES.M)
class KeyStoreHelper {

    private val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE_PROVIDER)

    private val rsaCipher: Cipher by lazy { Cipher.getInstance(RSA_MODE) }

    init {
        // The BC provider is deprecated and as of Android P this method will throw a NoSuchAlgorithmException.
        // To fix this you should stop specifying a provider and use the default implementation.
        // should init the BouncyCastleProvider in the application, code as follow:
        // Security.addProvider(BouncyCastleProvider)
        keyStore.load(null)
        createKey()
    }

    private fun createKey() {
        if (!keyStore.containsAlias(KEY_ALIAS)) {
            repeatTryCatchSleep(
                DEFAULT_RETRY_NUMBER,
                DEFAULT_RETRY_WAIT_MILLIS,
                CREATE_KEY_TAG,
            ) {
                tryGenerateKeyPair()
            }
        }
    }

    private fun tryGenerateKeyPair() {
        val keyPairGenerator = KeyPairGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_RSA,
            ANDROID_KEYSTORE_PROVIDER
        )
        keyPairGenerator.initialize(
            KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_DECRYPT or KeyProperties.PURPOSE_ENCRYPT
            )
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                .build()
        )
        keyPairGenerator.generateKeyPair()
    }

    fun generateEncryptedAESKey(): String {
        val key = ByteArray(16)
        val secureRandom = SecureRandom()
        secureRandom.nextBytes(key)
        val encryptedKey = rsaEncrypt(key)
        return Base64.encodeToString(encryptedKey, Base64.DEFAULT)
    }

    private fun getSecretKey(base64EncryptedKey: String): Key {
        val encryptedKey = Base64.decode(base64EncryptedKey, Base64.DEFAULT)
        val key = rsaDecrypt(encryptedKey)
        return SecretKeySpec(key, ALGORITHM_AES)
    }

    fun isValidSecretKey(base64EncryptedKey: String): Boolean {
        return try {
            retryToMitigateIntermittentAndroidTenIssue(KEYSTORE_INTERMITTENT_ERROR_NUMBER_OF_RETRIES) {
                getSecretKey(base64EncryptedKey)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * AES加密
     */
    fun encrypt(input: String, base64EncryptedKey: String): String {
        return try {
            val cipher = Cipher.getInstance(AES_MODE, BOUNCY_CASTLE_PROVIDER)
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(base64EncryptedKey))
            val inputBytes = input.toByteArray()
            val encodeBytes = cipher.doFinal(inputBytes)
            Base64.encodeToString(encodeBytes, Base64.DEFAULT)
        } catch (e: java.lang.Exception) {
            ""
        }
    }

    /**
     * AES解密
     */
    fun decrypt(encryptedBase64EncodeString: String, base64EncryptedKey: String): String {
        encryptedBase64EncodeString.ifEmpty {
            return ""
        }
        return try {
            val cipher = Cipher.getInstance(AES_MODE, BOUNCY_CASTLE_PROVIDER)
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(base64EncryptedKey))
            val encryptedBytes = Base64.decode(encryptedBase64EncodeString, Base64.DEFAULT)
            val decodeBytes = cipher.doFinal(encryptedBytes)
            String(decodeBytes)
        } catch (e: Exception) {
            ""
        }
    }

    @Synchronized
    private fun rsaEncrypt(secret: ByteArray): ByteArray {
        return repeatTryCatchSleep(
            DEFAULT_RETRY_NUMBER,
            DEFAULT_RETRY_WAIT_MILLIS,
            RSA_ENCRYPT_TAG
        ) {
            tryRsaEncrypt(secret)
        }
    }

    private fun tryRsaEncrypt(secret: ByteArray): ByteArray {
        val publicKey = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            keyStore.getCertificate(KEY_ALIAS).publicKey as RSAPublicKey
        } else {
            val keyEntry = keyStore.getEntry(KEY_ALIAS, null) as KeyStore.PrivateKeyEntry
            keyEntry.certificate.publicKey as RSAPublicKey
        }
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return rsaCipher.doFinal(secret)
    }

    @Synchronized
    private fun rsaDecrypt(encrypted: ByteArray): ByteArray {
        val privateKey = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            keyStore.getKey(KEY_ALIAS, null)
        } else {
            val privateKeyEntry = keyStore.getEntry(KEY_ALIAS, null) as KeyStore.PrivateKeyEntry
            privateKeyEntry.privateKey
        }
        rsaCipher.init(Cipher.DECRYPT_MODE, privateKey)
        return rsaCipher.doFinal(encrypted)
    }

    private fun <T> repeatTryCatchSleep(
        retries: Int,
        waitMillis: Long,
        tag: String,
        block: () -> T
    ): T {
        repeat(retries) {
            try {
                return block()
            } catch (e: Exception) {
                Thread.sleep(waitMillis)
            }
        }
        throw RetryCountMaxReached("Failed to repeatTryCatchSleep successfully $tag")
    }

    /**
     * Approach suggested by thi stackOverFlow questions:
     * 60482479 and 59404282
     * Both of them seems to work fine with a retry.
     */
    private fun retryToMitigateIntermittentAndroidTenIssue(retries: Int, operation: () -> Unit) {
        for (i in 1..retries) {
            try {
                operation()
                return
            } catch (e: Exception) {
                Thread.sleep(KEYSTORE_INTERMITTENT_ERROR_RETRY_WAIT_MILLIS)
            }
        }
        throw KeyStoreIntermittentIssueRetriesExhausted
    }

    private companion object {
        const val ANDROID_KEYSTORE_PROVIDER = "AndroidKeyStore"
        const val RSA_MODE = "RSA/ECB/PKCS1Padding"
        const val AES_MODE = "RSA/ECB/PKCS7Padding"
        const val BOUNCY_CASTLE_PROVIDER = "BC"
        const val KEY_ALIAS = "KEY_RUI_MVVM"
        const val ALGORITHM_AES = "AES"
        const val KEYSTORE_INTERMITTENT_ERROR_RETRY_WAIT_MILLIS = 100L
        const val KEYSTORE_INTERMITTENT_ERROR_NUMBER_OF_RETRIES = 3
        const val DEFAULT_RETRY_WAIT_MILLIS = 50L
        const val DEFAULT_RETRY_NUMBER = 3
        const val CREATE_KEY_TAG = "createKey"
        const val RSA_ENCRYPT_TAG = "rsaEncrypt"
    }

    private class RetryCountMaxReached(message: String) : RuntimeException(message)

    private object KeyStoreIntermittentIssueRetriesExhausted : RuntimeException()
}