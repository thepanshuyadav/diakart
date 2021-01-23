package com.thepanshu.diakart.authenticate

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.thepanshu.diakart.MainActivity
import com.thepanshu.diakart.R


class SignInFragment : Fragment() {

    private lateinit var signUpButton: Button
    private lateinit var frag_container: FrameLayout

    private lateinit var email: TextInputEditText
    private lateinit var pass: TextInputEditText

    private lateinit var forgotPass: TextView

    private lateinit var signInButton: Button
    private lateinit var signInWithGoogleButton: SignInButton
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var closeButton: ImageButton

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var auth: FirebaseAuth

    private lateinit var progressBar: ProgressBar
    private val RC_SIGN_IN = 9001


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        signUpButton = view.findViewById(R.id.signup_btn)
        signInButton = view.findViewById(R.id.signin_button)
        signInWithGoogleButton = view.findViewById(R.id.sign_in_with_google_btn)
        signInWithGoogleButton.setSize(SignInButton.SIZE_WIDE)
        closeButton = view.findViewById(R.id.signin_close_btn)

        frag_container = requireActivity().findViewById(R.id.register_container)
        email = view.findViewById(R.id.email)
        pass = view.findViewById(R.id.pass)
        forgotPass = view.findViewById(R.id.signin_forgot_pass_btn)
        progressBar = view.findViewById(R.id.signInProgressBar)
        progressBar.visibility = View.INVISIBLE

        firebaseAuth = FirebaseAuth.getInstance()
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

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

        signInWithGoogleButton.setOnClickListener {
            signInWithGoogle()
        }

        forgotPass.setOnClickListener {
            RegisterActivity.isResetPassFrag = true
            setFragment(ResetPasswordFragment())
        }

        closeButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
    private fun signInWithGoogle() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
//                progressBar.visibility = View.INVISIBLE
//                signUpButton.isEnabled = true
//                closeButton.isEnabled = true
//                signInWithGoogleButton.isEnabled = true
                val error = e.message
                Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
            }

        }
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        signUpButton.isEnabled = false
        progressBar.visibility = View.VISIBLE
        closeButton.isEnabled = false
        signInWithGoogleButton.isEnabled = false
        forgotPass.isEnabled = false
        // [END_EXCLUDE]
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    val name = user!!.displayName
                    val uuid = user.uid
                    val email = user.email
                    val profilePic = user.photoUrl.toString()

                    // TODO: Send to firebase
                    val db = Firebase.firestore
                    val docRef = db.collection("USERS").document(user.uid)
                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (!document.exists()) {
                                val userFb = hashMapOf(
                                    "name" to name,
                                    "uuid" to uuid,
                                    "email" to email,
                                    "profile_pic" to profilePic,
                                    "points" to 0
                                )
                                db.collection("USERS").document(user.uid)
                                    .set(userFb)
                                    .addOnCompleteListener {
                                        if(it.isSuccessful) {
                                            val intent = Intent(activity, MainActivity::class.java)
                                            startActivity(intent)
                                            requireActivity().finish()
                                        }
                                        else {
                                            progressBar.visibility = View.INVISIBLE
                                            signUpButton.isEnabled = true
                                            closeButton.isEnabled = true
                                            signInWithGoogleButton.isEnabled = true
                                            forgotPass.isEnabled = true

                                            val error = it.exception!!.message
                                            Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
                                        }
                                    }
                            }
                            else {
                                val intent = Intent(activity, MainActivity::class.java)
                                startActivity(intent)
                                requireActivity().finish()
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "get failed with ", exception)
                            progressBar.visibility = View.INVISIBLE
                            signUpButton.isEnabled = true
                            closeButton.isEnabled = true
                            signInWithGoogleButton.isEnabled = true
                            forgotPass.isEnabled = true
                            Toast.makeText(context, "Authentication Failed.\n${exception!!.message.toString()}", Toast.LENGTH_SHORT).show()
                        }

                } else {
                    // If sign in fails, display a message to the user.
                    progressBar.visibility = View.INVISIBLE
                    signUpButton.isEnabled = true
                    closeButton.isEnabled = true
                    signInWithGoogleButton.isEnabled = true
                    forgotPass.isEnabled = true
                    Toast.makeText(context, "Authentication Failed.\n${task.exception!!.message.toString()}", Toast.LENGTH_SHORT).show()

                }
            }


    }

    private fun checkEmailPass() {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"
        if(email.text.toString().matches(emailPattern.toRegex())) {
            signUpButton.isEnabled = false
            progressBar.visibility = View.VISIBLE
            closeButton.isEnabled = false
            signInWithGoogleButton.isEnabled = false
            firebaseAuth.signInWithEmailAndPassword(email.text.toString(), pass.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    else {
                        signUpButton.isEnabled = true
                        closeButton.isEnabled = true
                        signInWithGoogleButton.isEnabled = true
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
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
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