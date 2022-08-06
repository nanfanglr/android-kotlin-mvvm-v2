/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rui.retrofit2.module

import android.content.res.AssetManager
import com.google.gson.Gson
import com.rui.retrofit2.ApiConfigurationLoader
import com.rui.retrofit2.intercepter.AuthenticationInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

internal const val HTTP_LOGGING_INTERCEPTOR = "httpLoggingInterceptor"
internal const val AUTHENTICATION_INTERCEPTOR = "AuthenticationInterceptor"
internal const val API_CONFIGURATION_FILE = "apiConfiguration.json"
internal const val BASE_URL = "BaseUrl"
internal const val IS_DEBUG = "debug"
internal const val TIMEOUT_CONFIG = (15 * 1000).toLong()

@Module
class RetrofitModule {

    @Provides
    @Named(HTTP_LOGGING_INTERCEPTOR)
    internal fun providesHttpLoggingInterceptor(
        @Named(IS_DEBUG) isDebug: Boolean
    ): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        if (isDebug) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    @Provides
    @Singleton
    internal fun providesApiConfigurationLoader(
        assetManager: AssetManager,
        gson: Gson
    ): ApiConfigurationLoader {
        return ApiConfigurationLoader(assetManager, gson)
    }

    @Provides
    @Named(BASE_URL)
    internal fun providesBaseUrl(
        apiConfigurationLoader: ApiConfigurationLoader
    ): String {
        return apiConfigurationLoader.loadConfig(API_CONFIGURATION_FILE)?.host ?: ""
    }

    @Provides
    internal fun providesGson(): Gson = Gson()

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        @Named(HTTP_LOGGING_INTERCEPTOR) httpLoggingInterceptor: Interceptor,
        @Named(AUTHENTICATION_INTERCEPTOR) authenticationInterceptor: Interceptor
    ): OkHttpClient {

        val okHttpBuilder = OkHttpClient.Builder().apply {

            connectTimeout(TIMEOUT_CONFIG, TimeUnit.MILLISECONDS)
            writeTimeout(TIMEOUT_CONFIG, TimeUnit.MILLISECONDS)
            readTimeout(TIMEOUT_CONFIG, TimeUnit.MILLISECONDS)

            // Adds authentication headers when required in network calls
            addInterceptor(authenticationInterceptor)

            // Logs network calls for debug builds
            addInterceptor(httpLoggingInterceptor)
        }

        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    internal fun providesRetrofit(
        okHttpClient: OkHttpClient,
        @Named(BASE_URL) baseUrl: String,
        @Named(IS_DEBUG) isDebug: Boolean
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .validateEagerly(isDebug)// Fail early: check Retrofit configuration at creation time in Debug build.
            .addConverterFactory(GsonConverterFactory.create())
            //                .addConverterFactory(MyGsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Named(AUTHENTICATION_INTERCEPTOR)
    internal
    fun providesAuthenticationInterceptor(): Interceptor {
        return AuthenticationInterceptor()
    }
}
