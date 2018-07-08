package com.prongbang.archmvvm.db.convertor

import android.arch.persistence.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.ParameterizedType

/**
 * Created by prongbang on 3/28/2018 AD.
 */
class StringListConverter: BaseListConverter<String>() {

    @TypeConverter
    override fun fromString(value: String): ArrayList<String> {
        val typeToken = object : TypeToken<ArrayList<String>>() {}
        val type = typeToken.type as ParameterizedType
        val list = GsonBuilder().create().fromJson<ArrayList<String>>(value, type)
        return list ?: ArrayList()
    }

}