package com.prongbang.archmvvm.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.arch.persistence.room.migration.Migration
import android.content.Context
import com.prongbang.archmvvm.dao.AccessTokenDao
import com.prongbang.archmvvm.dao.UserDao
import com.prongbang.archmvvm.db.convertor.StringListConverter
import com.prongbang.archmvvm.jni.JniHelper
import com.prongbang.archmvvm.vo.AccessToken
import com.prongbang.archmvvm.vo.User

/**
 * Created by prongbang on 7/08/2018 AD.
 */

@Database(entities = [
    AccessToken::class,
    User::class
], version = 1)

@TypeConverters(value = [
    StringListConverter::class
])
abstract class AppDatabase : RoomDatabase() {

    abstract fun accessTokenDao(): AccessTokenDao
    abstract fun userDao(): UserDao

    // Other dao here..

    companion object {

        private val DATABASE_FILENAME = JniHelper.databaseName()
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private val LOCK_INMEM = Any()

        fun getInMemory(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(LOCK_INMEM) {
                    INSTANCE = Room.inMemoryDatabaseBuilder(context.applicationContext, AppDatabase::class.java)
                            // To simplify the codelab, allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE!!
        }

        fun getInDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(LOCK) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_FILENAME)
                            .addMigrations(object : Migration(2, 3) {
                                override fun migrate(supportSQLiteDatabase: SupportSQLiteDatabase) {

                                }
                            })
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }

            return INSTANCE!!
        }

        fun create(context: Context, useInMemory: Boolean): AppDatabase {

            return if (useInMemory) getInMemory(context) else getInDatabase(context)
        }

        fun destroyInstance() {
            if (INSTANCE != null) INSTANCE!!.close()
            INSTANCE = null
        }
    }

}