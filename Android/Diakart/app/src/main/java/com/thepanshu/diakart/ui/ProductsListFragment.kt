package com.thepanshu.diakart.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.ProductsListAdapter
import com.thepanshu.diakart.models.Product

class ProductsListFragment : Fragment(), ProductsListAdapter.OnProductClickListener {
    private lateinit var category: String
    private lateinit var rvProducts: RecyclerView
    private var productList : ArrayList<Product> = ArrayList()
    private val desc = "Chocolate is a preparation of roasted and ground cacao seeds that is made in the form of a liquid, paste, or in a block, which may also be used as a flavoring ingredient in other foods. The earliest signs of use are associated with Olmec sites (within what would become Mexico’s post-colonial territory) suggesting consumption of chocolate beverages, dating from the 19th century BC.[1][2] The majority of Mesoamerican people made chocolate beverages, including the Maya and Aztecs.[3] The English word \"chocolate\" comes, via Spanish, from the Classical Nahuatl word xocolātl.[4]\n" +
            "\n" +
            "The seeds of the cacao tree have an intense bitter taste and must be fermented to develop the flavor. After fermentation, the beans are dried, cleaned, and roasted. The shell is removed to produce cacao nibs, which are then ground to cocoa mass, unadulterated chocolate in rough form. Once the cocoa mass is liquefied by heating, it is called chocolate liquor. The liquor may also be cooled and processed into its two components: cocoa solids and cocoa butter. Baking chocolate, also called bitter chocolate, contains cocoa solids and cocoa butter in varying proportions, without any added sugar. Powdered baking cocoa, which contains more fiber than cocoa butter, can be processed with alkali to produce dutch cocoa. Much of the chocolate consumed today is in the form of sweet chocolate, a combination of cocoa solids, cocoa butter or added vegetable oils, and sugar. Milk chocolate is sweet chocolate that additionally contains milk powder or condensed milk. White chocolate contains cocoa butter, sugar, and milk, but no cocoa solids."

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_products_list, container, false)
        rvProducts = rootView.findViewById(R.id.products_rv) as RecyclerView
        rvProducts.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvProducts.adapter = ProductsListAdapter(productsList = productList, this)
        return rootView
    }
    override fun onProductClick(position: Int) {
        val bundle = bundleOf("product" to productList[position])
        val navController = view?.let { Navigation.findNavController(it) }
        navController?.navigate(R.id.action_productsListFragment_to_productDetailFragment, bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productImages = arrayListOf(R.drawable.ic_round_wishlist_24,R.drawable.ic_round_wishlist_24,R.drawable.ic_round_wishlist_24,R.drawable.ic_round_wishlist_24)
        productList.add(
            Product(1, "Almond Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",product_images = productImages,
                "https://www.flipkart.com", desc)
        )
        productList.add(
            Product(1, "Almond Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",product_images = productImages,
                "https://www.flipkart.com", desc)
        )
        productList.add(
            Product(1, "Almond Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",product_images = productImages,
                "https://www.flipkart.com", desc)
        )
        productList.add(
            Product(1, "Almond Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",product_images = productImages,
                "https://www.flipkart.com", desc)
        )
        productList.add(
            Product(1, "Almond Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",product_images = productImages,
                "https://www.flipkart.com", desc)
        )
        productList.add(
            Product(1, "Almond Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",product_images = productImages,
                "https://www.flipkart.com", desc)
        )
        productList.add(
            Product(1, "Almond Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",product_images = productImages,
                "https://www.flipkart.com", desc)
        )

        rvProducts.adapter!!.notifyDataSetChanged()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}