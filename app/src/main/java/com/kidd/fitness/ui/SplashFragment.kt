package com.kidd.fitness.ui

import com.kidd.fitness.R
import com.kidd.fitness.base.BaseFragment
import com.kidd.fitness.extension.getViewModel

class SplashFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.splash_fragment
    }

    override fun backFromAddFragment() {
    }

    override fun backPressed(): Boolean {
        return false
    }

    override fun initView() {
        viewModel = getViewModel(viewModelFactory)

    }

    override fun initData() {

    }


    private lateinit var viewModel: SplashViewModel
}
