package com.quicklydone.nt.render2x2

import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin

data class Vec3(val x: Float, val y: Float, val z: Float)

data class PickResult(
    val face: Face,
    val u: Float,
    val v: Float
)

enum class Face {
    FRONT, BACK, LEFT, RIGHT, TOP, BOTTOM
}

object Cube2x2Renderer {

    infix fun Offset.dot(o: Offset): Float {
        return x * o.x + y * o.y
    }

    // 8 вершин куба (без изменений — геометрия та же)
    val cubePoints = listOf(
        Vec3(-1f, -1f, -1f),
        Vec3(1f, -1f, -1f),
        Vec3(1f, 1f, -1f),
        Vec3(-1f, 1f, -1f),

        Vec3(-1f, -1f, 1f),
        Vec3(1f, -1f, 1f),
        Vec3(1f, 1f, 1f),
        Vec3(-1f, 1f, 1f),
    )

    // те же грани, что и у 3x3 — можно переиспользовать
    val faces: List<Pair<Face, List<Int>>> = listOf(
        Face.RIGHT to listOf(0, 1, 2, 3),
        Face.LEFT to listOf(4, 5, 6, 7),
        Face.BOTTOM to listOf(0, 1, 5, 4),
        Face.TOP to listOf(2, 3, 7, 6),
        Face.FRONT to listOf(1, 2, 6, 5),
        Face.BACK to listOf(0, 3, 7, 4)
    )

    fun rotate(p: Vec3, yaw: Float, pitch: Float): Vec3 {
        val cy = cos(yaw)
        val sy = sin(yaw)
        val cp = cos(pitch)
        val sp = sin(pitch)

        val x = p.x * cy - p.z * sy
        val z = p.x * sy + p.z * cy

        val y = p.y * cp - z * sp
        val z2 = p.y * sp + z * cp

        return Vec3(x, y, z2)
    }

    fun project(p: Vec3, w: Float, h: Float): Offset {
        val zOffset = 6f
        val scale = 1200f / (-p.z + zOffset)

        return Offset(
            w / 2 + p.x * scale,
            h / 2 - p.y * scale
        )
    }

    fun pickFace(
        touch: Offset,
        yaw: Float,
        pitch: Float,
        w: Float,
        h: Float
    ): PickResult? {

        val rotated = cubePoints.map { rotate(it, yaw, pitch) }

        val faceData = faces.map { (face, indices) ->

            val poly = indices.map { i ->
                project(rotated[i], w, h)
            }

            val depth = indices.map { rotated[it].z }.average()

            Triple(face, poly, depth)
        }.sortedByDescending { it.third }

        for ((face, poly, _) in faceData) {
            if (pointInPolygon(touch, poly)) {

                val uv = computeUV(touch, poly)
                return if (uv != null) {
                    PickResult(face, uv.first, uv.second)
                } else {
                    PickResult(face, 0.5f, 0.5f)
                }
            }
        }

        return null
    }

    fun pointInPolygon(p: Offset, poly: List<Offset>): Boolean {
        var inside = false
        var j = poly.lastIndex

        for (i in poly.indices) {
            val xi = poly[i].x
            val yi = poly[i].y
            val xj = poly[j].x
            val yj = poly[j].y

            val intersect =
                ((yi > p.y) != (yj > p.y)) &&
                        (p.x < (xj - xi) * (p.y - yi) / (yj - yi + 0.00001f) + xi)

            if (intersect) inside = !inside
            j = i
        }

        return inside
    }

    fun computeUV(p: Offset, quad: List<Offset>): Pair<Float, Float>? {

        val p0 = quad[0]
        val p1 = quad[1]
        val p2 = quad[2]
        val p3 = quad[3]

        barycentric(p, p0, p1, p2)?.let { (_, v, w) ->
            return (v + w) to w
        }

        barycentric(p, p0, p2, p3)?.let { (_, v, w) ->
            return v to (v + w)
        }

        return null
    }

    fun barycentric(
        p: Offset,
        a: Offset,
        b: Offset,
        c: Offset
    ): Triple<Float, Float, Float>? {

        val v0 = b - a
        val v1 = c - a
        val v2 = p - a

        val d00 = v0 dot v0
        val d01 = v0 dot v1
        val d11 = v1 dot v1
        val d20 = v2 dot v0
        val d21 = v2 dot v1

        val denom = d00 * d11 - d01 * d01
        if (denom == 0f) return null

        val v = (d11 * d20 - d01 * d21) / denom
        val w = (d00 * d21 - d01 * d20) / denom
        val u = 1f - v - w

        if (u >= 0f && v >= 0f && w >= 0f) {
            return Triple(u, v, w)
        }

        return null
    }
}