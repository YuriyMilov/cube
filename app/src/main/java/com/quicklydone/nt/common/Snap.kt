package com.quicklydone.nt.common

import kotlin.math.abs

private val SNAP_222 = listOf(
    -0.5f,
    0.5f
)

private val SNAP_333 = listOf(
    -1f,
    0f,
    1f
)

private val SNAP_444 = listOf(
    -1.5f,
    -0.5f,
    0.5f,
    1.5f
)

private val SNAP_AXIS = listOf(
    -1f,
    0f,
    1f
)

// =====================================================
// BASE
// =====================================================

private fun snapTo(
    value: Float,
    variants: List<Float>
): Float {

    return variants.minBy {
        abs(it - value)
    }
}

private fun snapVec3(
    v: Vec3,
    variants: List<Float>
): Vec3 {

    return Vec3(
        snapTo(v.x, variants),
        snapTo(v.y, variants),
        snapTo(v.z, variants)
    )
}

// =====================================================
// 2x2
// =====================================================

fun snap222(
    v: Vec3
): Vec3 {

    return snapVec3(
        v,
        SNAP_222
    )
}

// =====================================================
// 3x3
// =====================================================

fun snap333(
    v: Vec3
): Vec3 {

    return snapVec3(
        v,
        SNAP_333
    )
}

// =====================================================
// 4x4
// =====================================================

fun snap444(
    v: Vec3
): Vec3 {

    return snapVec3(
        v,
        SNAP_444
    )
}

// =====================================================
// AXIS
// =====================================================

fun snapAxis(
    v: Vec3
): Vec3 {

    return snapVec3(
        v,
        SNAP_AXIS
    )
}