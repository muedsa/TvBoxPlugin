package com.muedsa.tvbox.tool

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.CookieJar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jsoup.Connection
import retrofit2.Retrofit
import java.net.CookieStore

const val ChromeUserAgent =
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) " +
            "Chrome/118.0.0.0 " +
            "Safari/537.36"

fun createOkHttpClient(
    debug: Boolean = false,
    cookieJar: CookieJar = CookieJar.NO_COOKIES
): OkHttpClient =
    OkHttpClient.Builder()
        .cookieJar(cookieJar)
        .apply {
            if (debug) {
                addNetworkInterceptor(HttpLoggingInterceptor()
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

fun Connection.feignChrome(referrer: String? = null, cookieStore: CookieStore? = null): Connection {
    return userAgent(ChromeUserAgent)
        .also {
            if (!referrer.isNullOrEmpty()) {
                it.referrer(referrer)
            }
            if (cookieStore != null) {
                it.cookieStore(cookieStore)
            }
        }
        .header("Cache-Control", "no-cache")
        .header("Pragma", "no-cache")
        .header("Priority", "u=0, i")
        .header("Sec-Ch-Ua", "\"Chromium\";v=\"130\", \"Google Chrome\";v=\"130\", \"Not?A_Brand\";v=\"99\"")
        .header("Sec-Ch-Ua-Platform", "\"Windows\"")
        .header("Upgrade-Insecure-Requests", "1")
        .header("Connection", "close")
}