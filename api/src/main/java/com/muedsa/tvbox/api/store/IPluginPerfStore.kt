package com.muedsa.tvbox.api.store

interface IPluginPerfStore {

    suspend fun <T> get(key: PluginPerfKey<T>): T?

    suspend fun <T> getOrDefault(key: PluginPerfKey<T>, default: T): T

    suspend fun filter(predicate: (String) -> Boolean): Map<String, Any>

    suspend fun <T> update(key: PluginPerfKey<T>, value: T)

    suspend fun <T> remove(key: PluginPerfKey<T>)
}