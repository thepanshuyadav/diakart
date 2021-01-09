package com.thepanshu.diakart.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.thepanshu.diakart.R

class ImageListAdapter(
    //private val productImageList: ArrayList<String>
    private val productImageList: ArrayList<Int>)
    : RecyclerView.Adapter<ImageListAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.product_imageView)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListAdapter.ImageViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.product_image_list_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int = productImageList.size

    override fun onBindViewHolder(holder: ImageListAdapter.ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(productImageList[position])
//        Picasso.get()
//            .load(productImageList[position])
//            .placeholder(R.drawable.progress_animation)
//            .into(holder.imageView)
    }

}