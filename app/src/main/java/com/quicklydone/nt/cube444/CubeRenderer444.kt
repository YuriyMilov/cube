package com.quicklydone.nt.cube444

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.quicklydone.nt.animation.onLayer444
import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.common.cubeCenter
import com.quicklydone.nt.common.project
import com.quicklydone.nt.common.rotateAroundAxis
import com.quicklydone.nt.common.rotateX
import com.quicklydone.nt.common.rotateY
import com.quicklydone.nt.cube444.Cubelet
import com.quicklydone.nt.cube444.Face
import com.quicklydone.nt.cube444.VisibleFace

import kotlin.math.sqrt

object CubeRenderer444 {

    // =====================================================
    // CONFIG
    // =====================================================

    private const val CAMERA_DISTANCE = 24f
    private const val SCALE = 1200f
    private const val CUBELET_SIZE = 1.85f

    private val HIDDEN_COLOR =
        Color(0xFF444444)


    enum class Side444 {
        FRONT, BACK, LEFT, RIGHT, TOP, BOTTOM
    }


    // =====================================================
    // DRAW
    // =====================================================

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

            val facesToDraw =
                mutableListOf<DrawFace>()

            visibleFaces.clear()

            cubelets.forEach { cube ->

                val inAnimatedLayer =
                    animAxis != null &&
                            onLayer444(
                                cube.pos,
                                animAxis,
                                animLayer
                            )

                val position =
                    animatedPosition(
                        cube = cube,
                        inLayer = inAnimatedLayer,
                        animAxis = animAxis,
                        animAngle = animAngle
                    )

                val orientation =
                    animatedOrientation(
                        cube = cube,
                        inLayer = inAnimatedLayer,
                        animAxis = animAxis,
                        animAngle = animAngle
                    )

                buildFaces(CUBELET_SIZE, cube)
                    .forEach { face ->

                        val worldVerts =
                            faceWorldVertices444(
                                face = face,
                                position = position,
                                orientation = orientation
                            )

                        val rotatedVerts =
                            worldVerts.map {
                                cameraRotate(
                                    it,
                                    rotX,
                                    rotY
                                )
                            }

                        var normal =
                            rotateNormal(
                                face.normal,
                                orientation
                            )

                        normal =
                            cameraRotate(
                                normal,
                                rotX,
                                rotY
                            )

                        // backface culling
                        if (normal.z <= 0f)
                            return@forEach

                        val projected =
                            rotatedVerts.map {
                                project(
                                    it,
                                    cx,
                                    cy,
                                    SCALE,
                                    CAMERA_DISTANCE
                                )
                            }

                        val depth =
                            rotatedVerts
                                .map { it.z }
                                .average()
                                .toFloat()

                        val rotatedU =
                            cameraRotate(
                                orientation.x,
                                rotX,
                                rotY
                            )

                        val rotatedV =
                            cameraRotate(
                                orientation.y,
                                rotX,
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

                        visibleFaces.add(
                            VisibleFace(
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
                        )

                        facesToDraw.add(
                            DrawFace(
                                points = projected,
                                depth = depth,
                                color = face.color
                            )
                        )
                    }
            }

            facesToDraw
                .sortedBy { it.depth }
                .forEach { face ->

                    drawPath(
                        path = buildPath(face.points),
                        color = face.color
                    )
                }
        }
    }

    // =====================================================
    // ANIMATION
    // =====================================================

    fun animatedPosition(
        cube: Cubelet,
        inLayer: Boolean,
        animAxis: Vec3?,
        animAngle: Float
    ): Vec3 {

        if (!inLayer || animAxis == null)
            return cubeCenter(cube.pos)

        return rotateAroundAxis(
            cubeCenter(cube.pos),
            animAxis,
            animAngle
        )
    }

     fun animatedOrientation(
        cube: Cubelet,
        inLayer: Boolean,
        animAxis: Vec3?,
        animAngle: Float
    ): OrientationAxes {

        fun rotate(v: Vec3): Vec3 {

            if (!inLayer || animAxis == null)
                return v

            return rotateAroundAxis(
                v,
                animAxis,
                animAngle
            )
        }

        return OrientationAxes(
            x = rotate(cube.axisX),
            y = rotate(cube.axisY),
            z = rotate(cube.axisZ)
        )
    }

    // =====================================================
    // FACE TRANSFORMS
    // =====================================================

