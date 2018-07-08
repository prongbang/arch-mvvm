package com.prongbang.archmvvm.ui.base

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


/**
 * Created by prongbang on 7/08/2017 AD.
 */
@SuppressLint("Registered")
open class BaseAppCompatActivity : AppCompatActivity(), HasSupportFragmentInjector {

    private val TAG = BaseAppCompatActivity::class.java.simpleName

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Glide.get(this).setMemoryCategory(MemoryCategory.NORMAL)
    }

    protected fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS") { _, _ -> openSettings() }

        builder.setNegativeButton("Cancel") { p0, _ -> p0?.cancel() }
        builder.show()
    }

    // navigating user to app settings
    protected fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

    // Customize dexter permission callback
    abstract inner class OnPermissionListener : PermissionListener {

        abstract fun onPermissionGranted()

        override fun onPermissionGranted(response: PermissionGrantedResponse?) {
            onPermissionGranted()
        }

        override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
            token?.continuePermissionRequest()
        }

        override fun onPermissionDenied(response: PermissionDeniedResponse) {
            if (response.isPermanentlyDenied) {
                // navigate user to app settings
                showSettingsDialog()
            }
        }
    }
}