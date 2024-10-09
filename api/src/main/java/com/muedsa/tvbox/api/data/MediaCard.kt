package com.muedsa.tvbox.api.data

open class MediaCard(
    id: String,
    title: String,
    detailUrl: String,
    val coverUrl: String,
    val subTitle: String? = null,
) : MediaBase(
    id = id,
    title = title,
    detailUrl = detailUrl
)