package com.example.unsplashandroid.helper

import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.annotation.ColorRes
import android.util.Log
import androidx.core.content.ContextCompat
import java.io.File

object StatusBarUtil {
    fun setStatusBarColor(activity: Activity, @ColorRes colorResId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.statusBarColor = ContextCompat.getColor(activity, colorResId)
        }
    }

}

object CreateFilePath {
    fun createFilePath(context: Context): File {
        val myFolder = File(context.getExternalFilesDir(null), "")
        if (!myFolder.exists()) {
            myFolder.mkdirs()
            Log.e("PATH", myFolder.absoluteFile.toString())
        }
        Log.e("PATH", myFolder.absoluteFile.toString())
        return myFolder
    }
}
