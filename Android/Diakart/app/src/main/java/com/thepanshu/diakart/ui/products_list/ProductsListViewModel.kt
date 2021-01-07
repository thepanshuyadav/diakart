package com.thepanshu.diakart.ui.products_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thepanshu.diakart.R
import com.thepanshu.diakart.models.Product
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class ProductsListViewModel : ViewModel() {

    private var productsList: MutableLiveData<List<Product>>? = null
    private var isFetching = MutableLiveData<Boolean>().apply {
        value = false
    }

    internal fun getProductsList(): MutableLiveData<List<Product>> {
        if (productsList == null) {
            productsList = MutableLiveData()
            isFetching.postValue(true)
            loadProductsList()
            isFetching.postValue(false)
        }
        return productsList as MutableLiveData<List<Product>>
    }

    internal fun isFetchingResult(): MutableLiveData<Boolean> {
        return isFetching
    }

    private fun loadProductsList() {
        // do async operation to fetch users
        Timer("SettingUp", false).schedule(5000) {
            val fetchedProductsList = ArrayList<Product>()
            val desc = "Chocolate is a preparation of roasted and ground cacao seeds that is made in the form of a liquid, paste, or in a block, which may also be used as a flavoring ingredient in other foods. The earliest signs of use are associated with Olmec sites (within what would become Mexico’s post-colonial territory) suggesting consumption of chocolate beverages, dating from the 19th century BC.[1][2] The majority of Mesoamerican people made chocolate beverages, including the Maya and Aztecs.[3] The English word \"chocolate\" comes, via Spanish, from the Classical Nahuatl word xocolātl.[4]\n" +
                    "\n" +
                    "The seeds of the cacao tree have an intense bitter taste and must be fermented to develop the flavor. After fermentation, the beans are dried, cleaned, and roasted. The shell is removed to produce cacao nibs, which are then ground to cocoa mass, unadulterated chocolate in rough form. Once the cocoa mass is liquefied by heating, it is called chocolate liquor. The liquor may also be cooled and processed into its two components: cocoa solids and cocoa butter. Baking chocolate, also called bitter chocolate, contains cocoa solids and cocoa butter in varying proportions, without any added sugar. Powdered baking cocoa, which contains more fiber than cocoa butter, can be processed with alkali to produce dutch cocoa. Much of the chocolate consumed today is in the form of sweet chocolate, a combination of cocoa solids, cocoa butter or added vegetable oils, and sugar. Milk chocolate is sweet chocolate that additionally contains milk powder or condensed milk. White chocolate contains cocoa butter, sugar, and milk, but no cocoa solids."
            val productImages = arrayListOf(R.drawable.ic_baseline_account_circle_24, R.drawable.ic_baseline_add_circle_24, R.drawable.ic_baseline_favorite_24, R.drawable.ic_baseline_person_24)
            fetchedProductsList.add(
                    Product(1, "Almond Chocolate","50g", 50,
                            "Dairy Milk", "Chocolate",product_images = productImages,
                            "https://www.flipkart.com", desc)
            )
            fetchedProductsList.add(
                    Product(1, "Almond Chocolate","50g", 50,
                            "Dairy Milk", "Chocolate",product_images = productImages,
                            "https://www.flipkart.com", desc)
            )
            fetchedProductsList.add(
                    Product(1, "Almond Chocolate","50g", 50,
                            "Dairy Milk", "Chocolate",product_images = productImages,
                            "https://www.flipkart.com", desc)
            )
            fetchedProductsList.add(
                    Product(1, "Almond Chocolate","50g", 50,
                            "Dairy Milk", "Chocolate",product_images = productImages,
                            "https://www.flipkart.com", desc)
            )
            fetchedProductsList.add(
                    Product(1, "Almond Chocolate","50g", 50,
                            "Dairy Milk", "Chocolate",product_images = productImages,
                            "https://www.flipkart.com", desc)
            )
            fetchedProductsList.add(
                    Product(1, "Almond Chocolate","50g", 50,
                            "Dairy Milk", "Chocolate",product_images = productImages,
                            "https://www.flipkart.com", desc)
            )
            fetchedProductsList.add(
                    Product(1, "Almond Chocolate","50g", 50,
                            "Dairy Milk", "Chocolate",product_images = productImages,
                            "https://www.flipkart.com", desc)
            )
            productsList!!.postValue(fetchedProductsList)

        }

    }
}