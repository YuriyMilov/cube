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
import com.quicklydone.nt.cube222.InputCube222
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
        animLayer2: Float,   // ✔️ ДОБАВИЛИ
        animAngle: Float,

        drawScope: DrawScope,

        markers: List<FaceMarkerNew> = emptyList()
    ) {
        with(drawScope) {

            val cx = size.width / 2f
            val cy = size.height / 2f

            val facesToDraw = mutableListOf<DrawFaceNew>()

            cubelets.forEach { cube ->

                val inAnimatedLayer =
                    animAxis != null &&
                            (
                                    onLayerNew(cube.pos, animAxis, animLayer) ||
                                            onLayerNew(cube.pos, animAxis, animLayer2)
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
                            cameraRotateNew(it, rotX, rotY)
                        }

                    var normal =
                        rotateNormalNew(face.normal, orientation)

                    normal =
                        cameraRotateNew(normal, rotX, rotY)

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

                    facesToDraw += DrawFaceNew(
                        points = projected,
                        depth = depth,
                        color = face.color,
                        side = face.side,
                        cubePos = cube.pos
                    )
                }
            }

            facesToDraw
                .sortedBy { it.depth }
                .forEach { face ->
                    drawPath(
                        path = buildPathNew(face.points),
                        color = face.color
                    )
                }

            drawInputCubeArrows(
                markers = markers,
                yaw = rotY,
                pitch = rotX,
                w = size.width,
                h = size.height
            )
        }
    }

    // =========================================================
    // INPUT CUBE ARROWS
    // =========================================================

    private fun DrawScope.drawInputCubeArrows(

        markers: List<FaceMarkerNew>,

        yaw: Float,
        pitch: Float,

        w: Float,
        h: Float
    ) {

        val cells =
            InputCube222.buildCells(

                yaw = yaw,
                pitch = pitch,

                w = w,
                h = h
            )

        markers.forEach { marker ->

            val face =
                marker.face ?: return@forEach

            // =========================================
            // FACE VISIBILITY
            // =========================================

            val normal =
                when (face) {

                    InputCube222.Face.FRONT ->
                        Vec3(0f, 0f, 1f)

                    InputCube222.Face.BACK ->
                        Vec3(0f, 0f, -1f)

                    InputCube222.Face.LEFT ->
                        Vec3(-1f, 0f, 0f)

                    InputCube222.Face.RIGHT ->
                        Vec3(1f, 0f, 0f)

                    InputCube222.Face.TOP ->
                        Vec3(0f, 1f, 0f)

                    InputCube222.Face.BOTTOM ->
                        Vec3(0f, -1f, 0f)
                }

            val rotatedNormal =
                cameraRotateNew(
                    normal,
                    pitch,
                    yaw
                )

            // hidden behind cube

            if (rotatedNormal.z <= 0f)
                return@forEach

            // =========================================

            val row =
                marker.row ?: return@forEach

            val col =
                marker.col ?: return@forEach

            val arrow =
                marker.arrow ?: return@forEach

            val cell =
                cells.firstOrNull {

                    it.face == face &&
                            it.row == row &&
                            it.col == col
                }
                    ?: return@forEach

            val center =
                Offset(

                    x =
                        cell.poly
                            .map { it.x }
                            .average()
                            .toFloat(),

                    y =
                        cell.poly
                            .map { it.y }
                            .average()
                            .toFloat()
                )

            val uAxis =
                edgeDir(
                    cell.poly[0],
                    cell.poly[1]
                )

            val vAxis =
                edgeDir(
                    cell.poly[0],
                    cell.poly[3]
                )

            val chainAxis =
                when (arrow) {

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
                        center.x + chainAxis.x * shift,
                        center.y + chainAxis.y * shift
                    )

                drawFaceArrowNew(

                    center = arrowCenter,

                    dir = arrow,

                    uAxis = uAxis,
                    vAxis = vAxis,

                    color =
                        marker.color.copy(
                            alpha =
                                rotatedNormal.z
                                    .coerceIn(0f, 1f)
                        ),

                    size = marker.radius * 12f
                )
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

        val axis =
            when (dir) {

                ArrowDirNew.POS_U ->
                    uAxis

                ArrowDirNew.NEG_U ->
                    Offset(
                        -uAxis.x,
                        -uAxis.y
                    )

                ArrowDirNew.POS_V ->
                    vAxis

                ArrowDirNew.NEG_V ->
                    Offset(
                        -vAxis.x,
                        -vAxis.y
                    )
            }

        val start =
            Offset(
                center.x - axis.x * size * 0.5f,
                center.y - axis.y * size * 0.5f
            )

        val end =
            Offset(
                center.x + axis.x * size * 0.5f,
                center.y + axis.y * size * 0.5f
            )

        val stroke =
            size * 0.08f

        val headSize =
            size * 0.22f

        drawLine(
            color = color,
            start = start,
            end = end,
            strokeWidth = stroke
        )

        val perp =
            Offset(
                -axis.y,
                axis.x
            )

        val headLeft =
            Offset(
                end.x - axis.x * headSize + perp.x * headSize * 0.55f,
                end.y - axis.y * headSize + perp.y * headSize * 0.55f
            )

        val headRight =
            Offset(
                end.x - axis.x * headSize - perp.x * headSize * 0.55f,
                end.y - axis.y * headSize - perp.y * headSize * 0.55f
            )

        drawPath(
            path =
                Path().apply {

                    moveTo(end.x, end.y)

                    lineTo(
                        headLeft.x,
                        headLeft.y
                    )

                    lineTo(
                        headRight.x,
                        headRight.y
                    )

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

    fun createInitialCubelets(): List<CubeletNew> {

        val result = mutableListOf<CubeletNew>()

        var id = 0

        for (x in listOf(-0.5f, 0.5f))
            for (y in listOf(-0.5f, 0.5f))
                for (z in listOf(-0.5f, 0.5f)) {

                    result +=
                        CubeletNew(

                            id = id++,

                            pos = Vec3(x, y, z),

                            up =
                                if (y == 0.5f)
                                    Color.White
                                else
                                    null,

                            down =
                                if (y == -0.5f)
                                    Color.Yellow
                                else
                                    null,

                            left =
                                if (x == -0.5f)
                                    Color(0xFFFFA500)
                                else
                                    null,

                            right =
                                if (x == 0.5f)
                                    Color.Red
                                else
                                    null,

                            front =
                                if (z == 0.5f)
                                    Color.Green
                                else
                                    null,

                            back =
                                if (z == -0.5f)
                                    Color.Blue
                                else
                                    null
                        )
                }

        return result
    }

    private fun isFaceVisible(
        face: InputCube222.Face,
        yaw: Float,
        pitch: Float
    ): Boolean {

        val normal =
            when (face) {

                InputCube222.Face.FRONT ->
                    Vec3(0f, 0f, 1f)

                InputCube222.Face.BACK ->
                    Vec3(0f, 0f, -1f)

                InputCube222.Face.LEFT ->
                    Vec3(-1f, 0f, 0f)

                InputCube222.Face.RIGHT ->
                    Vec3(1f, 0f, 0f)

                InputCube222.Face.TOP ->
                    Vec3(0f, 1f, 0f)

                InputCube222.Face.BOTTOM ->
                    Vec3(0f, -1f, 0f)
            }

        val rotated =
            cameraRotateNew(
                normal,
                pitch,
                yaw
            )

        return rotated.z > 0f
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