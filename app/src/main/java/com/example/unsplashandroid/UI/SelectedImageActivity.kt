package com.example.unsplashandroid.UI

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.unsplashandroid.R
import com.example.unsplashandroid.databinding.ActivitySelectedImageBinding
import com.example.unsplashandroid.modal.UnPlashResponse
import com.squareup.picasso.Picasso
import java.util.*

class SelectedImageActivity : AppCompatActivity() {
    private var binding: ActivitySelectedImageBinding? = null
    var data: UnPlashResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedImageBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)

        getUpComeingData()

        binding?.downlodBtn?.setOnClickListener {
//            singleChoiceItem(it)
            val builder = AlertDialog.Builder(this)
            val items = arrayOf("Microsoft", "Apple", "Amazon", "Google")
            val selectedList = ArrayList<Int>()
            builder.setTitle("This is list choice dialog box")
//            builder.setMessage("Some My Test or more info")
            builder.setItems(items, { dialogInterface, which ->
                Toast.makeText(this@SelectedImageActivity, items[which], Toast.LENGTH_SHORT).show()
            })
//            builder.setSingleChoiceItems(
//                items,selectedList[0]
//            ){
//
//            }
//            builder.setSingleChoiceItems()
//            builder.setSingleChoiceItems(
//                items, null
//            ) { dialog, which, isChecked ->
//                if (isChecked) {
//                    selectedList.add(which)
//                } else if (selectedList.contains(which)) {
//                    selectedList.remove(Integer.valueOf(which))
//                }
//            }
            builder.setPositiveButton("DONE") { dialogInterface, i ->
                val selectedStrings = ArrayList<String>()
                for (j in selectedList.indices) {
                    selectedStrings.add(items[selectedList[j]])
                }
                Toast.makeText(
                    applicationContext,
                    "Items selected are: " + Arrays.toString(selectedStrings.toTypedArray()),
                    Toast.LENGTH_SHORT
                ).show()
            }
            builder.show()
        }
    }

    fun singleChoiceDialog(view: View) {
        val singleChoiceList = arrayOf("google", "yahoo", "bing", "yandex", "baidu", "duckduckgo")
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Select Search Engine")

        builder.setSingleChoiceItems(singleChoiceList, -1,
            { dialogInterface, which ->
                Toast.makeText(this@SelectedImageActivity, singleChoiceList[which], Toast.LENGTH_SHORT).show()
            }
        )

//        builder.setPositiveButton("ok", SelectedImageActivity.OnClickListener { dialog, id ->
//            dialog.dismiss()
//        })
        builder.setNegativeButton("cancel", { dialog, id ->
            dialog.dismiss()
        })

        // builder.create().show();
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun singleChoiceItem(view: View) {
        val singleChoiceList = arrayOf("google", "yahoo", "bing", "yandex", "baidu", "duckduckgo")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Search Engine")

        builder.setItems(singleChoiceList, { dialogInterface, which ->
            Toast.makeText(this@SelectedImageActivity, singleChoiceList[which], Toast.LENGTH_SHORT).show()
        })

        // builder.create().show();
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun getUpComeingData() {
        data = intent.getSerializableExtra("data") as UnPlashResponse?
        if (data != null) {
            setupView()
        }
    }

    private fun setupView() {
        supportActionBar?.title = data?.description?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        Picasso.get().load(data!!.urls?.regular)
            .placeholder(R.drawable.gray)
            .into(binding?.selectedImageView);
    }

}