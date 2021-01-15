package com.thepanshu.diakart.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.ProductsListAdapter
import com.thepanshu.diakart.data.Product

class WishListFragment : Fragment(), ProductsListAdapter.OnProductClickListener  {

    // TODO: Fetch Wish list

    private lateinit var wishListViewModel: WishListViewModel
    var data= MutableLiveData<List<Product>>()
    lateinit  var da:List<Product>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        wishListViewModel =
                ViewModelProvider(this).get(WishListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_wishlist, container, false)
//        val wishListRecyclerView: RecyclerView = root.findViewById(R.id.wish_list_rv)
//        val progressBar = root.findViewById<ProgressBar>(R.id.wishlist_progressBar)
//
//        wishListViewModel.getWishList().observe(viewLifecycleOwner,  {
//            progressBar.visibility = View.VISIBLE
//            data.value = it
//            da=it
//            wishListRecyclerView.adapter= ProductsListAdapter(da, this)
//            progressBar.visibility = View.INVISIBLE
//        })

        return root
    }
    override fun onProductClick(position: Int) {
//        val bundle = bundleOf("product" to da[position])
//        val navController = view?.let { Navigation.findNavController(it) }
//        navController?.navigate(R.id.action_nav_wishlist_to_productDetailFragment, bundle)
    }
}