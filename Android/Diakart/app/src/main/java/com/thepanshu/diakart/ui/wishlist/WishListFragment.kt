package com.thepanshu.diakart.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.ProductsListAdapter
import com.thepanshu.diakart.models.Product

class WishListFragment : Fragment(), ProductsListAdapter.OnProductClickListener  {

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
        val wishListRecyclerView: RecyclerView = root.findViewById(R.id.wish_list_rv)

        wishListViewModel.getWishList().observe(viewLifecycleOwner,  {
            data.value = it
            da=it
            wishListRecyclerView.adapter= ProductsListAdapter(data, this)
        })

        return root
    }
    override fun onProductClick(position: Int) {
        val bundle = bundleOf("product" to da[position])
        val navController = view?.let { Navigation.findNavController(it) }
        navController?.navigate(R.id.action_nav_wishlist_to_productDetailFragment, bundle)
    }
}