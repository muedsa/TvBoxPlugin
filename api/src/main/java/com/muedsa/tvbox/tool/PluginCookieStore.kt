package com.muedsa.tvbox.tool

import okhttp3.internal.canParseAsIpAddress
import java.net.CookieStore
import java.net.HttpCookie
import java.net.URI


open class PluginCookieStore(
    private val saver: SharedCookieSaver
) : CookieStore {

    override fun add(uri: URI?, cookie: HttpCookie) {
        saver.save(SerializableCookie.from(cookie))
    }

    override fun get(uri: URI): MutableList<HttpCookie> {
        return saver.load()
            .map { it.toHttpCookie() }
            .filter { it.matches(uri) }
            .toMutableList()
    }

    override fun getCookies(): MutableList<HttpCookie> {
        return saver.load()
            .map { it.toHttpCookie() }
            .toMutableList()
    }

    override fun getURIs(): MutableList<URI> {
        return saver.load().map { "${if (it.secure) "https" else "http"}://${it.domain}${it.path}" }
            .toSet()
            .map { URI.create(it) }
            .toMutableList()
    }

    override fun remove(uri: URI?, cookie: HttpCookie): Boolean {
        saver.remove(SerializableCookie.from(cookie))
        return true
    }

    override fun removeAll(): Boolean {
        saver.clear()
        return true
    }

    companion object {

        fun HttpCookie.matches(uri: URI): Boolean {
            val urlHost = uri.host
            val urlPath = uri.path
            val domainMatch = urlHost == domain
                    || (urlHost.endsWith(domain)
                    && urlHost[urlHost.length - domain.length - 1] == '.'
                    && !urlHost.canParseAsIpAddress())
            if (!domainMatch) return false
            val pathMatch =
                urlPath == path
                        || (urlPath.startsWith(path)
                        && (path.endsWith("/")
                        || urlPath[path.length] == '/'))
            if (!pathMatch) return false
            return !secure || uri.scheme == "https"
        }
    }
}