package com.thepanshu.diakart.ui.account

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.thepanshu.diakart.BuildConfig
import com.thepanshu.diakart.R
import com.thepanshu.diakart.authenticate.RegisterActivity
import com.thepanshu.diakart.models.UserModel


class AccountFragment : Fragment() {

    companion object {
        fun newInstance() = AccountFragment()
    }

    private lateinit var viewModel: AccountViewModel
    private lateinit  var user:UserModel

    private var redeemed = MutableLiveData<Boolean>()
    private lateinit var usernameTv: TextView
    private lateinit var userPointsTv: TextView
    private lateinit var userEmailTv: TextView
    private lateinit var profileImage: ImageView

    private lateinit var progressBar:ProgressBar
    private lateinit var inviteCodeEt:TextInputEditText
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
        inviteCodeEt = root.findViewById(R.id.invite_code_et)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        viewModel.getUserDetail().observe(viewLifecycleOwner, Observer<UserModel> {
            progressBar.visibility = View.VISIBLE
            user = it
            usernameTv.text = user.name
            userEmailTv.text = user.email
            Glide.with(requireContext()).load(Uri.parse(user.profile_pic)).into(profileImage)
            userPointsTv.text = user.points.toString()
            progressBar.visibility = View.GONE
        })

        redeemed.observe(viewLifecycleOwner, {
            if (it!= null) {
                if(it == true) {
                    Snackbar.make(requireView(), "Points redeemed 💰", Snackbar.LENGTH_SHORT).show()
                }
                else {
                    Snackbar.make(requireView(), "Couldn't redeem points 😥", Snackbar.LENGTH_SHORT).show()
                }
                // TODO: Refresh view
            }
        })




        redeemButton.setOnClickListener {

            viewModel.redeemPoints(inviteCodeEt.text.toString()).observe(viewLifecycleOwner, {
                if(it == true) {
                    redeemed.postValue(it)
                }
            })
            view?.isEnabled = true
        }

        inviteButton.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND

            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                    // TODO: Change text
                "Hey, Diakart is a healthy food catalog app with discount and coupon information. Get it for free at : https://play.google.com/store/apps/details?id= \nUse invite code to earn points: \n${user.uuid}" + BuildConfig.APPLICATION_ID
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