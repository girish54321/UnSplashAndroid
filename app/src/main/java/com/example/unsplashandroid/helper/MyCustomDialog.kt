package com.example.unsplashandroid.helper

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import com.example.myquizapp.helper.LoadingScreen.dialog
import com.example.unsplashandroid.R
import com.example.unsplashandroid.databinding.MyDialogLayoutBinding

class MyCustomDialog(context: Context) : Dialog(context) {
    lateinit var binding: MyDialogLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyDialogLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawableResource(R.drawable.round_corner);
        window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

}

