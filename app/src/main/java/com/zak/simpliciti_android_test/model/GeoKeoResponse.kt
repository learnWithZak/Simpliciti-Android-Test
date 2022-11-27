package com.zak.simpliciti_android_test.model

import com.google.gson.annotations.SerializedName

data class GeoKeoResponse(
    val results: List<GeoKeoResult>,
    val credits: String,
    val status: String
) {
    data class GeoKeoResult(
        val type: String,
        @SerializedName("formatted_address") val formattedAddress: String,
        val distance: String
    )
}