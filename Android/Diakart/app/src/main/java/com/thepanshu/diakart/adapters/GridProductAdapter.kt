package com.thepanshu.diakart.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thepanshu.diakart.R
import com.thepanshu.diakart.data.Product
import com.thepanshu.diakart.models.ProductDetailModel

class GridProductAdapter (
        private val categoryProductList: ArrayList<ProductDetailModel>,
        private val listener: OnCategoryGridProductClickListener
        ): RecyclerView.Adapter<GridProductAdapter.CategoryProductViewHolder>() {

    inner class CategoryProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val productImageView: ImageView = itemView.findViewById(R.id.grid_product_image)
        private val productTitleTv = itemView.findViewById<TextView>(R.id.grid_product_title)
        private val productSubtitleTv = itemView.findViewById<TextView>(R.id.grid_product_subtitle)

        init {
            itemView.setOnClickListener(this)
        }

        fun setProductTitle(productTitle: String) {
            productTitleTv.text = productTitle
        }
        fun setProductSubtitle(productSubtitle: Int) {
            productSubtitleTv.text = productSubtitle.toString()
        }
        fun setProductImage(productImageLink: String) {
            Picasso.get().load(productImageLink).into(productImageView)
            //productImageView.setImageResource(productResId)
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onCategoryGridProductClick(position)
            }
        }

    }

    interface OnCategoryGridProductClickListener {
        fun onCategoryGridProductClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.home_grid_view_product, parent, false)
        return CategoryProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryProductViewHolder, position: Int) {
        holder.setProductImage(categoryProductList[position].images[0])
        holder.setProductTitle(categoryProductList[position].name)
        holder.setProductSubtitle(categoryProductList[position].mrp)

    }

    override fun getItemCount(): Int = 4

}