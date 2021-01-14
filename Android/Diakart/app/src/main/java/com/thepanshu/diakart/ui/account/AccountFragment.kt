package com.thepanshu.diakart.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.GridProductAdapter
import com.thepanshu.diakart.authenticate.RegisterActivity
import com.thepanshu.diakart.data.Product


class AccountFragment : Fragment(), GridProductAdapter.OnCategoryGridProductClickListener {
    private lateinit var gridCategoryList: ArrayList<Product>
    private val desc = "Chocolate is a preparation of roasted and ground cacao seeds that is made in the form of a liquid, paste, or in a block, which may also be used as a flavoring ingredient in other foods. The earliest signs of use are associated with Olmec sites (within what would become Mexico’s post-colonial territory) suggesting consumption of chocolate beverages, dating from the 19th century BC.[1][2] The majority of Mesoamerican people made chocolate beverages, including the Maya and Aztecs.[3] The English word \"chocolate\" comes, via Spanish, from the Classical Nahuatl word xocolātl.[4]\n" +
            "\n" +
            "The seeds of the cacao tree have an intense bitter taste and must be fermented to develop the flavor. After fermentation, the beans are dried, cleaned, and roasted. The shell is removed to produce cacao nibs, which are then ground to cocoa mass, unadulterated chocolate in rough form. Once the cocoa mass is liquefied by heating, it is called chocolate liquor. The liquor may also be cooled and processed into its two components: cocoa solids and cocoa butter. Baking chocolate, also called bitter chocolate, contains cocoa solids and cocoa butter in varying proportions, without any added sugar. Powdered baking cocoa, which contains more fiber than cocoa butter, can be processed with alkali to produce dutch cocoa. Much of the chocolate consumed today is in the form of sweet chocolate, a combination of cocoa solids, cocoa butter or added vegetable oils, and sugar. Milk chocolate is sweet chocolate that additionally contains milk powder or condensed milk. White chocolate contains cocoa butter, sugar, and milk, but no cocoa solids."

    companion object {
        fun newInstance() = AccountFragment()
    }

    private lateinit var viewModel: AccountViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(R.layout.account_fragment, container, false)
        val wishListGrid = root.findViewById<TextView>(R.id.grid_category_title)
        wishListGrid.text = "My Wish List"
        val openWishListButton = root.findViewById<Button>(R.id.view_all_btn)
        openWishListButton.setOnClickListener {
            val navController = view?.let { Navigation.findNavController(it) }
            navController?.navigate(R.id.action_nav_account_to_nav_wishlist)
        }
        val rvGrid = root.findViewById<RecyclerView>(R.id.home_category_grid_view)
        rvGrid.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
        rvGrid.adapter = GridProductAdapter(gridCategoryList, this)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val signOutButton = view.findViewById<Button>(R.id.signout_btn)
        signOutButton.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            startActivity(Intent(activity, RegisterActivity::class.java))
            requireActivity().finish()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gridCategoryList = ArrayList()
        val productImages = arrayListOf(R.drawable.ic_baseline_favorite_24, R.drawable.ic_baseline_favorite_24, R.drawable.ic_baseline_favorite_24, R.drawable.ic_baseline_favorite_24)
        gridCategoryList.add(
                Product(1, "Almond Chocolate", "50g", 50,
                        "Dairy Milk", "Chocolate", product_images = productImages,
                        "https://www.flipkart.com", desc)
        )
        gridCategoryList.add(
                Product(1, "Almond Chocolate", "50g", 50,
                        "Dairy Milk", "Chocolate", product_images = productImages,
                        "https://www.flipkart.com", desc)
        )
        gridCategoryList.add(
                Product(1, "Almond Chocolate", "50g", 50,
                        "Dairy Milk", "Chocolate", product_images = productImages,
                        "https://www.flipkart.com", desc)
        )
        gridCategoryList.add(
                Product(1, "Almond Chocolate", "50g", 50,
                        "Dairy Milk", "Chocolate", product_images = productImages,
                        "https://www.flipkart.com", desc)
        )
        gridCategoryList.add(
                Product(1, "Almond Chocolate", "50g", 50,
                        "Dairy Milk", "Chocolate", product_images = productImages,
                        "https://www.flipkart.com", desc)
        )
        gridCategoryList.add(
                Product(1, "Almond Chocolate", "50g", 50,
                        "Dairy Milk", "Chocolate", product_images = productImages,
                        "https://www.flipkart.com", desc)
        )

        gridCategoryList.add(
                Product(1, "Almond Chocolate", "50g", 50,
                        "Dairy Milk", "Chocolate", product_images = productImages,
                        "https://www.flipkart.com", desc)
        )
    }

    override fun onCategoryGridProductClick(position: Int) {
        val bundle = bundleOf("product" to gridCategoryList[position])
        val navController = view?.let { Navigation.findNavController(it) }
        navController?.navigate(R.id.action_nav_account_to_productDetailFragment, bundle)
    }

}