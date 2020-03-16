package com.kidd.fitness.ui.register

import androidx.lifecycle.Observer
import com.kidd.fitness.R
import com.kidd.fitness.base.BaseFragment
import com.kidd.fitness.entity.User
import com.kidd.fitness.extension.getViewModel
import com.kidd.fitness.extension.onAvoidDoubleClick
import com.kidd.fitness.extension.toast
import kotlinx.android.synthetic.main.register_fragment.*


class RegisterFragment : BaseFragment() {

    private lateinit var viewModel: RegisterViewModel


    override fun getLayoutId() = R.layout.register_fragment

    override fun backFromAddFragment() {
    }

    override fun backPressed() :Boolean{
        viewController.backFromAddFragment(null)
        return false
    }

    override fun initView() {
        viewModel = getViewModel(viewModelFactory)

        viewModel.register.observe(this, Observer {
            handleObjectResponse(it)
        })
    }

    override fun <U : Any?> getObjectResponse(data: U) {
        if(data is User){
            toast("Tạo tài khoản thành công!")
            viewController.backFromAddFragment(mapOf("email" to data.email))
        }
    }

    override fun initData() {
        btn_register.onAvoidDoubleClick {
            viewModel.register(edt_email.text,edt_password.text,edt_confirm_password.text,edt_first_name.text)
        }

        txt_sign_in.onAvoidDoubleClick {
            viewController.backFromAddFragment(null)
        }
    }
}
