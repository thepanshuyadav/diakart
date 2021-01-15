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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.thepanshu.diakart.R
import com.thepanshu.diakart.models.CategoryModel


class GridListAdapter(
    private val activity: Activity,
    private val listener: GridProductAdapter.OnCategoryGridProductClickListener,
    CategoryList: List<CategoryModel>)
    : RecyclerView.Adapter<GridListAdapter.GridListViewHolder>() {
    private val viewPool = RecycledViewPool()
    private val categoryList = CategoryList

    inner class GridListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryTitleTv: TextView = itemView
                .findViewById(
                        R.id.grid_category_title)
        val categoryTitleImageView: ImageView = itemView
            .findViewById(
                R.id.grid_category_imageView)

        val childRecyclerView: RecyclerView = itemView.findViewById(R.id.home_category_grid_view)

        fun setCategoryIcon(categoryIcon: Uri) {
            GlideToVectorYou.justLoadImage(activity, categoryIcon, categoryTitleImageView)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridListViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.home_grid_layout, parent, false)
        return GridListViewHolder(view)
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: GridListAdapter.GridListViewHolder, position: Int) {
        holder.categoryTitleTv.text = categoryList[position].title
        holder.setCategoryIcon(categoryList[position].icon.toUri())
        val childItemAdapter = GridProductAdapter(categoryList[position].preview, listener)
        holder.childRecyclerView.setRecycledViewPool(viewPool)
        val layoutManager = LinearLayoutManager(holder.childRecyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        layoutManager.initialPrefetchItemCount = categoryList[position].preview.size
        holder.childRecyclerView.layoutManager = layoutManager
        holder.childRecyclerView.adapter = childItemAdapter

    }
}





