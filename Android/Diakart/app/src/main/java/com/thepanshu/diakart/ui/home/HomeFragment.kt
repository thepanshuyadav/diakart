package com.thepanshu.diakart.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.asksira.loopingviewpager.LoopingViewPager
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.CategoryAdapter
import com.thepanshu.diakart.adapters.CustomLoopingBannerAdapter
import com.thepanshu.diakart.adapters.SliderAdapter
import com.thepanshu.diakart.models.BannerSliderModel
import com.thepanshu.diakart.models.Category
import java.util.*
import kotlin.collections.ArrayList

@Suppress("UNREACHABLE_CODE")
class HomeFragment : Fragment(), CategoryAdapter.OnCategoryClickListener {

    /////////
    private lateinit var bannerList: ArrayList<BannerSliderModel>
    private lateinit var bannerViewPager:LoopingViewPager
    private var adapter: CustomLoopingBannerAdapter? = null
    ////////

    private lateinit var categoryList: ArrayList<Category>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val rvCategory = root.findViewById(R.id.categories_rv) as RecyclerView
        rvCategory.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        rvCategory.adapter = CategoryAdapter(categoryList, this)
        bannerViewPager = root.findViewById(R.id.banner_slider_viewpager)
        bannerViewPager.adapter = activity?.let { CustomLoopingBannerAdapter(it, bannerList, true) }


        return root
    }

    override fun onCategoryClick(position: Int) {
        Toast.makeText(activity, "Category", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryList = ArrayList()
        categoryList.add(Category("Home", "link"))
        categoryList.add(Category("Home", "link"))
        categoryList.add(Category("Home", "link"))
        categoryList.add(Category("Home", "link"))

        bannerList = ArrayList()

        bannerList.add(BannerSliderModel(R.drawable.ic_baseline_search_24))
        bannerList.add(BannerSliderModel(R.drawable.ic_baseline_close_24))
        bannerList.add(BannerSliderModel(R.drawable.ic_round_wishlist_24))
        bannerList.add(BannerSliderModel(R.drawable.ic_baseline_add_circle_24))
        bannerList.add(BannerSliderModel(R.drawable.ic_baseline_notifications_24))
        bannerList.add(BannerSliderModel(R.drawable.ic_round_home_24))
    }

}
