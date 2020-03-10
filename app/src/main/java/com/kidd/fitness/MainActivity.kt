package com.kidd.fitness

import com.kidd.fitness.base.BaseActivity
import com.kidd.fitness.ui.SplashFragment

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getFragmentContainerId(): Int {
        return R.id.container
    }

    override fun initView() {
        viewController?.addFragment(SplashFragment::class.java, null)
    }

    override fun initData() {
    }
}
