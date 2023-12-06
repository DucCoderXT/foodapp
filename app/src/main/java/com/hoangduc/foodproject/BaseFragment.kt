package com.hoangduc.foodproject

import androidx.fragment.app.Fragment
import com.hoangduc.foodproject.db.MealDatabase
import com.hoangduc.foodproject.locallogindata.local.LocalLoginDataSource
import com.hoangduc.foodproject.repository.LoginRepository

abstract class BaseFragment : Fragment() {
    private val database by lazy { MealDatabase.getInstance(requireContext()) }
    protected val repository by lazy {
        LoginRepository(dataSource = LocalLoginDataSource(database.userDao()))
    }
}