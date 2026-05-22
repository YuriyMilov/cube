// ============================================================
// FILE: cube_new/CubeRendererNew.kt
// ============================================================

package com.quicklydone.nt.cube_new

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.common.cubeCenter
import com.quicklydone.nt.common.project
import com.quicklydone.nt.common.rotateAroundAxis
import com.quicklydone.nt.common.rotateX
import com.quicklydone.nt.common.rotateY
import com.quicklydone.nt.cube.CubeConfig
import kotlin.math.abs
import kotlin.math.sqrt

object CubeRendererNew {

    private val HIDDEN_COLOR =
        Color(0xFF444444)

    // =========================================================
    // DRAW
    // =========================================================

    fun drawNew(

        cubelets: SnapshotStateList<CubeletNew>,

        config: CubeConfig,

        rotX: Float,
        rotY: Float,

        animAxis: Vec3?,
        animLayer: Float,
        animAngle: Float,

        visibleFaces: SnapshotStateList<VisibleFaceNew>,

        drawScope: DrawScope
    ) {

        with(drawScope) {

            val cx = size.width / 2f
            val cy = size.height / 2f

            val facesToDraw =
                mutableListOf<DrawFaceNew>()

            visibleFaces.clear()

            cubelets.forEach { cube ->

                val inAnimatedLayer =
                    animAxis != null &&
                            onLayerNew(
                                cube.pos,
                                animAxis,
                                animLayer
                            )

                val position =
                    animatedPositionNew(
                        cube,
                        inAnimatedLayer,
                        animAxis,
                        animAngle
                    )

                val orientation =
                    animatedOrientationNew(
                        cube,
                        inAnimatedLayer,
                        animAxis,
                        animAngle
                    )

                buildFacesNew(
                    config.cubeletSize,
                    cube
                ).forEach { face ->

                    val worldVerts =
                        faceWorldVerticesNew(
                            face,
                            position,
                            orientation
                        )

                    val rotatedVerts =
                        worldVerts.map {
                            cameraRotateNew(
                                it,
                                rotX,
                                rotY
                            )
                        }

                    var normal =
                        rotateNormalNew(
                            face.normal,
                            orientation
                        )

                    normal =
                        cameraRotateNew(
                            normal,
                            rotX,
                            rotY
                        )

                    if (normal.z <= 0f)
                        return@forEach

                    val projected =
                        rotatedVerts.map {

                            project(
                                it,
                                cx,
                                cy,
                                config.scale,
                                config.cameraDistance
                            )
                        }

                    val depth =
                        rotatedVerts
                            .map { it.z }
                            .average()
                            .toFloat()

                    val rotatedU =
                        cameraRotateNew(
                            orientation.x,
                            rotX,
                            rotY
                        )

                    val rotatedV =
                        cameraRotateNew(
                            orientation.y,
                            rotX,
                            rotY
                        )

                    val screenU =
                        normalize2DNew(
                            Offset(
                                rotatedU.x,
                                -rotatedU.y
                            )
                        )

                    val screenV =
                        normalize2DNew(
                            Offset(
                                rotatedV.x,
                                -rotatedV.y
                            )
                        )

                    visibleFaces +=
                        VisibleFaceNew(
                            polygon = projected,
                            normal = normal,
                            depth = depth,

                            cubePos = position,

                            side = face.side,

                            uAxis = orientation.x,
                            vAxis = orientation.y,

                            screenU = screenU,
                            screenV = screenV,
                        )

                    facesToDraw +=
                        DrawFaceNew(
                            points = projected,
                            depth = depth,
                            color = face.color
                        )
                }
            }

            facesToDraw
                .sortedBy { it.depth }
                .forEach {

                    drawPath(
                        path = buildPathNew(it.points),
                        color = it.color
                    )
                }
        }
    }

    // =========================================================
    // LAYER
    // =========================================================

    private fun onLayerNew(
        pos: Vec3,
        axis: Vec3,
        layer: Float
    ): Boolean {

        val dot =
            pos.x * axis.x +
                    pos.y * axis.y +
                    pos.z * axis.z

        return abs(dot - layer) < 0.01f
    }

    // =========================================================
    // ANIMATION
    // =========================================================

    private fun animatedPositionNew(
        cube: CubeletNew,
        inLayer: Boolean,
        animAxis: Vec3?,
        animAngle: Float
    ): Vec3 {

        val center =  cubeCenter(cube.pos)

        if (!inLayer || animAxis == null)
            return center

        return rotateAroundAxis(
            center,
            animAxis,
            animAngle
        )
    }

    private fun animatedOrientationNew(
        cube: CubeletNew,
        inLayer: Boolean,
        animAxis: Vec3?,
        animAngle: Float
    ): OrientationAxesNew {

        fun rotate(v: Vec3): Vec3 {

            if (!inLayer || animAxis == null)
                return v

            return rotateAroundAxis(
                v,
                animAxis,
                animAngle
            )
        }

        return OrientationAxesNew(
            x = rotate(cube.axisX),
            y = rotate(cube.axisY),
            z = rotate(cube.axisZ)
        )
    }

    // =========================================================
    // TRANSFORMS
    // =========================================================

