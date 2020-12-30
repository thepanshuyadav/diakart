package com.thepanshu.diakart.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.thepanshu.diakart.R
import com.thepanshu.diakart.models.BannerSliderModel

class SliderAdapter(private val bannerList:ArrayList<BannerSliderModel>): PagerAdapter() {

    override fun getCount() = bannerList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val li = container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = li.inflate(R.layout.slider_layout, container, false)
        val imageView: ImageView = view.findViewById(R.id.banner_slide)
        imageView.setImageResource(bannerList[position].banner)
        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}

