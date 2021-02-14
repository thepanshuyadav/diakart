package com.thepanshu.diakart.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.thepanshu.diakart.R
import com.thepanshu.diakart.models.ProductDetailModel

class GridProductAdapter (
        private val gridIdx: Int,
        private val categoryProductList: ArrayList<ProductDetailModel>,
        private val listener: OnCategoryGridProductClickListener,
        val context: Context
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
            productSubtitleTv.text = "RS $productSubtitle"
        }
        fun setProductImage(productImageLink: String) {
            Glide.with(context).load(Uri.parse(productImageLink)).into(productImageView)
        }
        fun setBackground(productImageLink: String) {
            Glide.with(context)
                    .asBitmap()
                    .load(productImageLink)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onLoadCleared(placeholder: Drawable?) {

                        }

                        override fun onResourceReady(
                                resource: Bitmap,
                                transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                        ) {
                            val palette = Palette.from(resource).generate()
                            val swatch = palette.vibrantSwatch
                            val color = swatch?.rgb
                            val gradientDrawable = GradientDrawable()
                            if(color!=null) {
                                gradientDrawable.colors = intArrayOf(color, color)
                            }
                            else {
                                gradientDrawable.colors = intArrayOf(
                                        Color.TRANSPARENT,
                                        Color.TRANSPARENT
                                )
                            }
                            productImageView.background = gradientDrawable

                        }
                    })
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onCategoryGridProductClick(gridIdx, position)
            }
        }

    }

    interface OnCategoryGridProductClickListener {
        fun onCategoryGridProductClick(gridIdx: Int, position: Int)
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
        holder.setBackground(categoryProductList[position].images[0])

    }

    override fun getItemCount(): Int = 4

}