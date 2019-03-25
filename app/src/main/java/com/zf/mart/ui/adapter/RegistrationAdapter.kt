package com.zf.mart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.zf.mart.R
import com.zf.mart.view.gridview.SquareRelativeLayout

class RegistrationAdapter(private val context: Context, private val days: Int, private val week: Int, private val mday: Int,private val year:Int,private val month: Int) : BaseAdapter() {



    private var dayNumber: IntArray? = null

    private var viewHolder: ViewHolder? = null
    private var a:Int=0
    private var Date_array:Array<String> = arrayOf("2019-02-01","2019-02-13","2019-03-20","2019-10-09","2019-10-18")

    //接收日期处理方法
    fun Date(){

       for((index, e) in Date_array.withIndex())
       {

           //截取年份
         var yy:Int=e.split("-")[0].toInt()
           //截取月份
          var mm:Int= e.split("-")[1].toInt()
           //截取日
          var dd:Int= e.split("-")[2].toInt()
          if (yy==year&&mm==(month+1)&&dd==dayNumber!![a]){
              viewHolder!!.day.setBackgroundResource(R.drawable.rili)
              viewHolder!!.sqly.setPadding(20, 20, 20, 20)
              viewHolder!!.day.text = ""
              viewHolder!!.back.setBackgroundResource(R.drawable.shape_calendar_bg)
          }



           }
       }



    init {
        getEveryDay()
    }


    override fun getCount(): Int {

        return 42
    }

    override fun getItem(i: Int): String? {

        return null
    }

    override fun getItemId(i: Int): Long {
        return dayNumber!![i].toLong()
    }//点击时

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.item_registrationadatper, null)
            viewHolder = ViewHolder(view!!)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        viewHolder!!.day.text = if (dayNumber!![i] == 0) "" else dayNumber!![i].toString() + ""

        //判断签到了的日子并显示样式
        a=i
        Date()

        return view
    }

    private inner class ViewHolder(view: View) {
        val day: TextView
        val back: LinearLayout
        val sqly: SquareRelativeLayout

        init {
            this.day = view.findViewById(R.id.day) as TextView
            this.sqly=view.findViewById(R.id.squarerly) as SquareRelativeLayout
            this.back = view.findViewById(R.id.calendar_bg) as LinearLayout
        }
    }

    /**
     * 得到42格子 每一格子的值
     */
    private fun getEveryDay() {
        dayNumber = IntArray(42)

        for (i in 0..41) {
            if (i < days + week && i >= week) {
                dayNumber!![i] = i - week + 1
            } else {
                dayNumber!![i] = 0
            }
        }
    }
}
