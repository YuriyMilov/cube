package com.quicklydone.nt.input

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.quicklydone.nt.common.Vec3
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

object InputCube {

    private const val CUBE_SIZE = 2f
    private const val CAMERA_DISTANCE = 12f
    private const val SCALE = 1000f

    // 2x2 сейчас
    // потом 3x3
    private const val GRID = 2

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
    // NORMALS
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

        val cp = cos(pitch)
        val sp = sin(pitch)

        val y1 = p.y * cp - p.z * sp
        val z1 = p.y * sp + p.z * cp

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
    // BUILD CELLS
    // =========================================================

    private fun buildCells(
        yaw: Float,
        pitch: Float,
        w: Float,
        h: Float
    ): List<InputCell> {

        val rotated =
            cubePoints.map {
                rotate(it, yaw, pitch)
            }

        val result = mutableListOf<InputCell>()

        faces.forEach { (face, indices) ->

            val rotatedNormal =
                rotateNormal(
                    faceNormals[face]!!,
                    yaw,
                    pitch
                )

            // backface culling
            // if (rotatedNormal.z <= 0f) {
            //    return@forEach
            //}

            val p0 = project(rotated[indices[0]], w / 2f, h / 2f)
            val p1 = project(rotated[indices[1]], w / 2f, h / 2f)
            val p2 = project(rotated[indices[2]], w / 2f, h / 2f)
            val p3 = project(rotated[indices[3]], w / 2f, h / 2f)

            for (row in 0 until GRID) {

                for (col in 0 until GRID) {

                    val u0 = col.toFloat() / GRID
                    val v0 = row.toFloat() / GRID

                    val u1 = (col + 1f) / GRID
                    val v1 = (row + 1f) / GRID

                    val q0 = bilerp(p0, p1, p2, p3, u0, v0)
                    val q1 = bilerp(p0, p1, p2, p3, u1, v0)
                    val q2 = bilerp(p0, p1, p2, p3, u1, v1)
                    val q3 = bilerp(p0, p1, p2, p3, u0, v1)

                    val poly = listOf(
                        q0,
                        q1,
                        q2,
                        q3
                    )

                    val depth =
                        indices
                            .map { rotated[it].z }
                            .average()
                            .toFloat()

                    result += InputCell(
                        face = face,
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
    // BILERP
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

        val top = lerp(p0, p1, u)
        val bottom = lerp(p3, p2, u)

        return lerp(top, bottom, v)
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
            )
                .sortedBy { it.depth }

        with(drawScope) {

            cells.forEach { cell ->

                val path = Path().apply {

                    moveTo(
                        cell.poly[0].x,
                        cell.poly[0].y
                    )

                    for (i in 1 until 4) {

                        lineTo(
                            cell.poly[i].x,
                            cell.poly[i].y
                        )
                    }

                    close()
                }

                drawPath(
                    path = path,
                    color = faceColors[cell.face]
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
    // PICK CELL
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

            if (dx > 0f) {
                SwipeDirection.RIGHT
            } else {
                SwipeDirection.LEFT
            }

        } else {

            if (dy > 0f) {
                SwipeDirection.DOWN
            } else {
                SwipeDirection.UP
            }
        }
    }

    // =========================================================
    // POLYGON
    // =========================================================

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


    fun detectFace(
        touch: Offset,
        yaw: Float,
        pitch: Float,
        w: Float,
        h: Float
    ): Face? {

       //  Log.d("qq", "yaw  = ${yaw}     pitch  = ${pitch}" )

        val cell = pickCell(
            touch = touch,
            yaw = yaw,
            pitch = pitch,
            w = w,
            h = h
        )

        return cell?.face
    }

    data class FaceAxes(
        val right: Vec3,
        val up: Vec3
    )
    private val faceAxes = mapOf(

        Face.FRONT to FaceAxes(
            right = Vec3(1f, 0f, 0f),
            up = Vec3(0f, -1f, 0f)
        ),

        Face.BACK to FaceAxes(
            right = Vec3(-1f, 0f, 0f),
            up = Vec3(0f, -1f, 0f)
        ),

        Face.RIGHT to FaceAxes(
            right = Vec3(0f, 0f, -1f),
            up = Vec3(0f, -1f, 0f)
        ),

        Face.LEFT to FaceAxes(
            right = Vec3(0f, 0f, 1f),
            up = Vec3(0f, -1f, 0f)
        ),

        Face.TOP to FaceAxes(
            right = Vec3(1f, 0f, 0f),
            up = Vec3(0f, 0f, 1f)
        ),

        Face.BOTTOM to FaceAxes(
            right = Vec3(1f, 0f, 0f),
            up = Vec3(0f, 0f, -1f)
        )
    )
    fun detectFaceSwipe(
        face: Face,
        dx: Float,
        dy: Float,
        yaw: Float,
        pitch: Float
    ): SwipeDirection {

        val axes = faceAxes[face]!!

        // вращаем локальные оси
        val right3 = rotateNormal(axes.right, yaw, pitch)
        val up3 = rotateNormal(axes.up, yaw, pitch)

        // проекция на экран
        val right2 = Vec2(right3.x, -right3.y)
        val up2 = Vec2(up3.x, -up3.y)

        // свайп
        val swipe = Vec2(dx, dy)

        // dot product
        val dotRight =
            swipe.x * right2.x +
                    swipe.y * right2.y

        val dotUp =
            swipe.x * up2.x +
                    swipe.y * up2.y

        return if (abs(dotRight) > abs(dotUp)) {

            if (dotRight > 0f) {
                SwipeDirection.RIGHT
            } else {
                SwipeDirection.LEFT
            }

        } else {

            if (dotUp > 0f) {
                SwipeDirection.DOWN
            } else {
                SwipeDirection.UP
            }
        }
    }

    data class Vec2(
        val x: Float,
        val y: Float
    )

}