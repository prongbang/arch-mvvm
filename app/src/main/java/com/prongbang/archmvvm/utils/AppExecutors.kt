package com.prongbang.archmvvm.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class AppExecutors(private var diskIO: Executor, private var networkIO: Executor, private var mainThread: Executor) {

    @Inject
    constructor() : this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(5), MainThreadExecutor())

    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {

        private val LOCK = Any()
        private val mInstance: AppExecutors? = null

        @JvmStatic
        fun getInstance(): AppExecutors {
            if (mInstance == null) {
                synchronized(LOCK) {
                    return AppExecutors()
                }
            }
            return mInstance;
        }
    }
}