package com.muedsa.tvbox.api.store

import androidx.datastore.preferences.core.Preferences

abstract class PluginPerfKey<T>(
    val name: String
) {
    abstract fun getAndroidKey(name: String): Preferences.Key<T>
}

