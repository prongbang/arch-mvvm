package com.prongbang.archmvvm.binding

import android.databinding.BindingAdapter
import android.view.View

/**
 * Data Binding adapters specific to the app.
 */
object BindingAdapters {

    /**
     * Use: app:visibleGone="false"
     */
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

}