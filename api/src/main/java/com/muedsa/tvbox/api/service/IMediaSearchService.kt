package com.muedsa.tvbox.api.service

import com.muedsa.tvbox.api.data.MediaCardRow

interface IMediaSearchService {

    suspend fun searchMedias(query: String) : MediaCardRow
}