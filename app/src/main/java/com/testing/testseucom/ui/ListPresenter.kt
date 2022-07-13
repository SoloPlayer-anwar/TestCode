package com.testing.testseucom.ui

import com.testing.testseucom.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListPresenter(private val view: ListContract.View): ListContract.Presenter {
    private val mCompositeDisposable: CompositeDisposable?

    init {
        mCompositeDisposable = CompositeDisposable()
    }

    override fun getList() {
        view.showLoading(true)
        val disposable = HttpClient.getInstance().getApi()!!.getList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when(it.status.equals("success", true)) {
                    true -> {
                        view.getListSuccess(it)
                        view.showLoading(false)
                    }

                    false -> {
                        view.getListFailure(it.message)
                        view.showLoading(false)
                    }
                }
            }, {
                view.getListFailure(it.message.toString())
                view.showLoading(false)
            })

        mCompositeDisposable?.add(disposable)
    }


    override fun subscribe() {}

    override fun unSubscribe() {
        mCompositeDisposable?.clear()
    }
}