package com.example.unsplashandroid.helper

import android.app.Activity
import android.os.Build
import android.support.annotation.ColorRes
import androidx.core.content.ContextCompat

object StatusBarUtil {
    fun setStatusBarColor(activity: Activity, @ColorRes colorResId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.statusBarColor = ContextCompat.getColor(activity, colorResId)
        }
    }

}
