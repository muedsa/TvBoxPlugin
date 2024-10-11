package com.muedsa.tvbox.tool

import kotlinx.serialization.json.Json

val LenientJson = Json {
    ignoreUnknownKeys = true
    isLenient = true
}