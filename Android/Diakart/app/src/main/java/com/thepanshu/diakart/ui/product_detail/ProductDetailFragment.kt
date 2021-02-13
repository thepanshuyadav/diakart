package com.thepanshu.diakart.ui.product_detail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.ImageListAdapter
import com.thepanshu.diakart.models.ProductDetailModel
import com.thepanshu.diakart.models.UserRatingModel


class ProductDetailFragment : Fragment() {
    private lateinit var product: ProductDetailModel

    //TODO: View all site prices
    private lateinit var intentToSite: Intent
    private lateinit var link: String
    private var oldRating: Int = -1

    private lateinit var ratingBar: RatingBar
    private lateinit var productRatings : ArrayList<Int>
    private var rating = MutableLiveData<Int>()

    private lateinit var addWishListButton: Button
    private lateinit var removeWishListButton: Button
    private lateinit var viewModel: ProductDetailViewModel

    private lateinit var progressBar: ProgressBar
    private var progressBarUsers: Int = 0

    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var toolbar: CollapsingToolbarLayout
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_product_detail, container, false)
        viewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)

        val mAdView = rootView.findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val mAdView2 = rootView.findViewById<AdView>(R.id.adView2)
        val adRequest2 = AdRequest.Builder().build()
        mAdView2.loadAd(adRequest2)


        var adRequest3 = AdRequest.Builder().build()

        InterstitialAd.load(requireContext(),"ca-app-pub-5665855701538045/3492547900", adRequest3, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d("Ad", "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })



        coordinatorLayout = rootView.findViewById<CoordinatorLayout>(R.id.coordinator)
        toolbar = rootView.findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        Glide.with(this)
            .asBitmap()
            .load(product.images[0])
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(
                        resource: Bitmap,
                        transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    val palette = Palette.from(resource).generate()
                    val darkVibrant = palette.darkVibrantSwatch?.rgb
                    val vibrant = palette.vibrantSwatch?.rgb
                    val gradientDrawable = GradientDrawable()
                    if (vibrant != null && darkVibrant != null) {
                        gradientDrawable.colors = intArrayOf(vibrant, vibrant,  darkVibrant)
                    } else {
                        gradientDrawable.colors = intArrayOf(
                                Color.TRANSPARENT,
                                Color.TRANSPARENT
                        )
                    }
                    gradientDrawable.orientation = GradientDrawable.Orientation.LEFT_RIGHT
                    toolbar.background = gradientDrawable
                    coordinatorLayout.background = gradientDrawable
                }
            })



        progressBar = rootView.findViewById(R.id.progressBar)

        val rvProductImage = rootView.findViewById(R.id.specific_product_image_rv) as RecyclerView
        rvProductImage.layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
        )
        rvProductImage.adapter = ImageListAdapter(product.images, requireContext())
        val productNameTv = rootView.findViewById<TextView>(R.id.specific_product_name)
        val brandNameTv = rootView.findViewById<TextView>(R.id.specific_product_brand_name)
        val productDescTv = rootView.findViewById<TextView>(R.id.specific_product_desc)
        val productQuantityTv = rootView.findViewById<TextView>(R.id.specific_product_quantity)
        productNameTv.text = product.name
        brandNameTv.text = product.brand
        productDescTv.text = product.description

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
        if(totalRatings == 0) {

            averageRatingTv.text = getString(R.string.no_ratings)
        }
        else {
            averageRatingTv.text = String.format("%.1f", average).toDouble().toString()
        }
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
            rating.postValue(ratingBar.rating.toInt())
        }
        viewModel.getRating(product.documentId).observe(viewLifecycleOwner, {
            addProgressBarUser()
            if (it != null) {
                oldRating = it
                ratingBar.rating = it.toFloat()
            }
            removeProgressBarUser()
        })
        rating.observe(viewLifecycleOwner, {
            addProgressBarUser()
            val rating = UserRatingModel(
                    it,
                    product.name,
                    product.quantity,
                    product.mrp,
                    product.brand,
                    product.images[0],
                    product.documentId
            )
            viewModel.setProductRating(rating, product.documentId)
            removeProgressBarUser()
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
                addProgressBarUser()
                if (it != null) {
                    if (it != true) {
                        Snackbar.make(
                                requireView(),
                                getString(R.string.added_wishlist),
                                Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
                removeProgressBarUser()
            })
        }

        removeWishListButton.setOnClickListener {
            viewModel.removeFromWishList(product.documentId).observe(viewLifecycleOwner, {
                addProgressBarUser()
                if (it != null) {
                    if (it != false) {
                        Snackbar.make(
                                requireView(),
                                getString(R.string.removed_wishlist),
                                Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
                removeProgressBarUser()
            })
        }

    }

    private fun addProgressBarUser() {
        progressBarUsers += 1
        progressBar.visibility = View.VISIBLE
    }

    private fun removeProgressBarUser() {
        progressBarUsers -= 1
        if(progressBarUsers <= 0) {
            progressBarUsers = 0
            progressBar.visibility = View.GONE
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
        super.onViewCreated(view, savedInstanceState)
        var price = Int.MAX_VALUE
        link = ""
        for(i in 0 .. product.prices.size-1) {
            if(product.prices[i]<price) {
                price = product.prices[i]
                link = product.links[i]
            }
        }
        val productMRPTv = view.findViewById<TextView>(R.id.specific_product_price)
        productMRPTv.text = "RS ${price}"
        intentToSite = Intent(Intent.ACTION_VIEW, Uri.parse(link))

        val buyButton: Button = view.findViewById<TextView>(R.id.link_external_btn) as Button

        buyButton.setOnClickListener {
            if(link!="") {
                MaterialAlertDialogBuilder(requireContext())
                        .setTitle(resources.getString(R.string.alert_title))
                        .setMessage(resources.getString(R.string.supporting_text))
                        .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->

                        }
                        .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                            mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                                override fun onAdDismissedFullScreenContent() {
                                    startActivity(intentToSite)
                                }

                                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                                    startActivity(intentToSite)
                                }
                            }
                            if (mInterstitialAd != null) {
                                mInterstitialAd!!.show(requireActivity());
                            } else {
                                startActivity(intentToSite)
                            }
                        }
                        .show()
            } else {
                Snackbar.make(view, "Invalid Link", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}