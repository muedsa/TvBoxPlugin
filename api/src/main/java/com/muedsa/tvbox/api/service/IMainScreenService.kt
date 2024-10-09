package com.muedsa.tvbox.api.service

import com.muedsa.tvbox.api.data.MediaCardRow

interface IMainScreenService {
    suspend fun getRowsData(): List<MediaCardRow>
}