package com.muedsa.tvbox.tool

import com.muedsa.tvbox.api.store.FakePluginPrefStore

class SharedCookieSaverTest {

    companion object {
        fun newCookieSaver(): SharedCookieSaver = SharedCookieSaver(store = FakePluginPrefStore())
    }
}