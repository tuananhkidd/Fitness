package com.kidd.fitness.ui.insert_meal

import android.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import com.kidd.fitness.R
import com.kidd.fitness.adapter.spinner.SpinnerFoodAdapter
import com.kidd.fitness.base.BaseFragment
import com.kidd.fitness.custom.BaseToolbar
import com.kidd.fitness.entity.Food
import com.kidd.fitness.entity.UserMealDetail
import com.kidd.fitness.extension.*
import com.kidd.fitness.ui.meal.UserMealFragment
import com.kidd.fitness.utils.Define
import kotlinx.android.synthetic.main.create_meal_fragment.*
import kotlinx.android.synthetic.main.layout_calo.*


class CreateMealFragment : BaseFragment() {
    private lateinit var viewModel: CreateMealViewModel
    private lateinit var spinnerFoodAdapter: SpinnerFoodAdapter
    override fun getLayoutId() = R.layout.create_meal_fragment

    override fun backFromAddFragment() {
    }

    override fun backPressed(): Boolean {
        viewController.backFromAddFragment(null)
        return false
    }

    override fun initView() {
        viewModel = getViewModel(viewModelFactory)
        viewModel.spinnerFood.observe(this, Observer {
            handleObjectResponse(it)
        })

        arguments?.let {
            viewModel.getUserMealDetail(
                it.getString(UserMealFragment.KEY_USER_MEAL_DOC, ""),
                it.getString(UserMealFragment.KEY_TIME_DOC, "")
            )

            viewModel.userMeal = it.getParcelable(UserMealFragment.KEY_USER_MEAL)
            tv_calo.text = viewModel.userMeal?.target_calo.format()
            tv_morning_calo.text = viewModel.userMeal?.morning_calo.format()
            tv_afternoon_calo.text = viewModel.userMeal?.afternoon_calo.format()
            tv_evening_calo.text = viewModel.userMeal?.evening_calo.format()
            tv_total_calo_remain.text =
                (viewModel.userMeal?.target_calo!! - viewModel.userMeal?.morning_calo!!
                        - viewModel.userMeal?.afternoon_calo!! - viewModel.userMeal?.evening_calo!!).format()
        }

        viewModel.createMealFlag.observe(this, Observer {
            handleObjectResponse(it)
        })

        btn_confirm.onAvoidDoubleClick {
            if (edt_weight.text.isNullOrEmpty()) {
                edt_weight.setError("Khối lượng không được để trống")
                return@onAvoidDoubleClick
            }
            if (tv_total_calo.text.toString().replaceDot()!!.toDouble() > getTotalRemainCalo()) {
                edt_weight.setError("Calo đã vượt mức quy định!")
                return@onAvoidDoubleClick
            }
            viewModel.createUserMealDetail(
                spinnerFoodAdapter.getItem(spinner_food.selectedItemPosition).id,
                spinnerFoodAdapter.getItem(spinner_food.selectedItemPosition).calo,
                spinnerFoodAdapter.getItem(spinner_food.selectedItemPosition).name,
                edt_weight.text.toInt()
            )
        }

        toolbar.setOnToolbarClickListener(object : BaseToolbar.OnToolbarClickListener {
            override fun onClick(id: Int) {
                viewController.backFromAddFragment(null)
            }
        })
    }

    override fun <U : Any?> getObjectResponse(data: U) {
        when (data) {
            is Pair<*, *> -> {
                val res = data as Pair<Pair<Int, List<Food>>, UserMealDetail?>
                spinnerFoodAdapter = SpinnerFoodAdapter(res.first.second)
                spinner_food.adapter = spinnerFoodAdapter

                if (res.first.first != -1) {
                    spinner_food.setSelection(res.first.first)
                }
                res.second?.let {
                    edt_weight.text = it.foodWeight.toString()
                    tv_total_calo_remain.text = (viewModel.userMeal?.target_calo!! -
                            viewModel.userMeal?.afternoon_calo!! -
                            viewModel.userMeal?.morning_calo!! -
                            viewModel.userMeal?.evening_calo!!).toString()
                }
            }

            is Boolean -> {
                if (data) {
                    toast("Cập nhật thành công!")
                    viewController.backFromAddFragment(null)
                }
            }
        }
    }


    override fun initData() {
        edt_weight.addTextChangeListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.v("ahuhu", "before $p0")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.v("ahuhu", "onTextChanged $p0")
                if (!p0.isNullOrBlank()) {
                    val totalCalo =
                        (spinnerFoodAdapter.getItem(spinner_food.selectedItemPosition).calo * edt_weight.text.toInt()) / 100
                    if (totalCalo > getTotalRemainCalo()) {
                        showDialogAlert()
                        edt_weight.apply {
                            text = p0.substring(0, p0.length - 1)
                        }
                    } else {
                        tv_total_calo.text = totalCalo.format()
                        tv_total_calo_remain.text = getTotalRemainCalo(totalCalo).format()
                    }

                } else {
                    tv_total_calo.text = null
                    tv_total_calo_remain.text = getTotalRemainCalo().toString().format()

                }
            }
        })

        when (viewModel.timeDoc) {
            Define.MORNING -> {
                toolbar.setToolbarTitle("Bữa Sáng")
                layout_afternoon.visible()
                layout_evening.visible()
                layout_morning.gone()
            }
            Define.AFTERNOON -> {
                toolbar.setToolbarTitle("Bữa Trưa")
                layout_afternoon.gone()
                layout_evening.visible()
                layout_morning.visible()
            }
            Define.EVENING -> {
                toolbar.setToolbarTitle("Bữa Tối")
                layout_afternoon.visible()
                layout_evening.gone()
                layout_morning.visible()
            }
        }

        spinner_food.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (!edt_weight.text.isNullOrBlank()) {
                    val totalCalo =
                        (spinnerFoodAdapter.getItem(p2).calo * edt_weight.text.toInt())/100
                    tv_total_calo.text = totalCalo.format()
                    tv_total_calo_remain.text = (viewModel.userMeal?.target_calo!! - totalCalo).format()
                    if (totalCalo > getTotalRemainCalo()) {
                        showDialogAlert()
                    }
                }
            }
        }
    }

    private fun showDialogAlert() {
        AlertDialog.Builder(context)
            .setTitle("Cảnh báo")
            .setMessage("Lương calo vượt quá mực quy định!")
            .setPositiveButton(
                android.R.string.yes
            ) { dialog, _ ->
                dialog.dismiss()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun updateTotalRemainCalo(calo: Int = 0) {
        tv_total_calo_remain.text = (viewModel.userMeal?.target_calo!! - calo).format()
    }

    private fun getTotalRemainCalo(calo: Double = 0.0): Double {
        when (viewModel.timeDoc) {
            Define.MORNING -> {
                return viewModel.userMeal?.target_calo!! - viewModel.userMeal?.evening_calo!! - viewModel.userMeal?.afternoon_calo!! - calo
            }
            Define.AFTERNOON -> {
                return viewModel.userMeal?.target_calo!! - viewModel.userMeal?.evening_calo!! - viewModel.userMeal?.morning_calo!! - calo
            }
            Define.EVENING -> {
                return viewModel.userMeal?.target_calo!! - viewModel.userMeal?.morning_calo!! - viewModel.userMeal?.afternoon_calo!! - calo
            }
            else -> {
                return 0.0
            }
        }
    }
}
