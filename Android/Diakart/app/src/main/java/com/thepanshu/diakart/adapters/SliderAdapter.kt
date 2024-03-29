package com.thepanshu.diakart.adapters

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import com.thepanshu.diakart.R
import com.thepanshu.diakart.models.SliderModel

class SliderAdapter(private val context: Context, private var mSliderItems: ArrayList<SliderModel>) :
    SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {
//    fun renewItems(sliderItems: ArrayList<SliderModel>) {
//        mSliderItems = sliderItems
//        notifyDataSetChanged()
//    }
//
//    fun deleteItem(position: Int) {
//        mSliderItems.removeAt(position)
//        notifyDataSetChanged()
//    }
//
//    fun addItem(sliderItem: SliderModel) {
//        mSliderItems.add(sliderItem)
//        notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.slider_list_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem: SliderModel = mSliderItems[position]
        viewHolder.textViewDescription.setText(sliderItem.description)
        viewHolder.textViewDescription.textSize = 16f
        viewHolder.textViewDescription.setTextColor(Color.WHITE)
        viewHolder.itemView.setOnClickListener {
//            Toast.makeText(
//                context,
//                "This is item in position $position",
//                Toast.LENGTH_SHORT
//            ).show()
        }

        Glide.with(context)
                .load(Uri.parse(sliderItem.image))
                .into(viewHolder.imageViewBackground)
    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    inner class SliderAdapterVH(itemView: View) :
        ViewHolder(itemView) {
        var imageViewBackground: ImageView
        var textViewDescription: TextView

        init {
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider)
            //TODO; Image GIF
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider)
        }
    }
}