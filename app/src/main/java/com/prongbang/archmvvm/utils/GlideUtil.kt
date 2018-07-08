package com.prongbang.archmvvm.utils

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import java.security.MessageDigest

/**
 * Created by prongbang on 7/08/2018 AD.
 */
object GlideUtil {

    @JvmStatic
    fun load(context: Context, url: String, imageView: ImageView, any: Any?) {
        Glide.with(context)
                .load(url)
                .listener(listener(any))
                .into(imageView)
    }

    fun loadCircle(context: Context?, uri: Uri?, imageView: ImageView, any: Any?) {
        if (context != null) Glide.with(context)
                .load(uri)
                .listener(listener(any))
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
    }

    fun loadCircle(context: Context?, bitmap: Bitmap?, imageView: ImageView, any: Any?) {
        if (context != null) Glide.with(context)
                .load(bitmap)
                .listener(listener(any))
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
    }

    fun loadBlur(context: Context, url: String, imageView: ImageView, any: Any?) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(context)))
                .listener(listener(any))
                .into(imageView)
    }

    private fun listener(any: Any?): RequestListener<Drawable> {
        return object : RequestListener<Drawable> {

            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                return false
            }

        }
    }

    class BlurTransformation(context: Context) : BitmapTransformation(context) {

        private val rs: RenderScript = RenderScript.create(context)

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
            val blurredBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true)

            // Allocate memory for Renderscript to work with
            val input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED)
            val output = Allocation.createTyped(rs, input.type)

            // Load up an instance of the specific script that we want to use.
            val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
            script.setInput(input)

            // Set the blur radius
            script.setRadius(10f)

            // Start the ScriptIntrinisicBlur
            script.forEach(output)

            // Copy the output to the blurred bitmap
            output.copyTo(blurredBitmap)

            return blurredBitmap
        }

        override fun updateDiskCacheKey(messageDigest: MessageDigest) {
            messageDigest.update("blur transformation".toByteArray())
        }
    }
}