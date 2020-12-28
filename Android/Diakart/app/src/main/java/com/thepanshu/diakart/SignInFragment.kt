package com.thepanshu.diakart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout

class SignInFragment : Fragment() {

    lateinit var signUpButton: Button
    lateinit var frag_container: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        signUpButton = view.findViewById(R.id.signup_btn)
        frag_container = activity!!.findViewById(R.id.register_container)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpButton.setOnClickListener {
            setFragment(SignUpFragment())
        }
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
        fragmentTransaction.replace(frag_container.id, fragment)
        fragmentTransaction.commit()
    }
}