package com.example.unsplashandroid.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashandroid.R
import com.example.unsplashandroid.databinding.ImageItemBinding
import com.example.unsplashandroid.modal.CategoryModal
import com.example.unsplashandroid.modal.UnPlashResponse
import com.squareup.picasso.Picasso

class PhotoRVAdapter(
    private val photoList: List<UnPlashResponse>?,
    private val topicList: List<CategoryModal>?,
    private val listener: OnItemClickLister
) : RecyclerView.Adapter<PhotoRVAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        var image = binding.imageView
        var categoryView = binding.categoryView
        var categoryText = binding.categoryText
        init {
            binding.imageView.setOnClickListener (this)
        }

        override fun onClick(v: View?) {
            var i = adapterPosition
            if(i != RecyclerView.NO_POSITION) {
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
        if (topicList.isNullOrEmpty()) {
            Picasso.get().load(photoList!![position].urls?.regular)
                .resize(photoList[position].width!! / 5, photoList[position].height!! / 5)
                .placeholder(R.drawable.gray)
                .into(holder.image);
            holder.categoryView.visibility = View.GONE
        } else {
            Picasso.get().load(topicList[position].coverPhoto?.urls?.regular)
                .resize(
                    topicList[position].coverPhoto?.width!! / 5,
                    topicList[position].coverPhoto?.height!! / 5
                )
                .placeholder(R.drawable.gray)
                .into(holder.image);
            holder.categoryText.text = topicList[position].title
        }
    }

    override fun getItemCount(): Int {
        return if (topicList.isNullOrEmpty()) {
            return photoList!!.count()
        } else {
            topicList.count()
        }
    }

    interface OnItemClickLister {
        fun onItemClick(position: Int)
    }

}