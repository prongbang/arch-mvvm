package com.prongbang.archmvvm.utils

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

fun FragmentManager.replace(@IdRes idLayout: Int, fragment: Fragment) {
    val fragmentTransaction = this.beginTransaction()
    fragmentTransaction.replace(idLayout, fragment)
    fragmentTransaction.commit()
}

fun FragmentManager.add(@IdRes idLayout: Int, fragment: Fragment) {
    val fragmentTransaction = this.beginTransaction()
    fragmentTransaction.add(idLayout, fragment)
    fragmentTransaction.commit()
}