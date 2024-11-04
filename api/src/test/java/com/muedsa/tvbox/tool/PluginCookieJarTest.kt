package com.muedsa.tvbox.tool

import okhttp3.Cookie
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.junit.Test

class PluginCookieJarTest {

    @Test
    fun regularCookie() {
        val cookieJar = newCookieJar()
        val cookie = createPersistentCookie()
        cookieJar.saveFromResponse(url = URL, cookies = listOf(cookie))
        val storedCookies = cookieJar.loadForRequest(URL)
        assert(cookie == storedCookies[0])
    }

    @Test
    fun differentUrlRequest() {
        val cookieJar = newCookieJar()
        val cookie = createPersistentCookie()
        cookieJar.saveFromResponse(url = URL, cookies = listOf(cookie))
        val storedCookies = cookieJar.loadForRequest(OTHER_URL)
        assert(storedCookies.isEmpty())
    }

    @Test
    fun updateCookie() {
        val cookieJar = newCookieJar()
        cookieJar.saveFromResponse(
            url = URL,
            cookies = listOf(
                createPersistentCookie(
                    name = "name",
                    value = "first"
                )
            )
        )
        val newCookie = createPersistentCookie(name = "name", value = "last")
        cookieJar.saveFromResponse(url = URL, cookies = listOf(newCookie))
        val storedCookies = cookieJar.loadForRequest(URL)
        assert(storedCookies.size == 1)
        assert(newCookie == storedCookies[0])
    }

    @Test
    fun expiredCookie() {
        val cookieJar = newCookieJar()
        cookieJar.saveFromResponse(URL, listOf(createExpiredCookie()))
        val cookies: List<Cookie> = cookieJar.loadForRequest(URL)
        assert(cookies.isEmpty())
    }

    @Test
    fun removeCookieWithExpiredOne() {
        val cookieJar = newCookieJar()
        cookieJar.saveFromResponse(URL, listOf(createPersistentCookie()))
        cookieJar.saveFromResponse(URL, listOf(createExpiredCookie()))
        assert(cookieJar.loadForRequest(URL).isEmpty())
    }

    companion object {
        fun newCookieJar(): PluginCookieJar =
            PluginCookieJar(saver = SharedCookieSaverTest.newCookieSaver())

        private const val DEFAULT_DOMAIN = "domain.com"
        private const val DEFAULT_PATH = "/"
        private const val OTHER_DOMAIN = "otherdomain.com"
        private val URL = "https://$DEFAULT_DOMAIN$DEFAULT_PATH".toHttpUrl()
        private val OTHER_URL = "https://$OTHER_DOMAIN$DEFAULT_PATH".toHttpUrl()

        fun createPersistentCookie(
            name: String = "name",
            value: String = "value",
            hostOnlyDomain: Boolean = false
        ): Cookie {
            val builder = Cookie.Builder()
                .path(DEFAULT_PATH)
                .name(name)
                .value(value)
                .expiresAt(System.currentTimeMillis() + 24 * 60 * 60 * 1000)
                .httpOnly()
                .secure()
            if (hostOnlyDomain) {
                builder.hostOnlyDomain(DEFAULT_DOMAIN)
            } else {
                builder.domain(DEFAULT_DOMAIN)
            }
            return builder.build()
        }

        fun createNonPersistentCookie(name: String = "name", value: String = "value"): Cookie {
            return Cookie.Builder()
                .domain(DEFAULT_DOMAIN)
                .path(DEFAULT_PATH)
                .name(name)
                .value(value)
                .httpOnly()
                .secure()
                .build()
        }

        fun createExpiredCookie(name: String = "name", value: String = "value"): Cookie {
            return Cookie.Builder()
                .domain(DEFAULT_DOMAIN)
                .path(DEFAULT_PATH)
                .name(name)
                .value(value)
                .expiresAt(Long.MIN_VALUE)
                .httpOnly()
                .secure()
                .build()
        }
    }
}