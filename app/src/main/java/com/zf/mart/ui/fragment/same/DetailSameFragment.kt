package com.zf.mart.ui.fragment.same

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager.widget.ViewPager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.mvp.bean.CommendList
import com.zf.mart.mvp.contract.EquallyGoodsContract
import com.zf.mart.mvp.contract.RecommendGoodsContract
import com.zf.mart.mvp.presenter.EquallyGoodsPresenter
import com.zf.mart.mvp.presenter.RecommendGoodsPresenter
import com.zf.mart.showToast
import com.zf.mart.ui.activity.AddressEditActivity
import com.zf.mart.ui.activity.ChoiceActivity
import com.zf.mart.ui.activity.GoodsDetailActivity
import com.zf.mart.ui.activity.OrderDetailActivity
import com.zf.mart.ui.adapter.LoveShopGoodsAdapter
import com.zf.mart.utils.LogUtils
import com.zf.mart.utils.bus.RxBus
import com.zf.mart.view.recyclerview.HorizontalPageLayoutManager
import com.zf.mart.view.recyclerview.PagingScrollHelper
import kotlinx.android.synthetic.main.fragment_detail_same.*

/**
 * 订单详情里面的相似推荐
 */
class DetailSameFragment : BaseFragment(), RecommendGoodsContract.View {
    override fun showError(message: String, errorCode: Int) {

    }

    override fun getRecommendGoods(bean: List<CommendList>) {
        mData.clear()
        mData.addAll(bean)
        val sum = if (mData.size % 6 != 0) {
            mData.size / 6 + 1
        } else {
            mData.size / 6
        }
        //滑动指示器
        //先写四页，后面再改
        repeat(sum) {
            val view = View(context)
            view.background = ContextCompat.getDrawable(context!!, R.drawable.selector_same_indicator)
            val lp = LinearLayout.LayoutParams(DensityUtil.dp2px(5f), DensityUtil.dp2px(5f))
            lp.setMargins(DensityUtil.dp2px(3f), 0, DensityUtil.dp2px(3f), 0)
            indicatorLayout.addView(view, lp)
            indicatorLayout[0].isSelected = true
        }
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }


    private var mType = ""

    companion object {
        const val BUY = "0"
        const val SELL = "1"
        fun newInstance(id: String?, type: String): DetailSameFragment {
            val fragment = DetailSameFragment()
            fragment.mType = type
            fragment.mId = id
//            val bundle = Bundle()
//            bundle.putString("id", id)
//            fragment.arguments = bundle
            return fragment
        }
    }

    private val mData = ArrayList<CommendList>()

    private val presenter by lazy { RecommendGoodsPresenter() }

    private var mId: String? = ""

    override fun getLayoutId(): Int = R.layout.fragment_detail_same


    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()

    }

    private val adapter by lazy { LoveShopGoodsAdapter(context, mData) }


    private val scrollHelper = PagingScrollHelper()

    override fun initView() {
        presenter.attachView(this)
        val manager = HorizontalPageLayoutManager(2, 3)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
        scrollHelper.setUpRecycleView(recyclerView)
        scrollHelper.updateLayoutManger()
        scrollHelper.setOnPageChangeListener { pos ->
            indicatorLayout?.apply {
                repeat(this.childCount) {
                    //indicatorLayout可能出现空
                    this[it].isSelected = pos == it
                }
            }

        }

        //滑动指示器
        //先写四页，后面再改
//        repeat(3) {
//            val view = View(context)
//            view.background = ContextCompat.getDrawable(context!!, R.drawable.selector_same_indicator)
//            val lp = LinearLayout.LayoutParams(DensityUtil.dp2px(5f), DensityUtil.dp2px(5f))
//            lp.setMargins(DensityUtil.dp2px(3f), 0, DensityUtil.dp2px(3f), 0)
//            indicatorLayout.addView(view, lp)
//            indicatorLayout[0].isSelected = true
//        }


    }


    override fun lazyLoad() {
        if (mType == BUY) {
            presenter.requestRecommendGoods(mId.toString(), "", 1)
        } else {
            presenter.requestRecommendGoods(mId.toString(), "asc", 1)
        }


    }

    override fun initEvent() {

        adapter.mClickListener = {
            //RxBus.getDefault().post(it,GoodsDetailActivity.FRESH_ORDER)
            GoodsDetailActivity.actionStart(context, it)
        }
    }
}