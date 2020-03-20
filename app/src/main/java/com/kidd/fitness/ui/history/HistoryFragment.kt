package com.kidd.fitness.ui.history

import android.util.Log
import androidx.lifecycle.Observer
import com.kidd.fitness.R
import com.kidd.fitness.base.BaseFragment
import com.kidd.fitness.custom.BaseToolbar
import com.kidd.fitness.custom.dialog.CustomDatePickerDialog
import com.kidd.fitness.custom.dialog.UserMealDialog
import com.kidd.fitness.entity.UserMeal
import com.kidd.fitness.entity.UserMealDetail
import com.kidd.fitness.extension.format
import com.kidd.fitness.extension.getViewModel
import com.kidd.fitness.extension.toast
import com.kidd.fitness.ui.home.HomeFragment.Companion.TIME_VALUE
import com.kidd.fitness.utils.Define
import kotlinx.android.synthetic.main.history_fragment.*
import kotlinx.android.synthetic.main.history_fragment.toolbar
import kotlinx.android.synthetic.main.layout_text_component.view.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class HistoryFragment : BaseFragment() {

    private lateinit var viewModel: HistoryViewModel

    override fun getLayoutId() = R.layout.history_fragment
    private lateinit var customDatePickerDialog: CustomDatePickerDialog

    override fun backFromAddFragment() {
    }

    override fun backPressed(): Boolean {
        viewController.backFromAddFragment(null)
        return false
    }

    override fun initView() {
        viewModel = getViewModel(viewModelFactory)
        arguments?.let {
            viewModel.timeVale = it.getString(TIME_VALUE, "")
            tv_day.text = "Ngày ${it.getString(TIME_VALUE, "")}"
        }
    }

    override fun initData() {
        txt_morning.onIconClick {
            viewModel.getDetailMeal(Define.MORNING)
        }
        txt_afternoon.onIconClick {
            viewModel.getDetailMeal(Define.AFTERNOON)
        }
        txt_evening.onIconClick {
            viewModel.getDetailMeal(Define.EVENING)
        }

        viewModel.getHistoryByDay()

        viewModel.userMeal.observe(this, Observer {
            handleObjectResponse(it)
        })

        viewModel.userMealDetail.observe(this, Observer {
            handleObjectResponse(it)
        })

        toolbar.setOnToolbarClickListener(object : BaseToolbar.OnToolbarClickListener {
            override fun onClick(id: Int) {
                when (id) {
                    imv_left.id -> {
                        viewController.backFromAddFragment(null)
                    }
                    imv_right.id -> {
                        customDatePickerDialog = CustomDatePickerDialog(context!!,
                            object : CustomDatePickerDialog.OnSelectDateListener {
                                override fun onSelectedDateSuccess(
                                    day: Int,
                                    month: Int,
                                    year: Int
                                ) {
                                    val newDate = customDatePickerDialog.getDate(day, month, year)
                                    if (newDate != viewModel.timeVale) {
                                        viewModel.timeVale = newDate
                                        tv_day.text = "Ngày $newDate"
                                        clearDataAndLoadNewData()
                                    }
                                }
                            })
                        customDatePickerDialog.showDialogDatePicker()
                    }
                }
            }
        })
    }

    private fun clearDataAndLoadNewData() {
        txt_morning.apply {
            tv_detail.text = null
            visibleIcon(false)
        }
        txt_afternoon.apply {
            tv_detail.text = null
            visibleIcon(false)
        }
        txt_evening.apply {
            tv_detail.text = null
            visibleIcon(false)
        }

        txt_target.tv_detail.text = null
        viewModel.getHistoryByDay()
    }

    override fun <U : Any?> getObjectResponse(data: U) {
        when (data) {
            is UserMeal -> {
                txt_target.tv_detail.text = data.target_calo.format()
                txt_morning.apply {
                    tv_detail.text = data.morning_calo.format()
                    visibleIcon(true)
                }
                txt_afternoon.apply {
                    tv_detail.text = data.afternoon_calo.format()
                    visibleIcon(true)
                }
                txt_evening.apply {
                    tv_detail.text = data.evening_calo.format()
                    visibleIcon(true)
                }

            }

            is UserMealDetail -> {
                showPopupDetail(data)
            }
        }
    }

    private fun showPopupDetail(userMealDetail: UserMealDetail) {
        val userMealDialog = UserMealDialog(context!!)
        userMealDialog.init(userMealDetail)
        userMealDialog.show()
    }
}
