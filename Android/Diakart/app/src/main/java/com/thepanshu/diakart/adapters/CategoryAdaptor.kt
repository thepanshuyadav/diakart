package com.thepanshu.diakart.adapters

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.thepanshu.diakart.R
import com.thepanshu.diakart.models.CategoryModel

class CategoryAdapter (
    private val activity: Activity,
    private val categoryList: List<CategoryModel>,
    private val listener: OnCategoryClickListener
): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val categoryImageView: ImageView = itemView.findViewById(R.id.category_icon_image)
        private val categoryNameTextView = itemView.findViewById<TextView>(R.id.category_name_tv)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onCategoryClick(position)
            }
        }

        fun setCategoryIcon(categoryIcon: Uri) {
            GlideToVectorYou.justLoadImage(activity, categoryIcon, categoryImageView)
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
        val imageUri = categoryList[position].icon.toUri()
        holder.setCategoryIcon(imageUri)
        holder.setCategoryName(categoryList[position].title)

    }

    override fun getItemCount(): Int = categoryList.size

}