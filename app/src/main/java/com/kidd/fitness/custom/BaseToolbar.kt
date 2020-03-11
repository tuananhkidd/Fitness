package com.kidd.fitness.custom

import android.content.Context
import android.content.res.TypedArray
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.beetech.tienichmuasam.base.custom.CustomViewConstraintLayout
import com.kidd.fitness.R
import com.kidd.fitness.extension.color
import com.kidd.fitness.extension.onAvoidDoubleClick
import com.kidd.fitness.extension.string
import kotlinx.android.synthetic.main.layout_toolbar.view.*


class BaseToolbar : CustomViewConstraintLayout {

    private var listener: OnToolbarClickListener? = null

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


    override fun getLayoutRes(): Int {
        return R.layout.layout_toolbar
    }

    override fun getStyableRes(): IntArray? {
        return R.styleable.BaseToolbar
    }

    override fun initView() {

    }

    override fun initListener() {
        imv_left.onAvoidDoubleClick {
            listener?.onClick(imv_left.id)
        }

        imv_right.onAvoidDoubleClick {
            listener?.onClick(imv_right.id)
        }
    }

    override fun initData() {

    }

    override fun initDataFromStyable(mTypedArray: TypedArray?) {
        val title = mTypedArray?.getString(R.styleable.BaseToolbar_toolbar_title)
        val iconLeft = mTypedArray?.getDrawable(R.styleable.BaseToolbar_icon_left)
        val iconRight = mTypedArray?.getDrawable(R.styleable.BaseToolbar_icon_right)
        val bg = mTypedArray?.getColor(R.styleable.BaseToolbar_toolbar_background,0)
        bg?.let {
            toolbar.setBackgroundColor(it)
        }
        setToolbarTitle(title)
        imv_left.setImageDrawable(iconLeft)
        imv_right.setImageDrawable(iconRight)
    }


    fun setToolbarBackground(@ColorRes color: Int) {
        toolbar.setBackgroundColor(context.color(color))
    }

    fun setToolbarTitle(title: String?) {
        tv_title.text = title
    }

    fun setOnToolbarClickListener(listener: OnToolbarClickListener?) {
        this.listener = listener
    }

    interface OnToolbarClickListener {
        fun onClick(id: Int)
    }

}