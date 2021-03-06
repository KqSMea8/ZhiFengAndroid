package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.RegisterContract
import com.zf.mart.mvp.model.RegisterModel
import com.zf.mart.net.exception.ExceptionHandle

class RegisterPresenter : BasePresenter<RegisterContract.View>(), RegisterContract.Presenter {

    private val model by lazy { RegisterModel() }


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

    override fun requestRegister(nickname: String, username: String, password: String, password2: String, code: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestRegister(nickname, username, password, password2, code)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> setRegister()
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