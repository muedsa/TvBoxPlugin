package com.muedsa.tvbox.tool

import com.muedsa.tvbox.api.store.IPluginPerfStore
import com.muedsa.tvbox.api.store.PluginPerfKey
import com.muedsa.tvbox.api.store.stringPluginPerfKey
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import java.util.TreeSet

open class SharedCookieSaver(
    private val store: IPluginPerfStore
) {

    private val cache: TreeSet<SerializableCookie> = TreeSet(compareBy<SerializableCookie>(
        { it.name }, { it.domain }, { it.path }, { it.secure }, { it.hostOnly }
    ))

    private fun cacheCookie(cookie: SerializableCookie) {
        if (cache.contains(cookie)) {
            cache.remove(cookie)
        }
        cache.add(cookie)
    }

    @Synchronized
    fun load(): List<SerializableCookie> {
        removeExpired()
        return cache.toList()
    }

    @Synchronized
    fun save(cookie: SerializableCookie) {
        cacheCookie(cookie)
        if (cookie.persistent) {
            runBlocking {
                store.update(
                    key = createCookieKey(cookie),
                    value = LenientJson.encodeToString(cookie)
                )
            }
        }
    }

    @Synchronized
    fun remove(cookie: SerializableCookie) {
        cache.remove(cookie)
        runBlocking { store.remove(key = createCookieKey(cookie)) }
    }

    @Synchronized
    fun clear() {
        cache.clear()
        runBlocking {
            store.filter { it.startsWith(COOKIE_STORE_PREFIX) }
                .forEach { store.remove(stringPluginPerfKey(it.key)) }
        }
    }

    @Synchronized
    fun newSession() {
        cache.clear()
        runBlocking {
            store.filter { it.startsWith(COOKIE_STORE_PREFIX) }
                .forEach {
                    if (it.value is String) {
                        cache.add(LenientJson.decodeFromString<SerializableCookie>(it.value as String))
                    }
                }
        }
    }

    private fun removeExpired() {
        cache.filter { it.expiresAt <= System.currentTimeMillis() }
            .forEach {
                remove(it)
            }
    }

    companion object {
        private const val COOKIE_STORE_PREFIX = "COOKIE|"

        private fun createCookieKey(
            name: String,
            domain: String,
            path: String,
            secure: Boolean
        ): PluginPerfKey<String> {
            return stringPluginPerfKey(
                "$COOKIE_STORE_PREFIX${if (secure) "https" else "http"}://${domain}${path}|${name}"
            )
        }

        private fun createCookieKey(cookie: SerializableCookie): PluginPerfKey<String> {
            return createCookieKey(
                name = cookie.name,
                domain = if(cookie.hostOnly) cookie.domain else ".${cookie.domain}",
                path = cookie.path,
                secure = cookie.secure
            )
        }
    }

    init {
        newSession()
    }
}