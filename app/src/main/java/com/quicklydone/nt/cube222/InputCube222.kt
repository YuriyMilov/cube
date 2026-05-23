package com.quicklydone.nt.cube222

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.quicklydone.nt.cube.Params
import com.quicklydone.nt.common.Vec3
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

object InputCube222 {

    // =========================================================
    // CONFIG
    // =========================================================

    private const val GRID_SIZE = 2

    private const val CUBE_SIZE = 2f
    private const val CAMERA_DISTANCE = Params.CAMERA_DISTANCE_222
    private const val SCALE = Params.SCALE

    // =========================================================
    // MODELS
    // =========================================================

    enum class Face {
        FRONT,
        BACK,
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }

    enum class SwipeDirection {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    data class InputCell(
        val face: Face,
        val row: Int,
        val col: Int,
        val poly: List<Offset>,
        val depth: Float
    )

    data class InputAction(
        val face: Face,
        val row: Int,
        val col: Int,
        val swipe: SwipeDirection
    )

    data class FaceAxes(
        val right: Vec3,
        val up: Vec3
    )

    private data class FaceGeometry(
        val face: Face,
        val indices: List<Int>,
        val normal: Vec3,
        val color: Color,
        val axes: FaceAxes
    )

    private data class Vec2(
        val x: Float,
        val y: Float
    )

    // =========================================================
    // GEOMETRY
    // =========================================================

    private val s = CUBE_SIZE

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

    private val faceGeometry = listOf(

        FaceGeometry(
            face = Face.FRONT,
            indices = listOf(4, 5, 6, 7),
            normal = Vec3(0f, 0f, 1f),
            color = Color.Green.copy(alpha = 0.7f),
            axes = FaceAxes(
                right = Vec3(1f, 0f, 0f),
                up = Vec3(0f, -1f, 0f)
            )
        ),

        FaceGeometry(
            face = Face.BACK,
            indices = listOf(1, 0, 3, 2),
            normal = Vec3(0f, 0f, -1f),
            color = Color.Blue.copy(alpha = 0.7f),
            axes = FaceAxes(
                right = Vec3(-1f, 0f, 0f),
                up = Vec3(0f, -1f, 0f)
            )
        ),

        FaceGeometry(
            face = Face.RIGHT,
            indices = listOf(5, 1, 2, 6),
            normal = Vec3(1f, 0f, 0f),
            color = Color.Red.copy(alpha = 0.7f),
            axes = FaceAxes(
                right = Vec3(0f, 0f, -1f),
                up = Vec3(0f, -1f, 0f)
            )
        ),

        FaceGeometry(
            face = Face.LEFT,
            indices = listOf(0, 4, 7, 3),
            normal = Vec3(-1f, 0f, 0f),
            color = Color(0xFFFF8800).copy(alpha = 0.7f),
            axes = FaceAxes(
                right = Vec3(0f, 0f, 1f),
                up = Vec3(0f, -1f, 0f)
            )
        ),

        FaceGeometry(
            face = Face.TOP,
            indices = listOf(7, 6, 2, 3),
            normal = Vec3(0f, -1f, 0f),
            color = Color.White.copy(alpha = 0.7f),
            axes = FaceAxes(
                right = Vec3(1f, 0f, 0f),
                up = Vec3(0f, 0f, 1f)
            )
        ),

        FaceGeometry(
            face = Face.BOTTOM,
            indices = listOf(0, 1, 5, 4),
            normal = Vec3(0f, 1f, 0f),
            color = Color.Yellow.copy(alpha = 0.7f),
            axes = FaceAxes(
                right = Vec3(1f, 0f, 0f),
                up = Vec3(0f, 0f, -1f)
            )
        )
    )

    // =========================================================
    // ROTATION
    // =========================================================

