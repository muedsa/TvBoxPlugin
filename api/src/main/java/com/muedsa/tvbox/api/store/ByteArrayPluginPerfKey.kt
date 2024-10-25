package com.muedsa.tvbox.api.store

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.byteArrayPreferencesKey

class ByteArrayPluginPerfKey(name: String) : PluginPerfKey<ByteArray>(name = name) {
    override fun getAndroidKey(name: String): Preferences.Key<ByteArray> =
        byteArrayPreferencesKey(name)
}

fun byteArrayPluginPerfKey(name: String): PluginPerfKey<ByteArray> = ByteArrayPluginPerfKey(name)