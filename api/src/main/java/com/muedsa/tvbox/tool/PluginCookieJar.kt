package com.muedsa.tvbox.tool

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

open class PluginCookieJar(
    private val saver: SharedCookieSaver
) : CookieJar {

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookies.forEach {
            saver.save(SerializableCookie.from(it))
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return saver.load().map { it.toCookie() }.filter { it.matches(url) }
    }
}
