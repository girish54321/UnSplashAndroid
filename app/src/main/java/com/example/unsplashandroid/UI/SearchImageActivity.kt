package com.example.unsplashandroid.UI

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myquizapp.helper.BasicAlertDialog
import com.example.myquizapp.helper.LoadingScreen
import com.example.unsplashandroid.Api.RetrofitInstance
import com.example.unsplashandroid.R
import com.example.unsplashandroid.adpter.PhotoRVAdapter
import com.example.unsplashandroid.const.Constants
import com.example.unsplashandroid.databinding.ActivitySearchImageBinding
import com.example.unsplashandroid.modal.UnPlashResponse
import retrofit2.HttpException
import java.io.IOException
import java.util.*

class SearchImageActivity : AppCompatActivity(), PhotoRVAdapter.OnItemClickLister {
    private var TAG = "SearchImageActivity"
    private var binding: ActivitySearchImageBinding? = null
    private var dataList: MutableList<UnPlashResponse?> = mutableListOf()
    private var photoRVAdapter = PhotoRVAdapter(dataList, null, this)
    private var searchQuery = ""
    private var pageNumber: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchImageBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)

        binding?.toolbar?.setNavigationOnClickListener {
            finish()
        }

        setUpImageList()
        setOnScrollEnd()
    }

    private fun setOnScrollEnd() {
        binding?.searchgridView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    getSearchImages()
                }
            }
        })
    }

    private fun setUpImageList() {
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding?.searchgridView?.layoutManager = staggeredGridLayoutManager
        binding?.searchgridView?.adapter = photoRVAdapter
        photoRVAdapter.notifyDataSetChanged()
    }

    private fun getSearchImages(isNewSearch: Boolean = false) {
//        supportActionBar?.title = searchQuery.replaceFirstChar {
//            if (it.isLowerCase()) it.titlecase(
//                Locale.getDefault()
//            ) else it.toString()
//        }
        if (LoadingScreen.isLoading){
            return
        }
        if(isNewSearch){
            dataList.removeAll(dataList)
            pageNumber = 0
        }
        pageNumber += 1
        val context: Context = this
        LoadingScreen.displayLoadingWithText(context, "Please wait...", false)
        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getSearchImage(Constants.APK_KEY, "15", searchQuery,pageNumber.toString())
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
                LoadingScreen.hideLoading()
                val data: List<UnPlashResponse?> = response.body()!!.results
                dataList.addAll(data)
                photoRVAdapter.notifyDataSetChanged()
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
        val data = dataList!![position]
        val intent = Intent(this.baseContext!!, SelectedImageActivity::class.java)
        intent.putExtra("data", data)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.search_image)
        val searchView = searchItem?.actionView as SearchView
        searchView.setSearchableInfo(manager.getSearchableInfo((componentName)))
        searchView.setIconifiedByDefault(true);
        searchView.isFocusable = true;
        searchView.isIconified = false;
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
//                searchView.setQuery("", false)
                searchItem.collapseActionView()
                if (query != null) {
                    searchQuery = query
                    getSearchImages(true)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }
}