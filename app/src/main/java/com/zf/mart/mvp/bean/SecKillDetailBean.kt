package com.zf.mart.mvp.bean

data class SecKillDetailBean(
    val info: SecKillInfo
)

data class SecKillInfo(
    val id: String,
    val title: String,
    val goods_id: String,
    val item_id: String,
    val price: String,
    val goods_name: String,
    val goods_images: List<String>,
    val goods_content: String,
    val start_time: Long,
    val end_time: Long,
    val shop_price: String,
    val store_count: String,
    val sales_sum: String
)