    private fun rotate(
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
    // BUILD CELLS
    // =========================================================

    private fun buildCells(
        yaw: Float,
        pitch: Float,
        w: Float,
        h: Float
    ): List<InputCell> {

        val rotatedPoints =
            cubePoints.map {
                rotate(it, yaw, pitch)
            }

        val result =
            mutableListOf<InputCell>()

        faceGeometry.forEach { geometry ->

            val indices = geometry.indices

            val p0 =
                project(
                    rotatedPoints[indices[0]],
                    w / 2f,
                    h / 2f
                )

            val p1 =
                project(
                    rotatedPoints[indices[1]],
                    w / 2f,
                    h / 2f
                )

            val p2 =
                project(
                    rotatedPoints[indices[2]],
                    w / 2f,
                    h / 2f
                )

            val p3 =
                project(
                    rotatedPoints[indices[3]],
                    w / 2f,
                    h / 2f
                )

            for (row in 0 until GRID_SIZE) {

                for (col in 0 until GRID_SIZE) {

                    val u0 = col.toFloat() / GRID_SIZE
                    val v0 = row.toFloat() / GRID_SIZE

                    val u1 = (col + 1f) / GRID_SIZE
                    val v1 = (row + 1f) / GRID_SIZE

                    val poly = listOf(
                        bilerp(p0, p1, p2, p3, u0, v0),
                        bilerp(p0, p1, p2, p3, u1, v0),
                        bilerp(p0, p1, p2, p3, u1, v1),
                        bilerp(p0, p1, p2, p3, u0, v1),
                    )

                    val depth =
                        indices
                            .map { rotatedPoints[it].z }
                            .average()
                            .toFloat()

                    result += InputCell(
                        face = geometry.face,
                        row = row,
                        col = col,
                        poly = poly,
                        depth = depth
                    )
                }
            }
        }

        return result
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

        val cells =
            buildCells(
                yaw,
                pitch,
                w,
                h
            ).sortedBy { it.depth }

        with(drawScope) {

            cells.forEach { cell ->

                val path =
                    buildPath(cell.poly)

                val color =
                    faceGeometry
                        .first { it.face == cell.face }
                        .color

                drawPath(
                    path = path,
                    color = color
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

    fun pickCell(
        touch: Offset,
        yaw: Float,
        pitch: Float,
        w: Float,
        h: Float
    ): InputCell? {

        val cells =
            buildCells(
                yaw,
                pitch,
                w,
                h
            )
                .sortedByDescending { it.depth }

        return cells.firstOrNull {

            pointInPolygon(
                touch,
                it.poly
            )
        }
    }

    fun detectFace(
        touch: Offset,
        yaw: Float,
        pitch: Float,
        w: Float,
        h: Float
    ): Face? {

        return pickCell(
            touch = touch,
            yaw = yaw,
            pitch = pitch,
            w = w,
            h = h
        )?.face
    }

    // =========================================================
    // SWIPE
    // =========================================================

    fun detectSwipe(
        start: Offset,
        end: Offset
    ): SwipeDirection {

        val dx = end.x - start.x
        val dy = end.y - start.y

        return if (abs(dx) > abs(dy)) {

            if (dx > 0f)
                SwipeDirection.RIGHT
            else
                SwipeDirection.LEFT

        } else {

            if (dy > 0f)
                SwipeDirection.DOWN
            else
                SwipeDirection.UP
        }
    }

    fun detectFaceSwipe(
        face: Face,
        dx: Float,
        dy: Float,
        yaw: Float,
        pitch: Float
    ): SwipeDirection {

        val axes =
            faceGeometry
                .first { it.face == face }
                .axes

        val right3 =
            rotate(
                axes.right,
                yaw,
                pitch
            )

        val up3 =
            rotate(
                axes.up,
                yaw,
                pitch
            )

        val right2 =
            Vec2(
                right3.x,
                -right3.y
            )

        val up2 =
            Vec2(
                up3.x,
                -up3.y
            )

        val swipe =
            Vec2(dx, dy)

        val dotRight =
            swipe.x * right2.x +
                    swipe.y * right2.y

        val dotUp =
            swipe.x * up2.x +
                    swipe.y * up2.y

        return if (abs(dotRight) > abs(dotUp)) {

            if (dotRight > 0f)
                SwipeDirection.RIGHT
            else
                SwipeDirection.LEFT

        } else {

            if (dotUp > 0f)
                SwipeDirection.DOWN
            else
                SwipeDirection.UP
        }
    }

    // =========================================================
    // POLYGON
    // =========================================================

    private fun buildPath(
        poly: List<Offset>
    ): Path {

        return Path().apply {

            moveTo(
                poly[0].x,
                poly[0].y
            )

            for (i in 1 until poly.size) {

                lineTo(
                    poly[i].x,
                    poly[i].y
                )
            }

            close()
        }
    }

    private fun pointInPolygon(
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
    // INTERPOLATION
    // =========================================================

    private fun lerp(
        a: Offset,
        b: Offset,
        t: Float
    ): Offset {

        return Offset(
            a.x + (b.x - a.x) * t,
            a.y + (b.y - a.y) * t
        )
    }

    private fun bilerp(
        p0: Offset,
        p1: Offset,
        p2: Offset,
        p3: Offset,
        u: Float,
        v: Float
    ): Offset {

        val top =
            lerp(p0, p1, u)

        val bottom =
            lerp(p3, p2, u)

        return lerp(
            top,
            bottom,
            v
        )
    }
}