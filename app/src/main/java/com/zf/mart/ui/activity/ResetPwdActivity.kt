package com.zf.mart.ui.activity

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.mart.R
import com.zf.mart.api.UriConstant
import com.zf.mart.base.BaseActivity
import com.zf.mart.mvp.contract.ForgetPwdContract
import com.zf.mart.mvp.presenter.ForgetPwdPresenter
import com.zf.mart.showToast
import com.zf.mart.utils.FormUtil
import com.zf.mart.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_setpwd.*

/**
 * 重设密码先验证手机号
 */
class ResetPwdActivity : BaseActivity(), ForgetPwdContract.View {

    private var countDownTimer: CountDownTimer? = null

    override fun setCode(msg: String) {
        showToast(msg)
        sendCode.isSelected = !sendCode.isSelected
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer((UriConstant.CODE_TIME * 1000).toLong(), 1000) {
            override fun onFinish() {
                isRun = false
                sendCode.text = "点击再次获取"
                sendCode.isSelected = !sendCode.isSelected
            }

            override fun onTick(millisUntilFinished: Long) {
                isRun = true
                sendCode.text = "${(millisUntilFinished / 1000)}秒后重新获取"
            }
        }
        countDownTimer?.start()
    }

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    //第一步通过
    override fun setContract() {
        InputPwdActivity.actionStart(this, phone.text.toString(),InputPwdActivity.SELL)
    }

    override fun setChangePwd() {
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun dismissLoading() {
        dismissLoadingDialog()
    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, ResetPwdActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
    }

    override fun layoutId(): Int = R.layout.activity_setpwd

    private val presenter by lazy { ForgetPwdPresenter() }

    override fun initData() {
    }

    override fun initView() {
        presenter.attachView(this)
    }

    private var isRun = false

    override fun initEvent() {

        confirm.setOnClickListener {
            when {
                !FormUtil.isMobile(phone.text.toString()) -> phoneError.visibility = View.VISIBLE
                code.text.isEmpty() -> {
                    phoneError.visibility = View.GONE
                    codeHint.visibility = View.VISIBLE
                }
                else -> {
                    phoneError.visibility = View.GONE
                    codeHint.visibility = View.GONE
                    presenter.requestContract(phone.text.toString(), code.text.toString())
                }
            }
        }

        sendCode.setOnClickListener {
            if (!isRun) {
                if (FormUtil.isMobile(phone.text.toString())) {
                    presenter.requestCode(2, phone.text.toString())
                } else {
                    showToast("请输入正确手机号")
                }
            }
        }

    }

    override fun start() {
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        presenter.detachView()
        super.onDestroy()
    }

}