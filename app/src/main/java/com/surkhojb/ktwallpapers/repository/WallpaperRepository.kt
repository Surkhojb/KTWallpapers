package com.surkhojb.ktwallpapers.data.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.surkhojb.ktwallpapers.data.WallpaperDataSource
import com.surkhojb.ktwallpapers.model.Wallpaper
import kotlinx.coroutines.flow.Flow

class WallpaperRepository(private val dataSource: WallpaperDataSource) {

    suspend fun getWallpapers(): Flow<PagingData<Wallpaper>>{
        return Pager(
            config = PagingConfig(pageSize = 25, enablePlaceholders = false),
            pagingSourceFactory = { dataSource }
        ).flow
    }

}