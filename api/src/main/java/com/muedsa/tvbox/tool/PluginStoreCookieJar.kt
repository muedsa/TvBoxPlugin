package com.muedsa.tvbox.tool

import com.muedsa.tvbox.api.store.IPluginPerfStore
import com.muedsa.tvbox.api.store.PluginPerfKey
import com.muedsa.tvbox.api.store.stringPluginPerfKey
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.TreeSet

class PluginStoreCookieJar(
    private val store: IPluginPerfStore
) : CookieJar {

    companion object {
        const val COOKIE_STORE_PREFIX = "COOKIE|"

        private fun createCookieKey(cookie: Cookie): PluginPerfKey<String> {
            return stringPluginPerfKey(
                "$COOKIE_STORE_PREFIX${if (cookie.secure) "https" else "http"}://${cookie.domain}${cookie.path}|${cookie.name}"
            )
        }
    }

    private val cache: TreeSet<Cookie> = TreeSet(compareBy<Cookie>(
        { it.name }, { it.domain }, { it.path }, { it.secure }, { it.hostOnly }
    ))

    @Synchronized
    fun cacheCookie(cookie: Cookie) {
        if (cache.contains(cookie)) {
            cache.remove(cookie)
        }
        cache.add(cookie)
    }

    init {
        runBlocking {
            newSession()
        }
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookies.forEach { cacheCookie(it) }
        cookies.filter { it.persistent }.forEach {
            runBlocking {
                store.update(
                    key = createCookieKey(it),
                    value = LenientJson.encodeToString(SerializableCookie.from(it))
                )
            }
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val willRemove: MutableSet<Cookie> = mutableSetOf()

        val list = cache.filter {
            val unexpired = it.expiresAt > System.currentTimeMillis()
            if (!unexpired) {
                willRemove.add(it)
            }
            unexpired && it.matches(url)
        }
        if (willRemove.isNotEmpty()) {
            willRemove.forEach {
                cache.remove(it)
                runBlocking {
                    store.remove(key = createCookieKey(it))
                }
            }
        }
        return list
    }

    suspend fun newSession() {
        cache.clear()
        store.filter { it.startsWith(COOKIE_STORE_PREFIX) }
            .forEach {
                val cookieJsonStr = it.value as String?
                cookieJsonStr?.let { cjs ->
                    val serializableCookie =
                        LenientJson.decodeFromString<SerializableCookie>(cjs)
                    cacheCookie(serializableCookie.toCookie())
                }
            }
    }

    suspend fun clearAll() {
        cache.clear()
        store.filter { it.startsWith(COOKIE_STORE_PREFIX) }
            .forEach { store.remove(stringPluginPerfKey(it.key)) }
    }
}

@Serializable
data class SerializableCookie(
    val name: String,
    val value: String,
    val expiresAt: Long,
    val domain: String,
    val path: String,
    val secure: Boolean,
    val httpOnly: Boolean,
    val persistent: Boolean,
    val hostOnly: Boolean
) {
    fun toCookie(): Cookie {
        val builder = Cookie.Builder()
            .name(name)
            .value(value)
            .expiresAt(expiresAt)
            .domain(domain)
            .path(path)

        if (secure) {
            builder.secure()
        }
        if (httpOnly) {
            builder.httpOnly()
        }
        return builder.build()
    }

    companion object {
        fun from(cookie: Cookie): SerializableCookie = SerializableCookie(
            name = cookie.name,
            value = cookie.value,
            expiresAt = cookie.expiresAt,
            domain = cookie.domain,
            path = cookie.path,
            secure = cookie.secure,
            httpOnly = cookie.httpOnly,
            persistent = cookie.persistent,
            hostOnly = cookie.hostOnly,
        )
    }
}