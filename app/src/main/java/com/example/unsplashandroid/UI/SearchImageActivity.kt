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
import com.example.unsplashandroid.databinding.ActivitySearchImageBinding
import com.example.unsplashandroid.modal.CategoryModal
import com.example.unsplashandroid.modal.UnPlashResponse
import retrofit2.HttpException
import java.io.IOException

class SearchImageActivity : AppCompatActivity(), PhotoRVAdapter.OnItemClickLister {
    private var TAG = "SearchImageActivity"
    private var binding: ActivitySearchImageBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchImageBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        getRandomQuestion()
    }

    private fun setUpImageList(photoList: List<UnPlashResponse>) {
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding?.searchgridView?.layoutManager = staggeredGridLayoutManager
        val photoRVAdapter = PhotoRVAdapter(photoList,null,this)
        binding?.searchgridView?.adapter = photoRVAdapter
        photoRVAdapter.notifyDataSetChanged()
    }

    private fun getRandomQuestion() {
        val context: Context = this
        LoadingScreen.displayLoadingWithText(context, "Please wait...", false)
        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getSearchImage(Constants.APK_KEY,"30","Car")
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
                val data: List<UnPlashResponse> = response.body()!!.results
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