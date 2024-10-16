package com.muedsa.tvbox.api.data

open class MediaDetail(
    id: String,
    title: String,
    detailUrl: String,
    val subTitle: String?,
    val description: String?,
    val backgroundImageUrl: String,
    // val backgroundImageHttpHeaders: Map<String, String>? = null,
    val playSourceList: List<MediaPlaySource>,
    val favoritedMediaCard: SavedMediaCard,
    val rows: List<MediaCardRow> = emptyList()
) : MediaBase(
    id = id,
    title = title,
    detailUrl = detailUrl
)