package com.quicklydone.nt.math

import androidx.compose.ui.geometry.Offset

fun pointInPolygon2(
    p: Offset,
    poly: List<Offset>
): Boolean {

    var inside = false
    var j = poly.lastIndex

    for (i in poly.indices) {

        val pi = poly[i]
        val pj = poly[j]

        if (
            (pi.y > p.y) != (pj.y > p.y) &&
            p.x < (pj.x - pi.x) *
            (p.y - pi.y) /
            (pj.y - pi.y) + pi.x
        ) {
            inside = !inside
        }

        j = i
    }

    return inside
}

fun pointInPolygon(p: Offset, poly: List<Offset>): Boolean {
    var inside = false
    var j = poly.lastIndex

    for (i in poly.indices) {
        val xi = poly[i].x
        val yi = poly[i].y
        val xj = poly[j].x
        val yj = poly[j].y

        val intersect =
            ((yi > p.y) != (yj > p.y)) &&
                    (p.x < (xj - xi) * (p.y - yi) / (yj - yi + 0.00001f) + xi)

        if (intersect) inside = !inside
        j = i
    }

    return inside
}