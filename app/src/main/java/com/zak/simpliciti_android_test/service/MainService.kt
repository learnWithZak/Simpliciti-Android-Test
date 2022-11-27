package com.zak.simpliciti_android_test.service

import com.zak.simpliciti_android_test.model.GeoKeoResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MainService {

    // todo: hide api key
    @GET("reverse.php?api=9da1fbafc8827213e41262a09afc3427")
    fun getReverseGeocoding(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double
    ): Single<Response<GeoKeoResponse>>

    companion object {
        val instance: MainService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://geokeo.com/geocode/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()

            retrofit.create(MainService::class.java)
        }
    }

}