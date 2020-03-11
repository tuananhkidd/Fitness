package com.kidd.fitness.ui.home

import com.kidd.fitness.R
import com.kidd.fitness.base.BaseFragment
import com.kidd.fitness.extension.getViewModel
import com.kidd.fitness.extension.onAvoidDoubleClick
import com.kidd.fitness.ui.insert_food.InertMealFragment
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : BaseFragment() {


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
    }

    override fun initData() {
    }
}
