package com.muedsa.tvbox.tool

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.CookieJar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

const val ChromeUserAgent =
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36"

fun createOkHttpClient(debug: Boolean = false, cookieJar: CookieJar = CookieJar.NO_COOKIES): OkHttpClient =
    OkHttpClient.Builder()
        .cookieJar(cookieJar)
        .apply {
            if (debug) {
                addInterceptor(HttpLoggingInterceptor()
                    .also { it.level = HttpLoggingInterceptor.Level.BODY })
            }
        }
        .build()

fun <T> createJsonRetrofit(
    baseUrl: String,
    service: Class<T>,
    debug: Boolean = false,
    cookieJar: CookieJar = CookieJar.NO_COOKIES
): T {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(LenientJson.asConverterFactory("application/json".toMediaType()))
        .client(createOkHttpClient(debug = debug, cookieJar = cookieJar))
        .build()
        .create(service)
}