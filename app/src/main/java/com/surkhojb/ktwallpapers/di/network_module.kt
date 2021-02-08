package com.surkhojb.ktwallpapers.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.surkhojb.ktwallpapers.data.network.WallpaperApi
import com.surkhojb.ktwallpapers.data.network.utils.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.pexels.com/v1/"

val networkModule = module {
    single { createWallpaperApi() }
}

private fun createWallpaperApi(): WallpaperApi =  Retrofit.Builder().baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build().run {
            this.create(WallpaperApi::class.java)
        }


private val okHttpClient = OkHttpClient().newBuilder()
    .addInterceptor(getAuthInterceptor())
    .addInterceptor(loggingInterceptor())
    .build()

private fun getAuthInterceptor() = AuthInterceptor()
private fun loggingInterceptor() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)