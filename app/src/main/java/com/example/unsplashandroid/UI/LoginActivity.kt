package com.example.unsplashandroid.UI

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.unsplashandroid.R
import com.example.unsplashandroid.const.Constants
import com.example.unsplashandroid.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
    private val mazUrl = "https://unsplash.com/oauth/authorize?client_id=jRBzm2zUw2eoIPSHZxLvY_hnSh0P8J91P2THDay4y8w&redirect_uri=https://girish54321.github.io/My-Portfolio&response_type=code"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_UnSplashAndroid)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        getToakn()
        binding?.loginWebView?.apply {
            settings.javaScriptEnabled = true
            webChromeClient = WebChromeClient()
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    val result = url?.contains("code")
                    if (result == true){
                        val uri =  Uri.parse(url)
                        val server: String? = uri.authority
                        val path:  String? = uri.path
                        val protocol:  String? = uri.scheme
                        val args: String? = uri.getQueryParameter("code");
                        args?.let { Log.d("Code", it) }
                        if(args != null){
                            saveTokan(args)
                        }
                    }
                    return false
                }
            }
            loadUrl(mazUrl)
        }
    }

    fun saveTokan (codeString:String){
        val sharedPref = this.getSharedPreferences(Constants.TOKAN, Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString(Constants.TOKAN, codeString)
            apply()
        }
    }

    fun getToakn (){
        val sharedPref = this.getSharedPreferences(Constants.TOKAN, Context.MODE_PRIVATE)
        val s1 = sharedPref.getString(Constants.TOKAN, "")
        if(s1 != ""){
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}