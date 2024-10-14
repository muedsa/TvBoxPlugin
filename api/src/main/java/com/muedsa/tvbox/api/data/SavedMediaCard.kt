package com.muedsa.tvbox.api.data

open class SavedMediaCard(
    id: String,
    title: String,
    detailUrl: String,
    coverImageUrl: String,
    // val coverImageHttpHeaders: Map<String, String>? = null,
    subTitle: String? = null,
    val cardWidth: Int,
    val cardHeight: Int,
) : MediaCard(
    id = id,
    title = title,
    detailUrl = detailUrl,
    coverImageUrl = coverImageUrl,
    // coverImageHttpHeaders = coverImageHttpHeaders,
    subTitle = subTitle
)