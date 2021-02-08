package com.surkhojb.ktwallpapers.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.surkhojb.ktwallpapers.R
import com.surkhojb.ktwallpapers.model.Wallpaper


class WallpaperAdapter(private val listener: (Wallpaper) -> Unit): PagingDataAdapter<Wallpaper, WallpaperAdapter.WallpaperViewHolder>(WallpaperComparator){
    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_wallpaper_view_item,parent,false)
        return WallpaperViewHolder(view)
    }

    inner class WallpaperViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val image = view.findViewById<ImageView>(R.id.image_item)
        private val author = view.findViewById<TextView>(R.id.author_name_item)

        fun bind(item: Wallpaper?){
            author.text = String.format("Author: %s",item?.author ?: "Uknown")
            Glide.with(image).load(item?.sizes?.landscapeSize).into(image)
            image.setOnClickListener { item?.let(listener) }
        }
    }

}

object WallpaperComparator : DiffUtil.ItemCallback<Wallpaper>() {
    override fun areItemsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
        return oldItem == newItem
    }
}