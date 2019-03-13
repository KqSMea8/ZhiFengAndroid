package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 秒杀 订单 支付
 */
class SecKillPayActivity : BaseActivity() {

    override fun initToolBar() {
        back.setOnClickListener {
            finish()
        }
        titleName.text = "提交订单"
        rightLayout.visibility = View.INVISIBLE

    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, SecKillPayActivity::class.java))
        }
    }

    override fun layoutId(): Int = R.layout.activity_seckill_pay

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initEvent() {
    }

    override fun start() {
    }
}