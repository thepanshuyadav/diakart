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
            Product(1, "Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",product_images = productImages,"https://www.flipkart.com",
                R.string.sample_paragraph.toString())
        )
        productList.add(
            Product(1, "Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",productImages,"https://www.flipkart.com",
                R.string.sample_paragraph.toString())
        )
        productList.add(
            Product(1, "Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",productImages,"https://www.flipkart.com",
                R.string.sample_paragraph.toString())
        )
        productList.add(
            Product(1, "Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",productImages,"https://www.flipkart.com",
                R.string.sample_paragraph.toString())
        )
        productList.add(
            Product(1, "Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",productImages,"https://www.flipkart.com",
                R.string.sample_paragraph.toString())
        )
        productList.add(
            Product(1, "Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",productImages,"https://www.flipkart.com",
                R.string.sample_paragraph.toString())
        )
        productList.add(
            Product(1, "Chocolate","50g", 50,
                "Dairy Milk", "Chocolate",productImages,"https://www.flipkart.com",
                R.string.sample_paragraph.toString())
        )
        rvProducts.adapter!!.notifyDataSetChanged()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}