package com.muedsa.tvbox.tool

import com.muedsa.tvbox.api.store.FakePluginPrefStore
import kotlinx.coroutines.test.runTest
import okhttp3.Cookie
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.junit.Test


class PluginStoreCookieJarTest {
    private val url = "https://domain.com/".toHttpUrl()
    private val otherUrl = "https://otherdomain.com".toHttpUrl()

    private fun newCookieJar(): PluginStoreCookieJar =
        PluginStoreCookieJar(store = FakePluginPrefStore())

    @Test
    fun regularCookie() {
        val cookieJar = newCookieJar()
        val cookie = TestCookieCreator.createPersistentCookie(hostOnlyDomain = false)
        cookieJar.saveFromResponse(url = url, cookies = listOf(cookie))
        val storedCookies = cookieJar.loadForRequest(url)
        assert(cookie == storedCookies[0])
    }

    @Test
    fun differentUrlRequest() {
        val cookieJar = newCookieJar()
        val cookie = TestCookieCreator.createPersistentCookie(hostOnlyDomain = false)
        cookieJar.saveFromResponse(url = url, cookies = listOf(cookie))
        val storedCookies = cookieJar.loadForRequest(otherUrl)
        assert(storedCookies.isEmpty())
    }


    @Test
    fun updateCookie() {
        val cookieJar = newCookieJar()
        cookieJar.saveFromResponse(
            url = url,
            cookies = listOf(
                TestCookieCreator.createPersistentCookie(
                    name = "name",
                    value = "first"
                )
            )
        )
        val newCookie = TestCookieCreator.createPersistentCookie(name = "name", value = "last")
        cookieJar.saveFromResponse(url = url, cookies = listOf(newCookie))
        val storedCookies = cookieJar.loadForRequest(url)
        assert(storedCookies.size == 1)
        assert(newCookie == storedCookies[0])
    }

    @Test
    fun expiredCookie() {
        val cookieJar = newCookieJar()
        cookieJar.saveFromResponse(url, listOf(TestCookieCreator.createExpiredCookie()))
        val cookies: List<Cookie> = cookieJar.loadForRequest(url)
        assert(cookies.isEmpty())
    }

    @Test
    fun removeCookieWithExpiredOne() {
        val cookieJar = newCookieJar()
        cookieJar.saveFromResponse(url, listOf(TestCookieCreator.createPersistentCookie(false)))
        cookieJar.saveFromResponse(url, listOf(TestCookieCreator.createExpiredCookie()))
        assert(cookieJar.loadForRequest(url).isEmpty())
    }

    @Test
    fun clearSessionCookies() = runTest {
        val cookieJar = newCookieJar()
        val persistentCookie = TestCookieCreator.createPersistentCookie(false)
        cookieJar.saveFromResponse(url, listOf(persistentCookie))
        cookieJar.saveFromResponse(url, listOf(TestCookieCreator.createNonPersistentCookie()))
        cookieJar.newSession()
        val storedCookies = cookieJar.loadForRequest(url)
        assert(storedCookies.size == 1)
        assert(storedCookies[0] == persistentCookie)
    }
}