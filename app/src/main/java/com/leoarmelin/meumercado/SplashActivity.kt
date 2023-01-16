package com.leoarmelin.meumercado

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@OptIn(ExperimentalPagerApi::class)
class SplashActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
        }
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                delay(300)

                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}