     fun faceWorldVertices444(
        face: Face,
        position: Vec3,
        orientation: OrientationAxes
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

     fun rotateNormal(
        normal: Vec3,
        orientation: OrientationAxes
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

    // =====================================================
    // CAMERA
    // =====================================================

     fun cameraRotate(
        v: Vec3,
        rotX: Float,
        rotY: Float
    ): Vec3 {

        return rotateY(
            rotateX(v, rotX),
            rotY
        )
    }

    // =====================================================
    // DRAW HELPERS
    // =====================================================

    private fun buildPath(
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

    // =====================================================
    // FACE GEOMETRY
    // =====================================================

    private fun buildFaces(
        size: Float,
        cube: Cubelet
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

        return listOf(

            Face(
                verts = listOf(
                    v(-1f, -1f, 1f),
                    v(1f, -1f, 1f),
                    v(1f, 1f, 1f),
                    v(-1f, 1f, 1f),
                ),
                normal = Vec3(0f, 0f, 1f),
                color = cube.front ?: HIDDEN_COLOR,
                side = Side444.FRONT
            ),

            Face(
                verts = listOf(
                    v(-1f, -1f, -1f),
                    v(-1f, 1f, -1f),
                    v(1f, 1f, -1f),
                    v(1f, -1f, -1f)
                ),
                normal = Vec3(0f, 0f, -1f),
                color = cube.back ?: HIDDEN_COLOR,
                side = Side444.BACK
            ),

            Face(
                verts = listOf(
                    v(-1f, -1f, -1f),
                    v(-1f, -1f, 1f),
                    v(-1f, 1f, 1f),
                    v(-1f, 1f, -1f)
                ),
                normal = Vec3(-1f, 0f, 0f),
                color = cube.left ?: HIDDEN_COLOR,
                side = Side444.LEFT
            ),

            Face(
                verts = listOf(
                    v(1f, -1f, -1f),
                    v(1f, 1f, -1f),
                    v(1f, 1f, 1f),
                    v(1f, -1f, 1f)
                ),
                normal = Vec3(1f, 0f, 0f),
                color = cube.right ?: HIDDEN_COLOR,
                side = Side444.RIGHT
            ),

            Face(
                verts = listOf(
                    v(-1f, 1f, -1f),
                    v(-1f, 1f, 1f),
                    v(1f, 1f, 1f),
                    v(1f, 1f, -1f)
                ),
                normal = Vec3(0f, 1f, 0f),
                color = cube.up ?: HIDDEN_COLOR,
                side = Side444.TOP
            ),

            Face(
                verts = listOf(
                    v(-1f, -1f, -1f),
                    v(1f, -1f, -1f),
                    v(1f, -1f, 1f),
                    v(-1f, -1f, 1f)
                ),
                normal = Vec3(0f, -1f, 0f),
                color = cube.down ?: HIDDEN_COLOR,
                side = Side444.BOTTOM
            )
        )
    }



    fun createInitialCubelets(): List<Cubelet> {

        val result = mutableListOf<Cubelet>()

        val coords =
            listOf(-1.5f, -0.5f, 0.5f, 1.5f)

        for (x in coords)
            for (y in coords)
                for (z in coords) {

                    result.add(
                        Cubelet(
                            pos = Vec3(x, y, z),

                            up = if (y == 1.5f) Color.White else null,
                            down = if (y == -1.5f) Color.Yellow else null,

                            left = if (x == -1.5f) Color(0xFFFFA500) else null,
                            right = if (x == 1.5f) Color.Red else null,

                            front = if (z == 1.5f) Color.Green else null,
                            back = if (z == -1.5f) Color.Blue else null,
                        )
                    )
                }

        return result
    }

}

// =====================================================
// INTERNAL MODELS
// =====================================================

 data class DrawFace(
    val points: List<Offset>,
    val depth: Float,
    val color: Color
)

 data class OrientationAxes(
    val x: Vec3,
    val y: Vec3,
    val z: Vec3
)

// =====================================================
// PUBLIC MODEL
// =====================================================

enum class Side444 {
    FRONT, BACK, LEFT, RIGHT, TOP, BOTTOM
}

data class VisibleFace(
    val polygon: List<Offset>,
    val normal: Vec3,
    val depth: Float,

    val cubePos: Vec3,

    val side: CubeRenderer444.Side444,

    val uAxis: Vec3,
    val vAxis: Vec3,

    val screenU: Offset,
    val screenV: Offset,
)


data class Face(
    val verts: List<Vec3>,
    val normal: Vec3,
    val color: Color,

    val side: CubeRenderer444.Side444
)

data class Cubelet(

    var pos: Vec3,

    var up: Color?,
    var down: Color?,
    var left: Color?,
    var right: Color?,
    var front: Color?,
    var back: Color?,

    var axisX: Vec3 = Vec3(1f,0f,0f),
    var axisY: Vec3 = Vec3(0f,1f,0f),
    var axisZ: Vec3 = Vec3(0f,0f,1f),
)



