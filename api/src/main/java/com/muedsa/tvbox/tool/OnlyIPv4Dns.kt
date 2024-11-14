package com.muedsa.tvbox.tool

import okhttp3.Dns
import java.net.Inet4Address
import java.net.InetAddress
import java.net.UnknownHostException

object OnlyIPv4Dns : Dns {
    override fun lookup(hostname: String): List<InetAddress> = InetAddress.getAllByName(hostname)
        .filter { it is Inet4Address }
        .also { addresses ->
            if (addresses.isEmpty()) {
                throw UnknownHostException("No IPv4 address for $hostname")
            }
        }
}