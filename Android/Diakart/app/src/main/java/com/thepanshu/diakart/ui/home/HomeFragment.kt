package com.thepanshu.diakart.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asksira.loopingviewpager.LoopingViewPager
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.CategoryAdapter
import com.thepanshu.diakart.adapters.CustomLoopingBannerAdapter
import com.thepanshu.diakart.adapters.HomeGridProductAdapter
import com.thepanshu.diakart.models.BannerSliderModel
import com.thepanshu.diakart.models.Category
import com.thepanshu.diakart.models.HomeGridProductModel
import kotlin.collections.ArrayList

class HomeFragment : Fragment(), CategoryAdapter.OnCategoryClickListener, HomeGridProductAdapter.OnCategoryGridProductClickListener {

    /////////
    private lateinit var bannerList: ArrayList<BannerSliderModel>
    private lateinit var gridCategoryList: ArrayList<HomeGridProductModel>
    private lateinit var bannerViewPager:LoopingViewPager
    ////////

    private lateinit var categoryList: ArrayList<Category>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val rvCategory = root.findViewById(R.id.categories_rv) as RecyclerView
        rvCategory.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvCategory.adapter = CategoryAdapter(categoryList, this)
        bannerViewPager = root.findViewById(R.id.banner_slider_viewpager)
        bannerViewPager.adapter = activity?.let { CustomLoopingBannerAdapter(it, bannerList, true) }


        val rvGrid = root.findViewById<RecyclerView>(R.id.home_category_grid_view)
        rvGrid.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
        rvGrid.adapter = HomeGridProductAdapter(gridCategoryList, this)
        return root
    }

    override fun onCategoryClick(position: Int) {
//        val bundle = bundleOf("category" to productList[position])
        val navController = view?.let { Navigation.findNavController(it) }
        navController?.navigate(R.id.action_nav_home_to_productsListFragment)
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

        // Grid Category Layout
        gridCategoryList = ArrayList()
        gridCategoryList.add(HomeGridProductModel(R.drawable.ic_round_home_24,"Chocolate","RS 300","Ketofy"))
        gridCategoryList.add(HomeGridProductModel(R.drawable.ic_round_home_24,"Chocolate","RS 300","Ketofy"))
        gridCategoryList.add(HomeGridProductModel(R.drawable.ic_round_home_24,"Chocolate","RS 300","Ketofy"))
        gridCategoryList.add(HomeGridProductModel(R.drawable.ic_round_home_24,"Chocolate","RS 300","Ketofy"))

        // Grid Category Layout
    }

    override fun onCategoryGridProductClick(position: Int) {
        Toast.makeText(context, "Product: $position", Toast.LENGTH_SHORT).show()
    }

}
