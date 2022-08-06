package com.rui.retrofit2

import android.content.res.AssetManager
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception

class ApiConfigurationLoader(
    private val assetManager: AssetManager,
    private val gson: Gson
) {
    fun loadConfig(fileName: String): ApiConfiguration? = try {
        assetManager.open(fileName)
            .reader()
            .readText()
            .let {
                gson.fromJson(it, ApiConfiguration::class.java)
            }
//        BufferedReader(InputStreamReader(assetManager.open(fileName))).use {
//            gson.fromJson(
//                it,
//                ApiConfiguration::class.java
//            )
//        }
    } catch (ex: Exception) {
        null
    }
}