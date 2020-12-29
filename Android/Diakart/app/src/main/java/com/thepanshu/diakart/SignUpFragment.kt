package com.thepanshu.diakart

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class SignUpFragment : Fragment() {

    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button

    private lateinit var frag_container: FrameLayout

    private lateinit var email:TextInputEditText
    private lateinit var name:TextInputEditText
    private lateinit var password:TextInputEditText

    private lateinit var progressBar: ProgressBar

    private lateinit var closeButton: ImageButton

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        signInButton = view.findViewById(R.id.signin_button)
        signUpButton = view.findViewById(R.id.signup_materialButton)
        closeButton = view.findViewById(R.id.signup_close_btn)

        email = view.findViewById(R.id.email_text)
        name = view.findViewById(R.id.name_text)
        password = view.findViewById(R.id.password_text)

        progressBar = view.findViewById(R.id.signUpProgressBar)
        progressBar.visibility = View.INVISIBLE

        frag_container = activity!!.findViewById(R.id.register_container)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

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
        closeButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity!!.finish()
        }
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
        fragmentTransaction.replace(frag_container.id, fragment)
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
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"
        if(email.text.toString().matches(emailPattern.toRegex())) {
            signUpButton.isEnabled = false
            progressBar.visibility = View.VISIBLE
            closeButton.isEnabled = false
            firebaseAuth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {

                            val userData = HashMap<Any, String>()
                            userData["name"] = name.text.toString()

                            firebaseFirestore.collection("USERS")
                                .add(userData)
                                .addOnCompleteListener {
                                    if(it.isSuccessful) {
                                        val intent = Intent(activity, MainActivity::class.java)
                                        startActivity(intent)
                                        activity!!.finish()
                                    }
                                    else {
                                        progressBar.visibility = View.INVISIBLE
                                        signUpButton.isEnabled = true
                                        closeButton.isEnabled = true
                                        val error = it.exception!!.message
                                        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                                    }
                                }
                        } else {
                            progressBar.visibility = View.INVISIBLE
                            signUpButton.isEnabled = true
                            val error = it.exception!!.message
                            Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                        }
                    }
        }
        else {
            email.error = "Email invalid"
        }
    }


}