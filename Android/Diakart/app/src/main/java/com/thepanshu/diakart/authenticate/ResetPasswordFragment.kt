package com.thepanshu.diakart.authenticate

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.thepanshu.diakart.R

class ResetPasswordFragment : Fragment() {

    private lateinit var email: TextInputEditText
    private lateinit var resetButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var backButton: Button

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var frag_container: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reset_password, container, false)
        email = view.findViewById(R.id.email)
        resetButton = view.findViewById(R.id.reset_button)
        backButton = view.findViewById(R.id.back_button)
        progressBar = view.findViewById(R.id.resetPassProgressBar)
        frag_container = requireActivity().findViewById(R.id.register_container)
        firebaseAuth = FirebaseAuth.getInstance()
        val resetPassImageView = view.findViewById<ImageView>(R.id.forgot_image)
        val imageLink = "https://firebasestorage.googleapis.com/v0/b/diakart.appspot.com/o/res%2Fundraw_my_password_d6kg.svg?alt=media&token=0886d8ad-974f-432f-8a87-19bab1fa73b3"
        GlideToVectorYou.justLoadImage(activity, Uri.parse(imageLink), resetPassImageView)
        //Picasso.get().load(imageLink).into(resetPassImageView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resetButton.isEnabled = false
        email.addTextChangedListener {
            resetButton.isEnabled = !(TextUtils.isEmpty(email.text.toString()))
        }

        resetButton.setOnClickListener {

            resetButton.isEnabled = false
            progressBar.visibility = View.VISIBLE

            firebaseAuth.sendPasswordResetEmail(email.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        // TODO: Show alert
                        Toast.makeText(activity, "Check your Email for reset link", Toast.LENGTH_LONG).show()
                    }
                    else {
                        val err = it.exception!!.message
                        resetButton.isEnabled = false
                        Toast.makeText(activity, err, Toast.LENGTH_SHORT).show()
                    }
                }
            progressBar.visibility = View.INVISIBLE
            resetButton.isEnabled = true
        }

        backButton.setOnClickListener {
            setFragment(SignInFragment())
        }
    }
    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
        fragmentTransaction.replace(frag_container.id, fragment)
        fragmentTransaction.commit()
    }
}