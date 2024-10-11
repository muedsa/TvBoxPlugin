package com.muedsa.tvbox.api.service

import com.muedsa.tvbox.api.data.MediaDetail
import com.muedsa.tvbox.api.data.MediaEpisode
import com.muedsa.tvbox.api.data.MediaHttpSource
import com.muedsa.tvbox.api.data.MediaPlaySource

interface IMediaDetailService {

    suspend fun getDetailData(mediaId: String, detailUrl: String) : MediaDetail

    suspend fun getEpisodePlayInfo(playSource: MediaPlaySource, episode: MediaEpisode) : MediaHttpSource
}