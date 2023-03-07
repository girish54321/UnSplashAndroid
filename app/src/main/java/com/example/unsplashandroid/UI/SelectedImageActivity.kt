package com.example.unsplashandroid.UI


import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment.DIRECTORY_PICTURES
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myquizapp.helper.AppAlertDialog
import com.example.unsplashandroid.R
import com.example.unsplashandroid.const.Constants
import com.example.unsplashandroid.const.LocalFile
import com.example.unsplashandroid.databinding.ActivitySelectedImageBinding
import com.example.unsplashandroid.helper.CreateFilePath
import com.example.unsplashandroid.helper.MyCustomDialog
import com.example.unsplashandroid.helper.PermissionsHelper
import com.example.unsplashandroid.helper.StatusBarUtil
import com.example.unsplashandroid.modal.UnPlashResponse
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.squareup.picasso.Picasso
import java.io.File
import java.util.*

class SelectedImageActivity : AppCompatActivity() {
    private var binding: ActivitySelectedImageBinding? = null
    var data: UnPlashResponse? = null
    lateinit var context: Context
    var localFile: LocalFile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedImageBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)
        binding?.toolbar?.setNavigationOnClickListener {
            finish()
        }
        context = this
        getUpComeingData()
        StatusBarUtil.setStatusBarColor(this, R.color.black)
        binding?.downlodBtn?.setOnClickListener {
            requestPermissions()
        }
    }

    private fun startDownload (url: String) {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(data?.description)
            .setDescription(data?.description)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setDestinationInExternalPublicDir(DIRECTORY_PICTURES,"${System.currentTimeMillis()}.jpeg") // Public Folder
        val de = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        de.enqueue(request)
    }


    private fun whereToSaveDilog (url: String){
        val customDialog = MyCustomDialog(this)
        customDialog.show()
        customDialog.binding.saveToApp.setOnClickListener {
            saveToAppSotrage(url)
            customDialog.dismiss()
        }
        customDialog.binding.saveToPhone.setOnClickListener {
            startDownload(url)
            customDialog.dismiss()
        }
    }

    private fun saveToAppSotrage (url: String) {
        val myFolder = CreateFilePath.createFilePath(this)
        Log.e("PATH", myFolder.absoluteFile.toString())
        if (myFolder.exists()) {
            val fileName = "${System.currentTimeMillis()}.jpeg"
            val file = File(myFolder.getAbsolutePath(), fileName)
            val request = DownloadManager.Request(Uri.parse(url))
                .setTitle("Downloading...${fileName}")
                .setDescription(data?.description)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverRoaming(false)
                .setDestinationUri(Uri.fromFile(file))
            val de = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            de.enqueue(request)
        }
    }


    private fun downloadImage() {
        if (!Constants.checkWriteExternalPermission(context) || !Constants.checkReadExternalPermission(context)) {
            requestPermissions()
            return
        }
        val builder = AlertDialog.Builder(this)
        val items = arrayOf("Raw", "Full", "Regular", "Small")
        val selectedList = ArrayList<Int>()
        builder.setTitle("Download Image")
        builder.setItems(items) { dialogInterface, which ->
            if (items[which] == "Raw") {
                data?.urls?.raw?.let { whereToSaveDilog(it) }
            } else if (items[which] == "Full") {
                data?.urls?.full?.let { whereToSaveDilog(it) }
            } else if (items[which] == "Regular") {
                data?.urls?.regular?.let { whereToSaveDilog(it) }
            } else if (items[which] == "Small") {
                data?.urls?.small?.let { whereToSaveDilog(it) }
            } else if (items[which] == "Thumb") {
                data?.urls?.thumb?.let { whereToSaveDilog(it) }
            } else if (items[which] == "Small_s3") {
                data?.urls?.smallS3?.let { whereToSaveDilog(it) }
            }
        }
        builder.setPositiveButton("Close") { dialogInterface, i ->
            val selectedStrings = ArrayList<String>()
            for (j in selectedList.indices) {
                selectedStrings.add(items[selectedList[j]])
            }
        }
        builder.show()
    }

    private fun getUpComeingData() {
        data = intent.getSerializableExtra("data") as UnPlashResponse?
        localFile = intent.getSerializableExtra("LocalFile") as LocalFile?
        if (data != null) {
            setupView()
        }
        if(localFile != null){
            binding?.downlodBtn?.visibility = View.GONE
            Picasso.get().load(localFile!!.file.absoluteFile)
                .into(binding?.selectedImageView);
            supportActionBar?.title = ""
        }
    }

    private fun setupView() {
        supportActionBar?.title = data?.description?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        Picasso.get().load(data!!.urls?.regular)
            .placeholder(R.color.black)
            .resize(
                data!!.width?.div(5) ?: 1,
                data!!.width?.div(5) ?: 1
            )
            .centerInside()
            .into(binding?.selectedImageView);
    }

    private fun requestPermissions () {
        if(Constants.checkReadExternalPermission(context) && Constants.checkWriteExternalPermission(context)){
            downloadImage()
            return
        }
        AppAlertDialog.displayLoadingWithText(
            context, "Permission required",
            "Permission required so we can store images on your storage.", false
        ) { dialogInterface, which ->
            PermissionsHelper().checkAppPermissions(this){
                if(it){
                    downloadImage()
                }
            }
        }
    }
}

