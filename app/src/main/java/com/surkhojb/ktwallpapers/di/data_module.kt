package com.surkhojb.ktwallpapers.di

import com.surkhojb.ktwallpapers.data.WallpaperDataSource
import com.surkhojb.ktwallpapers.data.network.WallpaperRepository
import org.koin.dsl.module

val dataModule = module {
    single { WallpaperDataSource(get()) }
    single { WallpaperRepository(get())}
}