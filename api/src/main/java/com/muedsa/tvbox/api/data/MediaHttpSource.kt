package com.muedsa.tvbox.api.data

open class MediaHttpSource(
    val url: String,
    val httpHeaders: Map<String, String>? = null,
    val skipSegments: List<Pair<Long, Long>>? = null,
) {
    constructor(url: String) : this(url = url, httpHeaders = null, skipSegments = null)

    constructor(url: String, httpHeaders: Map<String, String>?) : this(
        url = url,
        httpHeaders = httpHeaders,
        skipSegments = null,
    )
}