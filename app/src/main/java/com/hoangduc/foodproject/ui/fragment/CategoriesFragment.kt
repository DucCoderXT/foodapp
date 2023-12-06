package com.hoangduc.foodproject.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.hoangduc.foodproject.activities.CategoryMealsActivity
import com.hoangduc.foodproject.activities.HomeActivity
import com.hoangduc.foodproject.adapter.CategoriesAdapter
import com.hoangduc.foodproject.databinding.FragmentCategoriesBinding
import com.hoangduc.foodproject.pojo.Category
import com.hoangduc.foodproject.utils.MealUtil
import com.hoangduc.foodproject.videomodel.HomeViewModel

class CategoriesFragment : Fragment() {


    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as HomeActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observeCategories()
        onCategoriesClick()
    }

    private fun prepareRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategories() {
        viewModel.categoriesLiveData.observe(viewLifecycleOwner, Observer { categories ->
            categoriesAdapter.setCategoriesList(categoriesList = categories as ArrayList<Category>)
        })
    }

    private fun onCategoriesClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(MealUtil.CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }


}