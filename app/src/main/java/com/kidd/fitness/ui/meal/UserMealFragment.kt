package com.kidd.fitness.ui.meal

import android.util.Log
import androidx.lifecycle.Observer
import com.kidd.fitness.R
import com.kidd.fitness.base.BaseFragment
import com.kidd.fitness.entity.UserMeal
import com.kidd.fitness.extension.getViewModel
import com.kidd.fitness.extension.onAvoidDoubleClick
import com.kidd.fitness.ui.insert_meal.CreateMealFragment
import com.kidd.fitness.utils.Define
import kotlinx.android.synthetic.main.user_meal_fragment.*


class UserMealFragment : BaseFragment() {

    private lateinit var viewModel: UserMealViewModel
    companion object{
        const val KEY_USER_MEAL_DOC = "KEY_USER_MEAL_DOC"
        const val KEY_USER_MEAL = "KEY_USER_MEAL"
        const val KEY_TIME_DOC = "KEY_TIME_DOC"
    }

    private var timeDoc = ""
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
            timeDoc = Define.MORNING
            createOrUpdateUserMeal()
        }

        btn_afternoon.onAvoidDoubleClick {
            timeDoc = Define.AFTERNOON
            createOrUpdateUserMeal()
        }

        btn_evening.onAvoidDoubleClick {
            timeDoc = Define.EVENING
            createOrUpdateUserMeal()
        }

        viewModel.getUserTargetCalo()
        viewModel.userTargetCalo.observe(this, Observer {
            handleObjectResponse(it)
        })

        viewModel.createOrUpdate.observe(this, Observer {
            handleObjectResponse(it)
        })
    }

    override fun <U : Any?> getObjectResponse(data: U) {
        when (data) {
            is UserMeal -> {
                edt_kcal.text = data.target_calo.toString()
            }
            is Boolean -> {
                val userMeal = viewModel.userTargetCalo.value?.data
                val data = mapOf(KEY_TIME_DOC to timeDoc,
                    KEY_USER_MEAL_DOC to viewModel.userMealDocumentId,
                    KEY_USER_MEAL to userMeal)
                viewController.addFragment(CreateMealFragment::class.java,data)
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
