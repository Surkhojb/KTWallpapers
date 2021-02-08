package com.surkhojb.ktwallpapers.ui.common.utils

import android.app.DownloadManager
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.surkhojb.ktwallpapers.model.Wallpaper
import java.io.File
import java.lang.Exception

private val DOWNLOAD_DESCRIPTION = "Downloaded using KTWallpapers App."
private val DOWNLOAD_EXISTS = "Image already downloaded, check in your Download folder"
private val DOWNLOAD_MIME_TYPE = "image/png"
private val DOWNLOAD_EXTENSION = ".png"

fun ImageView.loadUrlWithIndicator(url: String){
    val placeholderIndicator = getCircularIndicator(this.context)

    Glide.with(this)
        .load(url)
        .placeholder(placeholderIndicator)
        .into(this)
}


fun ImageView.downloadImage(wallpaper: Wallpaper){
    val url = wallpaper.sizes.originalSize
    val fileName = "IMG_${wallpaper.author}_${wallpaper.id}"

    if(imageExists(fileName)){
        Toast.makeText(this.context,DOWNLOAD_EXISTS,Toast.LENGTH_LONG).show()
        return
    }

    val request = buildDownloadRequest(url, fileName)
    try {
        val downloadManager = this.context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }catch (ex: Exception){
        Log.e("DownloadManager", ex.localizedMessage)
    }
}

private fun buildDownloadRequest(url: String, fileName: String) = DownloadManager.Request(Uri.parse(url))
        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        .setTitle(fileName)
        .setDescription(DOWNLOAD_DESCRIPTION)
        .setMimeType(DOWNLOAD_MIME_TYPE)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(false)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$fileName$DOWNLOAD_EXTENSION")

private fun imageExists(fileName: String): Boolean{
    val extStore = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    return File(extStore.absolutePath + "/$fileName$DOWNLOAD_EXTENSION").exists()
}

private fun getCircularIndicator(context: Context): CircularProgressDrawable{
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    return circularProgressDrawable
}