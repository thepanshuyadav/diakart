package com.thepanshu.diakart.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.thepanshu.diakart.models.Product
import kotlin.collections.ArrayList

class HomeFragment : Fragment(), CategoryAdapter.OnCategoryClickListener, HomeGridProductAdapter.OnCategoryGridProductClickListener {

    /////////
    private lateinit var bannerList: ArrayList<BannerSliderModel>
    private lateinit var gridCategoryList: ArrayList<Product>
    private lateinit var bannerViewPager:LoopingViewPager
    private val desc = "Chocolate is a preparation of roasted and ground cacao seeds that is made in the form of a liquid, paste, or in a block, which may also be used as a flavoring ingredient in other foods. The earliest signs of use are associated with Olmec sites (within what would become Mexico’s post-colonial territory) suggesting consumption of chocolate beverages, dating from the 19th century BC.[1][2] The majority of Mesoamerican people made chocolate beverages, including the Maya and Aztecs.[3] The English word \"chocolate\" comes, via Spanish, from the Classical Nahuatl word xocolātl.[4]\n" +
            "\n" +
            "The seeds of the cacao tree have an intense bitter taste and must be fermented to develop the flavor. After fermentation, the beans are dried, cleaned, and roasted. The shell is removed to produce cacao nibs, which are then ground to cocoa mass, unadulterated chocolate in rough form. Once the cocoa mass is liquefied by heating, it is called chocolate liquor. The liquor may also be cooled and processed into its two components: cocoa solids and cocoa butter. Baking chocolate, also called bitter chocolate, contains cocoa solids and cocoa butter in varying proportions, without any added sugar. Powdered baking cocoa, which contains more fiber than cocoa butter, can be processed with alkali to produce dutch cocoa. Much of the chocolate consumed today is in the form of sweet chocolate, a combination of cocoa solids, cocoa butter or added vegetable oils, and sugar. Milk chocolate is sweet chocolate that additionally contains milk powder or condensed milk. White chocolate contains cocoa butter, sugar, and milk, but no cocoa solids."

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

        val viewCategoryButton = root.findViewById<Button>(R.id.view_all_btn)
        viewCategoryButton.setOnClickListener {
            val navController = view?.let { Navigation.findNavController(it) }
            navController?.navigate(R.id.action_nav_home_to_productsListFragment)
        }
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
        bannerList.add(BannerSliderModel(R.drawable.ic_baseline_favorite_24))
        bannerList.add(BannerSliderModel(R.drawable.ic_baseline_add_circle_24))
        bannerList.add(BannerSliderModel(R.drawable.ic_baseline_notifications_24))
        bannerList.add(BannerSliderModel(R.drawable.ic_round_home_24))

        // Grid Category Layout
        gridCategoryList = ArrayList()
        val productImages = arrayListOf(R.drawable.ic_baseline_favorite_24, R.drawable.ic_baseline_favorite_24, R.drawable.ic_baseline_favorite_24,R.drawable.ic_baseline_favorite_24)
        gridCategoryList.add(
            Product(1, "Almond Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",product_images = productImages,
                "https://www.flipkart.com", desc)
        )
        gridCategoryList.add(
            Product(1, "Almond Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",product_images = productImages,
                "https://www.flipkart.com", desc)
        )
        gridCategoryList.add(
            Product(1, "Almond Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",product_images = productImages,
                "https://www.flipkart.com", desc)
        )
        gridCategoryList.add(
            Product(1, "Almond Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",product_images = productImages,
                "https://www.flipkart.com", desc)
        )
        gridCategoryList.add(
            Product(1, "Almond Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",product_images = productImages,
                "https://www.flipkart.com", desc)
        )
        gridCategoryList.add(
            Product(1, "Almond Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",product_images = productImages,
                "https://www.flipkart.com", desc)
        )

        gridCategoryList.add(
                Product(1, "Almond Chocolate","50g", 50,
                    "Dairy Milk", "Chocolate",product_images = productImages,
                    "https://www.flipkart.com", desc)
                )


        // Grid Category Layout
    }

    override fun onCategoryGridProductClick(position: Int) {
        val bundle = bundleOf("product" to gridCategoryList[position])
        val navController = view?.let { Navigation.findNavController(it) }
        navController?.navigate(R.id.action_nav_home_to_productDetailFragment, bundle)
    }

}
