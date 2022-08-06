package com.rui.retrofit2.intercepter

import java.nio.charset.Charset

class ResourceFile(private val filePath: String) {
    private val loader: ClassLoader
        get() = Thread.currentThread().contextClassLoader
            ?: error("class loader not available")

    private fun exist(): Boolean = loader.getResource(filePath) != null

    fun readText(charset: Charset = Charsets.UTF_8): String {
        if (exist()) {
            error("File $filePath does not exist or it cannot be access")
        }
        return loader.getResourceAsStream(filePath)
            .bufferedReader(charset)
            .use { it.readLine() }
    }
}