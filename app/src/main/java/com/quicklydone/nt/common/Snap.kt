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
// 3x3
// =====================================================

fun snap333(
    v: Vec3
): Vec3 {

    fun s(x: Float): Float {

        return SNAP_333.minBy {
            kotlin.math.abs(it - x)
        }
    }

    return Vec3(
        s(v.x),
        s(v.y),
        s(v.z)
    )
}

// =====================================================
// 4x4
// =====================================================

fun snap444(
    v: Vec3
): Vec3 {

    fun s(x: Float): Float {

        return SNAP_444.minBy {
            kotlin.math.abs(it - x)
        }
    }

    return Vec3(
        s(v.x),
        s(v.y),
        s(v.z)
    )
}

// =====================================================
// AXIS
// =====================================================

fun snapAxis(
    v: Vec3
): Vec3 {

    fun s(x: Float): Float {

        return SNAP_AXIS.minBy {
            kotlin.math.abs(it - x)
        }
    }

    return Vec3(
        s(v.x),
        s(v.y),
        s(v.z)
    )
}
