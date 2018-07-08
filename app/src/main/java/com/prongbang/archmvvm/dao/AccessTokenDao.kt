package com.prongbang.archmvvm.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import com.prongbang.archmvvm.vo.AccessToken

@Dao
abstract class AccessTokenDao : BaseDao<AccessToken>() {

    @Transaction
    @Query("SELECT * FROM access_token")
    abstract fun getAll(): LiveData<List<AccessToken>>

    @Transaction
    @Query("SELECT * FROM access_token ORDER BY userId LIMIT 1")
    abstract fun getFirst(): AccessToken?

    @Transaction
    @Query("SELECT * FROM access_token ORDER BY userId DESC LIMIT 1")
    abstract fun getLast(): AccessToken?

    @Query("DELETE FROM access_token")
    abstract fun deleteAll(): Int

}