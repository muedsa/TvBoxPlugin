package com.muedsa.tvbox.tool

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.CacheControl
import okhttp3.CookieJar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
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
    okHttpClient: OkHttpClient
): T {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(LenientJson.asConverterFactory("application/json".toMediaType()))
        .client(okHttpClient)
        .build()
        .create(service)
}

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

@Deprecated(
    "Jsoup的HttpConnection发送cookieStore中的cookie会有一些问题，" +
            "不推荐直接使用jsoup自带的http请求工具。" +
            "建议用Okhttp3请求数据 Jsoup仅解析数据"
)
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

fun String.toRequestBuild(): Request.Builder =
    Request.Builder().url(this)

fun Request.Builder.feignChrome(referer: String? = null) =
    header("User-Agent", ChromeUserAgent)
        .apply {
            referer?.let { header("Referer", referer) }
        }
        .cacheControl(CacheControl.FORCE_NETWORK)
        .header("Priority", "u=0, i")
        .header(
            "Sec-Ch-Ua",
            "\"Chromium\";v=\"130\", \"Google Chrome\";v=\"130\", \"Not?A_Brand\";v=\"99\""
        )
        .header("Sec-Ch-Ua-Platform", "\"Windows\"")
        .header("Upgrade-Insecure-Requests", "1")

fun Request.Builder.get(okHttpClient: OkHttpClient): Response =
    okHttpClient.newCall(get().build()).execute()

fun Request.Builder.post(body: okhttp3.RequestBody, okHttpClient: OkHttpClient): Response =
    okHttpClient.newCall(post(body).build()).execute()

fun Response.checkSuccess(
    checker: (Response) -> Unit = { resp ->
        if (!resp.isSuccessful) {
            throw RuntimeException("请求失败[status:${resp.code}, url=${resp.request.url}]")
        }
    }
): Response = this.also { checker(it) }

fun Response.stringBody(): String =
    body?.string() ?: ""

fun Response.parseHtml(): Document =
    Jsoup.parse(stringBody())