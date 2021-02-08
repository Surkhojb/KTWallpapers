package com.surkhojb.ktwallpapers.data.network

import com.surkhojb.ktwallpapers.model.CuratedItems
import retrofit2.http.GET
import retrofit2.http.Query

interface WallpaperApi {

    @GET("curated")
    suspend fun getCuratedWallpapers( @Query("page") page: Int,
                                      @Query("per_page") itemsPerPage: Int): CuratedItems
}