package com.thepanshu.diakart.authenticate

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.thepanshu.diakart.MainActivity
import com.thepanshu.diakart.R

class SignInFragment : Fragment() {

    private lateinit var signUpButton: Button
    private lateinit var frag_container: FrameLayout

    private lateinit var email: TextInputEditText
    private lateinit var pass: TextInputEditText

    private lateinit var forgotPass: TextView

    private lateinit var signInButton: Button
    private lateinit var closeButton: ImageButton

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        signUpButton = view.findViewById(R.id.signup_btn)
        signInButton = view.findViewById(R.id.signin_button)
        closeButton = view.findViewById(R.id.signin_close_btn)

        frag_container = activity!!.findViewById(R.id.register_container)
        email = view.findViewById(R.id.email)
        pass = view.findViewById(R.id.pass)
        forgotPass = view.findViewById(R.id.signin_forgot_pass_btn)
        progressBar = view.findViewById(R.id.signInProgressBar)
        progressBar.visibility = View.INVISIBLE

        firebaseAuth = FirebaseAuth.getInstance()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpButton.setOnClickListener {
            setFragment(SignUpFragment())
        }

        email.addTextChangedListener {
            checkInput()
        }

        pass.addTextChangedListener {
            checkInput()
        }

        signInButton.setOnClickListener {

            checkEmailPass()
        }

        forgotPass.setOnClickListener {
            RegisterActivity.isResetPassFrag = true
            setFragment(ResetPasswordFragment())
        }

        closeButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity!!.finish()
        }
    }

    private fun checkEmailPass() {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"
        if(email.text.toString().matches(emailPattern.toRegex())) {
            signUpButton.isEnabled = false
            progressBar.visibility = View.VISIBLE
            closeButton.isEnabled = false
            firebaseAuth.signInWithEmailAndPassword(email.text.toString(), pass.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        activity!!.finish()
                    }
                    else {
                        signUpButton.isEnabled = true
                        closeButton.isEnabled = true
                        progressBar.visibility = View.INVISIBLE
                        val error = it.exception!!.message
                        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                    }
                }
        }
        else {
            Toast.makeText(activity, "Incorrect Email", Toast.LENGTH_LONG).show()
        }
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
        fragmentTransaction.replace(frag_container.id, fragment)
        fragmentTransaction.commit()
    }

    private fun checkInput() {
        if(!(TextUtils.isEmpty(email.text.toString())) ){
            signInButton.isEnabled = !(TextUtils.isEmpty(pass.text.toString())) && pass.length() >= 8
        }
        else {
            signInButton.isEnabled = false
        }
    }

}