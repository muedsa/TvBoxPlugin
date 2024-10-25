package com.muedsa.tvbox.api.store

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

class StringPluginPerfKey(name: String) : PluginPerfKey<String>(name = name) {
    override fun getAndroidKey(name: String): Preferences.Key<String> = stringPreferencesKey(name)
}

fun stringPluginPerfKey(name: String): PluginPerfKey<String> = StringPluginPerfKey(name)