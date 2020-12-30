package com.thepanshu.diakart.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thepanshu.diakart.R
import com.thepanshu.diakart.adapters.CategoryAdapter
import com.thepanshu.diakart.models.Category

@Suppress("UNREACHABLE_CODE")
class HomeFragment : Fragment(), CategoryAdapter.OnCategoryClickListener {

    val categoryList = arrayListOf<Category>(Category("Home","link"),
        Category("Home","link"),
        Category("Home","link"),
        Category("Home","link"), )

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val rvCategory = root.findViewById(R.id.categories_rv) as RecyclerView
        rvCategory.layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        rvCategory.adapter = CategoryAdapter(categoryList, this)

        return root
    }

    override fun onCategoryClick(position: Int) {
        Toast.makeText(activity, "Category", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}