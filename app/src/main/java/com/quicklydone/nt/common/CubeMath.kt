package com.quicklydone.nt.common

import androidx.compose.ui.geometry.Offset
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

// =========================================================
// VECTOR
// =========================================================

data class Vec3(
    var x: Float,
    var y: Float,
    var z: Float
)

fun Vec3.dot(other: Vec3): Float {
    return this.x * other.x +
            this.y * other.y +
            this.z * other.z
}


// =========================================================
// ROTATION
// =========================================================

fun rotX(
    v: Vec3,
    angle: Float
): Vec3 {

    val c = cos(angle)
    val s = sin(angle)

    return Vec3(
        x = v.x,
        y = v.y * c - v.z * s,
        z = v.y * s + v.z * c
    )
}

fun rotY(
    v: Vec3,
    angle: Float
): Vec3 {

    val c = cos(angle)
    val s = sin(angle)

    return Vec3(
        x = v.x * c + v.z * s,
        y = v.y,
        z = -v.x * s + v.z * c
    )
}



// =========================================================
// PROJECTION
// =========================================================

fun project(
    v: Vec3,
    center: Offset,
    scale: Float
): Offset {

    return Offset(
        x = center.x + v.x * scale,
        y = center.y - v.y * scale
    )
}

// =========================================================
// POLYGON
// =========================================================

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

// =========================================================
// AXIS
// =========================================================

fun snapAxis(
    v: Vec3
): Vec3 {

    val ax = abs(v.x)
    val ay = abs(v.y)
    val az = abs(v.z)

    return when {

        ax > ay && ax > az -> {

            Vec3(
                x = if (v.x > 0f) 1f else -1f,
                y = 0f,
                z = 0f
            )
        }

        ay > ax && ay > az -> {

            Vec3(
                x = 0f,
                y = if (v.y > 0f) 1f else -1f,
                z = 0f
            )
        }

        else -> {

            Vec3(
                x = 0f,
                y = 0f,
                z = if (v.z > 0f) 1f else -1f
            )
        }
    }
}


fun rotateX(
    v: Vec3,
    a: Float
): Vec3 {

    val c = cos(a)
    val s = sin(a)

    return Vec3(
        v.x,
        v.y * c - v.z * s,
        v.y * s + v.z * c
    )
}

fun rotateY(
    v: Vec3,
    a: Float
): Vec3 {

    val c = cos(a)
    val s = sin(a)

    return Vec3(
        v.x * c + v.z * s,
        v.y,
        -v.x * s + v.z * c
    )
}

// ----------------------------------------------------
// 📐 3D → 2D PROJECTION
// ----------------------------------------------------

// ---------------------------------------------------
// PROJECTION
// ---------------------------------------------------

fun project(
    v: Vec3,
    cx: Float,
    cy: Float,
    scale: Float,
    cameraDistance: Float
): Offset {

    val p =
        1f / (cameraDistance - v.z)
            .coerceAtLeast(0.1f)

    return Offset(
        cx + v.x * scale * p,
        cy - v.y * scale * p
    )
}
fun rotateAroundAxis(
    v: Vec3,
    axis: Vec3,
    angle: Float
): Vec3 {

    val c = cos(angle)
    val s = sin(angle)

    val dot =
        v.x * axis.x +
                v.y * axis.y +
                v.z * axis.z

    return Vec3(
        v.x * c +
                (axis.y * v.z - axis.z * v.y) * s +
                axis.x * dot * (1 - c),

        v.y * c +
                (axis.z * v.x - axis.x * v.z) * s +
                axis.y * dot * (1 - c),

        v.z * c +
                (axis.x * v.y - axis.y * v.x) * s +
                axis.z * dot * (1 - c)
    )
}

fun snap(v: Vec3): Vec3 {

    fun s(x: Float) =
        when {
            x > 0.5f -> 1f
            x < -0.5f -> -1f
            else -> 0f
        }

    return Vec3(
        s(v.x),
        s(v.y),
        s(v.z)
    )
}

