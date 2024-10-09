package com.muedsa.tvbox.api.plugin

import com.muedsa.tvbox.api.service.IMainScreenService
import com.muedsa.tvbox.api.service.IMediaDetailService
import com.muedsa.tvbox.api.service.IMediaSearchService

interface IPlugin {
    fun getMainScreenService(): IMainScreenService

    fun getMediaDetailService(): IMediaDetailService

    fun getMediaSearchService(): IMediaSearchService
}