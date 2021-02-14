package com.thepanshu.diakart.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.thepanshu.diakart.R
import com.thepanshu.diakart.authenticate.RegisterActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        firebaseAuth = FirebaseAuth.getInstance()

        Timer("SettingUp", false).schedule(3000) {

        }

    }

    override fun onStart() {
        super.onStart()
        val firebaseUser = firebaseAuth.currentUser

        if(firebaseUser == null) {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
            finish()
        }
        else {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}