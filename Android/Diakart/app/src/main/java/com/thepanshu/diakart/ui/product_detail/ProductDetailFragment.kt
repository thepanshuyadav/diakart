package com.thepanshu.diakart.ui.product_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.snackbar.Snackbar
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.ImageListAdapter
import com.thepanshu.diakart.models.ProductDetailModel
import com.thepanshu.diakart.models.UserRatingModel

class ProductDetailFragment : Fragment() {
    private lateinit var product: ProductDetailModel

    private lateinit var intentToSite: Intent

    private lateinit var ratingBar: RatingBar
    private lateinit var productRatings : ArrayList<Int>
    private var rating = MutableLiveData<Int>()

    private lateinit var addWishListButton: Button
    private lateinit var removeWishListButton: Button
    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_product_detail, container, false)
        viewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)

        val mAdView = rootView.findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        val rvProductImage = rootView.findViewById(R.id.specific_product_image_rv) as RecyclerView
        rvProductImage.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvProductImage.adapter = ImageListAdapter(product.images, requireContext())

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

        // MARK: Rating
        val averageRatingTv = rootView.findViewById<TextView>(R.id.average_rating)
        val totalRatingsTv = rootView.findViewById<TextView>(R.id.total_raters)
        val fiveRatingNo = rootView.findViewById<TextView>(R.id.number_of_five_ratings)
        val fiveRatingBar = rootView.findViewById<ProgressBar>(R.id.five_rating_progressBar)

        val fourRatingNo = rootView.findViewById<TextView>(R.id.number_of_four_ratings)
        val fourRatingBar = rootView.findViewById<ProgressBar>(R.id.four_rating_progressBar)

        val threeRatingNo = rootView.findViewById<TextView>(R.id.number_of_three_ratings)
        val threeRatingBar = rootView.findViewById<ProgressBar>(R.id.three_rating_progressBar)

        val twoRatingNo = rootView.findViewById<TextView>(R.id.number_of_two_ratings)
        val twoRatingBar = rootView.findViewById<ProgressBar>(R.id.two_rating_progressBar)

        val oneRatingNo = rootView.findViewById<TextView>(R.id.number_of_one_ratings)
        val oneRatingBar = rootView.findViewById<ProgressBar>(R.id.one_rating_progressBar)

        var totalRatings = 0
        for(i in productRatings) {
            totalRatings += i
        }
        totalRatingsTv.text = totalRatings.toString()
        val average = ((productRatings[0] + productRatings[1]*2
                +productRatings[2]*3 +productRatings[3]*4 +productRatings[4]*5).toDouble()/totalRatings)

        averageRatingTv.text = String.format("%.2f", average).toDouble().toString()
        oneRatingNo.text = productRatings[0].toString()
        oneRatingBar.progress = (((productRatings[0].toDouble()/totalRatings.toDouble()))*100).toInt()

        twoRatingNo.text = productRatings[1].toString()
        twoRatingBar.progress = ((productRatings[1].toDouble()/totalRatings)*100).toInt()

        threeRatingNo.text = productRatings[2].toString()
        threeRatingBar.progress = ((productRatings[2].toDouble()/totalRatings)*100).toInt()

        fourRatingNo.text = productRatings[3].toString()
        fourRatingBar.progress = ((productRatings[3].toDouble()/totalRatings)*100).toInt()

        fiveRatingNo.text = productRatings[4].toString()
        fiveRatingBar.progress = ((productRatings[4].toDouble()/totalRatings)*100).toInt()

        ratingBar = rootView.findViewById(R.id.product_ratingBar)
        ratingBar.setOnRatingBarChangeListener { _, _, _ ->
            rating.value = ratingBar.rating.toInt()
            Toast.makeText(context, "Rating = ${ratingBar.rating.toDouble()}", Toast.LENGTH_SHORT).show()
        }
        viewModel.getRating(product.documentId).observe(viewLifecycleOwner, {
            ratingBar.rating = it.toFloat()
        })
        rating.observe(viewLifecycleOwner, {
            val rating = UserRatingModel(it, product.name, product.quantity, product.mrp, product.brand, product.images[0], product.documentId)
            viewModel.setProductRating(rating, product.documentId)
        })
        // MARK: Rating End
        addWishListButton = rootView.findViewById(R.id.add_wish_list_btn)
        removeWishListButton = rootView.findViewById(R.id.remove_wish_list_btn)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addWishListButton.setOnClickListener {
            viewModel.addToWishList(product).observe(viewLifecycleOwner, {
                if(it!=null) {
                    if(it != true) {
                        Snackbar.make(requireView(), "Added to wish list! ♥️", Snackbar.LENGTH_SHORT).show()
                    }
                }
            })
        }

        removeWishListButton.setOnClickListener {
            viewModel.removeFromWishList(product.documentId).observe(viewLifecycleOwner, {
                if(it!=null) {
                    if(it != false) {
                        Snackbar.make(requireView(), "Removed from wish list! 💔", Snackbar.LENGTH_SHORT).show()
                    }
                }
            })
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments?.getParcelable<ProductDetailModel>("product") != null){
            product = arguments?.getParcelable<ProductDetailModel>("product")!!
            productRatings = product.rating
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // TODO: Rating bar
        super.onViewCreated(view, savedInstanceState)

        intentToSite = Intent(Intent.ACTION_VIEW, Uri.parse(product.links[0]))
        val buyButton: Button = view.findViewById<TextView>(R.id.link_external_btn) as Button

        buyButton.setOnClickListener {
            if(product.links[0] != "") {
                startActivity(intentToSite)
            }
        }
    }
}