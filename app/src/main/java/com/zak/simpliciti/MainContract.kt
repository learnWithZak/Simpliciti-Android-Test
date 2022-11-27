package com.zak.simpliciti

import com.zak.simpliciti.presenter.BasePresenter


interface MainContract {

    interface Presenter: BasePresenter {
        fun onGetLocationResult(lat: Double, lng: Double)
        fun onPermissionDenied()
        fun onErrorGetLocationResult()
        fun onRemoveLocationUpdatesSucceeded()
        fun onRemoveLocationUpdatesFailed()
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