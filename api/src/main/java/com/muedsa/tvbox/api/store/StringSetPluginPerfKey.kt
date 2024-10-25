package com.muedsa.tvbox.api.store

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey

class StringSetPluginPerfKey(name: String) : PluginPerfKey<Set<String>>(name = name) {
    override fun getAndroidKey(name: String): Preferences.Key<Set<String>> =
        stringSetPreferencesKey(name)
}

fun stringSetPluginPerfKey(name: String): PluginPerfKey<Set<String>> = StringSetPluginPerfKey(name)