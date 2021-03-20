package com.thepanshu.diakart.ui.user_rating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

    private lateinit var imageView: ImageView
    private lateinit var scrollView: NestedScrollView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.user_rating_fragment, container, false)
        userRatingRecyclerView = rootView.findViewById(R.id.user_rating_rv)
        progressBar = rootView.findViewById(R.id.progressBar)
        imageView = rootView.findViewById(R.id.list_bg_imageView)
        scrollView = rootView.findViewById(R.id.ratings_scroll_view)
        scrollView.visibility = View.GONE
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserRatingViewModel::class.java)
        viewModel.getProductsList().observe(viewLifecycleOwner, {
            progressBar.visibility = View.VISIBLE
            userRatingRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            if(it.isEmpty()){
                imageView.setImageResource(R.drawable.ic_undraw_empty_xct9)
                scrollView.visibility = View.GONE
            } else {
                scrollView.visibility = View.VISIBLE
                userRatingRecyclerView.adapter = UserRatingListAdapter(it, this, requireContext())
                imageView.setImageResource(R.drawable.ic_undraw_reviews)
            }
            progressBar.visibility = View.GONE
        })

    }

    override fun onProductClick(position: Int) {
        // TODO
    }

}