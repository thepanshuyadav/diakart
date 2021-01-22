package com.thepanshu.diakart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.thepanshu.diakart.authenticate.RegisterActivity


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_wishlist, R.id.nav_account, R.id.nav_rating), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val navHeader = navView.getHeaderView(0)
        val profileImageView = navHeader.findViewById<ImageView>(R.id.main_profile_img)
        val userNameTv = navHeader.findViewById<TextView>(R.id.main_full_name)
        val userEmailTv = navHeader.findViewById<TextView>(R.id.main_email)
        val shareButton = navView.menu.size().toString()
        Log.d("SHARE", shareButton)
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore
        val docRef = db.collection("USERS").document(user!!.uid)
        docRef.get().addOnSuccessListener {
            if (it != null) {

                Log.d("PHOTO", "DocumentSnapshot data: ${it.data}")
                userNameTv.text = it.data?.get("name").toString()
                userEmailTv.text = it.data?.get("email").toString()
                val imageSrc = it.data?.get("profile_pic").toString()
                Picasso.get().load(imageSrc).into(profileImageView)

            } else {
                //Log.d(TAG, "No such document")
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        //TODO: Search
        
        if(id == R.id.main_share_icon) {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Hey check out this app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
            return true
        }

        if(id == R.id.main_notif_icon) {
            // TODO: Notification fragment
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}