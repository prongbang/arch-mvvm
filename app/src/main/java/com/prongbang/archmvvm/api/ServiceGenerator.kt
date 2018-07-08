package com.prongbang.archmvvm.api

import android.content.Context
import com.prongbang.arch_mvvm.BuildConfig
import com.prongbang.archmvvm.utils.NetworkMonitor
import com.prongbang.archmvvm.utils.NoNetworkException
import com.prongbang.archmvvm.vo.Config
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Created by prongbang on 7/08/2018 AD.
 */

object ServiceGenerator {

    /**
     * Create Service Request to Server
     *
     * @param clazz
     * @param <T>
     * @return </T>
     * */
    fun <T> create(context: Context, networkMonitor: NetworkMonitor, config: Config, clazz: Class<T>): T {

        val cacheDir = File(context.cacheDir, UUID.randomUUID().toString())
        // 10 MiB cache
        val cache = Cache(cacheDir, 10 * 1024 * 1024)

        val httpClient = OkHttpClient.Builder()
                .cache(cache)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)

        val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.d(message) })
        logger.level = HttpLoggingInterceptor.Level.BASIC

        httpClient.addInterceptor(logger)

        httpClient.addInterceptor { chain ->
            val original = chain.request()

            if (BuildConfig.DEBUG) {
                try {
                    Timber.i("URL ---> %s", original.url())
                    Timber.i("${original.method()} ---> ${toString(original)}")
                } catch (e: Exception) {
                    Timber.e("Message ---> %s", e.message)
                }
            }

            // Request customization: add request headers
            val requestBuilder = original.newBuilder().apply {
                addHeader("Authorization", "${config.accessToken.tokenType} ${config.accessToken.accessToken}")
                addHeader("Accept-Language", config.languageKey)
            }

            val request = requestBuilder.build()
            try {
                val auth = request.header("Authorization")
                val lang = request.header("Accept-Language")
            } catch (e: Exception) {
                Timber.e("Message ---> %s", e.message)
            }

            chain.proceed(request)
        }

        // Network monitor interceptor:
        httpClient.addInterceptor { chain ->
            if (networkMonitor.isConnected()) {
                return@addInterceptor chain.proceed(chain.request())
            } else {
                throw NoNetworkException()
            }
        }

        val client = httpClient.build()
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(config.httpUrl)
                .client(client)
                .build()

        return retrofit.create(clazz)
    }

    /**
     * GET Value Request
     *
     * @param request
     * @return
     */
    private fun toString(request: Request): String? {
        try {
            if (request.method() == "GET") {
                return request.url().query()
            } else {
                val copy = request.newBuilder().build()
                if (copy != null) {
                    val buffer = Buffer()
                    val body = copy.body()
                    if (body != null) {
                        body.writeTo(buffer)
                        return buffer.readUtf8()
                    }
                }
            }
            return "null"
        } catch (e: IOException) {
            return "did not work"
        }

    }

}