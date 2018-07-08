package com.prongbang.archmvvm.vo

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by prongbang on 3/8/2018 AD.
 */
@Entity(tableName = "access_token")
open class AccessToken(
        @PrimaryKey
        @ColumnInfo(name = "userId")
        @SerializedName("userId")
        var userId: String,

        @ColumnInfo(name = "access_token")
        @SerializedName("access_token")
        var accessToken: String?,

        @ColumnInfo(name = "refresh_token")
        @SerializedName("refresh_token")
        var refreshToken: String?,

        @ColumnInfo(name = "token_type")
        @SerializedName("token_type")
        var tokenType: String?,

        @ColumnInfo(name = "expires_in")
        @SerializedName("expires_in")
        var expiresIn: Long
) {

    @Ignore
    constructor() : this("", "", "", "", 0)

}