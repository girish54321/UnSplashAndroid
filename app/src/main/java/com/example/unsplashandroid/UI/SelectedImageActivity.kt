package com.example.unsplashandroid.UI


import android.Manifest
import android.app.*
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment.DIRECTORY_PICTURES
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import com.example.myquizapp.helper.AppAlertDialog
import com.example.unsplashandroid.R
import com.example.unsplashandroid.const.Constants
import com.example.unsplashandroid.databinding.ActivitySelectedImageBinding
import com.example.unsplashandroid.modal.UnPlashResponse
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.squareup.picasso.Picasso
import okhttp3.internal.notify
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class SelectedImageActivity : AppCompatActivity() {
    private var binding: ActivitySelectedImageBinding? = null
    var data: UnPlashResponse? = null
    lateinit var context: Context
    private val permissions = listOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
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
        binding?.downlodBtn?.setOnClickListener {
//            requestPermissions()
            createNotification()
        }
    }

    private fun createNotification(){
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "CHANNEL_ID"
            val descriptionText = "getString(R.string.channel_description"
            val importance = IMPORTANCE_HIGH
            val channel = NotificationChannel(name, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            var builder = NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_search)
                .setContentTitle("textTitle")
                .setContentText("textContent")
            notificationManager.notify(1234, builder.build())
        }

    }

    private fun startDownload (url: String) {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(data?.description)
            .setDescription(data?.description)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
//            .setDestinationInExternalFilesDir(context,context.filesDir.toString(),"${System.currentTimeMillis()}.jpeg")
//            .setDestinationInExternalFilesDir(context,DIRECTORY_PICTURES,"${System.currentTimeMillis()}.jpeg") // Save it under package
            .setDestinationInExternalPublicDir(DIRECTORY_PICTURES,"${System.currentTimeMillis()}.jpeg") // Public Folder
        val de = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        de.enqueue(request)
    }

    private fun createFolder(): File {
        val file = File(getExternalFilesDir(null), "filename")//getExternalFilesDir
        if(!file.exists()){
            file.mkdir()
        }
        Log.e("FILE BATH",file.absolutePath)
        return file

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
                data?.urls?.raw?.let { startDownload(it) }
            } else if (items[which] == "Full") {
                data?.urls?.full?.let { startDownload(it) }
            } else if (items[which] == "Regular") {
                data?.urls?.regular?.let { startDownload(it) }
            } else if (items[which] == "Small") {
                data?.urls?.small?.let { startDownload(it) }
            } else if (items[which] == "Thumb") {
                data?.urls?.thumb?.let { startDownload(it) }
            } else if (items[which] == "Small_s3") {
                data?.urls?.smallS3?.let { startDownload(it) }
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
        if (data != null) {
            setupView()
        }
    }

    private fun setupView() {
        supportActionBar?.title = data?.description?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        Picasso.get().load(data!!.urls?.regular)
            .placeholder(R.drawable.gray)
            .into(binding?.selectedImageView);
    }

    private fun requestPermissions () {
        if(Constants.checkReadExternalPermission(context) && Constants.checkWriteExternalPermission(context)){
            downloadImage()
            return
        }
        AppAlertDialog.displayLoadingWithText(
            context, "Permission required",
            "More Info", false
        ) { dialogInterface, which ->
            Dexter.withActivity(context as Activity?)
                .withPermissions(permissions)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        report.let {
                            if (report.areAllPermissionsGranted()) {
                                Toast.makeText(context, "Permissions Granted", Toast.LENGTH_SHORT).show()
                                downloadImage()
                            } else {
                                Toast.makeText(context, "Please Grant Permissions to use the app", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest?>?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }
                }).withErrorListener{
                    Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
                }.check()
        }
    }
}

