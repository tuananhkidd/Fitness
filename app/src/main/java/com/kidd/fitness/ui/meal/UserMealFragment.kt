package com.kidd.fitness.ui.meal

import android.util.Log
import androidx.lifecycle.Observer
import com.kidd.fitness.R
import com.kidd.fitness.base.BaseFragment
import com.kidd.fitness.entity.UserMeal
import com.kidd.fitness.extension.getViewModel
import com.kidd.fitness.extension.onAvoidDoubleClick
import kotlinx.android.synthetic.main.user_meal_fragment.*


class UserMealFragment : BaseFragment() {

    private lateinit var viewModel: UserMealViewModel


    override fun getLayoutId() = R.layout.user_meal_fragment

    override fun backFromAddFragment() {
    }

    override fun backPressed(): Boolean {
        viewController.backFromAddFragment(null)
        return false
    }

    override fun initView() {
        viewModel = getViewModel(viewModelFactory)

        btn_morning.onAvoidDoubleClick {
            createOrUpdateUserMeal()
        }

        btn_afternoon.onAvoidDoubleClick {
            createOrUpdateUserMeal()
        }

        btn_evening.onAvoidDoubleClick {
            createOrUpdateUserMeal()
        }

        viewModel.getUserTargetCalo()
        viewModel.userTargetCalo.observe(this, Observer {
            handleObjectResponse(it)
        })
    }

    override fun <U : Any?> getObjectResponse(data: U) {
        when (data) {
            is UserMeal -> {
                edt_kcal.text = data.target_calo.toString()
                Log.v("ahuhu","da ${data.target_calo}")
            }
        }
    }

    private fun createOrUpdateUserMeal() {
        if (edt_kcal.text.isNullOrEmpty()) {
            edt_kcal.setError("Calo không được để trống")
        }
        viewModel.createOrUpdateUserMeal(edt_kcal.text.toInt())


    }

    override fun initData() {
    }
}
