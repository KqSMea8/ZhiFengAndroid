package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.ForgetPwdContract
import com.zf.mart.mvp.model.ForgetPwdModel
import com.zf.mart.net.exception.ExceptionHandle

class ForgetPwdPresenter : BasePresenter<ForgetPwdContract.View>(), ForgetPwdContract.Presenter {

    private val model: ForgetPwdModel by lazy { ForgetPwdModel() }

    override fun requestCode(scene: Int, mobile: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestCode(scene, mobile)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> setCode(it.msg)
                        else -> showError(it.msg, it.status)
                    }
                }

            }, {
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })

        addSubscription(disposable)
    }

    //第一步对比验证码
    override fun requestContract(mobile: String, code: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestContract(mobile, code)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> setContract()
                        else -> showError(it.msg, it.status)
                    }
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

    override fun requestChangePwd(mobile: String, password: String, password2: String, scene: Int) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestChangePwd(mobile, password, password2, scene)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> setChangePwd()
                        else -> showError(it.msg, it.status)
                    }
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

}