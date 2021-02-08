package com.surkhojb.ktwallpapers.di

import com.surkhojb.ktwallpapers.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { MainViewModel(get()) }
}