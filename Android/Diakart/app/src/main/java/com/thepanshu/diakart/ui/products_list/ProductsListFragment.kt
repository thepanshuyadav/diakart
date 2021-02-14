package com.thepanshu.diakart.ui.products_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.firebase.firestore.DocumentReference
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.ProductsListAdapter
import com.thepanshu.diakart.models.CategoryModel
import com.thepanshu.diakart.models.ProductDetailModel

class ProductsListFragment : Fragment(), ProductsListAdapter.OnProductClickListener{
    private lateinit var productListRef: DocumentReference
    private lateinit var productsListViewModel: ProductsListViewModel
    private var data= MutableLiveData<List<ProductDetailModel>>()
    private lateinit var da:List<ProductDetailModel>

    private lateinit var rvProducts: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_products_list, container, false)

        rvProducts = rootView.findViewById(R.id.products_rv) as RecyclerView
        progressBar = rootView.findViewById(R.id.products_list_progress_bar)
        imageView = rootView.findViewById(R.id.list_bg_imageView)

        val mAdView = rootView.findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        // TODO: Use data binding to set the visibility of progress bar
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        productsListViewModel =
                ViewModelProvider(this).get(ProductsListViewModel::class.java)
        rvProducts.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        productsListViewModel.getProductsList(productListRef).observe(viewLifecycleOwner, {
            progressBar.visibility = View.VISIBLE
            data.value = it
            da=it
            if(it.isEmpty()) {
                imageView.setImageResource(R.drawable.ic_undraw_empty_xct9)
            } else {
                rvProducts.adapter = ProductsListAdapter(da, this, requireContext())
            }
            progressBar.visibility = View.GONE
        })

    }

    override fun onProductClick(position: Int) {
        val bundle = bundleOf("product" to da[position])
        val navController = view?.let { Navigation.findNavController(it) }
        navController?.navigate(R.id.action_productsListFragment_to_productDetailFragment, bundle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments?.getParcelable<CategoryModel>("categoryProductsRef") != null){
            productListRef = arguments?.getParcelable<CategoryModel>("categoryProductsRef")!!.productList
        }
    }
}