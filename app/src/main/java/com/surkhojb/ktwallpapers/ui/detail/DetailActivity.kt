package com.surkhojb.ktwallpapers.ui.detail

import android.Manifest.permission
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.surkhojb.ktwallpapers.R
import com.surkhojb.ktwallpapers.databinding.ActivityDetailBinding
import com.surkhojb.ktwallpapers.model.Wallpaper
import com.surkhojb.ktwallpapers.ui.common.BaseActivity
import com.surkhojb.ktwallpapers.ui.common.utils.downloadImage
import com.surkhojb.ktwallpapers.ui.common.utils.loadUrlWithIndicator


const val WALLPAPER_ITEM_KEY = "wallpaper"
const val DOWNLOAD_PERMISSION_CODE = 1001

@RequiresApi(Build.VERSION_CODES.M)
class DetailActivity : BaseActivity<ActivityDetailBinding>(){

    override fun getViewBinding() = ActivityDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkDownloadPermission()

        val wallpaper = intent?.extras?.getSerializable(WALLPAPER_ITEM_KEY) as? Wallpaper
        wallpaper?.let {
            val imageView = binding?.imageView ?: return@let
            imageView.loadUrlWithIndicator(wallpaper.sizes.originalSize)
        }

        binding?.fabBack?.setOnClickListener { onBackPressed() }
        binding?.fabDownload?.setOnClickListener {
            wallpaper?.let { binding?.imageView?.downloadImage(it) }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults.size > 0 && grantResults[0] != PERMISSION_GRANTED){
            Toast.makeText(applicationContext,getString(R.string.permission_required_text),Toast.LENGTH_LONG).show()
            onBackPressed()
        }
    }


    private fun checkDownloadPermission(){
        if(ContextCompat.checkSelfPermission(applicationContext, permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED){
            requestPermissions(arrayOf(permission.WRITE_EXTERNAL_STORAGE),DOWNLOAD_PERMISSION_CODE)
        }
    }
}