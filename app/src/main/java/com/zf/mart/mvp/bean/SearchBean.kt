package com.zf.mart.mvp.bean

data class SearchBean(
    val goods_list: List<SearchList>?
)

data class SearchList(
    val goods_id: String,
    val goods_name: String,
    val goods_images: List<GoodsImagesList>?,
    val shop_price: String
)

data class GoodsImagesList(
    val img_id: String,
    val image_url: String
)