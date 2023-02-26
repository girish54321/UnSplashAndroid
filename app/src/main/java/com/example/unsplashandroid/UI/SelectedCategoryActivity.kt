package com.example.unsplashandroid.UI

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myquizapp.helper.BasicAlertDialog
import com.example.myquizapp.helper.LoadingScreen
import com.example.unsplashandroid.Api.RetrofitInstance
import com.example.unsplashandroid.adpter.PhotoRVAdapter
import com.example.unsplashandroid.const.Constants
import com.example.unsplashandroid.databinding.ActivitySelectedCategoryBinding
import com.example.unsplashandroid.modal.CategoryModal
import com.example.unsplashandroid.modal.UnPlashResponse
import retrofit2.HttpException
import java.io.IOException
import java.util.*

class SelectedCategoryActivity : AppCompatActivity(), PhotoRVAdapter.OnItemClickLister {
    private var TAG = "SelectedCategoryActivity"
    private var binding: ActivitySelectedCategoryBinding? = null
    var selectedCategory: CategoryModal? = null
    var datalist: List<UnPlashResponse?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedCategoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)
        binding?.toolbar?.setNavigationOnClickListener {
            finish()
        }
        getUpComeingData()
    }

    private fun setUpImageList() {
        val photoRVAdapter = PhotoRVAdapter(datalist,null,this)
        binding?.gridView?.adapter = photoRVAdapter
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding?.gridView?.layoutManager = staggeredGridLayoutManager
        photoRVAdapter.notifyDataSetChanged()

    }

    private fun getUpComeingData() {
        selectedCategory = intent.getSerializableExtra("data") as CategoryModal?
        if (selectedCategory != null) {
            supportActionBar?.title = selectedCategory?.title?.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
            getRandomQuestion()
        }
    }

    private fun getRandomQuestion() {
        selectedCategory!!.id?.let { Log.e("Data", it) }
        val context: Context = this
        LoadingScreen.displayLoadingWithText(context, "Please wait...", false)
        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getTopicImage("${Constants.BASE_URL}/topics/${selectedCategory?.id}/photos?client_id=${Constants.APK_KEY}&per_page=30")
            } catch (e: IOException) {
                LoadingScreen.hideLoading()
                BasicAlertDialog.displayBasicAlertDialog(
                    context,
                    "Error",
                    "IOException, you might not have internet connection",
                    false
                )
                Log.e(TAG, "IOException, you might not have internet connection")
                return@launchWhenCreated
            } catch (e: HttpException) {
                LoadingScreen.hideLoading()
                BasicAlertDialog.displayBasicAlertDialog(
                    context,
                    "Error",
                    "HttpException, unexpected response",
                    false
                )
                Log.e(TAG, "HttpException, unexpected response")
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                datalist = response.body()!!
                LoadingScreen.hideLoading()
                setUpImageList()
            } else {
                LoadingScreen.hideLoading()
                BasicAlertDialog.displayBasicAlertDialog(
                    context,
                    "Error",
                    "Response not successful",
                    false
                )
                Log.e(TAG, "Response not successful")
            }
        }
    }

    override fun onItemClick(position: Int) {
        if (datalist.isNullOrEmpty()) {
            return
        }
        val data = datalist!![position]
        val intent = Intent(this.baseContext!!, SelectedImageActivity::class.java)
        intent.putExtra("data", data)
        startActivity(intent)
    }
}