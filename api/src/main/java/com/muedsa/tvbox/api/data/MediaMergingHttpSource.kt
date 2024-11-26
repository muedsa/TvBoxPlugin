package com.muedsa.tvbox.api.data

class MediaMergingHttpSource(
    val urls: List<String>,
    httpHeaders: Map<String, String>? = null,
) : MediaHttpSource (
    url = urls[0],
    httpHeaders = httpHeaders
)