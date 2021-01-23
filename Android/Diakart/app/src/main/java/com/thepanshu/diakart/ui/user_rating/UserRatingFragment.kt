package com.thepanshu.diakart.ui.user_rating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.UserRatingListAdapter

class UserRatingFragment : Fragment(), UserRatingListAdapter.OnProductClickListener {

    companion object {
        fun newInstance() = UserRatingFragment()
    }

    private lateinit var viewModel: UserRatingViewModel
    private lateinit var userRatingRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var imageView: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.user_rating_fragment, container, false)
        val mAdView = rootView.findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        userRatingRecyclerView = rootView.findViewById(R.id.user_rating_rv)
        progressBar = rootView.findViewById(R.id.progressBar)
        imageView = rootView.findViewById(R.id.list_bg_imageView)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserRatingViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.getProductsList().observe(viewLifecycleOwner, {
            progressBar.visibility = View.VISIBLE
            userRatingRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            if(it.isEmpty()){
                imageView.setImageResource(R.drawable.ic_undraw_empty_xct9)
            } else {
                userRatingRecyclerView.adapter = UserRatingListAdapter(it, this, requireContext())
            }
            progressBar.visibility = View.GONE
        })

    }

    override fun onProductClick(position: Int) {
        // TODO
    }

}