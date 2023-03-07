package com.example.unsplashandroid.UI.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.unsplashandroid.UI.SelectedImageActivity
import com.example.unsplashandroid.adpter.PhotoRVAdapter
import com.example.unsplashandroid.const.LocalFile
import com.example.unsplashandroid.databinding.FragmentSavedImageBinding
import com.example.unsplashandroid.helper.CreateFilePath
import com.example.unsplashandroid.helper.PermissionsHelper
import java.io.File


class SavedImageFragment : Fragment(), PhotoRVAdapter.OnItemClickLister {
    private val TAG = "SavedImageFragment"
    private val binding get() = _binding!!
    private var _binding: FragmentSavedImageBinding? = null
    private var dataList: MutableList<File?>? = mutableListOf()
    private val photoRVAdapter = PhotoRVAdapter(null, null,dataList, this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSaveImageList()
        getLocalImage()
        setOnPullToRefreash()
    }

    private fun setOnPullToRefreash() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            getLocalImage()
        }
    }

    override fun onItemClick(position: Int) {
        if (dataList?.isEmpty() == true) {
            return
        }
        val data = dataList?.get(position)
        var localImage: LocalFile = LocalFile(fileName = data!!.name, filePath = data.absolutePath, file = data)
        val intent = Intent(this.activity?.baseContext!!, SelectedImageActivity::class.java)
        intent.putExtra("LocalFile", localImage)
        startActivity(intent)
    }

    private fun setupSaveImageList() {
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.saveImageList.layoutManager = staggeredGridLayoutManager
        binding.saveImageList.adapter = photoRVAdapter
        photoRVAdapter.notifyDataSetChanged()
    }

    private fun getLocalImage(){
        activity?.let {
            PermissionsHelper().checkAppPermissions(it){
                if(it){
                    dataList?.clear()
                    binding.swipeRefreshLayout.isRefreshing = true
                    val directory = activity?.let { CreateFilePath.createFilePath(it) }
                    val files = directory?.listFiles()
                    if (files != null) {
                        for (file in files) {
                            if (file.isFile) {
                                dataList?.add(file)
                                println(file.absolutePath)
                            }
                        }
                    }
                    photoRVAdapter.notifyDataSetChanged()
                    Handler().postDelayed({
                        binding.swipeRefreshLayout.isRefreshing = false
                    }, 1000)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}