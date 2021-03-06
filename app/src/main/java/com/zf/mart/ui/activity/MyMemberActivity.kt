package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.MyMemberBean
import com.zf.mart.mvp.contract.MyMemberContract
import com.zf.mart.mvp.presenter.MyMemberPresenter
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.MyMemberAdapter
import com.zf.mart.utils.LogUtils
import kotlinx.android.synthetic.main.activity_my_member.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 我的会员
 */
class MyMemberActivity : BaseActivity(), MyMemberContract.View {
    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
        showToast(msg)
    }

    override fun getMyMember(bean: List<MyMemberBean>) {
        refreshLayout.setEnableLoadMore(true)
        mData.clear()
        mData.addAll(bean)
        mAdapter.notifyDataSetChanged()

    }

    override fun freshEmpty() {
        mData.clear()
        mAdapter.notifyDataSetChanged()
        refreshLayout.setEnableLoadMore(false)
    }

    override fun setLoadMore(bean: List<MyMemberBean>) {
        mData.addAll(bean)
        mAdapter.notifyDataSetChanged()
    }

    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }


    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, MyMemberActivity::class.java))
        }
    }

    override fun initToolBar() {
        back.setOnClickListener {
            finish()
        }
        titleName.text = "我的会员"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_my_member

    private val mAdapter by lazy { MyMemberAdapter(this, mData) }

    private var userList = ArrayList<String>()
    private var userName = ArrayList<String>()
    private var userSum = 1

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            userSum -= 1
            if (userSum == 1) {
                userList.clear()
                userName.clear()
                user_name.text = "我的下级"
                refreshLayout.resetNoMoreData()
                presenter.requestMyMember(1, "")
            }
            if (userSum >= 2) {
                userList.remove(userList[userSum - 1])
                userName.remove(userName[userSum -1])
                user_name.text = userName[userSum -2]+"的下级"
                refreshLayout.resetNoMoreData()
                presenter.requestMyMember(1, userList[userSum - 2])
            }
            if (userSum == 0) {
                return super.onKeyDown(keyCode, event)
            }
        }
        return false
    }

    //网络接收数据
    private var mData = ArrayList<MyMemberBean>()

    private val presenter by lazy { MyMemberPresenter() }

    override fun initData() {

    }

    override fun initView() {
        presenter.attachView(this)

        member_rl.layoutManager = LinearLayoutManager(this)
        member_rl.adapter = mAdapter
    }

    override fun initEvent() {
        /**上拉加载*/
        refreshLayout.setOnLoadMoreListener {
            if (userList.isNotEmpty()) {
                presenter.requestMyMember(null, userList[userList.size - 1])
            } else {
                presenter.requestMyMember(null, "")
            }

        }
        /**下拉刷新*/
        refreshLayout.setOnRefreshListener {
            if (userList.isNotEmpty()) {
                presenter.requestMyMember(1, userList[userList.size - 1])
            } else {
                presenter.requestMyMember(1, "")
            }
        }

        mAdapter.mClickListener = { id: String, name: String ->
            userList.add(id)
            userName.add(name)
            userSum += 1
            user_name.text = name + "的下级"
            refreshLayout.resetNoMoreData()
            presenter.requestMyMember(1, id)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {
        refreshLayout.setEnableLoadMore(false)
        presenter.requestMyMember(1, "")

    }

}