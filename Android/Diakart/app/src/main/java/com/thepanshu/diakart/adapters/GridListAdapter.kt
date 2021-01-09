package com.thepanshu.diakart.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.thepanshu.diakart.R
import com.thepanshu.diakart.models.CategoryModel


class GridListAdapter(
        private val listener: GridProductAdapter.OnCategoryGridProductClickListener,
        CategoryList: ArrayList<CategoryModel>)
    : RecyclerView.Adapter<GridListAdapter.GridListViewHolder>() {
    private val viewPool = RecycledViewPool()
    private val categoryList = CategoryList

    inner class GridListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryTitleTv: TextView = itemView
                .findViewById(
                        R.id.grid_category_title)
        val childRecyclerView: RecyclerView = itemView.findViewById(R.id.home_category_grid_view)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridListViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = li.inflate(R.layout.home_grid_layout, parent, false)
        return GridListViewHolder(view)
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: GridListAdapter.GridListViewHolder, position: Int) {
        holder.categoryTitleTv.text = categoryList[position].category.title

        val childItemAdapter = GridProductAdapter(categoryList[position].productList, listener)
        holder.childRecyclerView.setRecycledViewPool(viewPool)
        val layoutManager = LinearLayoutManager(holder.childRecyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        layoutManager.initialPrefetchItemCount = categoryList[position].productList.size
        holder.childRecyclerView.layoutManager = layoutManager
        holder.childRecyclerView.adapter = childItemAdapter

    }
}





