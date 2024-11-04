package com.muedsa.tvbox.tool

import org.junit.Test
import java.net.CookieStore
import java.net.HttpCookie
import java.net.URI

class PluginCookieStoreTest {

    @Test
    fun regularCookie() {
        val cookieStore = newCookieStore()
        val cookie = createPersistentCookie()
        cookieStore.add(URI_1, cookie)
        val storedCookies = cookieStore.get(URI_1)
        assert(cookie == storedCookies[0])
    }

    @Test
    fun differentUrlRequest() {
        val cookieStore = newCookieStore()
        val cookie = createPersistentCookie()
        cookieStore.add(URI_1, cookie)
        val storedCookies = cookieStore.get(URI_2)
        assert(storedCookies.isEmpty())
    }

    @Test
    fun updateCookie() {
        val cookieStore = newCookieStore()
        cookieStore.add(
            URI_1,
            createPersistentCookie(
                name = "name",
                value = "first"
            )
        )
        val newCookie = createPersistentCookie(
            name = "name",
            value = "last"
        )
        cookieStore.add(URI_1, newCookie)
        val storedCookies = cookieStore.get(URI_1)
        assert(storedCookies.size == 1)
        assert(newCookie == storedCookies[0])
    }

    @Test
    fun expiredCookie() {
        val cookieStore = newCookieStore()
        cookieStore.add(URI_1, createExpiredCookie())
        val cookies = cookieStore.get(URI_1)
        assert(cookies.isEmpty())
    }

    @Test
    fun removeCookieWithExpiredOne() {
        val cookieStore = newCookieStore()
        cookieStore.add(URI_1, createPersistentCookie())
        cookieStore.add(URI_1, createExpiredCookie())
        assert(cookieStore.get(URI_1).isEmpty())
    }

    companion object {
        fun newCookieStore(): CookieStore =
            PluginCookieStore(saver = SharedCookieSaverTest.newCookieSaver())

        private const val DEFAULT_DOMAIN = "domain.com"
        private const val DEFAULT_PATH = "/"
        private const val OTHER_DOMAIN = "otherdomain.com"
        private val URI_1 = URI.create("https://$DEFAULT_DOMAIN$DEFAULT_PATH")
        private val URI_2 = URI.create("https://$OTHER_DOMAIN$DEFAULT_PATH")

        fun createPersistentCookie(name: String = "name", value: String = "value"): HttpCookie {
            return HttpCookie(name, value).apply {
                domain = DEFAULT_DOMAIN
                path = DEFAULT_PATH
                maxAge = 24 * 60 * 60
                isHttpOnly = true
                secure = true
            }
        }

        fun createExpiredCookie(name: String = "name", value: String = "value"): HttpCookie {
            return HttpCookie(name, value).apply {
                domain = DEFAULT_DOMAIN
                path = DEFAULT_PATH
                maxAge = 0
                isHttpOnly = true
                secure = true
            }
        }
    }
}