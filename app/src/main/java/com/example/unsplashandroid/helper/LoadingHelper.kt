package com.example.myquizapp.helper
import android.app.ProgressDialog
import android.content.Context

object LoadingScreen {
    var dialog: ProgressDialog? = null //obj
    fun displayLoadingWithText(context: Context?, text: String?, cancelable: Boolean) { // function -- context(parent (reference))
        dialog = ProgressDialog(context!!)
        dialog!!.setTitle(text)
        dialog!!.setMessage("Please wait")
        dialog!!.setCancelable(cancelable)
        try {
            dialog!!.show()
        } catch (e: Exception) {
        }

    }

    fun hideLoading() {
        try {
            if (dialog != null) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) {
        }
    }
}