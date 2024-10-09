package com.muedsa.tvbox.api.data

data class MediaCardRow(
    // val key: String,
    val title: String,
    val list: List<MediaCard>,
    val cardWith: Int = 200, // dp
    val cardHeight: Int = 283, // dp
    val cardType: MediaCardType = MediaCardType.STANDARD
)
