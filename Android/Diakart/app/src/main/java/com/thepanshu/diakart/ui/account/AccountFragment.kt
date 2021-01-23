package com.thepanshu.diakart.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import com.thepanshu.diakart.BuildConfig
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.GridProductAdapter
import com.thepanshu.diakart.authenticate.RegisterActivity
import com.thepanshu.diakart.models.UserModel


class AccountFragment : Fragment() {

    companion object {
        fun newInstance() = AccountFragment()
    }

    private lateinit var viewModel: AccountViewModel
    private lateinit  var user:UserModel

    private lateinit var usernameTv: TextView
    private lateinit var userPointsTv: TextView
    private lateinit var userEmailTv: TextView
    private lateinit var profileImage: ImageView

    private lateinit var progressBar:ProgressBar
    private lateinit var inviteButton: Button
    private lateinit var redeemButton: Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(R.layout.account_fragment, container, false)

        usernameTv = root.findViewById(R.id.username_tv)
        userPointsTv = root.findViewById(R.id.user_points)
        userEmailTv = root.findViewById(R.id.user_email_tv)
        profileImage = root.findViewById(R.id.profile_pic_img)

        inviteButton = root.findViewById(R.id.invite_button)
        redeemButton = root.findViewById(R.id.redeem_button)

        progressBar = root.findViewById(R.id.progressBar)

        return root
    }

    private fun checkRedeemCode(code: String) {
        redeemButton.isEnabled = code.length < 10
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        viewModel.getUserDetail().observe(viewLifecycleOwner, Observer<UserModel> {
            progressBar.visibility = View.VISIBLE
            user = it
            usernameTv.text = user.name
            userEmailTv.text = user.email
            Picasso.get().load(user.profile_pic).into(profileImage)
            userPointsTv.text = user.points.toString()
            progressBar.visibility = View.GONE
        })

        inviteButton.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND

            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Hey check out and install Diakart app to earn points : https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
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

}