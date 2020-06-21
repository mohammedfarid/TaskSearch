package com.swvl.tasksearch.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.swvl.tasksearch.R
import com.swvl.tasksearch.model.photoresponse.PhotoItem

class ImageRvAdapter(
    var context: Context,
    var photoItemArrayList: ArrayList<PhotoItem?>?
) :
    RecyclerView.Adapter<ImageRvAdapter.MyHolder>() {

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageMovie: AppCompatImageView = itemView.findViewById(R.id.image_movie)
        val loadingImage: ProgressBar = itemView.findViewById(R.id.loadingImage)
        fun bind(photoItem: PhotoItem) {
            Picasso.get()
                .load("http://farm${photoItem.farm}.static.flickr.com/${photoItem.server}/${photoItem.id}_${photoItem.secret}.jpg")
                .into(imageMovie, object : Callback {
                    override fun onSuccess() {
                        loadingImage.visibility = View.GONE
                        imageMovie.visibility = View.VISIBLE
                    }

                    override fun onError(e: Exception?) {
                    }

                })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie_image, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return photoItemArrayList?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        photoItemArrayList?.get(position)?.let { photoItem -> holder.bind(photoItem) }

    }

    fun addData(listItemPosts: ArrayList<PhotoItem>?) {
        listItemPosts?.let { result ->
            photoItemArrayList?.addAll(result)
        }
        notifyDataSetChanged()
    }
}
