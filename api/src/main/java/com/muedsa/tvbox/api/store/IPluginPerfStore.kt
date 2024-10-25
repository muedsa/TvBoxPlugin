package com.muedsa.tvbox.api.store

interface IPluginPerfStore {

    suspend fun <T> get(key: PluginPerfKey<T>): T?

    suspend fun <T> getOrDefault(key: PluginPerfKey<T>, default: T): T

    suspend fun <T> update(key: PluginPerfKey<T>, value: T)
}