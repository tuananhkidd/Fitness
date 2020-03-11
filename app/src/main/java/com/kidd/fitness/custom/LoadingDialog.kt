package com.kidd.fitness.custom

import android.content.Context
import com.kidd.fitness.R
import com.kidd.fitness.base.custom.BaseCustomDialog


class LoadingDialog(context: Context) : BaseCustomDialog(context) {
    override val layoutId: Int
        get() = R.layout.dialog_loading

    override fun initViews() {
        setCancelable(false)
    }

    override fun initData() {

    }

    override fun initListeners() {
    }
}