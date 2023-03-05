package com.example.unsplashandroid.UI

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myquizapp.helper.BasicAlertDialog
import com.example.myquizapp.helper.LoadingScreen
import com.example.unsplashandroid.Api.RetrofitInstance
import com.example.unsplashandroid.R
import com.example.unsplashandroid.adpter.PhotoRVAdapter
import com.example.unsplashandroid.const.Constants
import com.example.unsplashandroid.databinding.ActivitySelectedCategoryBinding
import com.example.unsplashandroid.modal.CategoryModal
import com.example.unsplashandroid.modal.UnPlashResponse
import com.squareup.picasso.Picasso
import retrofit2.HttpException
import java.io.IOException
import java.util.*

class SelectedCategoryActivity : AppCompatActivity(), PhotoRVAdapter.OnItemClickLister {
    private var TAG = "SelectedCategoryActivity"
    private var binding: ActivitySelectedCategoryBinding? = null
    var selectedCategory: CategoryModal? = null
    private var dataList: MutableList<UnPlashResponse> = mutableListOf()
    private var pageNumber: Int = 0
    private val photoRVAdapter = PhotoRVAdapter(dataList, null, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedCategoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)
        binding?.toolbar?.setNavigationOnClickListener {
            finish()
        }
        getUpComeingData()
        setUpImageList()
        setOnScrollEnd()
    }

    private fun setOnScrollEnd() {
        binding?.gridView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    getCategoryImages()
                }
            }
        })
    }

    private fun setUpImageList() {
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
            Picasso.get().load(selectedCategory?.coverPhoto?.urls?.regular)
                .placeholder(R.drawable.gray)
                .into(binding?.expandedImage);
            getCategoryImages()
            binding?.collapsingToolbar?.setCollapsedTitleTextColor(resources.getColor(R.color.white))
            binding?.collapsingToolbar?.setExpandedTitleColor(resources.getColor(R.color.white))
            binding?.toolbar?.setTitleTextColor(resources.getColor(R.color.white))
        }
    }

    private fun getCategoryImages() {
        if (LoadingScreen.isLoading){
            return
        }
        pageNumber += 1
        val context: Context = this
        LoadingScreen.displayLoadingWithText(context, "Please wait...", false)
        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getTopicImage(
                    "${Constants.BASE_URL}/topics/${selectedCategory?.id}/photos?client_id=${Constants.APK_KEY}&per_page=30&page=${pageNumber}"
                )
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
                dataList.addAll(response.body()!!)
                photoRVAdapter.notifyDataSetChanged()
                LoadingScreen.hideLoading()
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
        if (dataList.isEmpty()) {
            return
        }
        val data = dataList[position]
        val intent = Intent(this.baseContext!!, SelectedImageActivity::class.java)
        intent.putExtra("data", data)
        startActivity(intent)
    }
}