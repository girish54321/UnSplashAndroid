package com.example.unsplashandroid.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashandroid.R
import com.example.unsplashandroid.modal.UnPlashResponse
import com.squareup.picasso.Picasso

class PhotoRVAdapter(
    private val photoList: List<UnPlashResponse>,
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
        Picasso.get().load(photoList.get(position).urls?.regular)
            .resize(photoList.get(position).width!! / 5,photoList.get(position).height!! / 5)
            .placeholder(R.drawable.placeholder)
            .into(holder.photoIV);
    }


    override fun getItemCount(): Int {
        return photoList.size
    }

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoIV: ImageView = itemView.findViewById(R.id.imageView)
    }

}