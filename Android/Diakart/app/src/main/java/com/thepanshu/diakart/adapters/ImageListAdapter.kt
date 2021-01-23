package com.thepanshu.diakart.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thepanshu.diakart.R

class ImageListAdapter(
    private val productImageList: ArrayList<String>,
        val context: Context
    //private val productImageList: List<Int>
    )
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
        Glide.with(context).load(Uri.parse(productImageList[position])).into(holder.imageView)
    }

}