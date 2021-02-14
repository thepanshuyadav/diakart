package com.thepanshu.diakart.ui.coupon_list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.CouponAdapter

class CouponListFragment : Fragment() {

    companion object {
        fun newInstance() = CouponListFragment()
    }

    private lateinit var viewModel: CouponListViewModel

    private lateinit var rootView: View

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.coupon_list_fragment, container, false)
        progressBar = rootView.findViewById(R.id.progressBar)
        recyclerView = rootView.findViewById(R.id.coupon_list_rv)
        imageView = rootView.findViewById(R.id.list_bg_imageView)

        val mAdView = rootView.findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CouponListViewModel::class.java)
        viewModel.getCouponsList().observe(viewLifecycleOwner, {
            progressBar.visibility = View.VISIBLE
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            if(it.isEmpty()){
                imageView.setImageResource(R.drawable.ic_undraw_empty_xct9)
            }
            else {
                recyclerView.adapter = CouponAdapter(it, requireContext(), rootView)
            }
            progressBar.visibility = View.GONE
        })
    }

}