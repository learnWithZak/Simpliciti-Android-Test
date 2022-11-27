package com.zak.simpliciti_android_test

import com.zak.simpliciti_android_test.presenter.BasePresenter


interface MainContract {

    interface Presenter: BasePresenter {
        fun onGetLocationResult(lat: Double, lng: Double)
        fun onPermissionDenied()
        fun onErrorGetLocationResult()
    }

    interface View {
        fun showLatAndLng(lat:Double, lng: Double)
        fun showCurrentAddressText(address: String)
        fun showErrorMessage(message: String?)
        fun showCurrentAddressProgressBar()
        fun hideCurrentAddressProgressBar()
        fun hideCurrentAddressText()
    }
}