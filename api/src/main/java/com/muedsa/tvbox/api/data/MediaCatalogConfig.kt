package com.muedsa.tvbox.api.data

data class MediaCatalogConfig(
    val initKey: String,
    val pageSize: Int,
    val cardWidth: Int,
    val cardHeight: Int,
    val cardType: MediaCardType = MediaCardType.STANDARD,
    val catalogOptions: List<MediaCatalogOption> = emptyList(),
)