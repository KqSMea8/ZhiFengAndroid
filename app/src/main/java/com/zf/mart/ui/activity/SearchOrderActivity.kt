package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.SearchList
import com.zf.mart.mvp.contract.SearchContract
import com.zf.mart.mvp.presenter.SearchPresenter
import com.zf.mart.net.exception.ErrorStatus
import com.zf.mart.showToast
import com.zf.mart.ui.adapter.SearchOrderAdapter
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.view.RecDecoration
import com.zf.mart.view.popwindow.SearchFilterPopupWindow
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_search_order.*

/**
 * 搜索的订单结果
 */
class SearchOrderActivity : BaseActivity(), SearchContract.View {

    override fun loadMoreError(msg: String, status: Int) {
        showToast(msg)
    }

    override fun setLoadMore(bean: List<SearchList>) {
        data.addAll(bean)
        mAdapter.notifyDataSetChanged()
    }

    override fun loadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }


    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    //刷新结果为空列表
    override fun freshEmpty() {
        refreshLayout.setEnableLoadMore(false)
        mLayoutStatusView?.showEmpty()
    }

    //刷新结果
    override fun setSearchList(bean: List<SearchList>) {
        mLayoutStatusView?.showContent()
        refreshLayout.setEnableLoadMore(true)
        data.clear()
        data.addAll(bean)
        mAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    override fun initToolBar() {

        backLayout.setOnClickListener {
            finish()
        }

        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )
    }

    //搜索关键词
    var mKeyWord = ""

    companion object {
        fun actionStart(context: Context?, keyWord: String) {
            val intent = Intent(context, SearchOrderActivity::class.java)
            intent.putExtra("key", keyWord)
            context?.startActivity(intent)

        }
    }

    override fun layoutId(): Int = R.layout.activity_search_order

    override fun initData() {
        mKeyWord = intent.getStringExtra("key")
    }

    private val searchPresenter by lazy { SearchPresenter() }

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        searchPresenter.attachView(this)

        searchInput.text = mKeyWord

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(oneDivider)
    }

    private val data = ArrayList<SearchList>()

    private val mAdapter by lazy { SearchOrderAdapter(this, data) }

    private val oneDivider by lazy {
        RecyclerViewDivider(
            this,
            LinearLayoutManager.VERTICAL,
            1,
            ContextCompat.getColor(this, R.color.colorLine)
        )
    }

    private val twoDivider by lazy { RecDecoration(DensityUtil.dp2px(10f)) }

    //mSort:排序 mPriceSort：价格升降排序 page:null为加载更多
    private var mSort: String = ""
    private var mPriceSort: String = ""
    /** 请求数据 */
    private fun initRequest(page: Int?) {
        if (page == 1) {
            refreshLayout.setEnableLoadMore(false)
        }
        //判断是选中的哪个？
        searchPresenter.requestSearch(
            searchInput.text.toString(), "", "", mSort, "",
            "", "", "", mPriceSort, page
        )
    }


    override fun start() {
        if (data.isEmpty()) {
            mLayoutStatusView?.showLoading()
        }

        mSort = "comment_count"
        mPriceSort = ""
        initRequest(1)

    }

    override fun onDestroy() {
        super.onDestroy()
        searchPresenter.detachView()
    }

    override fun initEvent() {

        //加载更多
        refreshLayout.setOnLoadMoreListener {
            initRequest(null)
        }

        //新品
        newGoods.setOnClickListener {
            mSort = "is_new"
            mPriceSort = ""
            initRequest(1)

        }

        //销量
        saleSum.setOnClickListener {
            mSort = "sales_sum"
            mPriceSort = ""
            initRequest(1)
        }

        //价格
        priceRb.setOnClickListener {
            priceRb.isSelected = !priceRb.isSelected
            if (priceRb.isSelected) {
                //价格升序
                mSort = "shop_price"
                mPriceSort = "desc"
                initRequest(1)
            } else {
                //价格降序
                mSort = "shop_price"
                mPriceSort = "asc"
                initRequest(1)
            }

        }

        //综合
        synthesis.setOnClickListener {
            mSort = "comment_count"
            mPriceSort = ""
            initRequest(1)

            /** 下面是点击综合弹出选择框，先注释，不做选择 */
//            val popWindow = object : SynthesisPopupWindow(
//                this, R.layout.pop_search_synthesis, LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//            ) {}
//            val layoutGravity = LayoutGravity(LayoutGravity.ALIGN_RIGHT)
//            popWindow.showBashOfAnchor(rankLayout, layoutGravity, 0, 0)
        }

        //筛选
        filterBtn.setOnClickListener {
            val popWindow = object : SearchFilterPopupWindow(
                this, R.layout.pop_search_filter, DensityUtil.dp2px(280f),
                LinearLayout.LayoutParams.MATCH_PARENT
            ) {}
            popWindow.showAtLocation(parentLayout, Gravity.RIGHT, 0, 0)
        }

        /** 切换排版，改变layoutManager后找到第一个可见的item 定位到 */
        composeLayout.setOnClickListener {

            val position = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

            composeIcon.isSelected = !composeIcon.isSelected
            if (composeIcon.isSelected) {
                //两列排版
                val layoutManager = GridLayoutManager(this, 2)
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = mAdapter
                recyclerView.removeItemDecoration(oneDivider)
                recyclerView.addItemDecoration(twoDivider)
                mAdapter.changeType(2)

            } else {
                //一列排版
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = mAdapter
                recyclerView.removeItemDecoration(twoDivider)
                recyclerView.addItemDecoration(oneDivider)
                mAdapter.changeType(1)
            }

            recyclerView.scrollToPosition(position)
        }

        searchInput.setOnClickListener {
            SearchActivity.actionStart(this, searchInput.text.toString())
        }
    }


}