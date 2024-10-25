package com.muedsa.tvbox.api.store

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey

class IntPluginPerfKey(name: String) : PluginPerfKey<Int>(name = name) {
    override fun getAndroidKey(name: String): Preferences.Key<Int> = intPreferencesKey(name)
}

fun intPluginPerfKey(name: String): PluginPerfKey<Int> = IntPluginPerfKey(name)