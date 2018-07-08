package com.prongbang.archmvvm.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by prongbang on 7/08/2018 AD.
 */
class LiveNetworkMonitor(private val context: Context) : NetworkMonitor {

    override fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}