package com.rui.filedownload

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

internal const val FILE_DOWNLOAD_HTTP_LOGGING_INTERCEPTOR = "file_download_http_LoggingInterceptor"
internal const val AUTHENTICATION_INTERCEPTOR = "AuthenticationInterceptor"
internal const val FILE_HTTPCLIENT = "fileOkHttpClient"
internal const val FILE_RETROFIT = "fileRetrofit"
internal const val BASE_URL = "BaseUrl"
internal const val IS_DEBUG = "debug"
internal const val TIMEOUT_CONFIG = (120 * 1000).toLong()

@Module
class DownLoadModule {

    @Provides
    @Singleton
    internal fun provideDownloadService(@Named(FILE_RETROFIT) retrofit: Retrofit): DownloadService {
        return retrofit.create(DownloadService::class.java)
    }

    @Provides
    @Named(FILE_DOWNLOAD_HTTP_LOGGING_INTERCEPTOR)
    internal fun providesHttpLoggingInterceptor(
        @Named(IS_DEBUG) isDebug: Boolean
    ): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        if (isDebug) {
            // can not use the Level.BODY, It will cause the annotation @Streaming invalid in retrofit,
            // Use Level.HEADERS instead
            interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    @Provides
    @Named(FILE_HTTPCLIENT)
    internal fun provideOkHttpClient(
        @Named(FILE_DOWNLOAD_HTTP_LOGGING_INTERCEPTOR) httpLoggingInterceptor: Interceptor,
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
    @Named(FILE_RETROFIT)
    internal fun providesRetrofit(
        @Named(FILE_HTTPCLIENT) okHttpClient: OkHttpClient,
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


}