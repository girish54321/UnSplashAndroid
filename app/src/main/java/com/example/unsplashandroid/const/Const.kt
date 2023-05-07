package com.example.unsplashandroid.const

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import java.io.File
import java.io.Serializable

object Constants {
    const val APK_KEY: String = "jRBzm2zUw2eoIPSHZxLvY_hnSh0P8J91P2THDay4y8w"
    const val ORDER_BY_LATEST: String = "latest"
    const val ORDER_BY_POPULAR: String = "popular"
    const val BASE_URL: String = "https://api.unsplash.com/"
    const val TOKAN: String = "TOKAN"
    enum class UrlFileNmaes {
        Raw, Full, Regular, Small, Thumb, Small_s3
    }
     fun checkWriteExternalPermission(context: Context): Boolean {
        val permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val res: Int = context.checkCallingOrSelfPermission(permission)
        return res == PackageManager.PERMISSION_GRANTED
    }

    fun checkReadExternalPermission(context: Context): Boolean {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        val res: Int = context.checkCallingOrSelfPermission(permission)
        return res == PackageManager.PERMISSION_GRANTED
    }
}

data class LocalFile(val fileName: String, val filePath: String,val file:File): Serializable
