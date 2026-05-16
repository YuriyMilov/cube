package com.quicklydone.nt.model

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

