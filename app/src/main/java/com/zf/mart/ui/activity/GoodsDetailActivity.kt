package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.flyco.tablayout.listener.CustomTabEntity
import com.zf.mart.R
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.bean.TabEntity
import com.zf.mart.ui.adapter.DetailAskAdapter
import com.zf.mart.ui.adapter.DetailBrandAdapter
import com.zf.mart.ui.adapter.DetailEvaAdapter
import com.zf.mart.ui.adapter.GuideAdapter
import com.zf.mart.ui.fragment.graphic.GraphicFragment
import com.zf.mart.ui.fragment.graphic.OrderAnswerFragment
import com.zf.mart.ui.fragment.same.DetailSameFragment
import com.zf.mart.utils.LogUtils
import com.zf.mart.utils.StatusBarUtils
import com.zf.mart.view.dialog.ShareSuccessDialog
import com.zf.mart.view.popwindow.ServicePopupWindow
import kotlinx.android.synthetic.main.activity_goods_detail2.*
import kotlinx.android.synthetic.main.layout_detail_brand.*
import kotlinx.android.synthetic.main.layout_detail_eva.*
import kotlinx.android.synthetic.main.layout_detail_head.*
import kotlinx.android.synthetic.main.layout_detail_same.*
import kotlinx.android.synthetic.main.pop_detail_share.view.*

/**
 * 商品详情
 */
class GoodsDetailActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, GoodsDetailActivity::class.java))
        }
    }

    override fun initToolBar() {

        StatusBarUtils.darkMode(
                this,
                ContextCompat.getColor(this, R.color.colorSecondText),
                0.3f
        )


        //分享
        shareLayout.setOnClickListener {
            val popUpWindow = object : ServicePopupWindow(
                    this, R.layout.pop_detail_share,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ) {
                override fun initView() {
                    contentView.apply {

                        weChat.setOnClickListener {
                            onDismiss()
                            ShareSuccessDialog.showDialog(supportFragmentManager)
                        }

                        cancel.setOnClickListener {
                            onDismiss()
                        }
                    }
                }
            }
            popUpWindow.showAtLocation(shareLayout, Gravity.BOTTOM, 0, 0)
        }


        backLayout.setOnClickListener {
            finish()
        }
    }

    override fun layoutId(): Int = R.layout.activity_goods_detail2


    override fun initData() {
    }

    override fun initView() {

        floatingButton.hide()

        //全部评价
        allEvaluation.setOnClickListener {
            EvaluationActivity.actionStart(this, "")
        }

        //banner
        initBanner()

        //商品评价
        initEvaluation()

        //问大家
        initAsk()

        //商家品牌推荐
        initBrand()

        //相似推荐
        initSame()

        //图文详情
        initGraphic()


    }


    private fun changeAlpha(color: Int, fraction: Float): Int {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        val alpha = (Color.alpha(color) * fraction).toInt()
        return Color.argb(alpha, red, green, blue)
    }

    private fun initGraphic() {
        val titles = arrayOf("图文详情", "答疑")
        val fgms = arrayListOf(GraphicFragment.newInstance() as Fragment, OrderAnswerFragment.newInstance() as Fragment)
        segmentTabLayout.setTabData(titles, this, R.id.graphicFragment, fgms)

    }


    //商品评价adapter
    private val evaAdapter by lazy { DetailEvaAdapter(this) }

    private fun initEvaluation() {
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        evaRecyclerView.layoutManager = manager
        evaRecyclerView.adapter = evaAdapter
    }

    private val askAdapter by lazy { DetailAskAdapter(this) }

    private fun initAsk() {
        askRecyclerView.layoutManager = LinearLayoutManager(this)
        askRecyclerView.adapter = askAdapter
    }

    private val brandAdapter by lazy { DetailBrandAdapter(this) }

    private fun initBrand() {
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        brandRecyclerView.layoutManager = manager
        brandRecyclerView.adapter = brandAdapter
    }

    /**
     * 相似推荐
     * pageRecyclerView
     */
    private fun initSame() {

        val fgms = arrayListOf(
                DetailSameFragment.newInstance() as Fragment
                , DetailSameFragment.newInstance() as Fragment
        )
        val entitys = ArrayList<CustomTabEntity>()
        entitys.add(TabEntity("相似推荐", 0, 0))
        entitys.add(TabEntity("同类热销排行", 0, 0))
        sameTabLayout.setTabData(entitys, this, R.id.frameLayout, fgms)

    }

    private fun initBanner() {


        val images = listOf(R.mipmap.v1, R.mipmap.v2, R.mipmap.v3, R.mipmap.v4)
        val imageViews = ArrayList<ImageView>()
        repeat(images.size) { pos ->
            val img = ImageView(this)
            Glide.with(this).load(images[pos]).into(img)
            img.scaleType = ImageView.ScaleType.CENTER_CROP
            imageViews.add(img)
            img.setOnClickListener {
                LogUtils.e(">>>>>>点击了第：$pos")
            }
        }
        bannerViewPager.adapter = GuideAdapter(imageViews)
        bannerNum.text = "1/${images.size}"

        /** 滑动改变指示器 */
        bannerViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                bannerNum.text = "${position + 1}/${images.size}"
            }
        })
    }

    override fun initEvent() {

        scrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            //标题栏渐变
            var alpha = scrollY / 100 * 0.7f
            if (alpha >= 1.0) {
                alpha = 1.0f
            }
            orderDetailHead.setBackgroundColor(
                    changeAlpha(
                            ContextCompat.getColor(this, R.color.whit)
                            , alpha
                    )
            )
            //回到顶部按钮
            if (scrollY - oldScrollY > 0) {
                floatingButton.hide()
            } else {
                floatingButton.show()
            }

        }

        floatingButton.setOnClickListener {
            scrollView.fullScroll(ScrollView.SCROLL_INDICATOR_TOP)
        }

    }

    override fun start() {
    }
}