package com.testing.testseucom.ui.create

import com.testing.testseucom.base.BasePresenter
import com.testing.testseucom.base.BaseView
import com.testing.testseucom.response.uid.ResponseByUid
import retrofit2.http.Field

interface CreateContract {
    interface View: BaseView {
        fun saveSuccess(responseByUid: ResponseByUid)
        fun saveFailure(message:String)
    }

    interface Presenter: CreateContract, BasePresenter {
        fun submit(locName:String,
                   locType:String ,
                   projectCode:String,
                   buildingCode:String,
                   floorCode:String)

    }
}