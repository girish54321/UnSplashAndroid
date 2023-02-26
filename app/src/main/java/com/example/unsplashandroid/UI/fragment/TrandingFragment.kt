package com.example.unsplashandroid.UI.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
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
    private val TAG = "GeeksFragment"
    private val binding get() = _binding!!
    private var _binding: FragmentHomeBinding? = null
    private var dataList: List<UnPlashResponse?>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        getRandomQuestion()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpImageList() {
        val photoRVAdapter = PhotoRVAdapter(dataList,null,this)
        binding.gridView.adapter = photoRVAdapter
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.gridView.layoutManager = staggeredGridLayoutManager
        photoRVAdapter.notifyDataSetChanged()

    }

    private fun getRandomQuestion() {
        val context: Context = this.activity?.baseContext!!
        LoadingScreen.displayLoadingWithText(context, "Please wait...", false)
        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getPhotos(Constants.APK_KEY, Constants.ORDER_BY_POPULAR, "30")
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
                val data: List<UnPlashResponse> = response.body()!!
                LoadingScreen.hideLoading()
                dataList = data
                if (data.isEmpty()) {
                    return@launchWhenCreated
                }
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
        if (dataList.isNullOrEmpty()) {
            return
        }
        val data = dataList!![position]
        val intent = Intent(this.activity?.baseContext!!, SelectedImageActivity::class.java)
        intent.putExtra("data", data)
        startActivity(intent)
    }
}