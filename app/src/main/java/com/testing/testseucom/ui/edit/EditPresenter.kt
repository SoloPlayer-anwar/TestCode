package com.testing.testseucom.ui.edit

import com.testing.testseucom.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EditPresenter(private val view: EditContract.View): EditContract.Presenter {
    private val mCompositeDisposable: CompositeDisposable?

    init {
        mCompositeDisposable = CompositeDisposable()
    }

    override fun submitEdit(
        locID: String,
        locName: String,
        locType: String,
        projectCode: String,
        buildingCode: String,
        floorCode: String,
    ) {
        view.showLoading(true)
        val disposable = HttpClient.getInstance().getApi()!!.updateList(
            locID, locName, locType, projectCode, buildingCode, floorCode
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when(it.status.equals("success", true)) {
                    true -> {
                        view.editListSuccess(it)
                        view.showLoading(false)
                    }

                    false -> {
                        view.editListFailure(it.message)
                        view.showLoading(false)
                    }
                }
            }, {
                view.editListFailure(it.message.toString())
                view.showLoading(false)
            })

        mCompositeDisposable?.add(disposable)
    }


    override fun getList(locID: String) {
        view.showLoading(true)
        val disposable = HttpClient.getInstance().getApi()!!.getByUid(
            locID
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when(it.status.equals("success", true)) {
                    true -> {
                        view.getListSuccess(it)
                        view.showLoading(false)
                    }

                    false -> {
                        view.editListFailure(it.message)
                        view.showLoading(false)
                    }
                }
            }, {
                view.editListFailure(it.message.toString())
                view.showLoading(false)
            })

        mCompositeDisposable?.add(disposable)
    }

    override fun subscribe() {}

    override fun unSubscribe() {
        mCompositeDisposable?.clear()
    }
}