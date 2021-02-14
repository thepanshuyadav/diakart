package com.thepanshu.diakart.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.gms.ads.MobileAds
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.thepanshu.diakart.BuildConfig
import com.thepanshu.diakart.R


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var profileImageView: ImageView
    private lateinit var imageSrc: String
    private lateinit var userNameTv: TextView
    private lateinit var userEmailTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        MobileAds.initialize(this) {}


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
        profileImageView = navHeader.findViewById(R.id.main_profile_img)
        userNameTv = navHeader.findViewById(R.id.main_full_name)
        userEmailTv = navHeader.findViewById(R.id.main_email)


    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        val user = Firebase.auth.currentUser
        val db = Firebase.firestore
        val docRef = db.collection("USERS").document(user!!.uid)
        docRef.get().addOnSuccessListener {
            if (it != null) {
                userNameTv.text = it.data?.get("name").toString()
                userEmailTv.text = it.data?.get("email").toString()
                imageSrc = it.data?.get("profile_pic").toString()
                Glide.with(context).load(Uri.parse(imageSrc)).into(profileImageView)

            }
        }
        return super.onCreateView(parent, name, context, attrs)
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
                "Diakart is a healthy guilt free food catalog app with amazing discounts and offer information. Get it for free at :\n: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}