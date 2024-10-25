package com.muedsa.tvbox.tool

import okhttp3.Cookie
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

object TestCookieCreator {
    private const val DEFAULT_DOMAIN = "domain.com"
    private const val DEFAULT_PATH = "/"

    val DEFAULT_URL: HttpUrl = "https://$DEFAULT_DOMAIN$DEFAULT_PATH".toHttpUrl()
    val OTHER_URL: HttpUrl = "https://otherdomain.com/".toHttpUrl()

    fun createPersistentCookie(hostOnlyDomain: Boolean): Cookie {
        val builder = Cookie.Builder()
            .path(DEFAULT_PATH)
            .name("name")
            .value("value")
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

    fun createPersistentCookie(name: String, value: String): Cookie {
        return Cookie.Builder()
            .domain(DEFAULT_DOMAIN)
            .path(DEFAULT_PATH)
            .name(name)
            .value(value)
            .expiresAt(System.currentTimeMillis() + 24 * 60 * 60 * 1000)
            .httpOnly()
            .secure()
            .build()
    }

    fun createNonPersistentCookie(): Cookie {
        return Cookie.Builder()
            .domain(DEFAULT_DOMAIN)
            .path(DEFAULT_PATH)
            .name("name")
            .value("value")
            .httpOnly()
            .secure()
            .build()
    }

    fun createNonPersistentCookie(name: String, value: String): Cookie {
        return Cookie.Builder()
            .domain(DEFAULT_DOMAIN)
            .path(DEFAULT_PATH)
            .name(name)
            .value(value)
            .httpOnly()
            .secure()
            .build()
    }

    fun createExpiredCookie(): Cookie {
        return Cookie.Builder()
            .domain(DEFAULT_DOMAIN)
            .path(DEFAULT_PATH)
            .name("name")
            .value("value")
            .expiresAt(Long.MIN_VALUE)
            .httpOnly()
            .secure()
            .build()
    }
}