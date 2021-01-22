package com.thepanshu.diakart.ui.coupon_list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.coupon_list_fragment, container, false)
        progressBar = rootView.findViewById(R.id.progressBar)
        recyclerView = rootView.findViewById(R.id.coupon_list_rv)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CouponListViewModel::class.java)
        viewModel.getCouponsList().observe(viewLifecycleOwner, {
            progressBar.visibility = View.VISIBLE
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = CouponAdapter(it, requireContext(), rootView)
            progressBar.visibility = View.GONE
        })
        // TODO: Use the ViewModel
    }

}