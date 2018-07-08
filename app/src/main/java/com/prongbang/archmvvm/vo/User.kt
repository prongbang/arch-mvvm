package com.prongbang.archmvvm.vo

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
        @PrimaryKey
        @ColumnInfo(name = "id")
        @SerializedName("id")
        var id: Int = 0,
        @ColumnInfo(name = "gists_url")
        @SerializedName("gists_url")
        var gistsUrl: String = "",
        @ColumnInfo(name = "repos_url")
        @SerializedName("repos_url")
        var reposUrl: String = "",
        @ColumnInfo(name = "following_url")
        @SerializedName("following_url")
        var followingUrl: String = "",
        @ColumnInfo(name = "starred_url")
        @SerializedName("starred_url")
        var starredUrl: String = "",
        @ColumnInfo(name = "login")
        @SerializedName("login")
        var login: String = "",
        @ColumnInfo(name = "followers_url")
        @SerializedName("followers_url")
        var followersUrl: String = "",
        @ColumnInfo(name = "type")
        @SerializedName("type")
        var type: String = "",
        @ColumnInfo(name = "url")
        @SerializedName("url")
        var url: String = "",
        @ColumnInfo(name = "subscriptions_url")
        @SerializedName("subscriptions_url")
        var subscriptionsUrl: String = "",
        @ColumnInfo(name = "received_events_url")
        @SerializedName("received_events_url")
        var receivedEventsUrl: String = "",
        @ColumnInfo(name = "avatar_url")
        @SerializedName("avatar_url")
        var avatarUrl: String = "",
        @ColumnInfo(name = "events_url")
        @SerializedName("events_url")
        var eventsUrl: String = "",
        @ColumnInfo(name = "html_url")
        @SerializedName("html_url")
        var htmlUrl: String = "",
        @ColumnInfo(name = "site_admin")
        @SerializedName("site_admin")
        var siteAdmin: Boolean = false,
        @ColumnInfo(name = "gravatar_id")
        @SerializedName("gravatar_id")
        var gravatarId: String = "",
        @ColumnInfo(name = "node_id")
        @SerializedName("node_id")
        var nodeId: String = "",
        @ColumnInfo(name = "organizations_url")
        @SerializedName("organizations_url")
        var organizationsUrl: String = "") {
    @Ignore
    constructor() : this(0, "", "", "", "", "", "", "", "", "", "", "," +
            "", "", "", false, "", ",", "")
}