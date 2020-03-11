package com.kidd.fitness.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.kidd.fitness.R
import com.kidd.fitness.base.BaseFragment
import com.kidd.fitness.extension.getViewModel
import com.kidd.fitness.extension.onAvoidDoubleClick
import com.kidd.fitness.ui.home.HomeFragment
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : BaseFragment() {


    private lateinit var viewModel: LoginViewModel

    override fun initView() {
        viewModel = getViewModel(viewModelFactory)
    }

    override fun backFromAddFragment() {
    }


    override fun backPressed() = false

    override fun initData() {
        btn_sign_in.onAvoidDoubleClick {
            viewModel.login(edt_email.text, edt_password.text)
        }

        viewModel.login.observe(this, Observer {
            handleObjectResponse(it)
        })
    }

    override fun <U : Any?> getObjectResponse(data: U) {
        when (data) {
            is Boolean -> {
                if(data){
                    viewController.replaceFragment(HomeFragment::class.java,null)
                }
            }
        }
    }

    override fun getLayoutId() = R.layout.login_fragment

}
