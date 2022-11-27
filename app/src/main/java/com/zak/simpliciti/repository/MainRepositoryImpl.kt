package com.zak.simpliciti.repository

import com.zak.simpliciti.model.GeoKeoResponse
import com.zak.simpliciti.service.GeoKeoApiService
import io.reactivex.rxjava3.core.Single


class MainRepositoryImpl(private val geoKeoApiService: GeoKeoApiService): MainRepository {

    override fun getReverseGeocoding(lat: Double, lng: Double): Single<GeoKeoResponse> {
        return geoKeoApiService.getReverseGeocoding(lat, lng).map {
            if (it.isSuccessful)
                it.body() ?: throw Exception("Body not found")
            else throw Exception("Not successful response")
        }
    }
}