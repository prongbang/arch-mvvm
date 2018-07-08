package com.prongbang.archmvvm.dao

import android.arch.persistence.room.*

/**
 * Created by prongbang on 2/28/2018 AD.
 */

@Dao
abstract class BaseDao<in T> {

    /**
     * Insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(type: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertIfNotExists(entity: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun inserts(vararg entities: T): LongArray

    /**
     * Update
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(entity: T): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updates(vararg entities: T): Int

    /**
     * Delete
     */
    @Delete
    abstract fun delete(entity: T): Int

    @Delete
    abstract fun deletes(vararg entities: T): Int

}