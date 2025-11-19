package com.muedsa.tvbox.api.data

/**
 * 使用Webview对给的URL页面进行资源嗅探
 */
class MediaSniffingSource(
    url: String,
    httpHeaders: Map<String, String>? = null,
    val sniffingMediaExtensions: List<String>? = null,
): MediaHttpSource(url = url, httpHeaders = httpHeaders)