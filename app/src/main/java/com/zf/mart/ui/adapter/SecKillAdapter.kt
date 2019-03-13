package com.zf.mart.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.ui.activity.SecKillDetailActivity
import kotlinx.android.synthetic.main.item_seckill.view.*

class SecKillAdapter(val context: Context?) : RecyclerView.Adapter<SecKillAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_seckill, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            loadingView.setProgressColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
            loadingView.setBgColor(ContextCompat.getColor(context, R.color.colorBackground))
            loadingView.setPercentage(60f)

            buy.setOnClickListener {
                SecKillDetailActivity.actionStart(context)
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}