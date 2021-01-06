package com.thepanshu.diakart.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.ImageListAdapter
import com.thepanshu.diakart.models.Product

class ProductDetailFragment : Fragment() {
    private lateinit var product: Product
    private lateinit var intentToSite: Intent

    //////Ratings Layout
    private lateinit var rateNowLayout: LinearLayout
    /////
    companion object {
        var rating = 0
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_product_detail, container, false)
        val rvProductImage = rootView.findViewById(R.id.specific_product_image_rv) as RecyclerView
        rvProductImage.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvProductImage.adapter = ImageListAdapter(product.product_images)
        return rootView
    }

    private fun setRating() {
        for(i in 0..4) {
            val starImageView = rateNowLayout.getChildAt(i) as ImageView
            //starImageView.imageTintList = ColorStateList.valueOf(Color.parseColor("#545455"))
            if(i <= rating) {
                starImageView.imageTintList = ColorStateList.valueOf(Color.parseColor("#FFFFEA00"))
            }
            else {
                starImageView.imageTintList = ColorStateList.valueOf(Color.parseColor("#545455"))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments?.getParcelable<Product>("product") != null) {
            product = arguments?.getParcelable("product")!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.specific_product_name).text = product.product_name
        view.findViewById<TextView>(R.id.specific_product_brand_name).text = product.brand_name
        view.findViewById<TextView>(R.id.specific_product_desc).text = product.product_desc

        view.findViewById<TextView>(R.id.specific_product_price).text = product.price.toString()
        view.findViewById<TextView>(R.id.specific_product_quantity).text = " (${product.quantity})"

        rateNowLayout = view.findViewById(R.id.rate_now_container)
        rateNowLayout.setOnClickListener {
            Toast.makeText(context, rateNowLayout.childCount.toString(), Toast.LENGTH_SHORT).show()
        }
        val childCount: Int = rateNowLayout.childCount
        for (i in 0 until childCount) {
            val v: View = rateNowLayout.getChildAt(i)
            v.setOnClickListener {
                rating = i
                setRating()
            }
        }

        intentToSite = Intent(Intent.ACTION_VIEW, Uri.parse(product.link))
        val buyButton: Button = view.findViewById<TextView>(R.id.link_external_btn) as Button

        buyButton.setOnClickListener {
            if(product.link != "") {
                startActivity(intentToSite)
            }

        }
    }
}