package com.quicklydone.nt.render

import androidx.compose.ui.geometry.Offset
import com.quicklydone.nt.model.Vec3
import kotlin.math.cos
import kotlin.math.sin

// ----------------------------------------------------
// 🎥 CAMERA ROTATIONS
// ----------------------------------------------------

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
