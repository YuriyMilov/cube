package com.quicklydone.nt.render

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.quicklydone.nt.animation.onLayer
import com.quicklydone.nt.gesture.Side
import com.quicklydone.nt.math.rotateAroundAxis
import com.quicklydone.nt.model.Cubelet
import com.quicklydone.nt.model.Face
import com.quicklydone.nt.model.Vec3
import kotlin.math.sqrt

object CubeRenderer {

    fun draw(
        cubelets: SnapshotStateList<Cubelet>,
        rotX: Float,
        rotY: Float,
        animAxis: Vec3?,
        animLayer: Float,
        animAngle: Float,
        visibleFaces: SnapshotStateList<VisibleFace>,
        drawScope: DrawScope
    ) {

        with(drawScope) {

            val cx = size.width / 2f
            val cy = size.height / 2f

            val scale = 1000f
            val cameraDistance = 12f
            val cubeSize = 1.85f

            val facesToDraw =
                mutableListOf<Triple<List<Offset>, Float, Color>>()

            visibleFaces.clear()

            cubelets.forEach { cube ->

                val inLayer =
                    animAxis != null &&
                            onLayer(cube.pos, animAxis, animLayer)

                // ---------------------------------------------------
                // POSITION
                // ---------------------------------------------------

                val animatedPos =
                    if (inLayer && animAxis != null)
                        rotateAroundAxis(cube.pos, animAxis, animAngle)
                    else
                        cube.pos

                // ---------------------------------------------------
                // ORIENTATION
                // ---------------------------------------------------

                val axisX =
                    if (inLayer && animAxis != null)
                        rotateAroundAxis(cube.axisX, animAxis, animAngle)
                    else
                        cube.axisX

                val axisY =
                    if (inLayer && animAxis != null)
                        rotateAroundAxis(cube.axisY, animAxis, animAngle)
                    else
                        cube.axisY

                val axisZ =
                    if (inLayer && animAxis != null)
                        rotateAroundAxis(cube.axisZ, animAxis, animAngle)
                    else
                        cube.axisZ

                // ---------------------------------------------------
                // FACES
                // ---------------------------------------------------

                val faces = buildFaces(cubeSize, cube)

                faces.forEach { face ->

                    // local -> world
                    val worldVerts = face.verts.map { v ->

                        val p = Vec3(
                            v.x * axisX.x + v.y * axisY.x + v.z * axisZ.x,
                            v.x * axisX.y + v.y * axisY.y + v.z * axisZ.y,
                            v.x * axisX.z + v.y * axisY.z + v.z * axisZ.z
                        )

                        Vec3(
                            p.x + animatedPos.x,
                            p.y + animatedPos.y,
                            p.z + animatedPos.z
                        )
                    }

                    // ---------------------------------------------------
                    // CAMERA ROTATION
                    // ---------------------------------------------------

                    val rotatedVerts = worldVerts.map {
                        rotateY(
                            rotateX(it, rotX),
                            rotY
                        )
                    }

                    // ---------------------------------------------------
                    // NORMAL
                    // ---------------------------------------------------

                    var normal = Vec3(
                        face.normal.x * axisX.x +
                                face.normal.y * axisY.x +
                                face.normal.z * axisZ.x,

                        face.normal.x * axisX.y +
                                face.normal.y * axisY.y +
                                face.normal.z * axisZ.y,

                        face.normal.x * axisX.z +
                                face.normal.y * axisY.z +
                                face.normal.z * axisZ.z
                    )

                    normal =
                        rotateY(
                            rotateX(normal, rotX),
                            rotY
                        )

                    // backface culling
                    if (normal.z <= 0f)
                        return@forEach

                    // ---------------------------------------------------
                    // SCREEN AXES
                    // ---------------------------------------------------

                    val rotatedU =
                        rotateY(
                            rotateX(axisX, rotX),
                            rotY
                        )

                    val rotatedV =
                        rotateY(
                            rotateX(axisY, rotX),
                            rotY
                        )

                    val screenU =
                        normalize2D(
                            Offset(
                                rotatedU.x,
                                -rotatedU.y
                            )
                        )

                    val screenV =
                        normalize2D(
                            Offset(
                                rotatedV.x,
                                -rotatedV.y
                            )
                        )

                    // ---------------------------------------------------
                    // PROJECTION
                    // ---------------------------------------------------

                    val projected = rotatedVerts.map {
                        project(
                            it,
                            cx,
                            cy,
                            scale,
                            cameraDistance
                        )
                    }

                    val depth =
                        rotatedVerts
                            .map { it.z }
                            .average()
                            .toFloat()

                    visibleFaces.add(
                        VisibleFace(
                            polygon = projected,
                            normal = normal,
                            depth = depth,

                            cubePos = animatedPos,

                            side = face.side,   // 👈 ВОТ ЭТО ДОБАВИТЬ

                            uAxis = axisX,
                            vAxis = axisY,

                            screenU = screenU,
                            screenV = screenV,
                        ),
                    )

                    facesToDraw.add(
                        Triple(
                            projected,
                            depth,
                            face.color
                        )
                    )
                }
            }

            // ---------------------------------------------------
            // DRAW
            // ---------------------------------------------------

            facesToDraw
                .sortedBy { it.second }
                .forEach { (points, _, color) ->

                    val path = Path().apply {

                        moveTo(
                            points[0].x,
                            points[0].y
                        )

                        for (i in 1 until points.size) {

                            lineTo(
                                points[i].x,
                                points[i].y
                            )
                        }

                        close()
                    }

                    drawPath(path, color)
                }
        }
    }

