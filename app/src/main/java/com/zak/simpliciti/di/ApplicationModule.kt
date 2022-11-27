package com.zak.simpliciti.di

import com.zak.simpliciti.repository.MainRepository
import com.zak.simpliciti.repository.MainRepositoryImpl
import com.zak.simpliciti.service.GeoKeoApiService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val repositoryModule = module {
    single<MainRepository> {
        MainRepositoryImpl(get())
    }
}

val apiModule = module {
    fun provideUseApi(retrofit: Retrofit): GeoKeoApiService {
        return retrofit.create(GeoKeoApiService::class.java)
    }

    single { provideUseApi(get()) }
}

val retrofitModule = module {

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://geokeo.com/geocode/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    single { provideRetrofit() }
}