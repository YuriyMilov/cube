package com.quicklydone.nt.input

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.quicklydone.nt.common.Vec3
import kotlin.math.cos
import kotlin.math.sin

object InputCube {

    private const val CUBE_SIZE = 2f
    private const val CAMERA_DISTANCE = 12f
    private const val SCALE = 1000f

    private val s = CUBE_SIZE

    // =========================================================
    // FACE
    // =========================================================

    enum class Face {
        FRONT,
        BACK,
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }

    data class PickResult(
        val face: Face,
        val u: Float,
        val v: Float
    )

    // =========================================================
    // GEOMETRY
    // =========================================================

    private val cubePoints = listOf(

        Vec3(-s, -s, -s),
        Vec3(s, -s, -s),
        Vec3(s, s, -s),
        Vec3(-s, s, -s),

        Vec3(-s, -s, s),
        Vec3(s, -s, s),
        Vec3(s, s, s),
        Vec3(-s, s, s)
    )

    private val faces = listOf(

        Face.FRONT to listOf(4, 5, 6, 7),
        Face.BACK to listOf(1, 0, 3, 2),

        Face.RIGHT to listOf(5, 1, 2, 6),
        Face.LEFT to listOf(0, 4, 7, 3),

        Face.TOP to listOf(7, 6, 2, 3),
        Face.BOTTOM to listOf(0, 1, 5, 4)
    )

    // =========================================================
    // FIXED NORMALS
    // =========================================================

    private val faceNormals = mapOf(

        Face.FRONT to Vec3(0f, 0f, 1f),
        Face.BACK to Vec3(0f, 0f, -1f),

        Face.LEFT to Vec3(-1f, 0f, 0f),
        Face.RIGHT to Vec3(1f, 0f, 0f),

        Face.TOP to Vec3(0f, -1f, 0f),
        Face.BOTTOM to Vec3(0f, 1f, 0f)
    )

    // =========================================================
    // COLORS
    // =========================================================

    private val faceColors = mapOf(

        Face.FRONT to Color.Green.copy(0.7f),
        Face.BACK to Color.Blue.copy(0.7f),

        Face.RIGHT to Color.Red.copy(0.7f),
        Face.LEFT to Color(0xFFFF8800).copy(0.7f),

        Face.TOP to Color.White.copy(0.7f),
        Face.BOTTOM to Color.Yellow.copy(0.7f)
    )

    // =========================================================
    // ROTATION
    // =========================================================

    private fun rotate(
        p: Vec3,
        yaw: Float,
        pitch: Float
    ): Vec3 {

        // pitch (X)

        val cp = cos(pitch)
        val sp = sin(pitch)

        val y1 = p.y * cp - p.z * sp
        val z1 = p.y * sp + p.z * cp

        // yaw (Y)

        val cy = cos(yaw)
        val sy = sin(yaw)

        val x2 = p.x * cy + z1 * sy
        val z2 = -p.x * sy + z1 * cy

        return Vec3(
            x2,
            y1,
            z2
        )
    }

    private fun rotateNormal(
        v: Vec3,
        yaw: Float,
        pitch: Float
    ): Vec3 {

        val cp = cos(pitch)
        val sp = sin(pitch)

        val y1 = v.y * cp - v.z * sp
        val z1 = v.y * sp + v.z * cp

        val cy = cos(yaw)
        val sy = sin(yaw)

        val x2 = v.x * cy + z1 * sy
        val z2 = -v.x * sy + z1 * cy

        return Vec3(
            x2,
            y1,
            z2
        )
    }

    // =========================================================
    // PROJECTION
    // =========================================================

    private fun project(
        v: Vec3,
        cx: Float,
        cy: Float
    ): Offset {

        val perspective =
            1f / (CAMERA_DISTANCE - v.z)
                .coerceAtLeast(0.1f)

        return Offset(
            cx + v.x * SCALE * perspective,
            cy - v.y * SCALE * perspective
        )
    }

    // =========================================================
    // FACE DATA
    // =========================================================

    private data class FaceData(
        val face: Face,
        val poly: List<Offset>,
        val depth: Float,
        //val visible: Boolean
    )

    // =========================================================
    // BUILD
    // =========================================================

    private fun buildFaces(
        yaw: Float,
        pitch: Float,
        w: Float,
        h: Float
    ): List<FaceData> {

        val rotated =
            cubePoints.map {
                rotate(
                    it,
                    yaw,
                    pitch
                )
            }

        return faces.map { (face, indices) ->

            val verts =
                indices.map {
                    rotated[it]
                }

            val poly =
                indices.map { i ->

                    project(
                        rotated[i],
                        w / 2f,
                        h / 2f
                    )
                }

            // =================================================
            // ROTATED NORMAL
            // =================================================

            val rotatedNormal =
                rotateNormal(
                    faceNormals[face]!!,
                    yaw,
                    pitch
                )

            val visible =
                rotatedNormal.z > 0f

            // =================================================
            // DEPTH
            // =================================================

            val depth =
                verts
                    .map { it.z }
                    .average()
                    .toFloat()

            FaceData(
                face = face,
                poly = poly,
                depth = depth,
               // visible = visible
            )
        }
    }

