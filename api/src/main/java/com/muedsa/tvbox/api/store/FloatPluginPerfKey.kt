package com.muedsa.tvbox.api.store

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.floatPreferencesKey

class FloatPluginPerfKey(name: String) : PluginPerfKey<Float>(name = name) {
    override fun getAndroidKey(name: String): Preferences.Key<Float> = floatPreferencesKey(name)
}

fun floatPluginPerfKey(name: String): PluginPerfKey<Float> = FloatPluginPerfKey(name)