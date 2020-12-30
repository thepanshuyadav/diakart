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
import com.thepanshu.diakart.models.Category

class CategoryAdapter (
    private val categoryList: ArrayList<Category>,
    private val listener: OnCategoryClickListener
): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val categoryImageView: ImageView = itemView.findViewById(R.id.category_icon_image)
        val categoryNameTextView = itemView.findViewById<TextView>(R.id.category_name_tv)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onCategoryClick(position)
            }
        }

        fun setCategoryName(categoryName: String) {
            categoryNameTextView.text = categoryName
        }
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
//        Picasso.get()
//            .load(categoryList[position].categoryImageLink)
//            .into(holder.categoryImageView, object: com.squareup.picasso.Callback {
//                override fun onSuccess() {
//                    //set animations here
//                }
//
//                override fun onError(e: java.lang.Exception?) {
//                    //do smth when there is picture loading error
//                }
//            })

        holder.setCategoryName(categoryList[position].categoryName)

    }

    override fun getItemCount(): Int = categoryList.size

}