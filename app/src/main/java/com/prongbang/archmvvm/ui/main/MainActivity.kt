package com.prongbang.archmvvm.ui.main

import android.os.Bundle
import com.prongbang.arch_mvvm.R
import com.prongbang.archmvvm.ui.base.BaseAppCompatActivity
import com.prongbang.archmvvm.ui.user.UserFragment
import com.prongbang.archmvvm.utils.replace

class MainActivity : BaseAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.replace(R.id.container, UserFragment.newInstance())
        }
    }
}
