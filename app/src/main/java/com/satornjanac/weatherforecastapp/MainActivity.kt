package com.satornjanac.weatherforecastapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.satornjanac.weatherforcastapp.databinding.ActivityMainBinding
import com.satornjanac.weatherforecastapp.ui.adapters.WeatherAdapter
import com.satornjanac.weatherforecastapp.ui.viewmodels.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.TimeZone

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val forecastViewModel: ForecastViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setSupportActionBar(activityMainBinding.toolbar)

        checkForLocation()

        forecastViewModel.viewsAndData.observe(this) {
            activityMainBinding.swipeToRefresh.isRefreshing = false
            activityMainBinding.sectionsList.adapter = WeatherAdapter(this, it)
            activityMainBinding.sectionsList.setHasFixedSize(true)
        }

        activityMainBinding.swipeToRefresh.setOnRefreshListener {
            checkForLocation()
        }

    }

    private fun checkForLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForLocationPermission()
            return
        }

        getLastLocation()
    }

    private fun askForLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    getLastLocation()
                }

                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    getLastLocation()
                }

                else -> {
                    // No location access granted.
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val timeZone = TimeZone.getDefault()
                    forecastViewModel.getForecast(it.longitude, it.latitude, timeZone.id)
                }
            }
    }
}