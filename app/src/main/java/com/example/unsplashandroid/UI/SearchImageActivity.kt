package com.example.unsplashandroid.UI

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
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
    private var datalist: List<UnPlashResponse?>? = null
    lateinit var photoRVAdapter: PhotoRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchImageBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)
        setUpImageList()
    }

    private fun setUpImageList() {
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding?.searchgridView?.layoutManager = staggeredGridLayoutManager
        photoRVAdapter = PhotoRVAdapter(datalist, null, this)
        binding?.searchgridView?.adapter = photoRVAdapter
        photoRVAdapter.notifyDataSetChanged()
    }

    private fun getRandomQuestion(query: String) {
        supportActionBar?.title = query.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        datalist = null
        val context: Context = this
        LoadingScreen.displayLoadingWithText(context, "Please wait...", false)
        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getSearchImage(Constants.APK_KEY, "30", query)
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
                val data: List<UnPlashResponse?>? = response.body()!!.results
                println(data!![0]!!.urls)
                datalist = data
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
        Log.e(TAG, "Response not successful")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        var inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        var manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var searchItem = menu.findItem(R.id.search_image)
        var searchView = searchItem?.actionView as SearchView
        searchView.setSearchableInfo(manager.getSearchableInfo((componentName)))
//        searchView.requestFocus()
        searchView.setIconifiedByDefault(true);
        searchView.isFocusable = true;
        searchView.isIconified = false;
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()
                if (query != null) {
                    getRandomQuestion(query)
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