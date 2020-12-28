package com.thepanshu.diakart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class RegisterActivity : AppCompatActivity() {
    lateinit var frag_container: FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        frag_container = findViewById<FrameLayout>(R.id.register_container)
        setFragment(SignInFragment())
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(frag_container.id, fragment)
        fragmentTransaction.commit()
    }

}