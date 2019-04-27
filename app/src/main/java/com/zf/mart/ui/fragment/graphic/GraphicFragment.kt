package com.zf.mart.ui.fragment.graphic

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.mart.R
import com.zf.mart.base.BaseFragment
import com.zf.mart.mvp.bean.AttriBute
import com.zf.mart.mvp.contract.GoodsAttrContract
import com.zf.mart.mvp.presenter.GoodsAttrPresenter
import com.zf.mart.ui.adapter.OrderInfoAdapter
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.fragment_graphic.*
class GraphicFragment : BaseFragment(), GoodsAttrContract.View {

    override fun showError(msg: String, errorCode: Int) {

    }

    override fun getGoodsAttr(bean: List<AttriBute>) {
        mData.clear()
        mData.addAll(bean)
        adapter.notifyDataSetChanged()

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    //接收详情信息
    companion object {
        fun newInstance(data: String?, id: String?): GraphicFragment {
            val fragment = GraphicFragment()
            val bundle = Bundle()
            bundle.putString("mData", data)
            bundle.putString("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_graphic

    private val adapter by lazy { OrderInfoAdapter(context, mData) }

    private val presenter by lazy { GoodsAttrPresenter() }

    private var mData = ArrayList<AttriBute>()
    //接收传递过来的ID
    private var id: String = ""
    //接收传递过来的H5
    private var htmlText: String = ""
    //测试值
    private val aaa ="<p>This text is normal</p>"+
        "<p><img src='https://mobile.zhifengwangluo.c3w.cc/public/upload/goods/2019/03-16/6a293870038abbc58820f605d20e9e4b.jpg'/></p>"

    override fun initView() {
        presenter.attachView(this)

        htmlText = arguments?.getString("mData").toString()

        id = arguments?.getString("id").toString()

        RichText.initCacheDir(context)

        RichText.from(aaa).into(h5Decode)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            RecyclerViewDivider(
                context,
                LinearLayoutManager.VERTICAL,
                2,
                ContextCompat.getColor(context!!, R.color.colorLine)
            )
        )

    }


    override fun lazyLoad() {
    }

    override fun initEvent() {
        show.setOnClickListener {
            show.isSelected = !show.isSelected
            if (show.isSelected) {
                show.text = "收起"
                h5Decode.visibility = View.VISIBLE
            } else {
                show.text = "展开"
                h5Decode.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()

    }

    override fun onStart() {
        super.onStart()
        presenter.requestGoodsAttr(id)
    }

}