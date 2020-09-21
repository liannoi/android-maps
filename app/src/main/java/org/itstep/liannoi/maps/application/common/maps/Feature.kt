package org.itstep.liannoi.maps.application.common.maps

class Feature constructor(
    private val geometry: Geometry,
    private val properties: Array<Any> = emptyArray(),
    private val type: String = "Feature"
)
