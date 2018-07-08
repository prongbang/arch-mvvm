package com.prongbang.archmvvm.binding

import android.databinding.BindingAdapter
import android.net.Uri
import android.support.v4.app.Fragment
import android.widget.ImageView
import com.prongbang.archmvvm.utils.GlideUtil
import javax.inject.Inject

/**
 * Binding adapters that work with a fragment instance.
 */
class FragmentBindingAdapters @Inject constructor(val fragment: Fragment) {

    /**
     * Use: app:imageUrl="http://www.image.com/avatar.png"
     */
    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView, url: String?) {
        GlideUtil.loadCircle(imageView.context, Uri.parse(url), imageView, null)
    }

}
