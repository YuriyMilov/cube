package com.quicklydone.nt.math

import androidx.compose.ui.geometry.Offset
import com.quicklydone.nt.model.Vec3
import kotlin.math.cos
import kotlin.math.sin

// ----------------------------------------------------
// 🔁 ROTATION (3D)
// ----------------------------------------------------

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

fun rotateVec(
    v: Vec3,
    axis: Vec3,
    angle: Float
): Vec3 = rotateAroundAxis(v, axis, angle)


// ----------------------------------------------------
// 🧲 SNAP (фикс сетки кубика)
// ----------------------------------------------------

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


// ----------------------------------------------------
// 🎮 DRAG ROTATION (камера)
// ----------------------------------------------------

fun rotateDrag(
    dx: Float,
    dy: Float,
    rotY: Float
): Pair<Float, Float> {

    val c = cos(-rotY)
    val s = sin(-rotY)

    val lx = dx * c - dy * s
    val ly = dx * s + dy * c

    return Pair(lx, ly)
}


// ----------------------------------------------------
// 📐 PROJECTION (3D → 2D)
// ----------------------------------------------------

fun project(
    v: Vec3,
    cx: Float,
    cy: Float,
    scale: Float,
    cameraDistance: Float
): Offset {

    val p =
        1f / (cameraDistance - v.z).coerceAtLeast(0.1f)

    return Offset(
        cx + v.x * scale * p,
        cy - v.y * scale * p
    )
}


// ----------------------------------------------------
// 📏 LAYER CHECK (для вращения слоя)
// ----------------------------------------------------

fun onLayer(
    pos: Vec3,
    axis: Vec3,
    layer: Float
): Boolean {

    return when {
        axis.x != 0f -> pos.x == layer
        axis.y != 0f -> pos.y == layer
        else -> pos.z == layer
    }
}

fun rotateX(
    v: Vec3, a: Float
): Vec3 {

    val c = cos(a)
    val s = sin(a)

    return Vec3(
        v.x, v.y * c - v.z * s, v.y * s + v.z * c
    )
}

fun rotateY(
    v: Vec3, a: Float
): Vec3 {

    val c = cos(a)
    val s = sin(a)

    return Vec3(
        v.x * c + v.z * s, v.y, -v.x * s + v.z * c
    )
}

