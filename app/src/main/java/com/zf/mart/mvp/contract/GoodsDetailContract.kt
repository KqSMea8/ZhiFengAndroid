package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.*

interface GoodsDetailContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)
        //商品详情
        fun getGoodsDetail(bean: GoodsDetailBean)

        //秒杀商品详情
        fun setSecKillDetail(bean: SecKillDetailBean)

        //商品评论
        fun setGoodEva(bean: List<GoodEvaList>)

        //获取地址列表
        fun getAddress(bean: List<AddressBean>)

        //获得商品运费
        fun getGoodsFreight(bean: GoodsFreightBean)

        //获得商品规格
        fun getGoodsSpce(bean: List<List<GoodsSpecBean>>)

        //关注商品
        fun setCollectGoods(msg: String)

        //删除关注商品
        fun delCollectGoods(msg: String)

        //加入购物车
        fun addCartSuccess(msg: String)

        //根据规格key获取图片，库存
        fun getPricePic(bean: GoodsSpecInfo)
    }

    interface Presenter : IPresenter<View> {
        fun requestGoodsDetail(goods_id: String)

        fun requestGoodEva(goodId: String, type: Int, page: Int, num: Int)

        fun requestAddress()

        fun requestGoodsFreight(goods_id: String, region_id: String, buy_num: String)

        fun requestCollectGoods(goods_id: String)

        fun requestDelCollectGoods(goods_id: String)

        fun requestAddCart(goods_id: String, goods_num: Int, item_id: String)

        fun requestGoodsSpec(goods_id: String)

        fun requestPricePic(key: String, goods_id: String)

        fun requestSecKillDetail(id: String)
    }
}