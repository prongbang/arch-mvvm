package com.prongbang.archmvvm.ui.base

import android.arch.lifecycle.ViewModelProvider
import android.support.v4.app.Fragment
import com.prongbang.archmvvm.di.Injectable
import javax.inject.Inject

/**
 * Created by prongbang on 7/08/2017 AD.
 */
open class BaseFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

}