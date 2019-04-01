package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.ui.fragment.MyOrderFragment
import com.zf.mart.utils.StatusBarUtils
import kotlinx.android.synthetic.main.layout_toolbar.*


/**
 * 我的订单搜索结果
 */
class SearchMyOrderActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, SearchMyOrderActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        titleName.text = "我的订单"
        rightLayout.visibility = View.INVISIBLE
        back.setOnClickListener { finish() }
    }

    override fun layoutId(): Int = R.layout.activity_search_my_order

    override fun initData() {
    }

    private var searchOrderFragment: MyOrderFragment? = null

    override fun initView() {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        searchOrderFragment?.let {
            transaction.show(it)
        }
            ?: MyOrderFragment.newInstance("search").let {
                searchOrderFragment = it
                transaction.add(R.id.searchFragment, it)
            }
        transaction.commit()
    }

    override fun initEvent() {
    }

    override fun start() {
    }
}