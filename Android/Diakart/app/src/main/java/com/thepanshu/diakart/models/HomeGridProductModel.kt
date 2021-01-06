package com.thepanshu.diakart.models

import com.thepanshu.diakart.R
import kotlinx.android.synthetic.main.category_item.*

data class HomeGridProductModel(
    val productImage: Int = R.drawable.ic_baseline_notifications_24,
    val productTitle: String = "",
    val productPrice: String = "",
    val productBrand: String = ""
)
