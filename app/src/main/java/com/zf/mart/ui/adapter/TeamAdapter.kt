package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zf.mart.R
import com.zf.mart.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.item_team.view.*

class TeamAdapter(val context: Context) : RecyclerView.Adapter<TeamAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_team, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            headLayout.setOnClickListener {
                arrowIcon.isSelected = !arrowIcon.isSelected
                if (arrowIcon.isSelected) {
                    //展开
                    detailLayout.visibility = View.VISIBLE
                } else {
                    //隐藏
                    detailLayout.visibility = View.GONE
                }
            }

            val adapter = TeamDetailAdapter(context)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                RecyclerViewDivider(
                    context,
                    LinearLayoutManager.VERTICAL,
                    1,
                    ContextCompat.getColor(context, R.color.colorBackground)
                )
            )
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}