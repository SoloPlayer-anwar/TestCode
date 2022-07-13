package com.testing.testseucom.ui.edit

import com.testing.testseucom.base.BasePresenter
import com.testing.testseucom.base.BaseView
import com.testing.testseucom.response.uid.ResponseByUid

interface EditContract {
    interface View:BaseView {
        fun editListSuccess(responseByUid: ResponseByUid)
        fun getListSuccess(responseByUid: ResponseByUid)
        fun editListFailure(message:String)
    }
    interface Presenter: EditContract, BasePresenter {
        fun submitEdit(locID:String,
                       locName:String,
                       locType:String,
                       projectCode:String,
                       buildingCode:String,
                       floorCode:String)
        fun getList(locID:String)
    }
}