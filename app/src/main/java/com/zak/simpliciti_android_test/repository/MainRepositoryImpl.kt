package com.zak.simpliciti_android_test.repository

import com.zak.simpliciti_android_test.model.GeoKeoResponse
import com.zak.simpliciti_android_test.service.MainService
import io.reactivex.rxjava3.core.Single


class MainRepositoryImpl(private val mainService: MainService): MainRepository {

    override fun getReverseGeocoding(lat: Double, lng: Double): Single<GeoKeoResponse> {
        return mainService.getReverseGeocoding(lat, lng).map {
            if (it.isSuccessful)
                it.body() ?: throw Exception("Body not found")
            else throw Exception("Not successful response")
        }
    }
}