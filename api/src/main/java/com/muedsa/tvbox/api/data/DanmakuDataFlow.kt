package com.muedsa.tvbox.api.data

import kotlinx.coroutines.flow.Flow

interface DanmakuDataFlow {
    val flow: Flow<DanmakuData>
    fun close()
}