    // =========================================================
    // DRAW
    // =========================================================

    fun drawInputCube(
        drawScope: DrawScope,
        yaw: Float,
        pitch: Float,
        w: Float,
        h: Float
    ) {

        val visibleFaces =
            buildFaces(
                yaw,
                pitch,
                w,
                h
            )
               // .filter { it.visible }

                // far -> near
                .sortedBy { it.depth }

        with(drawScope) {

            visibleFaces.forEach { f ->

                val path = Path().apply {

                    moveTo(
                        f.poly[0].x,
                        f.poly[0].y
                    )

                    for (i in 1 until f.poly.size) {

                        lineTo(
                            f.poly[i].x,
                            f.poly[i].y
                        )
                    }

                    close()
                }

                drawPath(
                    path = path,
                    color = faceColors[f.face]
                        ?: Color.Magenta
                )

                drawPath(
                    path = path,
                    color = Color.Black,
                    style = Stroke(width = 2f)
                )
            }
        }
    }

    // =========================================================
    // PICK
    // =========================================================

    fun pickFace(
        touch: Offset,
        yaw: Float,
        pitch: Float,
        w: Float,
        h: Float
    ): PickResult? {

        val faceData =
            buildFaces(
                yaw,
                pitch,
                w,
                h
            )
              //  .filter { it.visible }

                // near first
                .sortedByDescending { it.depth }

        for (data in faceData) {

            if (
                pointInPolygon(
                    touch,
                    data.poly
                )
            ) {

                val uv =
                    computeUV(
                        touch,
                        data.poly
                    )

                return if (uv != null) {

                    PickResult(
                        data.face,
                        uv.first,
                        uv.second
                    )

                } else {

                    PickResult(
                        data.face,
                        0.5f,
                        0.5f
                    )
                }
            }
        }

        return null
    }

    fun detectFace(
        touch: Offset,
        yaw: Float,
        pitch: Float,
        w: Float,
        h: Float
    ): Face? {

        val faceData =
            buildFaces(
                yaw,
                pitch,
                w,
                h
            )
            //    .filter { it.visible }

                // near first
                .sortedByDescending { it.depth }

        for (data in faceData) {

            if (
                pointInPolygon(
                    touch,
                    data.poly
                )
            ) {
                return data.face
            }
        }

        return null
    }

    // =========================================================
    // POLYGON
    // =========================================================

    fun pointInPolygon(
        p: Offset,
        poly: List<Offset>
    ): Boolean {

        var inside = false
        var j = poly.lastIndex

        for (i in poly.indices) {

            val xi = poly[i].x
            val yi = poly[i].y

            val xj = poly[j].x
            val yj = poly[j].y

            val intersect =

                ((yi > p.y) != (yj > p.y)) &&

                        (
                                p.x <
                                        (xj - xi) *
                                        (p.y - yi) /
                                        (yj - yi + 0.00001f) +
                                        xi
                                )

            if (intersect) {
                inside = !inside
            }

            j = i
        }

        return inside
    }

    // =========================================================
    // UV
    // =========================================================

    fun computeUV(
        p: Offset,
        quad: List<Offset>
    ): Pair<Float, Float>? {

        val p0 = quad[0]
        val p1 = quad[1]
        val p2 = quad[2]
        val p3 = quad[3]

        // triangle 1

        barycentric(
            p,
            p0,
            p1,
            p2
        )?.let { (_, v, w) ->

            val u = v + w
            val vv = w

            return u to vv
        }

        // triangle 2

        barycentric(
            p,
            p0,
            p2,
            p3
        )?.let { (_, v, w) ->

            val u = v
            val vv = v + w

            return u to vv
        }

        return null
    }

    // =========================================================
    // BARYCENTRIC
    // =========================================================

    infix fun Offset.dot(
        o: Offset
    ): Float {

        return x * o.x + y * o.y
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

        val denom =
            d00 * d11 - d01 * d01

        if (denom == 0f) {
            return null
        }

        val v =
            (d11 * d20 - d01 * d21) / denom

        val w =
            (d00 * d21 - d01 * d20) / denom

        val u =
            1f - v - w

        return if (
            u >= 0f &&
            v >= 0f &&
            w >= 0f
        ) {

            Triple(u, v, w)

        } else {
            null
        }
    }
}