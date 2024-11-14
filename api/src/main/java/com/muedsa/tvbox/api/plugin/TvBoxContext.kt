package com.muedsa.tvbox.api.plugin

import com.muedsa.tvbox.api.store.IPluginPerfStore
import com.muedsa.tvbox.tool.IPv6Checker

data class TvBoxContext(
    val screenWidth: Int,                       // TV屏幕宽度dp
    val screenHeight: Int,                      // TV屏幕高度dp
    val debug: Boolean,                         // debug模式
    val store: IPluginPerfStore,                // 插件PrefStore
    val iPv6Status: IPv6Checker.IPv6Status     // IPv6状态
)
