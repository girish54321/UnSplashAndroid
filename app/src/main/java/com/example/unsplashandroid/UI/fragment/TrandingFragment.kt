package com.example.unsplashandroid.UI.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myquizapp.helper.BasicAlertDialog
import com.example.myquizapp.helper.LoadingScreen
import com.example.unsplashandroid.Api.RetrofitInstance
import com.example.unsplashandroid.UI.SelectedImageActivity
import com.example.unsplashandroid.adpter.PhotoRVAdapter
import com.example.unsplashandroid.const.Constants
import com.example.unsplashandroid.databinding.FragmentHomeBinding
import com.example.unsplashandroid.modal.UnPlashResponse
import retrofit2.HttpException
import java.io.IOException

class TrandingFragment : Fragment(), PhotoRVAdapter.OnItemClickLister {
    private val TAG = "TradingFragment"
    private val binding get() = _binding!!
    private var _binding: FragmentHomeBinding? = null
    private var dataList: MutableList<UnPlashResponse?>? = mutableListOf()
    private var pageNumber: Int = 0
    val photoRVAdapter = PhotoRVAdapter(dataList,null,this)
    var isLoading: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setUpImageList()
        getTradingImage()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnScrollEnd() {
        binding.gridView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    getTradingImage()
                }
            }
        })
    }

    private fun setUpImageList() {
        binding.gridView.adapter = photoRVAdapter
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.gridView.layoutManager = staggeredGridLayoutManager
        photoRVAdapter.notifyDataSetChanged()
        setOnScrollEnd()
    }

    private fun getTradingImage() {
       if (isLoading){
            return
        }
        toggleIsLoading()
        pageNumber += 1
        LoadingScreen.displayLoadingWithText(activity, "Please wait...", false)
        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getPhotos(Constants.APK_KEY, Constants.ORDER_BY_POPULAR, "15",pageNumber.toString())
            } catch (e: IOException) {
                LoadingScreen.hideLoading()
                toggleIsLoading()
                BasicAlertDialog.displayBasicAlertDialog(
                    activity,
                    "Error",
                    "IOException, you might not have internet connection",
                    false
                )
                Log.e(TAG, "IOException, you might not have internet connection")
                return@launchWhenCreated
            } catch (e: HttpException) {
                LoadingScreen.hideLoading()
                toggleIsLoading()
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
                val data: List<UnPlashResponse> = response.body()!!
                LoadingScreen.hideLoading()
                toggleIsLoading()
                dataList?.addAll(data)
                photoRVAdapter.notifyDataSetChanged()
                return@launchWhenCreated
            } else {
                LoadingScreen.hideLoading()
                toggleIsLoading()
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

    private fun toggleIsLoading() {
        isLoading = !isLoading
    }

    override fun onItemClick(position: Int) {
        if (dataList.isNullOrEmpty()) {
            return
        }
        val data = dataList!![position]
        val intent = Intent(this.activity?.baseContext!!, SelectedImageActivity::class.java)
        intent.putExtra("data", data)
        startActivity(intent)
    }
}