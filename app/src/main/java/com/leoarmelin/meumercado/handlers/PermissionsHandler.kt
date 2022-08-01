package com.leoarmelin.meumercado.handlers

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.accompanist.pager.ExperimentalPagerApi
import com.leoarmelin.meumercado.MainActivity

@ExperimentalPagerApi
open class PermissionsHandler(
    private val activity: MainActivity,
    private val listener: AccessListener
) {
    private val requestPermissionLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                listener.onGrantedCameraAccess()
                return@registerForActivityResult
            }

            listener.onNotGrantedCameraAccess()
        }

    interface AccessListener {
        fun onGrantedCameraAccess()
        fun onNotGrantedCameraAccess()
        fun onShowCameraUIAccess()
    }

    fun requestCameraPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            // Permission previously granted
            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.CAMERA
            ) -> {
                listener.onGrantedCameraAccess()
            }

            else -> listener.onShowCameraUIAccess()
        }
    }

    fun launchPermissionRequest() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }
}