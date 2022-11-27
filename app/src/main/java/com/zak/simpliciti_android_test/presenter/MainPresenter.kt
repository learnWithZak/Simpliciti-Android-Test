package com.zak.simpliciti_android_test.presenter

import android.util.Log
import com.zak.simpliciti_android_test.MainContract
import com.zak.simpliciti_android_test.model.GeoKeoResponse
import com.zak.simpliciti_android_test.repository.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class MainPresenter(
    view: MainContract.View,
    private val mainRepository: MainRepository
) : MainContract.Presenter {

    companion object {
        private val TAG = MainPresenter::class.java.simpleName
    }

    private val disposables = CompositeDisposable()
    private var view: MainContract.View? = view
    private var distanceInMeter: Double = 0.0

    override fun onGetLocationResult(lat: Double, lng: Double) {
        view?.showLatAndLng(lat, lng)
        getReverseGeocoding(lat, lng)
    }

    private fun getReverseGeocoding(lat: Double, lng: Double) {
        if (distanceInMeter < 10) {
            disposables.add(
                mainRepository.getReverseGeocoding(lat, lng)
                    .delay(10000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        view?.hideCurrentAddressText()
                        view?.showCurrentAddressProgressBar()
                    }
                    .subscribe({ response ->
                        onGetReverseGeocodingSuccess(response)
                    }, { throwable ->
                        onGetReverseGeocodingError(throwable)
                    })
            )
        }
    }

    private fun onGetReverseGeocodingSuccess(response: GeoKeoResponse) {
        val firstResult = response.results.first()
        distanceInMeter = firstResult.distance.replace("km", "").toDouble() * 1000
        view?.showCurrentAddressText(firstResult.formattedAddress)
        view?.hideCurrentAddressProgressBar()
    }

    private fun onGetReverseGeocodingError(throwable: Throwable) {
        view?.showErrorMessage(throwable.message)
        view?.hideCurrentAddressProgressBar()
    }

    override fun onErrorGetLocationResult() {
        view?.showErrorMessage("No location found")
    }

    override fun onRemoveLocationUpdatesSucceeded() {
        Log.d(TAG, "Location Callback removed.")
    }

    override fun onRemoveLocationUpdatesFailed() {
        Log.d(TAG, "Failed to remove Location Callback.")
    }


    override fun onPermissionDenied() {
        view?.showErrorMessage("Location permission denied")
    }

    override fun onDestroy() {
        view = null
        disposables.dispose()
    }
}