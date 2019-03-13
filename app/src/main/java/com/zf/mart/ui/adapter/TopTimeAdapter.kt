package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import kotlinx.android.synthetic.main.item_top_time.view.*

class TopTimeAdapter(val context: Context?, val data: List<String>) :
    RecyclerView.Adapter<TopTimeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_top_time, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    private var selectedPos = 0

    private var mListener: OnItemClickListener? = null
    fun setOnClickListener(listener: OnItemClickListener) {
        mListener = listener
    }


    fun setCheck(position: Int) {
        selectedPos = position
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(pos: Int)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.apply {

            time.text = data[position]

            time.isSelected = selectedPos == position
            state.isSelected = selectedPos == position
            linearLayout.isSelected = selectedPos == position
            setOnClickListener {
                selectedPos = position
                notifyDataSetChanged()
                mListener?.onClick(position)
            }
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}