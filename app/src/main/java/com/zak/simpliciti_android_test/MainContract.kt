package com.zak.simpliciti_android_test

import com.zak.simpliciti_android_test.presenter.BasePresenter


interface MainContract {

    interface Presenter: BasePresenter {
        fun onGetLocationResult(lat: Double, lng: Double)
        fun onPermissionDenied()
        fun onErrorGetLocationResult()
    }

    interface View {

    }
}