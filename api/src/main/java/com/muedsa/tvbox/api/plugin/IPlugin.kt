package com.muedsa.tvbox.api.plugin

import com.muedsa.tvbox.api.service.IMainScreenService
import com.muedsa.tvbox.api.service.IMediaDetailService
import com.muedsa.tvbox.api.service.IMediaSearchService

abstract class IPlugin(var tvBoxOptions: TvBoxOptions) {
    abstract var options: PluginOptions

    abstract fun provideMainScreenService(): IMainScreenService

    abstract fun provideMediaDetailService(): IMediaDetailService

    abstract fun provideMediaSearchService(): IMediaSearchService
}