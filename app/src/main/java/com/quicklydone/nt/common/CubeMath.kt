package com.quicklydone.nt.common


import androidx.compose.ui.geometry.Offset
import com.quicklydone.nt.common.Vec3
import kotlin.math.*

//data class Vec3(val x: Float, val y: Float, val z: Float)

fun rotY(v: Vec3, a: Float): Vec3 {
    val c = cos(a)
    val s = sin(a)
    return Vec3(
        v.x * c + v.z * s,
        v.y,
        -v.x * s + v.z * c
    )
}

fun rotX(v: Vec3, a: Float): Vec3 {
    val c = cos(a)
    val s = sin(a)
    return Vec3(
        v.x,
        v.y * c - v.z * s,
        v.y * s + v.z * c
    )
}

fun dot(a: Vec3, b: Vec3) =
    a.x * b.x + a.y * b.y + a.z * b.z

fun project(v: Vec3, center: Offset, scale: Float) =
    Offset(center.x + v.x * scale, center.y - v.y * scale)

fun isPointInPolygon(p: Offset, poly: List<Offset>): Boolean {

    var inside = false
    var j = poly.lastIndex

    for (i in poly.indices) {

        val xi = poly[i].x
        val yi = poly[i].y
        val xj = poly[j].x
        val yj = poly[j].y

        val hit =
            ((yi > p.y) != (yj > p.y)) &&
                    (p.x < (xj - xi) * (p.y - yi) / (yj - yi + 0.0001f) + xi)

        if (hit) inside = !inside
        j = i
    }

    return inside
}


fun pointInPoly(p: Offset, poly: List<Offset>): Boolean {
    var c = false
    var j = poly.lastIndex

    for (i in poly.indices) {
        if ((poly[i].y > p.y) != (poly[j].y > p.y) &&
            p.x < (poly[j].x - poly[i].x) * (p.y - poly[i].y) /
            (poly[j].y - poly[i].y) + poly[i].x
        ) {
            c = !c
        }
        j = i
    }
    return c
}

fun snapAxis(v: Vec3): Vec3 {

    val ax = kotlin.math.abs(v.x)
    val ay = kotlin.math.abs(v.y)
    val az = kotlin.math.abs(v.z)

    return when {

        ax > ay && ax > az ->

            Vec3(
                if (v.x > 0f) 1f else -1f,
                0f,
                0f
            )

        ay > ax && ay > az ->

            Vec3(
                0f,
                if (v.y > 0f) 1f else -1f,
                0f
            )

        else ->

            Vec3(
                0f,
                0f,
                if (v.z > 0f) 1f else -1f
            )
    }
}