    private fun faceWorldVerticesNew(
        face: FaceNew,
        position: Vec3,
        orientation: OrientationAxesNew
    ): List<Vec3> {

        return face.verts.map { v ->

            val rotated = Vec3(

                v.x * orientation.x.x +
                        v.y * orientation.y.x +
                        v.z * orientation.z.x,

                v.x * orientation.x.y +
                        v.y * orientation.y.y +
                        v.z * orientation.z.y,

                v.x * orientation.x.z +
                        v.y * orientation.y.z +
                        v.z * orientation.z.z
            )

            Vec3(
                rotated.x + position.x,
                rotated.y + position.y,
                rotated.z + position.z
            )
        }
    }

    private fun rotateNormalNew(
        normal: Vec3,
        orientation: OrientationAxesNew
    ): Vec3 {

        return Vec3(

            normal.x * orientation.x.x +
                    normal.y * orientation.y.x +
                    normal.z * orientation.z.x,

            normal.x * orientation.x.y +
                    normal.y * orientation.y.y +
                    normal.z * orientation.z.y,

            normal.x * orientation.x.z +
                    normal.y * orientation.y.z +
                    normal.z * orientation.z.z
        )
    }

    private fun cameraRotateNew(
        v: Vec3,
        rotX: Float,
        rotY: Float
    ): Vec3 {

        return rotateY(
            rotateX(v, rotX),
            rotY
        )
    }

    // =========================================================
    // GEOMETRY
    // =========================================================

    private fun buildFacesNew(
        size: Float,
        cube: CubeletNew
    ): List<FaceNew> {

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

        return listOf(

            FaceNew(
                verts = listOf(
                    v(-1f, -1f, 1f),
                    v(1f, -1f, 1f),
                    v(1f, 1f, 1f),
                    v(-1f, 1f, 1f),
                ),
                normal = Vec3(0f, 0f, 1f),
                color = cube.front ?: HIDDEN_COLOR,
                side = SideNew.FRONT
            ),

            FaceNew(
                verts = listOf(
                    v(-1f, -1f, -1f),
                    v(-1f, 1f, -1f),
                    v(1f, 1f, -1f),
                    v(1f, -1f, -1f)
                ),
                normal = Vec3(0f, 0f, -1f),
                color = cube.back ?: HIDDEN_COLOR,
                side = SideNew.BACK
            ),

            FaceNew(
                verts = listOf(
                    v(-1f, -1f, -1f),
                    v(-1f, -1f, 1f),
                    v(-1f, 1f, 1f),
                    v(-1f, 1f, -1f)
                ),
                normal = Vec3(-1f, 0f, 0f),
                color = cube.left ?: HIDDEN_COLOR,
                side = SideNew.LEFT
            ),

            FaceNew(
                verts = listOf(
                    v(1f, -1f, -1f),
                    v(1f, 1f, -1f),
                    v(1f, 1f, 1f),
                    v(1f, -1f, 1f)
                ),
                normal = Vec3(1f, 0f, 0f),
                color = cube.right ?: HIDDEN_COLOR,
                side = SideNew.RIGHT
            ),

            FaceNew(
                verts = listOf(
                    v(-1f, 1f, -1f),
                    v(-1f, 1f, 1f),
                    v(1f, 1f, 1f),
                    v(1f, 1f, -1f)
                ),
                normal = Vec3(0f, 1f, 0f),
                color = cube.up ?: HIDDEN_COLOR,
                side = SideNew.TOP
            ),

            FaceNew(
                verts = listOf(
                    v(-1f, -1f, -1f),
                    v(1f, -1f, -1f),
                    v(1f, -1f, 1f),
                    v(-1f, -1f, 1f)
                ),
                normal = Vec3(0f, -1f, 0f),
                color = cube.down ?: HIDDEN_COLOR,
                side = SideNew.BOTTOM
            )
        )
    }

    // =========================================================
    // DRAW HELPERS
    // =========================================================

    private fun buildPathNew(
        points: List<Offset>
    ): Path {

        return Path().apply {

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
    }

    private fun normalize2DNew(
        o: Offset
    ): Offset {

        val len =
            sqrt(
                o.x * o.x +
                        o.y * o.y
            )

        if (len < 0.0001f)
            return Offset.Zero

        return Offset(
            o.x / len,
            o.y / len
        )
    }

    fun createInitialCubelets(): List<CubeletNew> {

        val result = mutableListOf<CubeletNew>()

        for (x in listOf(-0.5f, 0.5f))
            for (y in listOf(-0.5f, 0.5f))
                for (z in listOf(-0.5f, 0.5f)) {

                    result.add(
                        CubeletNew(
                            pos = Vec3(x, y, z),

                            up = if (y == 0.5f) Color.White else null,
                            down = if (y == -0.5f) Color.Yellow else null,

                            left = if (x == -0.5f) Color(0xFFFFA500) else null,
                            right = if (x == 0.5f) Color.Red else null,

                            front = if (z == 0.5f) Color.Green else null,
                            back = if (z == -0.5f) Color.Blue else null,
                        )
                    )
                }

        return result
    }
}

private data class DrawFaceNew(

    val points: List<Offset>,

    val depth: Float,

    val color: Color
)