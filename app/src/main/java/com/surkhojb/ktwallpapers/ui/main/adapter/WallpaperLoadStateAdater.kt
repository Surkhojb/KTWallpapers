package com.surkhojb.ktwallpapers.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.surkhojb.ktwallpapers.R
import com.surkhojb.ktwallpapers.databinding.RowWallpaperStateViewItemBinding

class WallpaperLoadStateAdater(private val actionRetry: () -> Unit): LoadStateAdapter<WallpaperLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: WallpaperLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): WallpaperLoadStateViewHolder {

        return WallpaperLoadStateViewHolder.build(parent,actionRetry)
    }
}

class WallpaperLoadStateViewHolder(private val binding: RowWallpaperStateViewItemBinding,
                                   actionRetry: () -> Unit): RecyclerView.ViewHolder(binding.root){

    init {
        binding.btnRetryItem.setOnClickListener { actionRetry.invoke() }
    }

    fun bind(state: LoadState){
        if(state is LoadState.Error){
            binding.errorMsgItem.text = state.error.localizedMessage
        }

        binding.loadingMoreItem.isVisible = state is LoadState.Loading
        binding.btnRetryItem.isVisible = state !is LoadState.Loading
        binding.errorMsgItem.isVisible = state !is LoadState.Loading
    }

    companion object{
        fun build(parent: ViewGroup, actionRetry: () -> Unit): WallpaperLoadStateViewHolder{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_wallpaper_state_view_item,parent,false)
            val binding = RowWallpaperStateViewItemBinding.bind(view)
            return WallpaperLoadStateViewHolder(binding,actionRetry)
        }
    }

}