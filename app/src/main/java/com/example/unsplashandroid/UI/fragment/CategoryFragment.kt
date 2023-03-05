package com.example.unsplashandroid.UI.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myquizapp.helper.BasicAlertDialog
import com.example.myquizapp.helper.LoadingScreen
import com.example.unsplashandroid.Api.RetrofitInstance
import com.example.unsplashandroid.UI.SelectedCategoryActivity
import com.example.unsplashandroid.UI.SelectedImageActivity
import com.example.unsplashandroid.adpter.PhotoRVAdapter
import com.example.unsplashandroid.const.Constants
import com.example.unsplashandroid.databinding.FragmentCategoryBinding
import com.example.unsplashandroid.modal.CategoryModal
import com.example.unsplashandroid.modal.UnPlashResponse
import retrofit2.HttpException
import java.io.IOException

class CategoryFragment : Fragment() ,PhotoRVAdapter.OnItemClickLister {
    private val TAG = "CategoryFragment"
    private val binding get() = _binding!!
    private var _binding: FragmentCategoryBinding? = null
    var cateGoryList: List<CategoryModal?>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        getRandomQuestion()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpImageList(categoryList: List<CategoryModal?>?) {
        val photoRVAdapter = PhotoRVAdapter(null,categoryList,this)
        binding.gridView.adapter = photoRVAdapter
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.gridView.layoutManager = staggeredGridLayoutManager
        photoRVAdapter.notifyDataSetChanged()

    }

    private fun getRandomQuestion() {
        LoadingScreen.displayLoadingWithText(activity, "Please wait...", false)
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
                cateGoryList = response.body()!!
                LoadingScreen.hideLoading()
                if (cateGoryList?.isEmpty() == true) {
                    return@launchWhenCreated
                }
                setUpImageList(cateGoryList)
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
        if (cateGoryList.isNullOrEmpty()) {
            return
        }
        val data = cateGoryList!![position]
        val intent = Intent(this.activity?.baseContext!!, SelectedCategoryActivity::class.java)
        intent.putExtra("data", data)
        startActivity(intent)
    }
}