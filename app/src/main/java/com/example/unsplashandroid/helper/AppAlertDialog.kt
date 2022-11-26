package com.example.myquizapp.helper

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

object AppAlertDialog {
    var builder: AlertDialog.Builder? = null //obj
    var alertDialog: AlertDialog? = null
    fun displayLoadingWithText(context: Context?, title: String = "Error",subTitle: String, cancelable: Boolean,onOk: DialogInterface.OnClickListener) { // function -- context(parent (reference))
        builder = AlertDialog.Builder(context!!)
        builder!!.setTitle(title)
        builder!!.setMessage(subTitle)
        builder!!.setCancelable(cancelable)
        //performing positive action
//        builder!!.setPositiveButton("Yes"){dialogInterface, which ->
//            Toast.makeText(context,"clicked yes",Toast.LENGTH_LONG).show()
//        }
        builder!!.setPositiveButton("Yes",onOk)
        //performing cancel action
//        builder!!.setNeutralButton("Cancel"){dialogInterface , which ->
//            Toast.makeText(context,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
//        }
        //performing negative action
        builder!!.setNegativeButton("No"){dialogInterface, which ->
            Toast.makeText(context,"clicked No",Toast.LENGTH_LONG).show()
        }
        // Create the AlertDialog
        alertDialog = builder!!.create()
        // Set other dialog properties
        try {
            alertDialog!!.show()
        } catch (e: Exception) {
        }

    }

    fun hideAlertDialog() {
        try {
            if (builder != null) {
                alertDialog!!.dismiss()
            }
        } catch (e: Exception) {
        }
    }
}

object BasicAlertDialog {
    var builder: AlertDialog.Builder? = null //obj
    private var alertDialog: AlertDialog? = null
    fun displayBasicAlertDialog(context: Context?, title: String = "Error",subTitle: String, cancelable: Boolean,) {
        builder = AlertDialog.Builder(context!!)
        builder!!.setTitle(title)
        builder!!.setMessage(subTitle)
        builder!!.setCancelable(cancelable)
        builder!!.setPositiveButton("OK"){dialogInterface, which ->
        }
        alertDialog = builder!!.create()
        try {
            alertDialog!!.show()
        } catch (e: Exception) {
        }
    }
}