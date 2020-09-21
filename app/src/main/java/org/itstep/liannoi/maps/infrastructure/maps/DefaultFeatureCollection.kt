package org.itstep.liannoi.maps.infrastructure.maps

import com.google.android.gms.maps.model.LatLng
import org.itstep.liannoi.maps.application.common.maps.Feature
import org.itstep.liannoi.maps.application.common.maps.FeatureCollection
import org.itstep.liannoi.maps.application.common.maps.Geometry

class DefaultFeatureCollection constructor(
    private val features: MutableList<Feature> = mutableListOf(),
    private val type: String = "FeatureCollection"
) : FeatureCollection {

    override fun add(latLng: LatLng) {
        add(Feature(Geometry(arrayOf(latLng.longitude, latLng.latitude))))
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    private fun add(feature: Feature) {
        features.add(feature)
    }
}
