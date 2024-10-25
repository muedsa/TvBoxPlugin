package com.muedsa.tvbox.api.plugin

import com.muedsa.tvbox.api.service.IMainScreenService
import com.muedsa.tvbox.api.service.IMediaDetailService
import com.muedsa.tvbox.api.service.IMediaSearchService

abstract class IPlugin(var tvBoxContext: TvBoxContext) {
    abstract var options: PluginOptions

    abstract suspend fun onInit()

    abstract suspend fun onLaunched()

    abstract fun provideMainScreenService(): IMainScreenService

    abstract fun provideMediaDetailService(): IMediaDetailService

    abstract fun provideMediaSearchService(): IMediaSearchService
}