package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.base.BaseFragmentAdapter
import com.zf.mart.ui.fragment.action.GroupEvaluationFragment
import kotlinx.android.synthetic.main.activity_evaluation.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 用户的全部评价
 */
class EvaluationActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?, goodId: String?) {
            val intent = Intent(context, EvaluationActivity::class.java)
            intent.putExtra("goodId", goodId)
            context?.startActivity(intent)
        }
    }

    private var mGoodId = ""

    override fun initToolBar() {
        back.setOnClickListener { finish() }
        titleName.text = "用户评价"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_evaluation

    override fun initData() {
        mGoodId = intent.getStringExtra("goodId")
    }

    override fun initView() {
        val titles = arrayListOf("全部\n10+", "好评\n4", "中评\n2", "差评\n1", "晒单\n0")
        val fgms = arrayListOf(
                GroupEvaluationFragment.newInstance(mGoodId, 1),
                GroupEvaluationFragment.newInstance(mGoodId, 2),
                GroupEvaluationFragment.newInstance(mGoodId, 3),
                GroupEvaluationFragment.newInstance(mGoodId, 4),
                GroupEvaluationFragment.newInstance(mGoodId, 5)
        )
        val adapter = BaseFragmentAdapter(supportFragmentManager, fgms, titles)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 4
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun initEvent() {
    }

    override fun start() {
    }
}