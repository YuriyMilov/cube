package com.quicklydone.nt.common

import androidx.compose.ui.geometry.Offset

fun pointInPolygon(
    point: Offset,
    polygon: List<Offset>
): Boolean {

    var inside = false
    var j = polygon.lastIndex

    for (i in polygon.indices) {

        val xi = polygon[i].x
        val yi = polygon[i].y

        val xj = polygon[j].x
        val yj = polygon[j].y

        val intersects =

            ((yi > point.y) != (yj > point.y)) &&

                    (
                            point.x <
                                    (xj - xi) *
                                    (point.y - yi) /
                                    (yj - yi + 0.0001f) +
                                    xi
                            )

        if (intersects) {
            inside = !inside
        }

        j = i
    }

    return inside
}