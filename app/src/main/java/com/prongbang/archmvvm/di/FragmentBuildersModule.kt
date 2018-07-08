package com.prongbang.archmvvm.di

import com.prongbang.archmvvm.ui.user.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): UserFragment

}