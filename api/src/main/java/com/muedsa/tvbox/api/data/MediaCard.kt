package com.muedsa.tvbox.api.data

open class MediaCard(
    id: String,
    title: String,
    detailUrl: String,
    val subTitle: String? = null,
    val coverImageUrl: String = "",
    val coverImageHttpHeaders: Map<String, List<String>>? = null,
    val backgroundColor: Long = 0x00_00_00_00 // argb
) : MediaBase(
    id = id,
    title = title,
    detailUrl = detailUrl
)