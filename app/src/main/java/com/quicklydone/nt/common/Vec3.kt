package com.quicklydone.nt.common

data class Vec3(
    var x: Float,
    var y: Float,
    var z: Float
)

fun Vec3.dot(other: Vec3): Float {

    return x * other.x +
            y * other.y +
            z * other.z
}