package com.muedsa.tvbox.api.service

import com.muedsa.tvbox.api.data.MediaCard

interface IMediaSearchService {

    suspend fun searchData(query: String) : List<MediaCard>
}