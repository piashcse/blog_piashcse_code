package com.piashcse.experiment.mvvm_hilt.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.piashcse.experiment.mvvm_hilt.R
import com.piashcse.experiment.mvvm_hilt.utils.AppConstants
import com.piashcse.experiment.mvvm_hilt.databinding.FragmentGPSLocationBinding
import com.piashcse.experiment.mvvm_hilt.utils.base.BaseBindingFragment
import com.piashcse.experiment.mvvm_hilt.utils.showToast
import timber.log.Timber

class GPSLocationFragment : BaseBindingFragment<FragmentGPSLocationBinding>() {
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity())
    }
    private val locationRequest = LocationRequest.create().apply {
        interval = AppConstants.LOCATION.UPDATE_INTERVAL
        fastestInterval = AppConstants.LOCATION.FASTEST_UPDATE_INTERVAL
        maxWaitTime = AppConstants.LOCATION.MAX_WAIT_TIME
    }

    override fun init() {
        locationPermission()
    }

    private fun locationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                location()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                confirmationUiForPermission()
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
    }

    private fun confirmationUiForPermission() {
        val alertDialog: AlertDialog? = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                this.setMessage(getString(R.string.permission_detail))
                setPositiveButton(R.string.ok) { dialog, id ->
                    // User clicked OK button
                    requestPermissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    dialog.dismiss()
                }
                setNegativeButton(R.string.cancel) { dialog, id ->
                    // User cancelled the dialog
                    dialog.dismiss()
                }
            }
            // Set other dialog properties

            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show()
    }

    @SuppressLint("MissingPermission")
    private fun location() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    binding.apply {
                        if (isAdded) {
                            lat.text = resources.getString(
                                R.string.lat,
                                locationResult.lastLocation?.latitude.toString()
                            )
                            lon.text = resources.getString(
                                R.string.lon,
                                locationResult.lastLocation?.longitude.toString()
                            )
                        }
                    }
                    Timber.e("location : ${locationResult.locations}")
                }
            },
            Looper.getMainLooper()
        )
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                location()
            } else {
                requireContext().showToast("Permission denied")
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }
}