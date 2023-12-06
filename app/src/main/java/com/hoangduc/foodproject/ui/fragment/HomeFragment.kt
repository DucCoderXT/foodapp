package com.hoangduc.foodproject.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hoangduc.foodproject.R
import com.hoangduc.foodproject.activities.CategoryMealsActivity
import com.hoangduc.foodproject.activities.HomeActivity
import com.hoangduc.foodproject.activities.MealActivity
import com.hoangduc.foodproject.adapter.CategoriesAdapter
import com.hoangduc.foodproject.adapter.MostPopularAdapter
import com.hoangduc.foodproject.databinding.FragmentHomeBinding
import com.hoangduc.foodproject.pojo.Category
import com.hoangduc.foodproject.pojo.Meal
import com.hoangduc.foodproject.pojo.MealsByCategory
import com.hoangduc.foodproject.utils.MealUtil
import com.hoangduc.foodproject.videomodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Recheck
        viewModel = (activity as HomeActivity).viewModel
        popularItemAdapter = MostPopularAdapter()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.getRandomMeal()
        viewModel.getPopularItems()
        viewModel.getCategories()

        observerRandomMeal()
        onRandomMealClick()

        preparePopularMealsRecyclerView()
        observerPopularItemsLiveData()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        observerCategoriesLiveData()
        onCategoryClick()

        onSearchIconClick()


    }


    //random Meal
    private fun observerRandomMeal() {
        viewModel.randomMealLiveData.observe(
            viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal.strMealThumb)
                .into(binding.imgRandomMeal)

            //lấy thông tin về this meal
            this.randomMeal = meal
        }
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MealUtil.MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MealUtil.MEAL_Name, randomMeal.strMeal)
            intent.putExtra(MealUtil.MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularMealsRecyclerView() {
        //thiết lập bố cục
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemAdapter

        }
    }

    private fun observerPopularItemsLiveData() {
        viewModel.popularItemsLiveData.observe(viewLifecycleOwner) { mealList ->
            popularItemAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)
        }
    }

    private fun onPopularItemClick() {
        popularItemAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MealUtil.MEAL_ID, meal.idMeal)
            intent.putExtra(MealUtil.MEAL_Name, meal.strMeal)
            intent.putExtra(MealUtil.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observerCategoriesLiveData() {
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.setCategoriesList(categoriesList = categories as ArrayList<Category>)

        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(MealUtil.CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

    }


}
