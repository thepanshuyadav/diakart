package com.thepanshu.diakart.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.ProductsListAdapter
import com.thepanshu.diakart.models.ProductDetailModel

class WishListFragment : Fragment(), ProductsListAdapter.OnProductClickListener  {

    // TODO: Fetch Wish list

    private lateinit var wishListViewModel: WishListViewModel
    var data= MutableLiveData<List<ProductDetailModel>>()
    lateinit  var da:List<ProductDetailModel>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        wishListViewModel =
                ViewModelProvider(this).get(WishListViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_wishlist, container, false)

        val rvProducts = rootView.findViewById(R.id.wish_list_rv) as RecyclerView
        val progressBar = rootView.findViewById<ProgressBar>(R.id.wish_list_progressBar)
        rvProducts.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        wishListViewModel
                .getWishList()
                .observe(viewLifecycleOwner, Observer<List<ProductDetailModel>> {
            progressBar.visibility = View.VISIBLE
            data.value = it
            da=it
            rvProducts.adapter = ProductsListAdapter(da, this)
            progressBar.visibility = View.GONE
        })


        return rootView
    }

    override fun onProductClick(position: Int) {
        val bundle = bundleOf("product" to da[position])
        val navController = view?.let { Navigation.findNavController(it) }
        navController?.navigate(R.id.action_nav_wishlist_to_productDetailFragment, bundle)
    }
}