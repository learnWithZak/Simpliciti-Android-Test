package com.zak.simpliciti.service

import com.zak.simpliciti.model.GeoKeoResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoKeoApiService {

    // todo: hide api key
    @GET("reverse.php?api=9da1fbafc8827213e41262a09afc3427")
    fun getReverseGeocoding(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double
    ): Single<Response<GeoKeoResponse>>

}