package com.example.unsplashandroid.helper

import android.Manifest
import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class PermissionsHelper {
    private val permissions = listOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun checkAppPermissions(context:Context,callback: (result: Boolean) -> Unit) {
        Dexter.withActivity(context as Activity?)
            .withPermissions(permissions)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    report.let {
                        if (report.areAllPermissionsGranted()) {
                            callback(true)
                        } else {
                            Toast.makeText(context, "Please Grant Permissions to use the app", Toast.LENGTH_SHORT).show()
                            callback(false)
                        }
                    }
                }
                override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest?>?, token: PermissionToken?) {
                    token?.continuePermissionRequest()
                }
            }).withErrorListener{
                Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
            }.check()
    }
}