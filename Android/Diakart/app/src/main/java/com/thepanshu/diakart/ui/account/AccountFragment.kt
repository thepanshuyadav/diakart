package com.thepanshu.diakart.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.GridProductAdapter
import com.thepanshu.diakart.authenticate.RegisterActivity
import com.thepanshu.diakart.models.UserModel


class AccountFragment : Fragment(), GridProductAdapter.OnCategoryGridProductClickListener {

    companion object {
        fun newInstance() = AccountFragment()
    }

    private lateinit var viewModel: AccountViewModel
    private lateinit  var user:UserModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(R.layout.account_fragment, container, false)
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

//        val wishListGrid = root.findViewById<TextView>(R.id.grid_category_title)
//        wishListGrid.text = "My Wish List"
//        val openWishListButton = root.findViewById<Button>(R.id.view_all_btn)
//        openWishListButton.setOnClickListener {
//            val navController = view?.let { Navigation.findNavController(it) }
//            navController?.navigate(R.id.action_nav_account_to_nav_wishlist)
//        }
        val usernameTv = root.findViewById<TextView>(R.id.username_tv)
        val userEmailTv = root.findViewById<TextView>(R.id.user_email_tv)
        val profileImage = root.findViewById<ImageView>(R.id.profile_pic_img)
        viewModel.getUserDetail().observe(viewLifecycleOwner, Observer<UserModel> {
            //progressBar.visibility = View.VISIBLE
            user = it
            usernameTv.text = user.name
            userEmailTv.text = user.email
            Picasso.get().load(user.profile_pic).into(profileImage)
            //progressBar.visibility = View.GONE
        })
        //        val rvGrid = root.findViewById<RecyclerView>(R.id.home_category_grid_view)
//        rvGrid.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
//        rvGrid.adapter = GridProductAdapter(gridCategoryList, this)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val signOutButton = view.findViewById<Button>(R.id.signout_btn)
        signOutButton.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            startActivity(Intent(activity, RegisterActivity::class.java))
            requireActivity().finish()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCategoryGridProductClick(gridIdx: Int, position: Int) {
        //TODO("Not yet implemented")
    }

}