package com.testing.testseucom.ui.create

import com.testing.testseucom.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CreatePresenter(private val view: CreateContract.View):CreateContract.Presenter {
    private val mCompositeDisposable : CompositeDisposable?
    init {
        mCompositeDisposable = CompositeDisposable()
    }

    override fun submit(
        locName: String,
        locType: String,
        projectCode: String,
        buildingCode: String,
        floorCode: String,
    ) {
        view.showLoading(true)
        val disposable = HttpClient.getInstance().getApi()!!.saveList(
            locName, locType, projectCode, buildingCode, floorCode
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when(it.status.equals("success", true)) {
                    true -> {
                        view.saveSuccess(it)
                        view.showLoading(false)
                    }

                    false -> {
                        view.saveFailure(it.message)
                        view.showLoading(false)
                    }
                }
            }, {
                view.saveFailure(it.message.toString())
                view.showLoading(false)
            })

        mCompositeDisposable?.add(disposable)
    }

    override fun subscribe() {}

    override fun unSubscribe() {
        mCompositeDisposable?.clear()
    }
}