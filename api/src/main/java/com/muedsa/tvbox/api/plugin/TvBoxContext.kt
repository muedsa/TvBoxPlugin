package com.muedsa.tvbox.api.plugin

data class TvBoxContext(
    val screenWidth: Int,       // TV屏幕宽度dp
    val screenHeight: Int,      // TV屏幕高度dp
    val debug: Boolean          // debug模式
)
