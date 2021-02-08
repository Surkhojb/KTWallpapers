package com.surkhojb.ktwallpapers.ui.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.Job

/*Base activity to create and destroy common attributes*/
abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity() {
    var job: Job? = null

    var binding: VB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getViewBinding()

        setContentView(binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
        binding = null
    }

    abstract fun getViewBinding(): VB
}