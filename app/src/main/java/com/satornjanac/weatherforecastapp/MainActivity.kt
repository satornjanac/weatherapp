package com.satornjanac.weatherforecastapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.satornjanac.weatherforcastapp.databinding.ActivityMainBinding
import com.satornjanac.weatherforecastapp.ui.adapters.WeatherAdapter
import com.satornjanac.weatherforecastapp.ui.viewmodels.ForecastViewModel
import com.satornjanac.weatherforecastapp.ui.viewmodels.SectionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.TimeZone
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val forecastViewModel: ForecastViewModel by viewModels()
    private val sectionViewModel: SectionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setSupportActionBar(activityMainBinding.toolbar)

        checkForLocation()

        forecastViewModel.viewsAndData.observe(this) {
            activityMainBinding.sectionsList.adapter = WeatherAdapter(this, it)
            activityMainBinding.sectionsList.setHasFixedSize(true)
        }

//        sectionViewModel.sectionList.observe(this) {
//            Toast.makeText(this, "Size is: ${it.size}", Toast.LENGTH_LONG).show()
//        }
//        sectionViewModel.getSections()

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