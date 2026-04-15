package com.muedsa.tvbox.api.data

class MediaMergingHttpSource(
    val urls: List<String>,
    httpHeaders: Map<String, String>? = null,
    skipSegments: List<Pair<Long, Long>>? = null,
) : MediaHttpSource (
    url = urls[0],
    httpHeaders = httpHeaders,
    skipSegments = skipSegments,
) {
    constructor(urls: List<String>, httpHeaders: Map<String, String>?) : this(
        urls = urls,
        httpHeaders = httpHeaders,
        skipSegments = null,
    )
}