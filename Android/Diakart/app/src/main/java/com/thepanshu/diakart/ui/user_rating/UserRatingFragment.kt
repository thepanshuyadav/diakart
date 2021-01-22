package com.thepanshu.diakart.ui.user_rating

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
import com.thepanshu.diakart.adapters.UserRatingListAdapter

class UserRatingFragment : Fragment(), UserRatingListAdapter.OnProductClickListener {

    companion object {
        fun newInstance() = UserRatingFragment()
    }

    private lateinit var viewModel: UserRatingViewModel
    private lateinit var userRatingRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.user_rating_fragment, container, false)
        userRatingRecyclerView = rootView.findViewById(R.id.user_rating_rv)
        progressBar = rootView.findViewById(R.id.progressBar)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserRatingViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.getProductsList().observe(viewLifecycleOwner, {
            progressBar.visibility = View.VISIBLE
            userRatingRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            userRatingRecyclerView.adapter = UserRatingListAdapter(it, this)
            progressBar.visibility = View.GONE
        })

    }

    override fun onProductClick(position: Int) {
        // TODO
    }

}