package com.hoangduc.foodproject.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.hoangduc.foodproject.activities.HomeActivity
import com.hoangduc.foodproject.activities.MealActivity
import com.hoangduc.foodproject.adapter.FavoriteMealsAdapter
import com.hoangduc.foodproject.databinding.FragmentSearchBinding
import com.hoangduc.foodproject.pojo.Meal
import com.hoangduc.foodproject.utils.MealUtil
import com.hoangduc.foodproject.videomodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel



    //sử dụng lại adapter favorite
    private lateinit var searchRecyclerViewAdapter: FavoriteMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as HomeActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()


        binding.imgSearchDone.setOnClickListener {
            searchMeals()
        }


        observeSearchedMealsLiveData()

        //search,
        var searchJob: Job? = null
        binding.edSearchBox.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500) //Thời gian trễ gọi api
                viewModel.searchMeals(searchQuery.toString())
            }
        }

        onDetailMealSearchClick()
    }

    private fun prepareRecyclerView() {
        searchRecyclerViewAdapter = FavoriteMealsAdapter()
        binding.rvSearchedMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = searchRecyclerViewAdapter
        }
    }

    private fun searchMeals() {
        val searchQuery = binding.edSearchBox.text.toString()
        if (searchQuery.isNotEmpty()) {
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun observeSearchedMealsLiveData() {
        viewModel.searchedMealLiveData.observe(viewLifecycleOwner, Observer { mealsList ->
            searchRecyclerViewAdapter.differ.submitList(mealsList)
            //set data cho từng position item
            searchRecyclerViewAdapter.setFavoriteMeals(mealsList = mealsList as ArrayList<Meal>)
        })
    }

    private fun onDetailMealSearchClick(){
        searchRecyclerViewAdapter.onItemClick = {
            meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MealUtil.MEAL_ID, meal.idMeal)
            intent.putExtra(MealUtil.MEAL_Name, meal.strMeal)
            intent.putExtra(MealUtil.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

}