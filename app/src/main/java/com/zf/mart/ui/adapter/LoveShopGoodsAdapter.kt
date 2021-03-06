package com.zf.mart.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.api.UriConstant.BASE_URL
import com.zf.mart.mvp.bean.CommendList
import com.zf.mart.ui.activity.GoodsDetailActivity
import com.zf.mart.utils.GlideUtils
import kotlinx.android.synthetic.main.item_same_love_goods.view.*

class LoveShopGoodsAdapter(val context: Context?, val data: List<CommendList>) :
    RecyclerView.Adapter<LoveShopGoodsAdapter.ViewHolder>() {

    var mClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_same_love_goods, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            GlideUtils.loadUrlImage(context, BASE_URL + data[position].original_img, goodsIcon)

            goods_name.text = data[position].goods_name

            goods_price.text = data[position].shop_price
        }

        holder.itemView.setOnClickListener {
            mClickListener?.invoke(data[position].goods_id)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}