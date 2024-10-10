package com.muedsa.tvbox.api.service

import com.muedsa.tvbox.api.data.MediaDetail
import com.muedsa.tvbox.api.data.MediaEpisode
import com.muedsa.tvbox.api.data.MediaPlayInfo
import com.muedsa.tvbox.api.data.MediaPlaySource

interface IMediaDetailService {

    suspend fun getDetailData(mediaId: String, detailUrl: String) : MediaDetail

    suspend fun getEpisodePlayInfo(playSource: MediaPlaySource, episode: MediaEpisode) : MediaPlayInfo
}