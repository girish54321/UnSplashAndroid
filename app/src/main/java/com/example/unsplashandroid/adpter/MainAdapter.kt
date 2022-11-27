package com.example.unsplashandroid.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashandroid.R
import com.example.unsplashandroid.modal.CategoryModal
import com.example.unsplashandroid.modal.UnPlashResponse
import com.squareup.picasso.Picasso

class PhotoRVAdapter(
    private val photoList: List<UnPlashResponse>?,
    private val topicList: List<CategoryModal>?,
) : RecyclerView.Adapter<PhotoRVAdapter.PhotoViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoRVAdapter.PhotoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.image_item,
            parent, false
        )
        return PhotoRVAdapter.PhotoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhotoRVAdapter.PhotoViewHolder, position: Int) {
        if (topicList.isNullOrEmpty()) {
            Picasso.get().load(photoList!![position].urls?.regular)
                .resize(photoList[position].width!! / 5, photoList[position].height!! / 5)
                .placeholder(R.drawable.gray)
                .into(holder.photoIV);
            holder.categoryView.visibility = View.GONE
        } else {
            Picasso.get().load(topicList[position].coverPhoto?.urls?.regular)
                .resize(
                    topicList[position].coverPhoto?.width!! / 5,
                    topicList[position].coverPhoto?.height!! / 5
                )
                .placeholder(R.drawable.gray)
                .into(holder.photoIV);
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

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoIV: ImageView = itemView.findViewById(R.id.imageView)
        val categoryView: LinearLayout = itemView.findViewById(R.id.categoryView)
        val categoryText: TextView = itemView.findViewById(R.id.categoryText)
    }

}