    // ---------------------------------------------------
    // FACE GEOMETRY
    // ---------------------------------------------------

    private fun buildFaces(
        size: Float,
        c: Cubelet
    ): List<Face> {

        val s = size / 2f

        fun v(
            x: Float,
            y: Float,
            z: Float
        ) = Vec3(
            x * s,
            y * s,
            z * s
        )

        val gray = Color(0xFF444444)

        return listOf(

            Face(
                verts = listOf(
                    v(-1f, -1f, 1f),
                    v(1f, -1f, 1f),
                    v(1f, 1f, 1f),
                    v(-1f, 1f, 1f),
                ),
                normal = Vec3(0f, 0f, 1f),
                color = c.front ?: gray,
                side = Side.FRONT   // 👈 ВОТ ЭТО ГЛАВНОЕ
            ),

            Face(
                listOf(
                    v(-1f, -1f, -1f),
                    v(-1f, 1f, -1f),
                    v(1f, 1f, -1f),
                    v(1f, -1f, -1f)
                ),
                Vec3(0f, 0f, -1f),
                color = c.back ?: gray,
                side = Side.BACK   // 👈 ВОТ ЭТО ГЛАВНОЕ
            ),

            Face(
                listOf(
                    v(-1f, -1f, -1f),
                    v(-1f, -1f, 1f),
                    v(-1f, 1f, 1f),
                    v(-1f, 1f, -1f)
                ),
                Vec3(-1f, 0f, 0f),
                color = c.left ?: gray,
                side = Side.LEFT
            ),

            Face(
                listOf(
                    v(1f, -1f, -1f),
                    v(1f, 1f, -1f),
                    v(1f, 1f, 1f),
                    v(1f, -1f, 1f)
                ),
                Vec3(1f, 0f, 0f),
                color = c.right ?: gray,
                side = Side.RIGHT
            ),

            Face(
                listOf(
                    v(-1f, 1f, -1f),
                    v(-1f, 1f, 1f),
                    v(1f, 1f, 1f),
                    v(1f, 1f, -1f)
                ),
                Vec3(0f, 1f, 0f),
                color = c.up ?: gray,
                side = Side.TOP
            ),

            Face(
                listOf(
                    v(-1f, -1f, -1f),
                    v(1f, -1f, -1f),
                    v(1f, -1f, 1f),
                    v(-1f, -1f, 1f)
                ),
                Vec3(0f, -1f, 0f),
                color = c.down ?: gray,
                side = Side.BOTTOM
            )
        )
    }


    // ---------------------------------------------------
    // 2D NORMALIZE
    // ---------------------------------------------------

    private fun normalize2D(
        o: Offset
    ): Offset {

        val len =
            sqrt(o.x * o.x + o.y * o.y)

        if (len < 0.0001f)
            return Offset.Zero

        return Offset(
            o.x / len,
            o.y / len
        )
    }

    private fun faceNormal(
        a: Vec3,
        b: Vec3,
        c: Vec3
    ): Vec3 {

        val ux = b.x - a.x
        val uy = b.y - a.y
        val uz = b.z - a.z

        val vx = c.x - a.x
        val vy = c.y - a.y
        val vz = c.z - a.z

        return Vec3(
            uy * vz - uz * vy,
            uz * vx - ux * vz,
            ux * vy - uy * vx
        )
    }

}

data class VisibleFace(
    val polygon: List<Offset>,
    val normal: Vec3,
    val depth: Float,

    val cubePos: Vec3,

    val side: Side,   // 👈 ДОБАВИТЬ ЭТО

    val uAxis: Vec3,
    val vAxis: Vec3,

    val screenU: Offset,
    val screenV: Offset,
)