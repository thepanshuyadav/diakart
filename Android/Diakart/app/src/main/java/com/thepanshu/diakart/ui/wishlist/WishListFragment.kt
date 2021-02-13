package com.thepanshu.diakart.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.ProductsListAdapter
import com.thepanshu.diakart.models.ProductDetailModel

class WishListFragment : Fragment(), ProductsListAdapter.OnProductClickListener  {

    // TODO: Fetch Wish list

    private lateinit var wishListViewModel: WishListViewModel
    private var data= MutableLiveData<List<ProductDetailModel>>()
    private lateinit var da:List<ProductDetailModel>

    private lateinit var rvProducts:RecyclerView
    private lateinit var imageView: ImageView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_wishlist, container, false)
        rvProducts = rootView.findViewById(R.id.wish_list_rv)
        progressBar = rootView.findViewById(R.id.wish_list_progressBar)
        imageView = rootView.findViewById(R.id.list_bg_imageView)


        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        wishListViewModel =
            ViewModelProvider(this).get(WishListViewModel::class.java)
        rvProducts.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        wishListViewModel.getWishList().observe(viewLifecycleOwner, {
            progressBar.visibility = View.VISIBLE
            data.value = it
            da=it
            if(it.isEmpty()) {
                imageView.setImageResource(R.drawable.ic_undraw_empty_xct9)
            }
            else{
                rvProducts.adapter = ProductsListAdapter(da, this, requireContext())
            }
            progressBar.visibility = View.GONE
            })
    }

    override fun onProductClick(position: Int) {
        val bundle = bundleOf("product" to da[position])
        val navController = view?.let { Navigation.findNavController(it) }
        navController?.navigate(R.id.action_nav_wishlist_to_productDetailFragment, bundle)
    }
}