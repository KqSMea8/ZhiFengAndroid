package com.zf.mart.mvp.presenter

import com.zf.mart.api.UriConstant
import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.GroupContract
import com.zf.mart.mvp.model.GroupModel
import com.zf.mart.net.exception.ExceptionHandle

class GroupPresenter : BasePresenter<GroupContract.View>(), GroupContract.Presenter {

    private val model: GroupModel by lazy { GroupModel() }

    private var mPage = 1

    override fun requestGroup(page: Int?) {

        mPage = page ?: mPage

        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getGroup(mPage)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (mPage == 1) {
                                if (it.data != null && it.data.isNotEmpty()) {
                                    setGroup(it.data)
                                } else {
                                    freshEmpty()
                                }
                            } else {
                                if (it.data != null && it.data.isNotEmpty()) {
                                    loadMore(it.data)
                                } else {
                                    loadComplete()
                                }
                            }
                            if (it.data != null) {
                                if (it.data.size < UriConstant.PER_PAGE) {
                                    loadComplete()
                                }
                            }
                        }
                        else -> if (mPage == 1) showError(it.msg, it.status) else loadMoreError(it.msg, it.status)
                    }
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    if (mPage == 1) showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    else loadMoreError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

}