package com.zak.simpliciti.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.zak.simpliciti.MainContract
import com.zak.simpliciti.presenter.MainPresenter
import com.zak.simpliciti.repository.MainRepository
import org.koin.android.ext.android.inject
import com.zak.simpliciti.R
import com.zak.simpliciti.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var mainPresenter: MainContract.Presenter
    private val repository: MainRepository by inject()

    companion object {
        private const val REQUEST_LOCATION = 1
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mainPresenter = MainPresenter(this, repository)
    }

    override fun onStart() {
        super.onStart()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        initLocationRequest()
        initLocationCallBack()
        getCurrentLocation()
    }

    private fun initLocationRequest() {
        locationRequest = LocationRequest.Builder( 1000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()
    }

    private fun initLocationCallBack() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let {
                    mainPresenter.onGetLocationResult(it.latitude, it.longitude)
                } ?: mainPresenter.onErrorGetLocationResult()
            }
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermissions()
        } else {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )
        }
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                mainPresenter.onPermissionDenied()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                mainPresenter.onRemoveLocationUpdatesSucceeded()
            } else {
                mainPresenter.onRemoveLocationUpdatesFailed()

            }
        }
    }

    override fun showLatAndLng(lat: Double, lng: Double) {
        val currentPosition = String.format(resources.getString(R.string.lat_lng_formatted_text), lat, lng)
        binding.currentPositionTv.text = currentPosition
    }

    override fun showCurrentAddressText(address: String) {
        binding.currentAddressTv.text = address
    }

    override fun showErrorMessage(message: String?) {
        val errorMessage = message ?: "An error occurred"
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun showCurrentAddressProgressBar() {
        binding.currentAddressPb.visibility = View.VISIBLE
    }

    override fun hideCurrentAddressProgressBar() {
        binding.currentAddressPb.visibility = View.INVISIBLE
    }

    override fun hideCurrentAddressText() {
        binding.currentAddressTv.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        mainPresenter.onDestroy()
        super.onDestroy()
    }
}