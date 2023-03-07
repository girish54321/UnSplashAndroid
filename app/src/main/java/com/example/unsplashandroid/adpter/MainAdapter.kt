package com.example.unsplashandroid.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashandroid.R
import com.example.unsplashandroid.UI.SearchImageActivity
import com.example.unsplashandroid.databinding.ImageItemBinding
import com.example.unsplashandroid.modal.CategoryModal
import com.example.unsplashandroid.modal.UnPlashResponse
import com.squareup.picasso.Picasso
import java.io.File

class PhotoRVAdapter(
    private val photoList: List<UnPlashResponse?>?,
    private val topicList: List<CategoryModal?>?,
    private var localImagePath: List<File?>?,
    private val listener: OnItemClickLister
) : RecyclerView.Adapter<PhotoRVAdapter.ViewHolder>() {
    inner class ViewHolder(binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        var image = binding.imageView
        var categoryView = binding.categoryView
        var categoryText = binding.categoryText
        init {
            binding.imageView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            var i = adapterPosition
            if (i != RecyclerView.NO_POSITION) {
                listener.onItemClick(i)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ImageItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!photoList.isNullOrEmpty()) {
            Picasso.get().load(photoList!![position]!!.urls?.regular)
                .fit()
                .centerInside()
                .placeholder(R.drawable.gray)
                .into(holder.image);
            holder.categoryView.visibility = View.GONE
        }
        if (!localImagePath.isNullOrEmpty()){
            Picasso.get().load(localImagePath?.get(position)!!.absoluteFile)
                .fit()
                .centerInside()
                .placeholder(R.drawable.gray)
                .into(holder.image);
            holder.categoryView.visibility = View.GONE
        }
        if(!topicList.isNullOrEmpty()) {
            Picasso.get().load(topicList[position]!!.coverPhoto?.urls?.regular)
                .fit()
                .centerInside()
                .placeholder(R.drawable.gray)
                .into(holder.image);
            holder.categoryText.text = topicList[position]!!.title
        }
    }

    override fun getItemCount(): Int {
        return if (!photoList.isNullOrEmpty()) {
            photoList.count()
        } else if (!topicList.isNullOrEmpty()) {
            topicList.count()
        } else if(!localImagePath.isNullOrEmpty()){
            localImagePath!!.count()
        } else {
            0
        }
    }

    interface OnItemClickLister {
        fun onItemClick(position: Int)
    }
}