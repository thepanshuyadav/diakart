package com.thepanshu.diakart.ui.product_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentReference
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.ImageListAdapter
import com.thepanshu.diakart.models.ProductDetailModel

class ProductDetailFragment : Fragment() {
    private lateinit var product: ProductDetailModel
    private lateinit var intentToSite: Intent
    //private lateinit var productRef: DocumentReference
    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_product_detail, container, false)
        viewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)

        val rvProductImage = rootView.findViewById(R.id.specific_product_image_rv) as RecyclerView
        rvProductImage.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val productNameTv = rootView.findViewById<TextView>(R.id.specific_product_name)
        val brandNameTv = rootView.findViewById<TextView>(R.id.specific_product_brand_name)
        val productDescTv = rootView.findViewById<TextView>(R.id.specific_product_desc)
        val productMRPTv = rootView.findViewById<TextView>(R.id.specific_product_price)
        val productQuantityTv = rootView.findViewById<TextView>(R.id.specific_product_quantity)
        productNameTv.text = product.name
        brandNameTv.text = product.brand
        productDescTv.text = product.description
        productMRPTv.text = "RS ${product.mrp}"
        productQuantityTv.text = product.quantity

        rvProductImage.adapter = ImageListAdapter(product.images)

//
//        viewModel.getProductDetail(productRef).observe(viewLifecycleOwner,{
//            if(it!=null) {
//                product = it
//
//                productNameTv.text = it.name
//                brandNameTv.text = it.brand
//                productDescTv.text = it.description
//                productMRPTv.text = "RS ${it.mrp}"
//                productQuantityTv.text = it.quantity
//
//                rvProductImage.adapter = ImageListAdapter(it.images)
//            }
//        })
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments?.getParcelable<ProductDetailModel>("product") != null){
            product = arguments?.getParcelable<ProductDetailModel>("product")!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // TODO: Rating bar
        super.onViewCreated(view, savedInstanceState)

        val ratingBar = view.findViewById<RatingBar>(R.id.product_ratingBar)
        ratingBar.setOnRatingBarChangeListener { _, _, _ ->
            Toast.makeText(context, "Rating = ${ratingBar.rating.toInt()}", Toast.LENGTH_SHORT).show()
        }

        intentToSite = Intent(Intent.ACTION_VIEW, Uri.parse(product.links[0]))
        val buyButton: Button = view.findViewById<TextView>(R.id.link_external_btn) as Button

        buyButton.setOnClickListener {
            if(product.links[0] != "") {
                startActivity(intentToSite)
            }

        }
    }
}