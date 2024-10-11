package com.muedsa.tvbox.api.data

open class MediaHttpSource(
    val url: String,
    val httpHeaders: Map<String, String>? = null
)