package com.muedsa.tvbox.api.store

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey

class LongPluginPerfKey(name: String) : PluginPerfKey<Long>(name = name) {
    override fun getAndroidKey(name: String): Preferences.Key<Long> = longPreferencesKey(name)
}

fun longPluginPerfKey(name: String): PluginPerfKey<Long> = LongPluginPerfKey(name)