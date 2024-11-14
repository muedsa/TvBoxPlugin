package com.muedsa.tvbox.tool

import java.net.Inet6Address
import java.net.InetSocketAddress
import java.net.NetworkInterface
import java.net.Socket

object IPv6Checker {

    private val HOSTS = listOf(
        // Google DNS
        "2001:4860:4860::8888" to 53,
        // 阿里DNS
        "2400:3200::1" to 53,
        // 百度DNS
        "2400:da00::6666" to 53,
        // CNNIC DNS
        "2001:dc7:1000::1" to 53
    )

    // 检查是否有可用的IPv6地址
    fun hasIPv6Support(): Boolean = try {
        NetworkInterface.getNetworkInterfaces()
            .asSequence()
            .flatMap { it.inetAddresses.asSequence() }
            .any { it is Inet6Address && !it.isLoopbackAddress }
    } catch (_: Exception) {
        false
    }

    // 尝试创建IPv6 Socket
    fun canConnectIPv6(): Boolean {
        return HOSTS.any { (host, port) ->
            try {
                Socket().use { socket ->
                    socket.connect(InetSocketAddress(host, port), 1000)
                    true
                }
            } catch (_: Exception) {
                false
            }
        }
    }

    // 综合检测方法
    fun checkIPv6Support(): IPv6Status {
        val hasIPv6Interface = hasIPv6Support()
        val systemIPv6Enabled = !System.getProperty("java.net.preferIPv4Stack", "false").toBoolean()

        return when {
            !systemIPv6Enabled -> IPv6Status.DISABLED_BY_SYSTEM
            !hasIPv6Interface -> IPv6Status.NO_IPV6_INTERFACE
            !canConnectIPv6() -> IPv6Status.NO_IPV6_CONNECTION
            else -> IPv6Status.SUPPORTED
        }
    }

    enum class IPv6Status {
        SUPPORTED,          // IPv6完全支持
        NO_IPV6_INTERFACE,  // 没有IPv6网络接口
        NO_IPV6_CONNECTION, // 有接口但无法连接
        DISABLED_BY_SYSTEM  // 系统禁用IPv6
    }
}
