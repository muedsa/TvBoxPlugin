package com.muedsa.tvbox.api.data

open class MediaDetail(
    id: String,
    title: String,
    detailUrl: String,
    val backgroundImageUrl: String,
) : MediaBase(
    id = id,
    title = title,
    detailUrl = detailUrl
)