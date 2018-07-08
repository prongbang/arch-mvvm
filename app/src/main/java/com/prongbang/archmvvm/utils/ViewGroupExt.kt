package com.prongbang.berdd.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

// Extend ViewGroup class with inflate function
fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}