package com.thepanshu.diakart.ui.products_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.ProductsListAdapter
import com.thepanshu.diakart.data.Product

class ProductsListFragment : Fragment(), ProductsListAdapter.OnProductClickListener {
    private lateinit var category: String
    private lateinit var productsListViewModel: ProductsListViewModel
    private var data= MutableLiveData<List<Product>>()
    private lateinit  var da:List<Product>
//    private var isAnimating = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_products_list, container, false)
        productsListViewModel =
                ViewModelProvider(this).get(ProductsListViewModel::class.java)
        val rvProducts = rootView.findViewById(R.id.products_rv) as RecyclerView
        val progressBar = rootView.findViewById<ProgressBar>(R.id.products_list_progress_bar)
        rvProducts.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        productsListViewModel.getProductsList().observe(viewLifecycleOwner, Observer<List<Product>> {
            progressBar.visibility = View.VISIBLE
            data.value = it
            da=it
            rvProducts.adapter = ProductsListAdapter(data, this)
            progressBar.visibility = View.GONE
        })

        // TODO: Use data binding to set the visibility of progress bar
        return rootView
    }
    override fun onProductClick(position: Int) {
        val bundle = bundleOf("product" to da[position])
        val navController = view?.let { Navigation.findNavController(it) }
        navController?.navigate(R.id.action_productsListFragment_to_productDetailFragment, bundle)
    }

}