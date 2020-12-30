package com.thepanshu.diakart.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.thepanshu.diakart.R
import com.thepanshu.diakart.models.BannerSliderModel

class CustomLoopingBannerAdapter (
    context: Context,
    itemList: ArrayList<BannerSliderModel>,
    isInfinite: Boolean
) : LoopingPagerAdapter<BannerSliderModel>(context, itemList, isInfinite) {

    private lateinit var imageView: ImageView

    //This method will be triggered if the item View has not been inflated before.
    override fun inflateView(
        viewType: Int,
        container: ViewGroup,
        listPosition: Int
    ): View {
        val view = LayoutInflater.from(context).inflate(R.layout.slider_layout, container, false)
        imageView = view.findViewById(R.id.banner_slide)
        return view
    }

    //Bind your data with your item View here.
    //Below is just an example in the demo app.
    //You can assume convertView will not be null here.
    //You may also consider using a ViewHolder pattern.
    override fun bindView(
        convertView: View,
        listPosition: Int,
        viewType: Int
    ) {
        imageView.setImageResource(itemList!![listPosition].banner)
    }
}