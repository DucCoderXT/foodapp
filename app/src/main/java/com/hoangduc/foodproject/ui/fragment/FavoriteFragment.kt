package com.hoangduc.foodproject.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hoangduc.foodproject.activities.HomeActivity
import com.hoangduc.foodproject.activities.MealActivity
import com.hoangduc.foodproject.adapter.FavoriteMealsAdapter
import com.hoangduc.foodproject.databinding.FragmentFavoriteBinding
import com.hoangduc.foodproject.pojo.Meal
import com.hoangduc.foodproject.utils.MealUtil
import com.hoangduc.foodproject.videomodel.HomeViewModel


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: HomeViewModel


    private lateinit var favoritesAdapter: FavoriteMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as HomeActivity).viewModel


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorite()
        onFavoriteMealClick()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            //xử lý chức năng vuốt delete left,right
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deleteMeal = favoritesAdapter.differ.currentList[position]

                viewModel.deleteMeal(deleteMeal)
                Snackbar.make(requireView(), "Meal deleted", Snackbar.LENGTH_SHORT).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.insertMeal(deleteMeal)
                    }
                ).show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorite)

    }

    private fun prepareRecyclerView() {
        favoritesAdapter = FavoriteMealsAdapter()
        binding.rvFavorite.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }
    }

    private fun observeFavorite() {
        viewModel.favoritesMealsLiveData.observe(viewLifecycleOwner, Observer { meals ->
            favoritesAdapter.differ.submitList(meals)
            favoritesAdapter.setFavoriteMeals(mealsList = meals as ArrayList<Meal>)
        })
    }

    private fun onFavoriteMealClick() {
        favoritesAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MealUtil.MEAL_ID, meal.idMeal)
            intent.putExtra(MealUtil.MEAL_Name, meal.strMeal)
            intent.putExtra(MealUtil.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

}