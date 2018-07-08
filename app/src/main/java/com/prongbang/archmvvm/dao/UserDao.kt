package com.prongbang.archmvvm.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import com.prongbang.archmvvm.vo.User

@Dao
abstract class UserDao : BaseDao<User>() {

    @Transaction
    @Query("SELECT * FROM user")
    abstract fun getUsers(): LiveData<List<User>>

}