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
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.thepanshu.diakart.MainActivity
import com.thepanshu.diakart.R

class SignUpFragment : Fragment() {

    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button

    private lateinit var fragContainer: FrameLayout

    private lateinit var email:TextInputEditText
    private lateinit var name:TextInputEditText
    private lateinit var password:TextInputEditText

    private lateinit var progressBar: ProgressBar

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        signInButton = view.findViewById(R.id.signin_button)
        signUpButton = view.findViewById(R.id.signup_materialButton)

        email = view.findViewById(R.id.email_text)
        name = view.findViewById(R.id.name_text)
        password = view.findViewById(R.id.password_text)

        progressBar = view.findViewById(R.id.signUpProgressBar)
        progressBar.visibility = View.INVISIBLE

        fragContainer = requireActivity().findViewById(R.id.register_container)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signInButton.setOnClickListener {
            setFragment(SignInFragment())
        }

        email.addTextChangedListener {
            checkInput()
        }
        password.addTextChangedListener {
            checkInput()
        }
        name.addTextChangedListener {
            checkInput()
        }
        signUpButton.setOnClickListener {
            checkEmail()
            // Send data to firebase
        }
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
        fragmentTransaction.replace(fragContainer.id, fragment)
        fragmentTransaction.commit()
    }

    private fun checkInput() {
        if(!(TextUtils.isEmpty(email.text)) ) {
            if(!(TextUtils.isEmpty(name.text))) {
                signUpButton.isEnabled = !(TextUtils.isEmpty(password.text)) && password.length() >= 8
            }
            else{
                signUpButton.isEnabled = false
            }
        }
        else {
            signUpButton.isEnabled = false
        }
    }

    private fun checkEmail() {
        val emailPattern = getString(R.string.email_pattern_regex)
        if(email.text.toString().matches(emailPattern.toRegex())) {
            signUpButton.isEnabled = false
            progressBar.visibility = View.VISIBLE
            firebaseAuth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {


                            val user = hashMapOf(
                                    "name" to name.text.toString(),
                                    "uuid" to task.result!!.user!!.uid,
                                    "email" to email.text.toString(),
                                    "profile_pic" to "https://firebasestorage.googleapis.com/v0/b/diakart.appspot.com/o/res%2Fdefault_user_avatar.png?alt=media&token=3d84a23a-a3e1-4a63-93b5-af0a4b601a44",
                                "points" to 0
                            )

                            firestore.collection("USERS")
                                    .document(task.result!!.user!!.uid)
                                    .set(user)
                                    .addOnCompleteListener {
                                        if(it.isSuccessful) {
                                            val intent = Intent(activity, MainActivity::class.java)
                                            startActivity(intent)
                                            requireActivity().finish()
                                        }
                                        else {
                                            progressBar.visibility = View.INVISIBLE
                                            signUpButton.isEnabled = true
                                            val error = it.exception!!.message
                                            if (error != null) {
                                                Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                        } else {
                            progressBar.visibility = View.INVISIBLE
                            signUpButton.isEnabled = true
                            val error = task.exception!!.message.toString()
                            Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT).show()
                        }
                    }
        }
        else {
            email.error = "Email invalid"
        }
    }


}