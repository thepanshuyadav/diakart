package com.thepanshu.diakart.ui

import android.content.Intent
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
import com.thepanshu.diakart.data.Product

class ProductDetailFragment : Fragment() {
    private lateinit var product: Product
    private lateinit var intentToSite: Intent

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

        val ratingBar = view.findViewById<RatingBar>(R.id.product_ratingBar)
        ratingBar.setOnRatingBarChangeListener { _, _, _ ->
            Toast.makeText(context, "Rating = ${ratingBar.rating.toInt()}", Toast.LENGTH_SHORT).show()
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