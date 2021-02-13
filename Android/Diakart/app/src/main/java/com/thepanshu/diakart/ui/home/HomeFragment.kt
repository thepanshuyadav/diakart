package com.thepanshu.diakart.ui.home

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.card.MaterialCardView
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.CategoryAdapter
import com.thepanshu.diakart.adapters.GridListAdapter
import com.thepanshu.diakart.adapters.GridProductAdapter
import com.thepanshu.diakart.adapters.SliderAdapter
import com.thepanshu.diakart.models.CategoryModel
import com.thepanshu.diakart.models.SliderModel
import java.util.*


class HomeFragment : Fragment(),
        CategoryAdapter.OnCategoryClickListener,
        GridProductAdapter.OnCategoryGridProductClickListener,
        GridListAdapter.OnGridClickListener{


    private lateinit var gridListRv: RecyclerView
    private lateinit var rvCategory: RecyclerView

    private lateinit var progressBar: ProgressBar
    private lateinit var sliderView: SliderView

    private lateinit var categoryList: List<CategoryModel>
    private lateinit var bannerList: ArrayList<SliderModel>

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val mAdView1 = root.findViewById<AdView>(R.id.adView1)
        val adRequest1 = AdRequest.Builder().build()
        mAdView1.loadAd(adRequest1)


        progressBar = root.findViewById(R.id.home_progress_bar)
        rvCategory = root.findViewById(R.id.categories_rv) as RecyclerView
        gridListRv = root.findViewById(R.id.grid_list_rv)
        sliderView = root.findViewById(R.id.banner_slider_ad)
        val couponCardImageView: ImageView = root.findViewById(R.id.coupon_card_image)
        Glide.with(requireContext())
                .load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/diakart.appspot.com/o/res%2FScreenshot%202021-02-13%20at%2011.32.07%20PM.png?alt=media&token=1a9e7f49-375f-4d90-a067-089924235383"))
                .into(couponCardImageView)
        val couponListCard = root.findViewById<MaterialCardView>(R.id.coupons_card_view)
        couponListCard.setOnClickListener {
            //val bundle = bundleOf("categoryProductsRef" to categoryList[position])
            val navController = view?.let { Navigation.findNavController(it) }
            navController?.navigate(R.id.action_nav_home_to_couponListFragment)
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        rvCategory.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        gridListRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        homeViewModel.getBannerList().observe(viewLifecycleOwner, {
            //progressBar.visibility = View.VISIBLE
            bannerList = it
            sliderView.setSliderAdapter(SliderAdapter(requireContext(), bannerList))
            sliderView.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM)
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
            sliderView.indicatorSelectedColor = Color.WHITE
            sliderView.indicatorUnselectedColor = Color.GRAY
            sliderView.scrollTimeInSec = 4 //set scroll delay in seconds :
            sliderView.startAutoCycle()
            //progressBar.visibility = View.GONE
        })

        homeViewModel.getCategoryDetail().observe(viewLifecycleOwner, {
            // TODO: Show progress bar
            progressBar.visibility = View.VISIBLE
            categoryList = it
            gridListRv.adapter = GridListAdapter(
                    requireContext(), requireActivity(), this,this, it)
            rvCategory.adapter = CategoryAdapter(requireActivity(), it, this)
            progressBar.visibility = View.GONE

        })
    }

    override fun onCategoryClick(position: Int) {
        val bundle = bundleOf("categoryProductsRef" to categoryList[position])
        val navController = view?.let { Navigation.findNavController(it) }
        navController?.navigate(R.id.action_nav_home_to_productsListFragment, bundle)
    }

    override fun onCategoryGridProductClick(gridIdx: Int, position: Int) {
        val bundle = bundleOf("product" to categoryList[gridIdx].preview[position])
        val navController = view?.let { Navigation.findNavController(it) }
        navController?.navigate(R.id.action_nav_home_to_productDetailFragment, bundle)
    }

    override fun onGridViewAllClick(position: Int) {
        val bundle = bundleOf("categoryProductsRef" to categoryList[position])
        val navController = view?.let { Navigation.findNavController(it) }
        navController?.navigate(R.id.action_nav_home_to_productsListFragment, bundle)
    }
}