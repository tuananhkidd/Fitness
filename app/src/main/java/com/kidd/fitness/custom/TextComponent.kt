package com.kidd.fitness.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.beetech.tienichmuasam.base.custom.CustomViewConstraintLayout
import com.kidd.fitness.R
import com.kidd.fitness.extension.gone
import com.kidd.fitness.extension.onAvoidDoubleClick
import com.kidd.fitness.extension.visible
import kotlinx.android.synthetic.main.layout_text_component.view.*

class TextComponent : CustomViewConstraintLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    override fun getLayoutRes() = R.layout.layout_text_component

    override fun getStyableRes() = R.styleable.TextComponent

    override fun initView() {
    }

    override fun initListener() {
    }

    override fun initData() {
    }

    override fun initDataFromStyable(mTypedArray: TypedArray?) {
        val title = mTypedArray?.getString(R.styleable.TextComponent_title)
        tv_title.text = title
        val detail = mTypedArray?.getString(R.styleable.TextComponent_detail)
        tv_detail.text = detail
        val ic = mTypedArray?.getBoolean(R.styleable.TextComponent_visible_ic, false) ?: false
        if (ic) img_info.visible() else img_info.gone()
    }


    fun onIconClick(init: () -> Unit) {
        img_info.onAvoidDoubleClick {
            init()
        }
    }

    fun visibleIcon(visible:Boolean){
        if (visible) img_info.visible() else img_info.gone()
    }
}