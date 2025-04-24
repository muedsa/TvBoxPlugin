package com.muedsa.tvbox.api.store

sealed interface PluginPerfKey<T> {
    val name: String

    class IntPluginPerfKey(override val name: String) : PluginPerfKey<Int>
    class DoublePluginPerfKey(override val name: String) : PluginPerfKey<Double>
    class StringPluginPerfKey(override val name: String) : PluginPerfKey<String>
    class BooleanPluginPerfKey(override val name: String) : PluginPerfKey<Boolean>
    class FloatPluginPerfKey(override val name: String) : PluginPerfKey<Float>
    class LongPluginPerfKey(override val name: String) : PluginPerfKey<Long>
    class StringSetPluginPerfKey(override val name: String) : PluginPerfKey<Set<String>>
    class ByteArrayPluginPerfKey(override val name: String) : PluginPerfKey<ByteArray>
}

fun intPluginPerfKey(name: String) = PluginPerfKey.IntPluginPerfKey(name)
fun doublePluginPerfKey(name: String) = PluginPerfKey.DoublePluginPerfKey(name)
fun stringPluginPerfKey(name: String) = PluginPerfKey.StringPluginPerfKey(name)
fun booleanPluginPerfKey(name: String) = PluginPerfKey.BooleanPluginPerfKey(name)
fun floatPluginPerfKey(name: String) = PluginPerfKey.FloatPluginPerfKey(name)
fun longPluginPerfKey(name: String) = PluginPerfKey.LongPluginPerfKey(name)
fun byteArrayPluginPerfKey(name: String) = PluginPerfKey.ByteArrayPluginPerfKey(name)
fun stringSetPluginPerfKey(name: String) = PluginPerfKey.StringSetPluginPerfKey(name)
