package com.prongbang.archmvvm.di

import android.app.Application
import android.content.Context
import com.prongbang.archmvvm.api.ApiService
import com.prongbang.archmvvm.api.ServiceGenerator
import com.prongbang.archmvvm.dao.AccessTokenDao
import com.prongbang.archmvvm.db.AppDatabase
import com.prongbang.archmvvm.jni.JniHelper
import com.prongbang.archmvvm.prefs.PrefsHelper
import com.prongbang.archmvvm.utils.LiveNetworkMonitor
import com.prongbang.archmvvm.utils.NetworkMonitor
import com.prongbang.archmvvm.vo.AccessToken
import com.prongbang.archmvvm.vo.Config
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase = AppDatabase.create(app, false)

    @Provides
    fun provideApiSerivce(app: Application, networkMonitor: NetworkMonitor, prefsHelper: PrefsHelper, accessTokenDao: AccessTokenDao): ApiService {

        return ServiceGenerator.create(
                app.applicationContext,
                networkMonitor,
                Config(
                        HttpUrl.parse(JniHelper.apiService()) as HttpUrl,
                        prefsHelper.language,
                        accessTokenDao.getLast() ?: AccessToken()
                ),
                ApiService::class.java
        )
    }

    @Singleton
    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @Singleton
    @Provides
    fun providePrefsHelper(app: Application): PrefsHelper = PrefsHelper(app)

    @Singleton
    @Provides
    fun provideNetworkMonitor(app: Application): NetworkMonitor = LiveNetworkMonitor(app)

    @Singleton
    @Provides
    fun provideAccessTokenDao(db: AppDatabase): AccessTokenDao = db.accessTokenDao()

}