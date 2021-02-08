package com.surkhojb.ktwallpapers.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.surkhojb.ktwallpapers.data.network.WallpaperRepository
import com.surkhojb.ktwallpapers.model.Wallpaper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val wallpaperRepository: WallpaperRepository): ViewModel() {

    suspend fun getWallpapers(): Flow<PagingData<Wallpaper>> =
        wallpaperRepository.getWallpapers().cachedIn(viewModelScope)
}