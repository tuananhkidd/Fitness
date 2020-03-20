package com.kidd.fitness.custom.dialog

import android.content.Context
import android.os.Bundle
import android.view.Window
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kidd.fitness.R
import com.kidd.fitness.entity.UserMealDetail
import kotlinx.android.synthetic.main.layout_dialog_user_meal.*
import kotlinx.android.synthetic.main.layout_text_component.view.*

class UserMealDialog(context: Context) : BottomSheetDialog(context) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_dialog_user_meal)
    }

    fun init(userMeal:UserMealDetail){
        txt_food.tv_detail.text = userMeal.foodName
        txt_weight.tv_detail.text = userMeal.foodWeight.toString()
    }
}