package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.SearchList

interface SearchContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        //搜索结果
        fun setSearchList(bean: List<SearchList>)

        //加载结果
        fun setLoadMore(bean: List<SearchList>)

        //刷新无结果
        fun freshEmpty()

        //加载第二页失败
        fun loadMoreError(msg:String,status:Int)

        //全部加载完成
        fun loadComplete()

    }

    interface Presenter : IPresenter<View> {
        fun requestSearch(
            q: String,
            id: String,
            brand_id: String,
            sort: String,
            sel: String,
            price: String,
            start_price: String,
            end_price: String,
            price_sort: String,
            page: Int?
        )

    }

}