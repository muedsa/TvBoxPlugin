package com.muedsa.tvbox.api.store

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

class BooleanPluginPerfKey(name: String) : PluginPerfKey<Boolean>(name = name) {
    override fun getAndroidKey(name: String): Preferences.Key<Boolean> = booleanPreferencesKey(name)
}

fun booleanPluginPerfKey(name: String): PluginPerfKey<Boolean> {
    return BooleanPluginPerfKey(name)
}