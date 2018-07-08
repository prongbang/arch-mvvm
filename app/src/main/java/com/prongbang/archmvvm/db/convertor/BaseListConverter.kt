package com.prongbang.archmvvm.db.convertor

import android.arch.persistence.room.TypeConverter
import com.google.gson.GsonBuilder

/**
 * Created by prongbang on 7/08/2018 AD.
 */
abstract class BaseListConverter<T> {

    @TypeConverter
    abstract fun fromString(value: String): ArrayList<T>

    @TypeConverter
    fun fromArrayList(list: ArrayList<T>): String {
        val json = GsonBuilder().create().toJson(list)
        return json ?: ""
    }

}