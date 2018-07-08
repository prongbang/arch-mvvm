package com.prongbang.archmvvm.vo

import com.google.gson.annotations.SerializedName


/**
 * Created by prongbang on 3/19/2018 AD.
 */

data class ResponseErrorBody<T>(
		@SerializedName("code") var code: String,
		@SerializedName("message") var message: String,
		@SerializedName("data") var data: T
)