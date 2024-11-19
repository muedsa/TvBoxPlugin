package com.muedsa.tvbox.api.data

data class PagingResult<T>(
    val list: List<T> = emptyList(),
    val prevKey: String? = null,
    val nextKey: String? = null,
)