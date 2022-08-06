package com.rui.filedownload

import android.content.Context
import android.os.Environment
import android.text.TextUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.*

internal const val APP_DOWNLOAD = "download"

object FileUtils {

    fun createTargetFile(context: Context, fileName: String): File {
        return File(getCacheDirectory(context, APP_DOWNLOAD), fileName)
    }

    /**
     * https://www.jianshu.com/p/b752b2e70b8c
     * 获取应用专属缓存目录
     * android 4.4及以上系统不需要申请SD卡读写权限
     * 因此也不用考虑6.0系统动态申请SD卡读写权限问题，切随应用被卸载后自动清空 不会污染用户存储空间
     *
     * @param context 上下文
     * @param type    文件夹类型 可以为空，为空则返回API得到的一级目录
     * @return 缓存文件夹 如果没有SD卡或SD卡有问题则返回内存缓存目录，否则优先返回SD卡缓存目录
     */
    private fun getCacheDirectory(context: Context, type: String): String {
        var appCacheDir: File? = getExternalCacheDirectory(context, type)
        if (appCacheDir == null) {
            appCacheDir = getInternalCacheDirectory(context, type)
        }
        if (appCacheDir == null) {
            Timber.e("getCacheDirectory fail ,the reason is mobile phone unknown exception !")
        } else {
            if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                Timber.e("getCacheDirectory fail ,the reason is make directory fail !")
            }
        }
        Timber.d("appCacheDir = ${appCacheDir?.getPath()}${File.separator}")
        return appCacheDir?.path + File.separator
    }

    /**
     * 获取SD卡缓存目录
     *
     * @param context 上下文
     * @param type    文件夹类型 如果为空则返回 /storage/emulated/0/Android/data/app_package_name/cache
     * 否则返回对应类型的文件夹如Environment.DIRECTORY_PICTURES 对应的文件夹为 .../data/app_package_name/files/Pictures
     * [android.os.Environment.DIRECTORY_MUSIC],
     * [android.os.Environment.DIRECTORY_PODCASTS],
     * [android.os.Environment.DIRECTORY_RINGTONES],
     * [android.os.Environment.DIRECTORY_ALARMS],
     * [android.os.Environment.DIRECTORY_NOTIFICATIONS],
     * [android.os.Environment.DIRECTORY_PICTURES], or
     * [android.os.Environment.DIRECTORY_MOVIES].or 自定义文件夹名称
     * @return 缓存目录文件夹 或 null（无SD卡或SD卡挂载失败）
     */
    private fun getExternalCacheDirectory(context: Context, type: String): File? {
        var appCacheDir: File? = null
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            appCacheDir = if (TextUtils.isEmpty(type)) {
                context.externalCacheDir
            } else {
                context.getExternalFilesDir(type)
            }
            if (appCacheDir == null) { // 有些手机需要通过自定义目录
                appCacheDir = File(
                    Environment.getExternalStorageDirectory(),
                    "Android/data/" + context.packageName + "/cache/" + type
                )
            }
            if (appCacheDir == null) {
                Timber.e("getExternalDirectory fail ,the reason is sdCard unknown exception !")
            } else {
                if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                    Timber.e("getExternalDirectory fail ,the reason is make directory fail !")
                }
            }
        } else {
            Timber.e("getExternalDirectory fail ,the reason is sdCard nonexistence or sdCard mount fail !")
        }
        return appCacheDir
    }


    /**
     * 获取内存缓存目录
     *
     * @param type 子目录，可以为空，为空直接返回一级目录
     * @return 缓存目录文件夹 或 null（创建目录文件失败）
     * 注：该方法获取的目录是能供当前应用自己使用，外部应用没有读写权限，如 系统相机应用
     */
    private fun getInternalCacheDirectory(context: Context, type: String?): File? {
        val appCacheDir: File = if (type.isNullOrEmpty()) {
            context.cacheDir // /data/data/app_package_name/cache
        } else {
            File(context.filesDir, type) // /data/data/app_package_name/files/type
        }
        if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
            Timber.e("getInternalDirectory fail ,the reason is make directory fail !")
        }
        return appCacheDir
    }

    fun writeInputStreamToDisk(
        inputStream: InputStream,
        fileSize: Long? = null,
        targetFile: File,
        isActive: Boolean,
        onStart: () -> Unit = {},
        onProgress: (Int) -> Unit = {},
        onFinish: (File) -> Unit = {},
        onError: (Exception?) -> Unit = {}
    ): Boolean {
        var outputStream: OutputStream? = null
        return try {
            Timber.d("fileSize is $fileSize")
            onStart.invoke()
            targetFile.apply { if (exists()) delete() }
            Timber.d("targetFile is ${targetFile.canonicalPath}")
            outputStream = FileOutputStream(targetFile)
            var bytesCopied: Long = 0
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            var bytes = inputStream.read(buffer)
            while (bytes >= 0 && isActive) {
                outputStream.write(buffer, 0, bytes)
                bytesCopied += bytes
                bytes = inputStream.read(buffer)
                //计算当前下载百分比，回调传出
                fileSize?.let {
                    onProgress.invoke((100 * bytesCopied / fileSize).toInt())
                }
            }
            outputStream.flush()
            onFinish.invoke(targetFile)
            true
        } catch (e: IOException) {
            onError.invoke(e)
            false
        } finally {
            inputStream.close()
            outputStream?.close()
        }
    }
}