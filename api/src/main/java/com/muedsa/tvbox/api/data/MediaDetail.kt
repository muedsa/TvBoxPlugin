package com.muedsa.tvbox.api.data

open class MediaDetail(
    id: String,
    title: String,
    detailUrl: String,
    val subTitle: String? = null,
    val description: String? = null,
    val backgroundImageUrl: String,
    // val backgroundImageHttpHeaders: Map<String, String>? = null,
    val playSourceList: List<MediaPlaySource>,
    val favoritedMediaCard: SavedMediaCard? = null,
    val rows: List<MediaCardRow> = emptyList(),
    val disableEpisodeProgression: Boolean = false,
) : MediaBase(
    id = id,
    title = title,
    detailUrl = detailUrl
)