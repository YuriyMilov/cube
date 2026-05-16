package com.quicklydone.nt.math

import com.quicklydone.nt.model.Vec3
import kotlin.math.sqrt

fun normalize(v: Vec3): Vec3 {
    val len = sqrt(v.x * v.x + v.y * v.y + v.z * v.z)
    if (len < 1e-6f) return Vec3(0f, 0f, 0f)

    return Vec3(
        v.x / len,
        v.y / len,
        v.z / len
    )
}