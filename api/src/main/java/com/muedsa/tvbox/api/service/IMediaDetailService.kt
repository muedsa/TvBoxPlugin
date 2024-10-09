package com.muedsa.tvbox.api.service

import com.muedsa.tvbox.api.data.MediaDetail

interface IMediaDetailService {

    suspend fun getDetailData(mediaId: String, detailUrl: String) : MediaDetail
}