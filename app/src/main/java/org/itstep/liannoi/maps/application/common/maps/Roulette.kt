package org.itstep.liannoi.maps.application.common.maps

import com.google.android.gms.maps.model.LatLng

interface Roulette {

    fun commit(latLng: LatLng): List<LatLng>

    fun measure(): Double
}
