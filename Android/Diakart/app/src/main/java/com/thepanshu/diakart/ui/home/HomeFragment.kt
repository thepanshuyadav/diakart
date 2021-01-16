package com.thepanshu.diakart.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.*
import com.thepanshu.diakart.models.CategoryModel
import com.thepanshu.diakart.models.ProductDetailModel
import com.thepanshu.diakart.models.SliderModel
import java.util.*


class HomeFragment : Fragment(), CategoryAdapter.OnCategoryClickListener, GridProductAdapter.OnCategoryGridProductClickListener {


    private lateinit var gridListRv: RecyclerView
    private lateinit var rvCategory: RecyclerView

    private lateinit var progressBar: ProgressBar
    private lateinit var sliderView: SliderView

    private lateinit var categoryList: List<CategoryModel>
    private lateinit var bannerList: ArrayList<SliderModel>
    private lateinit var gridPreviewList: List<ProductDetailModel>

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        progressBar = root.findViewById(R.id.home_progress_bar)

        rvCategory = root.findViewById(R.id.categories_rv) as RecyclerView
        rvCategory.layoutManager = LinearLayoutManager(
            activity, LinearLayoutManager.HORIZONTAL, false
        )

        gridListRv = root.findViewById(R.id.grid_list_rv)
        gridListRv.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        homeViewModel.getBannerList().observe(viewLifecycleOwner, {
            //progressBar.visibility = View.VISIBLE
            Log.d("BANNER", it.toString())
            bannerList = it
            sliderView = root.findViewById(R.id.banner_slider_ad)
            sliderView.setSliderAdapter(SliderAdapter(requireContext(), bannerList))
            sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH;
            sliderView.indicatorSelectedColor = Color.WHITE;
            sliderView.indicatorUnselectedColor = Color.GRAY;
            sliderView.scrollTimeInSec = 4; //set scroll delay in seconds :
            sliderView.startAutoCycle();
            //progressBar.visibility = View.GONE
        })
        homeViewModel.getCategoryDetail().observe(viewLifecycleOwner, {
            // TODO: Show progress bar
            progressBar.visibility = View.VISIBLE
            categoryList = it
            gridListRv.adapter = GridListAdapter(requireActivity(), this, it)
            rvCategory.adapter = CategoryAdapter(requireActivity(), it, this)
            progressBar.visibility = View.GONE

        })
        return root
    }

    override fun onCategoryClick(position: Int) {
        val bundle = bundleOf("categoryProductsRef" to categoryList[position])
        val navController = view?.let { Navigation.findNavController(it) }
        navController?.navigate(R.id.action_nav_home_to_productsListFragment, bundle)
    }

    override fun onCategoryGridProductClick(position: Int) {
//        val bundle = bundleOf("product" to categoryList[position].preview[position])
//        val navController = view?.let { Navigation.findNavController(it) }
//        navController?.navigate(R.id.action_nav_home_to_productDetailFragment, bundle)
    }

}