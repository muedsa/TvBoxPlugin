package com.muedsa.tvbox.api.service

import com.muedsa.tvbox.api.data.MediaCard
import com.muedsa.tvbox.api.data.MediaCatalogConfig
import com.muedsa.tvbox.api.data.MediaCatalogOption
import com.muedsa.tvbox.api.data.PagingResult

interface IMediaCatalogService {

    suspend fun getConfig(): MediaCatalogConfig

    suspend fun catalog(options: List<MediaCatalogOption>, loadKey: String, loadSize: Int): PagingResult<MediaCard>

}