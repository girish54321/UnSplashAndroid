package com.example.unsplashandroid.UI

import android.content.Context
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
import retrofit2.HttpException
import java.io.IOException

class SelectedCategoryActivity : AppCompatActivity(), PhotoRVAdapter.OnItemClickLister {
    private var TAG = "SelectedCategoryActivity"
    private var binding: ActivitySelectedCategoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedCategoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    private fun setUpImageList(categoryList: List<CategoryModal>) {
        val photoRVAdapter = PhotoRVAdapter(null,categoryList,this)
        binding?.gridView?.adapter = photoRVAdapter
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding?.gridView?.layoutManager = staggeredGridLayoutManager
        photoRVAdapter.notifyDataSetChanged()

    }

    private fun getRandomQuestion() {
        val context: Context = this
        LoadingScreen.displayLoadingWithText(context, "Please wait...", false)
        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getTopic(Constants.APK_KEY,"30")
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
                val data: List<CategoryModal> = response.body()!!
                LoadingScreen.hideLoading()
                if (data.isEmpty()) {
                    return@launchWhenCreated
                }
                setUpImageList(data)
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
        Log.e(TAG, "Response not successful")
    }
}