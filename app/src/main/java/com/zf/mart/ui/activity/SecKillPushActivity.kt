package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import kotlinx.android.synthetic.main.activity_seckill_order.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 秒杀  提交订单
 */
class SecKillPushActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, SecKillPushActivity::class.java))
        }
    }

    override fun initToolBar() {
        rightLayout.visibility = View.INVISIBLE
        titleName.text = "填写订单"
        back.setOnClickListener { finish() }
    }

    override fun layoutId(): Int = R.layout.activity_seckill_order

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initEvent() {
        confirm.setOnClickListener {
            SecKillPayActivity.actionStart(this)
        }
    }

    override fun start() {
    }
}