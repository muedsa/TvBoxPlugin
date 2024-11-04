package com.muedsa.tvbox.tool

import kotlinx.serialization.Serializable
import okhttp3.Cookie
import java.net.HttpCookie

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

    fun toHttpCookie(): HttpCookie {
        return HttpCookie(name, value).also {
            if (expiresAt == 253402300799999L) {
                it.maxAge = -1L
            } else {
                it.maxAge = (expiresAt - System.currentTimeMillis()) / 1000
            }
            it.domain = domain
            it.path = path
            it.secure = secure
            it.isHttpOnly = httpOnly
        }
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

        fun from(cookie: HttpCookie): SerializableCookie = SerializableCookie(
            name = cookie.name,
            value = cookie.value,
            expiresAt = if (cookie.maxAge == -1L) 253402300799999L else System.currentTimeMillis() + cookie.maxAge * 1000,
            domain = cookie.domain,
            path = cookie.path,
            secure = cookie.secure,
            httpOnly = cookie.isHttpOnly,
            persistent = true,
            hostOnly = false,
        )
    }
}