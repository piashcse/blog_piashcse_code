package com.piashcse.experiment.mvvm_hilt.ui.googlemap

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GoogleMapViewModel @Inject constructor() : ViewModel() {
    val sydney = LatLng(-34.0, 151.0)
}