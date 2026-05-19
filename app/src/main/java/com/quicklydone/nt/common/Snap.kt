package com.quicklydone.nt.common

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
