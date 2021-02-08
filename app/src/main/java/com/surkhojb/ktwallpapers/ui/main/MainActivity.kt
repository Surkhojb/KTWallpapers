package com.surkhojb.ktwallpapers.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.surkhojb.ktwallpapers.R
import com.surkhojb.ktwallpapers.databinding.ActivityMainBinding
import com.surkhojb.ktwallpapers.model.Wallpaper
import com.surkhojb.ktwallpapers.ui.common.BaseActivity
import com.surkhojb.ktwallpapers.ui.detail.DetailActivity
import com.surkhojb.ktwallpapers.ui.detail.WALLPAPER_ITEM_KEY
import com.surkhojb.ktwallpapers.ui.main.adapter.WallpaperAdapter
import com.surkhojb.ktwallpapers.ui.main.adapter.WallpaperLoadStateAdater
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {

    val viewModel: MainViewModel by viewModel()
    private val adapter: WallpaperAdapter by lazy { WallpaperAdapter(::navigateToDetail) }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        getWallPapers()
    }

    private fun getWallPapers(){
       job?.cancel()
       job =  lifecycleScope.launch {
            viewModel.getWallpapers().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initView(){
        binding?.listWallpapers?.layoutManager = LinearLayoutManager(applicationContext)
        binding?.listWallpapers?.adapter = adapter.withLoadStateFooter(
            footer = WallpaperLoadStateAdater { adapter.retry() }
        )

        adapter.addLoadStateListener {
            binding?.listWallpapers?.isVisible = it.source.refresh is LoadState.NotLoading
            binding?.loadingIndicator?.isVisible = it.refresh is LoadState.Loading

            showError(it)

        }
    }

    private fun navigateToDetail(wallpaper: Wallpaper){
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(WALLPAPER_ITEM_KEY, wallpaper)
        }
        startActivity(intent)
    }

    private fun showError(loadState: CombinedLoadStates){
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error
            ?: loadState.append as? LoadState.Error
            ?: loadState.prepend as? LoadState.Error

        errorState?.let {
            Toast.makeText(
                this,
                "\uD83D\uDE28 Wooops ${it.error}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}