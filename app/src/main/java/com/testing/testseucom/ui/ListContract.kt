package com.testing.testseucom.ui

import com.testing.testseucom.base.BasePresenter
import com.testing.testseucom.base.BaseView
import com.testing.testseucom.response.ResponseList
import com.testing.testseucom.response.uid.ResponseByUid
import retrofit2.http.Field
import retrofit2.http.Path

interface ListContract {
    interface View: BaseView {
        fun getListSuccess(responseList: ResponseList)
        fun getListFailure(message:String)
    }

    interface Presenter: ListContract, BasePresenter {
        fun getList()
    }
}