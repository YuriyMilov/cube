// ============================================================
// FILE: cube_new/CubeRendererNew.kt
// ============================================================

package com.quicklydone.nt.cube_new

import android.util.Log
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

object CubeRenderer222 {

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

        drawScope: DrawScope,

        markers: List<FaceMarkerNew> = emptyList()
    ) {

        with(drawScope) {

            val cx = size.width / 2f
            val cy = size.height / 2f

            val facesToDraw =
                mutableListOf<DrawFaceNew>()

            visibleFaces.clear()

            // =====================================================
            // BUILD FACES
            // =====================================================

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

                    // BACKFACE CULLING

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

                            cubePos = cube.pos,

                            side = face.side,

                            uAxis = orientation.x,
                            vAxis = orientation.y,

                            screenU = screenU,
                            screenV = screenV
                        )

                    facesToDraw +=
                        DrawFaceNew(

                            points = projected,

                            depth = depth,

                            color = face.color,

                            side = face.side,

                            cubePos = cube.pos
                        )
                }
            }

            // =====================================================
            // DRAW SORTED
            // =====================================================

            facesToDraw
                .sortedBy { it.depth }
                .forEach { face ->

                    drawPath(
                        path = buildPathNew(face.points),
                        color = face.color
                    )

                    val visibleFace =
                        visibleFaces.firstOrNull {

                            it.side == face.side &&
                                    it.cubePos == face.cubePos
                        }

                    if (visibleFace == null)
                        return@forEach

                    val faceMarkers =
                        markers.filter { marker ->

                            marker.side == face.side &&

                                    (
                                            marker.cubePos == null ||
                                                    marker.cubePos == face.cubePos
                                            )
                        }

                    if (faceMarkers.isNotEmpty()) {

                        val centerX =
                            face.points
                                .map { it.x }
                                .average()
                                .toFloat()

                        val centerY =
                            face.points
                                .map { it.y }
                                .average()
                                .toFloat()

                        val uAxis =
                            edgeDir(
                                face.points[0],
                                face.points[1]
                            )

                        val vAxis =
                            edgeDir(
                                face.points[0],
                                face.points[3]
                            )

                        faceMarkers.forEach { marker ->

                            if (marker.arrow == null)
                                return@forEach

                            val chainAxis =
                                when (marker.arrow) {

                                    ArrowDirNew.POS_U,
                                    ArrowDirNew.NEG_U -> uAxis

                                    ArrowDirNew.POS_V,
                                    ArrowDirNew.NEG_V -> vAxis
                                }

                            repeat(marker.count) { i ->

                                val shift =
                                    (
                                            i -
                                                    (marker.count - 1) / 2f
                                            ) *
                                            marker.radius *
                                            marker.spacing

                                val arrowCenter =
                                    Offset(
                                        centerX + chainAxis.x * shift,
                                        centerY + chainAxis.y * shift
                                    )

                                drawFaceArrowNew(

                                    center = arrowCenter,

                                    dir = marker.arrow,

                                    uAxis = uAxis,
                                    vAxis = vAxis,

                                    color = marker.color,

                                    size = marker.radius * 12f
                                )
                            }
                        }
                    }
                    // TEST CENTER


                    /*
                                        if (face.side == SideNew.FRONT) {

                                            val center =
                                                face.points.reduce { acc, p ->

                                                    Offset(
                                                        acc.x + p.x,
                                                        acc.y + p.y
                                                    )
                                                }

                                            drawCircle(
                                                color = Color.Black,

                                                radius = 12f,

                                                center = Offset(
                                                    center.x / 4f,
                                                    center.y / 4f
                                                )
                                            )
                                        }*/
                }
        }
    }

    // =========================================================
    // DRAW ARROW
    // =========================================================

    private fun DrawScope.drawFaceArrowNew(
        center: Offset,
        dir: ArrowDirNew,
        uAxis: Offset,
        vAxis: Offset,
        color: Color,
        size: Float
    ) {
        val axis = when (dir) {
            ArrowDirNew.POS_U -> uAxis
            ArrowDirNew.NEG_U -> Offset(-uAxis.x, -uAxis.y)
            ArrowDirNew.POS_V -> vAxis
            ArrowDirNew.NEG_V -> Offset(-vAxis.x, -vAxis.y)
        }

        val start = Offset(
            center.x - axis.x * size * 0.5f,
            center.y - axis.y * size * 0.5f
        )

        val end = Offset(
            center.x + axis.x * size * 0.5f,
            center.y + axis.y * size * 0.5f
        )

        val stroke = size * 0.08f
        val headSize = size * 0.22f

        drawLine(
            color = color,
            start = start,
            end = end,
            strokeWidth = stroke
        )

        val perp = Offset(
            -axis.y,
            axis.x
        )

        val headLeft = Offset(
            end.x - axis.x * headSize + perp.x * headSize * 0.55f,
            end.y - axis.y * headSize + perp.y * headSize * 0.55f
        )

        val headRight = Offset(
            end.x - axis.x * headSize - perp.x * headSize * 0.55f,
            end.y - axis.y * headSize - perp.y * headSize * 0.55f
        )

        drawPath(
            path = Path().apply {
                moveTo(end.x, end.y)
                lineTo(headLeft.x, headLeft.y)
                lineTo(headRight.x, headRight.y)
                close()
            },
            color = color
        )
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

        val center =
            cubeCenter(cube.pos)

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

            val rotated =
                Vec3(

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

        val s =
            size / 2f

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
        var id = 0

        for (x in listOf(-0.5f, 0.5f))
            for (y in listOf(-0.5f, 0.5f))
                for (z in listOf(-0.5f, 0.5f)) {

                    result.add(
                        CubeletNew(
                            id = id++,

                            pos = Vec3(x, y, z),

                            up =
                                if (y == 0.5f) Color.White else null,

                            down =
                                if (y == -0.5f) Color.Yellow else null,

                            left =
                                if (x == -0.5f) Color(0xFFFFA500) else null,

                            right =
                                if (x == 0.5f) Color.Red else null,

                            front =
                                if (z == 0.5f) Color.Green else null,

                            back =
                                if (z == -0.5f) Color.Blue else null
                        )
                    )
                }

        return result
    }
}

// ============================================================
// DATA
// ============================================================

enum class ArrowDirNew {

    POS_U,
    NEG_U,

    POS_V,
    NEG_V
}

data class FaceMarkerNew(

    val side: SideNew,

    val cubePos: Vec3? = null,

    val color: Color = Color.Black,

    val radius: Float = 10f,

    val arrow: ArrowDirNew? = null,

    val layer: Float? = null,

    val count: Int = 1,

    val spacing: Float = 1.8f
)

data class DrawFaceNew(

    val points: List<Offset>,

    val depth: Float,

    val color: Color,

    val side: SideNew,

    val cubePos: Vec3
)

// ============================================================
// HELPERS
// ============================================================

private fun edgeDir(
    a: Offset,
    b: Offset
): Offset {

    val dx =
        b.x - a.x

    val dy =
        b.y - a.y

    val len =
        sqrt(
            dx * dx +
                    dy * dy
        )

    if (len < 0.0001f)
        return Offset.Zero

    return Offset(
        dx / len,
        dy / len
    )
}