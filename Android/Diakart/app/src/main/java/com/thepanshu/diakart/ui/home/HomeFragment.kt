package com.thepanshu.diakart.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.*
import com.thepanshu.diakart.models.CategoryModel
import com.thepanshu.diakart.models.ProductDetailModel

class HomeFragment : Fragment(), CategoryAdapter.OnCategoryClickListener, GridProductAdapter.OnCategoryGridProductClickListener {

    //private lateinit var bannerList: ArrayList<Int>

    private lateinit var gridListRv: RecyclerView
    private lateinit var rvCategory: RecyclerView
    private lateinit var categoryList: List<CategoryModel>
    private lateinit var progressBar: ProgressBar
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
        progressBar.visibility = View.VISIBLE
        rvCategory = root.findViewById(R.id.categories_rv) as RecyclerView
        rvCategory.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        //val categoryAdapter = CategoryAdapter(requireActivity(), categoryList, this)
        //rvCategory.adapter = categoryAdapter
        gridListRv = root.findViewById(R.id.grid_list_rv)
        gridListRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        //gridListRv.adapter = GridListAdapter(this, gridList)
        homeViewModel.getCategoryDetail().observe(viewLifecycleOwner, {
            // TODO: Show progress bar
            categoryList = it
            gridListRv.adapter = GridListAdapter(requireActivity(), this, it)
            rvCategory.adapter = CategoryAdapter(requireActivity(), it, this)

        })


        return root

    }


    override fun onCategoryClick(position: Int) {
        val bundle = bundleOf("categoryProductsRef" to categoryList[position])
        val navController = view?.let { Navigation.findNavController(it) }
        navController?.navigate(R.id.action_nav_home_to_productsListFragment, bundle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Show banner list
//        bannerList = ArrayList()
//
//        bannerList.add(R.drawable.ic_baseline_search_24)
//        bannerList.add(R.drawable.ic_baseline_close_24)
//        bannerList.add(R.drawable.ic_baseline_favorite_24)
//        bannerList.add(R.drawable.ic_baseline_add_circle_24)
//        bannerList.add(R.drawable.ic_baseline_notifications_24)
//        bannerList.add(R.drawable.ic_round_home_24)
    }

    override fun onCategoryGridProductClick(position: Int) {
//        val bundle = bundleOf("product" to categoryList[position].preview[position])
//        val navController = view?.let { Navigation.findNavController(it) }
//        navController?.navigate(R.id.action_nav_home_to_productDetailFragment, bundle)
    }

}