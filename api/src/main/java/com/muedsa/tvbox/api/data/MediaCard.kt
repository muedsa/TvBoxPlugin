package com.muedsa.tvbox.api.data

open class MediaCard(
    id: String,
    title: String,
    detailUrl: String,
    val subTitle: String? = null,
    val coverImageUrl: String = "",
    // val coverImageHttpHeaders: Map<String, String>? = null,
    val backgroundColor: Int = 0
) : MediaBase(
    id = id,
    title = title,
    detailUrl = detailUrl
)