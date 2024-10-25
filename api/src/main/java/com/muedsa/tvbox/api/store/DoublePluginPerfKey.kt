package com.muedsa.tvbox.api.store

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey

class DoublePluginPerfKey(name: String) : PluginPerfKey<Double>(name = name) {
    override fun getAndroidKey(name: String): Preferences.Key<Double> = doublePreferencesKey(name)
}

fun doublePluginPerfKey(name: String): PluginPerfKey<Double> = DoublePluginPerfKey(name)