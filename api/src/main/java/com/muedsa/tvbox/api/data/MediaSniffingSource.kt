package com.muedsa.tvbox.api.data

/**
 * 使用Webview对给的URL页面进行资源嗅探
 */
class MediaSniffingSource(
    url: String,
    httpHeaders: Map<String, String>? = null,
    skipSegments: List<Pair<Long, Long>>? = null,
    val sniffingMediaExtensions: List<String>? = null,
) : MediaHttpSource(url = url, httpHeaders = httpHeaders, skipSegments = skipSegments) {

    constructor(url: String) : this(
        url = url,
        httpHeaders = null,
        skipSegments = null,
        sniffingMediaExtensions = null,
    )

    constructor(url: String, httpHeaders: Map<String, String>?) : this(
        url = url,
        httpHeaders = httpHeaders,
        skipSegments = null,
        sniffingMediaExtensions = null,
    )

    constructor(
        url: String,
        httpHeaders: Map<String, String>?,
        sniffingMediaExtensions: List<String>? = null
    ) : this(
        url = url,
        httpHeaders = httpHeaders,
        skipSegments = null,
        sniffingMediaExtensions = sniffingMediaExtensions,
    )
}