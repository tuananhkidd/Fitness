package com.kidd.fitness.ui.home

import androidx.lifecycle.Observer
import com.kidd.fitness.R
import com.kidd.fitness.base.BaseFragment
import com.kidd.fitness.custom.dialog.CustomDatePickerDialog
import com.kidd.fitness.extension.getViewModel
import com.kidd.fitness.extension.onAvoidDoubleClick
import com.kidd.fitness.ui.history.HistoryFragment
import com.kidd.fitness.ui.insert_food.InertMealFragment
import com.kidd.fitness.ui.meal.UserMealFragment
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : BaseFragment() {

    companion object {
        const val TIME_VALUE = "TIME_VALUE"
    }

    private lateinit var customDatePickerDialog: CustomDatePickerDialog
    private lateinit var viewModel: HomeViewModel

    override fun getLayoutId() = R.layout.home_fragment

    override fun backFromAddFragment() {
    }

    override fun backPressed(): Boolean {
        return true
    }

    override fun initView() {
        viewModel = getViewModel(viewModelFactory)
        btn_insert.onAvoidDoubleClick {
            viewController.addFragment(InertMealFragment::class.java, null)
        }

        btn_today.onAvoidDoubleClick {
            viewController.addFragment(UserMealFragment::class.java, null)
        }

        btn_history.onAvoidDoubleClick {
            customDatePickerDialog = CustomDatePickerDialog(context!!,
                object : CustomDatePickerDialog.OnSelectDateListener {
                    override fun onSelectedDateSuccess(day: Int, month: Int, year: Int) {
                        viewController.addFragment(
                            HistoryFragment::class.java,
                            mapOf(TIME_VALUE to customDatePickerDialog.getDate(day, month, year))
                        )
                    }
                })
            customDatePickerDialog.showDialogDatePicker()
        }
    }

    override fun initData() {
        txt_user.isSelected = true
        viewModel.userInfo.observe(this, Observer {
            txt_user.text = "${it.name}"
        })
    }
}
