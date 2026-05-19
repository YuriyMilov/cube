package com.quicklydone.nt.common

import androidx.compose.ui.geometry.Offset

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