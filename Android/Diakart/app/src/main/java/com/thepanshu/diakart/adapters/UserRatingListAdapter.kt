package com.thepanshu.diakart.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thepanshu.diakart.R
import com.thepanshu.diakart.models.UserRatingModel

class UserRatingListAdapter(
        private val userRatingList: List<UserRatingModel>,
        private val listener: OnProductClickListener,
        val context: Context
):
        RecyclerView.Adapter<UserRatingListAdapter.ProductRatingListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductRatingListViewHolder {

        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.rating_list_item, parent, false)
        return ProductRatingListViewHolder(view)
    }

    override fun getItemCount(): Int  = userRatingList.size

    override fun onBindViewHolder(holder: ProductRatingListViewHolder, position: Int) {
        holder.productNameTv.text = userRatingList[position].name
        holder.brandNameTv.text = userRatingList[position].brand
        holder.productQuantityTv.text = userRatingList[position].quantity
        holder.productPriceTv.text = userRatingList[position].mrp.toString()
        holder.setProductImage(userRatingList[position].image)
        holder.productRatingView.rating = userRatingList[position].rating.toFloat()
        //TODO: Picasso to update  product image placeholder
    }

    inner class ProductRatingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val productNameTv: TextView = itemView.findViewById(R.id.product_name_tv)
        val brandNameTv: TextView = itemView.findViewById(R.id.brand_name_tv)
        val productPriceTv: TextView = itemView.findViewById(R.id.product_price_tv)
        val productQuantityTv: TextView = itemView.findViewById(R.id.product_quantity_tv)
        val productImageView: ImageView = itemView.findViewById(R.id.rating_list_item_image)
        val productRatingView: RatingBar = itemView.findViewById(R.id.rating_list_item_ratings_layout)


        init {
            //itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onProductClick(position)
            }
        }

        fun setProductImage(imageSrc: String) {
            Glide.with(context).load(Uri.parse(imageSrc)).into(productImageView)
        }

    }

    interface OnProductClickListener {
        fun onProductClick(position: Int)
    }

}