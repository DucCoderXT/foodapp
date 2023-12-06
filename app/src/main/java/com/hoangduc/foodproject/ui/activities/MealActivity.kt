package com.hoangduc.foodproject.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.hoangduc.foodproject.R
import com.hoangduc.foodproject.databinding.ActivityMealBinding
import com.hoangduc.foodproject.db.MealDatabase
import com.hoangduc.foodproject.pojo.Meal
import com.hoangduc.foodproject.utils.MealUtil
import com.hoangduc.foodproject.videomodel.MealViewModel
import com.hoangduc.foodproject.videomodel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youTobeLink: String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMVVM: MealViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMealInformationFromIntent()

        setInformationInViews()

        observerMealDetailsLiveData()

        loadingCase()

        onYoutobeClick()

        onFavoriteClick()
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(MealUtil.MEAL_ID)!!
        mealName = intent.getStringExtra(MealUtil.MEAL_Name)!!
        mealThumb = intent.getStringExtra(MealUtil.MEAL_THUMB)!!
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    //Hàm lắng nghe dữ liệu khi có thay đổi nó sẽ response
    private var mealToSave: Meal? = null
    private fun observerMealDetailsLiveData() {

//        mealMVVM = ViewModelProvider(this)[MealViewModel::class.java]
        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMVVM = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]
        mealMVVM.getMealDetail(mealId)
        mealMVVM.mealDetailsLiveData.observe(this, object : Observer<Meal> {
            override fun onChanged(meal: Meal) {
                val mealx = meal
                mealToSave = mealx
                onResponseCase()
                binding.tvCategories.text = "Categories: ${meal.strCategory}"
                binding.tvArea.text = "Area: ${meal.strArea}"
                binding.tvInstructionsSt.text = meal.strInstructions
                youTobeLink = meal.strYoutube
            }
        })

    }

    //Trường hợp tải
    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFavorite.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.tvCategories.visibility = View.INVISIBLE
        binding.tvInstructionsSt.visibility = View.INVISIBLE
        binding.imgYT.visibility = View.INVISIBLE
    }

    //Trường hợp response
    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFavorite.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.tvCategories.visibility = View.VISIBLE
        binding.tvInstructionsSt.visibility = View.VISIBLE
        binding.imgYT.visibility = View.VISIBLE
    }

    private fun onYoutobeClick() {
        binding.imgYT.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youTobeLink))
            startActivity(intent)
        }
    }

    private fun onFavoriteClick() {

        binding.btnAddToFavorite.setOnClickListener {
            mealToSave?.let {
                mealMVVM.insertMeal(it)
                Toast.makeText(this, "Meal saved", Toast.LENGTH_LONG).show()
            }
        }
    }
}