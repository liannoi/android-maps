package org.itstep.liannoi.maps.application.common.maps

import com.google.android.gms.maps.model.LatLng

interface FeatureCollection {

    fun add(latLng: LatLng)
}
