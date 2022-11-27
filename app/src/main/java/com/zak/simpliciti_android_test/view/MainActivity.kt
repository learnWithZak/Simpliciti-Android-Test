package com.zak.simpliciti_android_test.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.zak.simpliciti_android_test.MainContract
import com.zak.simpliciti_android_test.databinding.ActivityMainBinding
import com.zak.simpliciti_android_test.presenter.MainPresenter
import com.zak.simpliciti_android_test.repository.MainRepositoryImpl
import com.zak.simpliciti_android_test.service.MainService

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var mainPresenter: MainContract.Presenter

    companion object {
        private const val REQUEST_LOCATION = 1
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mainPresenter = MainPresenter(this, MainRepositoryImpl(MainService.instance))
    }

    override fun onStart() {
        super.onStart()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder( 1000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let {
                    mainPresenter.onGetLocationResult(it.latitude, it.longitude)
                } ?: mainPresenter.onErrorGetLocationResult()
            }
        }
        getCurrentLocation()
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
                Log.d(TAG, "Location Callback removed.")
            } else {
                Log.d(TAG, "Failed to remove Location Callback.")
            }
        }
    }

    override fun showLatAndLng(lat: Double, lng: Double) {
        binding.currentPositionTv.text = "Lat: $lat - lng: $lng"
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