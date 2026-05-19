package com.quicklydone.nt.common

import kotlin.math.cos
import kotlin.math.sin

fun rotateX(
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

fun rotateY(
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