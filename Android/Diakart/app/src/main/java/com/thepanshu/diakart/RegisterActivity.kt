package com.thepanshu.diakart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class RegisterActivity : AppCompatActivity() {
    private lateinit var frag_container: FrameLayout
    companion object {
        var isResetPassFrag: Boolean = false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        frag_container = findViewById(R.id.register_container)
        setDefaultFragment(SignInFragment())
    }

    private fun setDefaultFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
        fragmentTransaction.replace(frag_container.id, fragment)
        fragmentTransaction.commit()
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
        fragmentTransaction.replace(frag_container.id, fragment)
        fragmentTransaction.commit()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(isResetPassFrag) {
                isResetPassFrag = false
                setFragment(SignInFragment())
                return false
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}