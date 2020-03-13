package com.kidd.fitness.ui.insert_food

import androidx.lifecycle.Observer
import com.kidd.fitness.R
import com.kidd.fitness.adapter.spinner.SpinnerTimeAdapter
import com.kidd.fitness.base.BaseFragment
import com.kidd.fitness.custom.BaseToolbar
import com.kidd.fitness.entity.DailyTime
import com.kidd.fitness.entity.Food
import com.kidd.fitness.extension.getViewModel
import com.kidd.fitness.extension.onAvoidDoubleClick
import com.kidd.fitness.extension.toast
import kotlinx.android.synthetic.main.inert_meal_fragment.*


class InertMealFragment : BaseFragment() {
    private lateinit var spinnerTimerAdapter: SpinnerTimeAdapter

    private lateinit var viewModel: InertMealViewModel

    override fun getLayoutId() = R.layout.inert_meal_fragment

    override fun backFromAddFragment() {
    }

    override fun backPressed(): Boolean {
        viewController.backFromAddFragment(null)
        return false
    }

    override fun initView() {
        viewModel = getViewModel(viewModelFactory)

        viewModel.insertMeal.observe(this, Observer {
            handleObjectResponse(it)
        })

        btn_insert.onAvoidDoubleClick {
            viewModel.insertFood(
                Food(
                    System.currentTimeMillis().toString(),
                    edt_name.text,
                    edt_calo.text.toInt(),
                    spinnerTimerAdapter.getItem(spinner_time.selectedItemPosition)
                    )
            )
        }

        toolbar.setOnToolbarClickListener(object :BaseToolbar.OnToolbarClickListener{
            override fun onClick(id: Int) {
//                when(id){
//                    is R.id.im
//                }
            }
        })

        initSpinner()
    }

    private fun initSpinner() {
        spinnerTimerAdapter = SpinnerTimeAdapter()
        spinner_time.adapter = spinnerTimerAdapter
    }

    override fun <U : Any?> getObjectResponse(data: U) {
        when (data) {
            is Boolean -> {
                if (data) {
                    toast("Thêm món ăn thành công!")
                    viewController.backFromAddFragment(null)
                }
            }
        }
    }

    override fun initData() {
    }
}
