package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.api.UriConstant
import com.zf.mart.mvp.bean.OrderGoodsList
import com.zf.mart.utils.GlideUtils
import kotlinx.android.synthetic.main.item_order_goods.view.*

class OrderGoodsAdapter(private val context: Context?, private val data: List<OrderGoodsList>) : RecyclerView.Adapter<OrderGoodsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_order_goods, parent, false)
        return ViewHolder(view)
    }

    var onItemClickListener: (() -> Unit)? = null

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + data[position].original_img, goodsIcon)
            goodsName.text = data[position].goods_name
            goodsPrice.text = "¥${data[position].final_price}×${data[position].goods_num}"
            goodsSize.text = data[position].spec_key_name

            setOnClickListener {
                onItemClickListener?.invoke()
            }
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}