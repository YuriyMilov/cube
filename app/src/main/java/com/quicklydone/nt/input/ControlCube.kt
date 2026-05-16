package com.quicklydone.nt.input

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.cos
import kotlin.math.sin

object ControlCube {

    private const val CUBE_SIZE = 1f
    private const val CAMERA_DISTANCE = 7f
    private const val SCALE = 160f

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

    data class Vec3(
        val x: Float,
        val y: Float,
        val z: Float
    )

    private val colors = arrayOf(
        Color.Red.copy(0.7f),
        Color(0xFFFFA500).copy(0.7f),
        Color.Blue.copy(0.7f),
        Color.Green.copy(0.7f),
        Color.White.copy(0.7f),
        Color.Yellow.copy(0.7f)
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
    // GEOMETRY
    // =========================================================

    private val vertices = arrayOf(
        floatArrayOf(-CUBE_SIZE, -CUBE_SIZE, -CUBE_SIZE),
        floatArrayOf(CUBE_SIZE, -CUBE_SIZE, -CUBE_SIZE),
        floatArrayOf(CUBE_SIZE, CUBE_SIZE, -CUBE_SIZE),
        floatArrayOf(-CUBE_SIZE, CUBE_SIZE, -CUBE_SIZE),

        floatArrayOf(-CUBE_SIZE, -CUBE_SIZE, CUBE_SIZE),
        floatArrayOf(CUBE_SIZE, -CUBE_SIZE, CUBE_SIZE),
        floatArrayOf(CUBE_SIZE, CUBE_SIZE, CUBE_SIZE),
        floatArrayOf(-CUBE_SIZE, CUBE_SIZE, CUBE_SIZE)
    )

    private val faces = arrayOf(

        intArrayOf(4, 5, 6, 7), // front
        intArrayOf(0, 1, 2, 3), // back

        intArrayOf(0, 4, 7, 3), // left
        intArrayOf(1, 5, 6, 2), // right

        intArrayOf(3, 2, 6, 7), // top
        intArrayOf(0, 1, 5, 4)  // bottom
    )

    // =========================================================
    // ROTATE VEC3
    // =========================================================

    private fun rotateX(
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

    private fun rotateY(
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

    // =========================================================
    // ROTATE ARRAY
    // =========================================================

    private fun rotate(
        v: FloatArray,
        angleX: Float,
        angleY: Float
    ): FloatArray {

        val x = v[0]
        val y = v[1]
        val z = v[2]

        val cosX = cos(angleX)
        val sinX = sin(angleX)

        val y1 = y * cosX - z * sinX
        val z1 = y * sinX + z * cosX

        val cosY = cos(angleY)
        val sinY = sin(angleY)

        val x2 = x * cosY + z1 * sinY
        val z2 = -x * sinY + z1 * cosY

        return floatArrayOf(
            x2,
            y1,
            z2
        )
    }

    // =========================================================
    // BUILD
    // =========================================================

    private data class FaceData(
        val index: Int,
        val poly: List<Offset>,
        val depth: Double
    )

    private fun buildFaces(
        angleX: Float,
        angleY: Float,
        width: Float,
        height: Float,
        scale: Float,
        distance: Float
    ): List<FaceData> {

        val rotated =
            vertices.map {
                rotate(
                    it,
                    angleX,
                    angleY
                )
            }

        val centerX = width / 2f
        val centerY = height / 2f

        return faces.indices.map { faceIndex ->

            val poly =
                faces[faceIndex].map { vertexIndex ->

                    val p = rotated[vertexIndex]

                    val perspective =
                        distance / (distance - p[2])

                    Offset(
                        centerX + p[0] * scale * perspective,
                        centerY - p[1] * scale * perspective
                    )
                }

            val depth =
                faces[faceIndex]
                    .map { rotated[it][2] }
                    .average()

            FaceData(
                index = faceIndex,
                poly = poly,
                depth = depth
            )
        }
    }

    // =========================================================
    // DRAW
    // =========================================================

    fun draw(
        drawScope: DrawScope,
        angleX: Float,
        angleY: Float,
        width: Float,
        height: Float,
        scale: Float = SCALE,
        distance: Float = CAMERA_DISTANCE
    ) {

        val sortedFaces =
            buildFaces(
                angleX,
                angleY,
                width,
                height,
                scale,
                distance
            ).sortedBy { it.depth }

        with(drawScope) {

            for (data in sortedFaces) {

                val path = Path()

                data.poly.forEachIndexed { i, p ->

                    if (i == 0) {
                        path.moveTo(p.x, p.y)
                    } else {
                        path.lineTo(p.x, p.y)
                    }
                }

                path.close()

                drawPath(
                    path = path,
                    color = colors[data.index]
                )

                drawPath(
                    path = path,
                    color = Color.Black,
                    style = Stroke(width = 4f)
                )
            }
        }
    }

    // =========================================================
    // FRONT FACE
    // =========================================================

    fun pickFrontFace(
        angleX: Float,
        angleY: Float
    ): Face {

        val result =
            faceNormals
                .mapValues { (_, normal) ->

                    val r1 =
                        rotateY(
                            normal,
                            angleY
                        )

                    val r2 =
                        rotateX(
                            r1,
                            angleX
                        )

                    r2.z
                }
                .maxBy { it.value }
                .key

        Log.d(
            "cube",
            "pickFrontFace -> $result"
        )

        return result
    }

    fun pickFrontFaceIndex(
        angleX: Float,
        angleY: Float
    ): Int {

        return when (
            pickFrontFace(
                angleX,
                angleY
            )
        ) {

            Face.FRONT -> 0
            Face.BACK -> 1

            Face.LEFT -> 2
            Face.RIGHT -> 3

            Face.TOP -> 4
            Face.BOTTOM -> 5
        }
    }
}