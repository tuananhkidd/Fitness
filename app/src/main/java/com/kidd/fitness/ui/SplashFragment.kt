package com.kidd.fitness.ui

import com.bumptech.glide.Glide
import com.kidd.fitness.R
import com.kidd.fitness.base.BaseFragment
import com.kidd.fitness.extension.getViewModel
import com.kidd.fitness.ui.home.HomeFragment
import com.kidd.fitness.ui.login.LoginFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.splash_fragment.*
import java.util.concurrent.TimeUnit

class SplashFragment : BaseFragment() {
    private lateinit var mDisposable: CompositeDisposable
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
        Glide.with(this)
            .load(R.drawable.splash)
            .into(splash)
    }

    override fun initData() {
        mDisposable = CompositeDisposable()

        mDisposable.add(io.reactivex.Completable.timer(3000,TimeUnit.MILLISECONDS)
            .subscribe {
                if(viewModel.isUserLogin()){
                    viewController.replaceFragment(HomeFragment::class.java,null)
                }else{
                    viewController.replaceFragment(LoginFragment::class.java,null)
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable.clear()
    }

    private lateinit var viewModel: SplashViewModel
}
