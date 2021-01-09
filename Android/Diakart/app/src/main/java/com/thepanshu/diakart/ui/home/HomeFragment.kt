package com.thepanshu.diakart.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.*
import com.thepanshu.diakart.data.Category
import com.thepanshu.diakart.data.Product
import com.thepanshu.diakart.models.CategoryModel
import kotlin.collections.ArrayList

class HomeFragment : Fragment(), CategoryAdapter.OnCategoryClickListener, GridProductAdapter.OnCategoryGridProductClickListener {

    /////////
    private lateinit var bannerList: ArrayList<Int>
    private var gridList = ArrayList<CategoryModel>()
    private lateinit var rvCategory: RecyclerView
    private lateinit var gridListRv: RecyclerView
    private lateinit var progressBar: ProgressBar
    private var gridCategoryList = ArrayList<Product>()
    private val desc = "Chocolate is a preparation of roasted and ground cacao seeds that is made in the form of a liquid, paste, or in a block, which may also be used as a flavoring ingredient in other foods. The earliest signs of use are associated with Olmec sites (within what would become Mexico’s post-colonial territory) suggesting consumption of chocolate beverages, dating from the 19th century BC.[1][2] The majority of Mesoamerican people made chocolate beverages, including the Maya and Aztecs.[3] The English word \"chocolate\" comes, via Spanish, from the Classical Nahuatl word xocolātl.[4]\n" +
            "\n" +
            "The seeds of the cacao tree have an intense bitter taste and must be fermented to develop the flavor. After fermentation, the beans are dried, cleaned, and roasted. The shell is removed to produce cacao nibs, which are then ground to cocoa mass, unadulterated chocolate in rough form. Once the cocoa mass is liquefied by heating, it is called chocolate liquor. The liquor may also be cooled and processed into its two components: cocoa solids and cocoa butter. Baking chocolate, also called bitter chocolate, contains cocoa solids and cocoa butter in varying proportions, without any added sugar. Powdered baking cocoa, which contains more fiber than cocoa butter, can be processed with alkali to produce dutch cocoa. Much of the chocolate consumed today is in the form of sweet chocolate, a combination of cocoa solids, cocoa butter or added vegetable oils, and sugar. Milk chocolate is sweet chocolate that additionally contains milk powder or condensed milk. White chocolate contains cocoa butter, sugar, and milk, but no cocoa solids."

    ////////

    private var categoryList= ArrayList<Category>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        progressBar = root.findViewById(R.id.home_progress_bar)
        getCategory()
        rvCategory = root.findViewById(R.id.categories_rv) as RecyclerView
        rvCategory.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val categoryAdapter = CategoryAdapter(requireActivity(), categoryList, this)
        rvCategory.adapter = categoryAdapter

        gridListRv = root.findViewById<RecyclerView>(R.id.grid_list_rv)
        gridListRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        gridListRv.adapter = GridListAdapter(this, gridList)

        return root

    }

    private fun getCategory() {
        val db = Firebase.firestore



        db.collection("CATEGORIES")
                .get()
                .addOnSuccessListener {

                    categoryList.clear()
                    for(document in it) {
                        val p: Category = document.toObject(Category::class.java)
                        categoryList.add(p)
                        rvCategory.adapter!!.notifyDataSetChanged()
                    }
                    gridList.clear()
                    for(i in categoryList) {
                        gridList.add(CategoryModel(Category(i.icon, i.title), gridCategoryList))
                    }
                    gridListRv.adapter!!.notifyDataSetChanged()
                }
                .addOnFailureListener {
                    val error = it.message
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
    }


    override fun onCategoryClick(position: Int) {
//        val bundle = bundleOf("category" to productList[position])
        val navController = view?.let { Navigation.findNavController(it) }
        navController?.navigate(R.id.action_nav_home_to_productsListFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bannerList = ArrayList()

        bannerList.add(R.drawable.ic_baseline_search_24)
        bannerList.add(R.drawable.ic_baseline_close_24)
        bannerList.add(R.drawable.ic_baseline_favorite_24)
        bannerList.add(R.drawable.ic_baseline_add_circle_24)
        bannerList.add(R.drawable.ic_baseline_notifications_24)
        bannerList.add(R.drawable.ic_round_home_24)

        // Grid Category Layout
        val productImages = arrayListOf(R.drawable.ic_baseline_favorite_24, R.drawable.ic_baseline_favorite_24, R.drawable.ic_baseline_favorite_24, R.drawable.ic_baseline_favorite_24)
        gridCategoryList.add(
                Product(1, "Almond Chocolate", "50g", 50,
                        "Dairy Milk", "Chocolate", product_images = productImages,
                        "https://www.flipkart.com", desc)
        )
        gridCategoryList.add(
                Product(1, "Almond Chocolate", "50g", 50,
                        "Dairy Milk", "Chocolate", product_images = productImages,
                        "https://www.flipkart.com", desc)
        )
        gridCategoryList.add(
                Product(1, "Almond Chocolate", "50g", 50,
                        "Dairy Milk", "Chocolate", product_images = productImages,
                        "https://www.flipkart.com", desc)
        )
        gridCategoryList.add(
                Product(1, "Almond Chocolate", "50g", 50,
                        "Dairy Milk", "Chocolate", product_images = productImages,
                        "https://www.flipkart.com", desc)
        )
        gridCategoryList.add(
                Product(1, "Almond Chocolate", "50g", 50,
                        "Dairy Milk", "Chocolate", product_images = productImages,
                        "https://www.flipkart.com", desc)
        )
        gridCategoryList.add(
                Product(1, "Almond Chocolate", "50g", 50,
                        "Dairy Milk", "Chocolate", product_images = productImages,
                        "https://www.flipkart.com", desc)
        )

        gridCategoryList.add(
                Product(1, "Almond Chocolate", "50g", 50,
                        "Dairy Milk", "Chocolate", product_images = productImages,
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