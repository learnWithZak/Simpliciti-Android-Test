package com.zak.simpliciti_android_test.repository

import com.zak.simpliciti_android_test.model.GeoKeoResponse
import io.reactivex.rxjava3.core.Single

interface MainRepository {
    fun getReverseGeocoding(lat: Double, lng: Double): Single<GeoKeoResponse>
}
