package com.zak.simpliciti.repository

import com.zak.simpliciti.model.GeoKeoResponse
import io.reactivex.rxjava3.core.Single

interface MainRepository {
    fun getReverseGeocoding(lat: Double, lng: Double): Single<GeoKeoResponse>
}
