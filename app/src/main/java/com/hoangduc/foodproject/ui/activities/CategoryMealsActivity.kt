package com.hoangduc.foodproject.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.hoangduc.foodproject.adapter.CategoryMealsAdapter
import com.hoangduc.foodproject.databinding.ActivityCategoryMealsBinding
import com.hoangduc.foodproject.utils.MealUtil
import com.hoangduc.foodproject.videomodel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var categoryMealsViewModel: CategoryMealsViewModel
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRecycleView()
        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]
        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(MealUtil.CATEGORY_NAME)!!)
        categoryMealsViewModel.mealsLiveData.observe(this, Observer { mealsList ->
            binding.tvCategoriesCount.text = "Number of foods: " + mealsList.size.toString()
            categoryMealsAdapter.setMealsList(mealsList)
        })
        onDetailMealCategoryClick()
    }


    private fun prepareRecycleView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }

    private fun onDetailMealCategoryClick() {
        categoryMealsAdapter.onClick= {meal->
            val intent = Intent(this, MealActivity::class.java)
            intent.putExtra(MealUtil.MEAL_ID, meal.idMeal)
            intent.putExtra(MealUtil.MEAL_Name, meal.strMeal)
            intent.putExtra(MealUtil.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }


}