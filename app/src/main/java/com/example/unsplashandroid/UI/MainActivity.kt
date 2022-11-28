package com.example.unsplashandroid.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.unsplashandroid.R
import com.example.unsplashandroid.UI.fragment.CategoryFragment
import com.example.unsplashandroid.UI.fragment.HomeFragment
import com.example.unsplashandroid.UI.fragment.TrandingFragment
import com.example.unsplashandroid.adpter.ViewPagerAdapter
import com.example.unsplashandroid.databinding.ActivityMainBinding
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(), "HOME")
        adapter.addFragment(TrandingFragment(), "Trending".toUpperCase())
        adapter.addFragment(CategoryFragment(), "Category".toUpperCase())
        adapter.addFragment(HomeFragment(), "Saved".toUpperCase())

        binding?.viewPager?.adapter = adapter
        binding?.tabs?.setupWithViewPager(binding?.viewPager)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)

        var button = menu.findItem(R.id.shareButton)
        button.setOnMenuItemClickListener {
            intent = Intent(applicationContext,SearchImageActivity::class.java)
            startActivity(intent)
            return@setOnMenuItemClickListener true
        }
        return super.onCreateOptionsMenu(menu)
    }


}