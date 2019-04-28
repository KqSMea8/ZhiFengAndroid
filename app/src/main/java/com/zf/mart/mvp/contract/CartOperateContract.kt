package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.CartPrice
import com.zf.mart.mvp.bean.SpecBean
import okhttp3.RequestBody

interface CartOperateContract {

    interface View : IBaseView {

        fun cartOperateError(msg: String, errorCode: Int)

        //修改数量
        fun setCount(bean: CartPrice)

        //修改选中状态
        fun setSelect(bean: CartPrice)

        //全选
        fun setCheckAll(bean: CartPrice)

        //删除
        fun setDeleteCart(bean: CartPrice)

        //获取商品规格
        fun setGoodsSpec(specBean: SpecBean)

        //修改商品规格
        fun setChangeSpec(bean: CartPrice)
    }

    interface Presenter : IPresenter<View> {

        fun requestCount(id: String, num: Int)

        fun requestSelect(cart: RequestBody)

        fun requestCheckAll(flag: Int)

        fun requestDeleteCart(id: RequestBody)

        fun requestGoodsSpec(id: String)

        fun requestChangeSpec(cartId: String, specId: String)
    }

}