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
import com.thepanshu.diakart.models.Product


class ProductsListAdapter(
    private val productsList: ArrayList<Product>,
    private val listener: OnProductClickListener
):
    RecyclerView.Adapter<ProductsListAdapter.ProductsListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsListViewHolder {

        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.products_list_item, parent, false)
        return ProductsListViewHolder(view)
    }

    override fun getItemCount(): Int  = productsList.size

    override fun onBindViewHolder(holder: ProductsListViewHolder, position: Int) {
        holder.productNameTv.text = productsList[position].product_name
        holder.brandNameTv.text = productsList[position].brand_name
        holder.productQuantityTv.text = productsList[position].quantity
        holder.productPriceTv.text = productsList[position].price.toString()
        //TODO: Picasso to update  product image placeholder
        // ERROR: Different shimmer size than object
//        Picasso.get()
//            .load(productsList[position].product_images[0])
//            .into(holder.productListImageView, object: com.squareup.picasso.Callback {
//                override fun onSuccess() {
//                    //set animations here
//                }
//
//                override fun onError(e: java.lang.Exception?) {
//                    //do smth when there is picture loading error
//                }
//            })
        holder.productListImageView.setImageResource(productsList[position].product_images[0])
    }

    inner class ProductsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val productNameTv: TextView = itemView.findViewById(R.id.product_name_tv)
        val brandNameTv: TextView = itemView.findViewById(R.id.brand_name_tv)
        val productPriceTv: TextView = itemView.findViewById(R.id.product_price_tv)
        val productQuantityTv: TextView = itemView.findViewById(R.id.product_quantity_tv)
        val productListImageView: ImageView = itemView.findViewById(R.id.products_list_item_image)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onProductClick(position)
            }
        }

    }

    interface OnProductClickListener {
        fun onProductClick(position: Int)
    }

}