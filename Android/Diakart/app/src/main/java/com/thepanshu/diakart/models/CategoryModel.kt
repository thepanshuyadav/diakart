package com.thepanshu.diakart.models

import com.thepanshu.diakart.data.Product

data class CategoryModel(
        val icon: String = "",
        val title: String = "",
        val productList :ArrayList<Product>
)