package com.surkhojb.ktwallpapers.data

import androidx.paging.PagingSource
import com.surkhojb.ktwallpapers.data.network.WallpaperApi
import com.surkhojb.ktwallpapers.data.network.utils.errorByCode
import com.surkhojb.ktwallpapers.model.Wallpaper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception

const val INITIAL_PAGE = 1

class WallpaperDataSource(private val wallpaperApi: WallpaperApi): PagingSource<Int, Wallpaper>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Wallpaper> {
        try {
            val nextPage = params.key ?: INITIAL_PAGE
            val response = wallpaperApi.getCuratedWallpapers(nextPage,25)

            if(response.wallpapers.isNullOrEmpty()){
                return LoadResult.Error(throwable = Exception("Unknown error: We can't fetch wallpapers."))
            }

            return LoadResult.Page(
                prevKey = if(nextPage == 1) null else nextPage - 1,
                nextKey = nextPage + 1,
                data = response.wallpapers
            )

        }catch (ex: Exception){
            when(ex){
                is HttpException -> {
                   val throwable = ex.errorByCode(ex.code())
                   return LoadResult.Error(throwable = throwable)
                }
                else ->   return LoadResult.Error(throwable = ex)
            }

        }
    }
}