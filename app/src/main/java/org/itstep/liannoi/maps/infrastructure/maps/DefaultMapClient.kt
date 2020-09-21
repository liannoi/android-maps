package org.itstep.liannoi.maps.infrastructure.maps

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import org.itstep.liannoi.maps.R
import org.itstep.liannoi.maps.application.common.maps.LocationProvider
import org.itstep.liannoi.maps.application.common.maps.MapClient
import org.itstep.liannoi.maps.application.common.maps.Roulette

class DefaultMapClient constructor(
    private val fragment: SupportMapFragment,
    private val locationProvider: LocationProvider,
    private val roulette: Roulette
) : MapClient {

    private lateinit var map: GoogleMap
    private val gson: Gson = Gson()
    private val markers: DefaultFeatureCollection = DefaultFeatureCollection()

    ///////////////////////////////////////////////////////////////////////////
    // Permissions
    ///////////////////////////////////////////////////////////////////////////

    override fun request(notification: LocationProvider.FineLocationNotification) {
        locationProvider.request(notification)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Map
    ///////////////////////////////////////////////////////////////////////////

    override fun prepare(notification: MapClient.MapClickNotification) {
        fragment.getMapAsync {
            initialize(it)
            setup()
            subscribe(notification)
        }
    }

    override fun geoJsonMarkers(): String = gson.toJson(markers)

    ///////////////////////////////////////////////////////////////////////////
    // Shapes
    ///////////////////////////////////////////////////////////////////////////

    override fun marker(latLng: LatLng) {
        map.addMarker(MarkerOptions().position(latLng))
        markers.add(latLng)
    }

    override fun polyline(latest: List<LatLng>) {
        val options: PolylineOptions = PolylineOptions().add(latest[0], latest[1])

        val polyline: Polyline = map.addPolyline(options)
        polyline.color = R.color.colorAccent
        polyline.startCap = RoundCap()
        polyline.endCap = RoundCap()
        polyline.width = 20.0F
        polyline.isGeodesic = true
        polyline.jointType = JointType.BEVEL
    }

    ///////////////////////////////////////////////////////////////////////////
    // Roulette
    ///////////////////////////////////////////////////////////////////////////

    override fun commit(latLng: LatLng) {
        roulette.commit(latLng)
    }

    override fun markedCommit(latLng: LatLng) {
        polyline(roulette.commit(latLng))
    }

    override fun measure(): Double = roulette.measure()

    ///////////////////////////////////////////////////////////////////////////
    // Dispose
    ///////////////////////////////////////////////////////////////////////////

    override fun stop() {
        locationProvider.stop()
    }

    override fun destroy() {
        locationProvider.destroy()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helpers - Map
    ///////////////////////////////////////////////////////////////////////////

    private fun initialize(map: GoogleMap) {
        this.map = map
    }

    private fun subscribe(notification: MapClient.MapClickNotification) {
        map.setOnMapClickListener { notification.onClick(it) }
    }

    private fun setup() {
        try {
            map.isMyLocationEnabled = true
        } catch (e: SecurityException) {
            return
        }
    }
}
