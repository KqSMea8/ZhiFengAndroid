package com.zf.mart.view.popwindow

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.zf.mart.R
import kotlinx.android.synthetic.main.pop_order_pay.view.*

/**
 * 支付方式
 */
abstract class OrderPayPopupWindow(var context: Activity, layoutRes: Int, w: Int, h: Int) {
    val contentView: View
    val popupWindow: PopupWindow
    private var isShowing = false

    init {
        contentView = LayoutInflater.from(context).inflate(layoutRes, null, false)
        initView()
        popupWindow = PopupWindow(contentView, w, h, true)
        initWindow()
    }

    private var mListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener {
//        fun onBack(address: String)
    }

    private fun initView() {
        contentView.apply {
            close.setOnClickListener {
                onDismiss()
            }
        }
    }

    private fun initWindow() {
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.isOutsideTouchable = true
        popupWindow.isTouchable = true
        /** 设置出入动画  */
        popupWindow.animationStyle = R.style.pop_translate_around
    }

    fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        popupWindow.showAtLocation(parent, gravity, x, y)
        isShowing = true
        popupWindow.setOnDismissListener {
            //隐藏后显示背景为透明
            val lp = context.window.attributes
            lp.alpha = 1.0f
            context.window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            context.window.attributes = lp
        }

        //显示时候设置背景为灰色
        val lp = context.window.attributes
        lp.alpha = 0.7f
        context.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        context.window.attributes = lp
    }

    fun onDismiss() {
        popupWindow.dismiss()
        isShowing = false

    }

}