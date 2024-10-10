package com.muedsa.tvbox.api.data

open class MediaCard(
    id: String,
    title: String,
    detailUrl: String,
    val coverImageUrl: String,
    val coverImageHttpHeaders: Map<String, String>? = null,
    val subTitle: String? = null,
) : MediaBase(
    id = id,
    title = title,
    detailUrl = detailUrl
)