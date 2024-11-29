package com.muedsa.tvbox.tool

import kotlinx.serialization.Serializable
import okhttp3.Cookie
import java.net.HttpCookie

@Serializable
data class SerializableCookie(
    val name: String,
    val value: String,
    val expiresAt: Long,
    val domain: String, // 对于.example.com这样的domain=example.com,hostOnly=false
    val path: String,
    val secure: Boolean,
    val httpOnly: Boolean,
    val persistent: Boolean,
    val hostOnly: Boolean
) {

    init {
        check(!domain.startsWith(".")) { "please domain=${domain.removePrefix(".")}, hostOnly=false" }
    }

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
            it.domain = if (hostOnly) domain else ".$domain"
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

        fun from(cookie: HttpCookie): SerializableCookie {
            val hostOnly = !cookie.domain.startsWith(".")
            val domain = if (!hostOnly) {
                cookie.domain.removePrefix(".")
            } else cookie.domain
            return SerializableCookie(
                name = cookie.name,
                value = cookie.value,
                expiresAt = if (cookie.maxAge == -1L) 253402300799999L else System.currentTimeMillis() + cookie.maxAge * 1000,
                domain = domain,
                path = cookie.path,
                secure = cookie.secure,
                httpOnly = cookie.isHttpOnly,
                persistent = cookie.maxAge != -1L,
                hostOnly = hostOnly,
            )
        }
    }
}