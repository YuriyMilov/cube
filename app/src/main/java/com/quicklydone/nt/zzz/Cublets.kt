package com.quicklydone.nt.zzz

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.input.pointer.consume
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.*




/* ---------------- SCREEN ---------------- */

@Composable
fun Cublets(
    goMenu: () -> Unit
) {

    var qq by remember { mutableStateOf("") }
    var n  by remember { mutableStateOf(0) }
    fun qq(s: String) {
        qq =s
    }
    val cubelets = remember {

        mutableStateListOf<Cubelet>().apply {

            for (x in listOf(-1f, 1f))
                for (y in listOf(-1f, 1f))
                    for (z in listOf(-1f, 1f)) {

                        add(
                            Cubelet(
                                pos = Vec3(x, y, z),

                                up = if (y == 1f) Color.White else null,
                                down = if (y == -1f) Color.Yellow else null,

                                left =
                                    if (x == -1f)
                                        Color(0xFFFFA500)
                                    else null,

                                right =
                                    if (x == 1f)
                                        Color.Red
                                    else null,

                                front =
                                    if (z == 1f)
                                        Color.Green
                                    else null,

                                back =
                                    if (z == -1f)
                                        Color.Blue
                                    else null,
                            )
                        )
                    }
        }
    }

    val visibleFaces = remember {
        mutableStateListOf<VisibleFace>()
    }

    var rotX by remember {
        mutableStateOf(0.8f)
    }

    var rotY by remember {
        mutableStateOf(-0.8f)
    }

    var animAxis by remember {
        mutableStateOf<Vec3?>(null)
    }

    var animLayer by remember {
        mutableStateOf(0f)
    }

    var animAngle by remember {
        mutableStateOf(0f)
    }

    val scope = rememberCoroutineScope()

    fun rotateAll(
        dx: Float,
        dy: Float
    ) {
        rotY += dx * 0.01f
        rotX -= dy * 0.01f
    }

    fun startRotation(
        axis: Vec3,
        layer: Float,
        dir: Float
    ) {

        if (animAxis != null)
            return

        scope.launch {

            rotateLayer(
                cubelets = cubelets,

                axis = axis,
                layer = layer,
                dir = dir,

                onStart = {
                    animAxis = axis
                    animLayer = layer
                },

                onStep = {
                    animAngle = it
                },

                onEnd = {
                    animAxis = null
                    animAngle = 0f
                }
            )
        }
    }


    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),

            horizontalArrangement =
                Arrangement.SpaceEvenly
        ) {

            Button(
                onClick = goMenu
            ) {
                Text("MENU")
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {

            var canvasWidth by remember {
                mutableStateOf(0f)
            }

            var canvasHeight by remember {
                mutableStateOf(0f)
            }

            fun detectFaceHit(
                pos: Offset
            ): HitFace? {

                if (visibleFaces.isEmpty())
                    return null

                val nearest =
                    visibleFaces.minByOrNull {

                        val dx =
                            it.screenCenter.x - pos.x

                        val dy =
                            it.screenCenter.y - pos.y

                        dx * dx + dy * dy
                    } ?: return null

                val dist =
                    sqrt(
                        (nearest.screenCenter.x - pos.x).pow(2) +
                                (nearest.screenCenter.y - pos.y).pow(2)
                    )

                // 🔥 ВАЖНО: если далеко — считаем что это НЕ грань
                if (dist > 130f)
                    return null

                val n = nearest.normal

                return when {

                    abs(n.x) > abs(n.y) &&
                            abs(n.x) > abs(n.z) -> {

                        HitFace(
                            axis = Vec3(1f, 0f, 0f),
                            layer = nearest.layer
                        )
                    }

                    abs(n.y) > abs(n.z) -> {

                        HitFace(
                            axis = Vec3(0f, 1f, 0f),
                            layer = nearest.layer
                        )
                    }

                    else -> {

                        HitFace(
                            axis = Vec3(0f, 0f, 1f),
                            layer = nearest.layer
                        )
                    }
                }
            }
            var lockedToFace by remember { mutableStateOf<HitFace?>(null) }

            Canvas(

                modifier = Modifier
                    .fillMaxSize()

                    .pointerInput(Unit) {

                        var hitFace: HitFace? = null
                        var moved = false

                        detectDragGestures(

                            onDragStart = { offset ->

                                lockedToFace = detectFaceHit(offset)
                            },

                            onDrag = { change, drag ->

                                change.consume()

                                // 🚫 защита от повторного запуска вращения
                                if (animAxis != null) {
                                    return@detectDragGestures
                                }

                                val face = lockedToFace

                                if (face == null) {
                                    rotateAll(drag.x, drag.y)
                                    return@detectDragGestures
                                }

                                val axis = face.axis

                                val (lx, ly) = rotateDrag(drag.x, drag.y, rotY)

                                val dir = when {

                                    axis.x != 0f -> {
                                        if (ly > 0f) 1f else -1f
                                    }

                                    axis.y != 0f -> {
                                        if (lx > 0f) 1f else -1f
                                    }

                                    else -> {
                                        if (lx > 0f) 1f else -1f
                                    }
                                }

qq(face.toString()  )

                                startRotation(
                                    axis = axis,
                                    layer = face.layer,
                                    dir = dir
                                )
                            }
                        )
                    }
            ) {

                canvasWidth = size.width
                canvasHeight = size.height

                val cx = size.width / 2f
                val cy = size.height / 2f

                val scale = 1500f
                val cameraDistance = 14f

                fun rotateX(
                    v: Vec3,
                    a: Float
                ): Vec3 {

                    val c = cos(a)
                    val s = sin(a)

                    return Vec3(
                        v.x,
                        v.y * c - v.z * s,
                        v.y * s + v.z * c
                    )
                }

                fun rotateY(
                    v: Vec3,
                    a: Float
                ): Vec3 {

                    val c = cos(a)
                    val s = sin(a)

                    return Vec3(
                        v.x * c + v.z * s,
                        v.y,
                        -v.x * s + v.z * c
                    )
                }

                fun project(
                    v: Vec3
                ): Offset {

                    val p =
                        1f / (
                                cameraDistance - v.z
                                ).coerceAtLeast(0.1f)

                    return Offset(
                        cx + v.x * scale * p,
                        cy - v.y * scale * p
                    )
                }

                fun applyAnimBasis(
                    v: Vec3,
                    c: Cubelet,
                    animAxis: Vec3?,
                    animAngle: Float,
                    inLayer: Boolean
                ): Vec3 {

                    var x = c.axisX
                    var y = c.axisY
                    var z = c.axisZ

                    if (animAxis != null && inLayer) {

                        x = rotateAroundAxis(
                            x,
                            animAxis,
                            animAngle
                        )

                        y = rotateAroundAxis(
                            y,
                            animAxis,
                            animAngle
                        )

                        z = rotateAroundAxis(
                            z,
                            animAxis,
                            animAngle
                        )
                    }

                    return Vec3(
                        v.x * x.x + v.y * y.x + v.z * z.x,

                        v.x * x.y + v.y * y.y + v.z * z.y,

                        v.x * x.z + v.y * y.z + v.z * z.z
                    )
                }

                fun buildFaces(
                    size: Float,
                    c: Cubelet
                ): List<Face> {

                    val s = size / 2f

                    fun v(
                        x: Float,
                        y: Float,
                        z: Float
                    ) =
                        Vec3(
                            x * s,
                            y * s,
                            z * s
                        )

                    val gray =
                        Color(0xFF444444)

                    return listOf(

                        Face(
                            verts = listOf(
                                v(-1f,-1f,1f),
                                v(1f,-1f,1f),
                                v(1f,1f,1f),
                                v(-1f,1f,1f),
                            ),

                            normal =
                                Vec3(0f,0f,1f),

                            color =
                                c.front ?: gray
                        ),

                        Face(
                            verts = listOf(
                                v(-1f,-1f,-1f),
                                v(-1f,1f,-1f),
                                v(1f,1f,-1f),
                                v(1f,-1f,-1f),
                            ),

                            normal =
                                Vec3(0f,0f,-1f),

                            color =
                                c.back ?: gray
                        ),

                        Face(
                            verts = listOf(
                                v(-1f,-1f,-1f),
                                v(-1f,-1f,1f),
                                v(-1f,1f,1f),
                                v(-1f,1f,-1f),
                            ),

                            normal =
                                Vec3(-1f,0f,0f),

                            color =
                                c.left ?: gray
                        ),

                        Face(
                            verts = listOf(
                                v(1f,-1f,-1f),
                                v(1f,1f,-1f),
                                v(1f,1f,1f),
                                v(1f,-1f,1f),
                            ),

                            normal =
                                Vec3(1f,0f,0f),

                            color =
                                c.right ?: gray
                        ),

                        Face(
                            verts = listOf(
                                v(-1f,1f,-1f),
                                v(-1f,1f,1f),
                                v(1f,1f,1f),
                                v(1f,1f,-1f),
                            ),

                            normal =
                                Vec3(0f,1f,0f),

                            color =
                                c.up ?: gray
                        ),

                        Face(
                            verts = listOf(
                                v(-1f,-1f,-1f),
                                v(1f,-1f,-1f),
                                v(1f,-1f,1f),
                                v(-1f,-1f,1f),
                            ),

                            normal =
                                Vec3(0f,-1f,0f),

                            color =
                                c.down ?: gray
                        ),
                    )
                }

                val cubeSize = 1.8f

                val facesToDraw =
                    mutableListOf<
                            Triple<
                                    List<Offset>,
                                    Float,
                                    Color
                                    >
                            >()

                visibleFaces.clear()

                cubelets.forEach { cube ->

                    var pos = cube.pos

                    val inLayer =
                        animAxis != null &&
                                onLayer(
                                    pos,
                                    animAxis!!,
                                    animLayer
                                )

                    if (inLayer) {

                        pos =
                            rotateVec(
                                pos,
                                animAxis!!,
                                animAngle
                            )
                    }

                    val faces =
                        buildFaces(
                            cubeSize,
                            cube
                        )

                    faces.forEach { face ->

                        val rotatedVerts =
                            face.verts.map {

                                var v =
                                    applyAnimBasis(
                                        it,
                                        cube,
                                        animAxis,
                                        animAngle,
                                        inLayer
                                    )

                                v = Vec3(
                                    v.x + pos.x,
                                    v.y + pos.y,
                                    v.z + pos.z
                                )

                                rotateY(
                                    rotateX(v, rotX),
                                    rotY
                                )
                            }

                        var normal =
                            applyAnimBasis(
                                face.normal,
                                cube,
                                animAxis,
                                animAngle,
                                inLayer
                            )

                        normal =
                            rotateY(
                                rotateX(normal, rotX),
                                rotY
                            )

                        if (normal.z > 0f) {

                            val projected =
                                rotatedVerts.map {
                                    project(it)
                                }

                            val depth =
                                rotatedVerts
                                    .map { it.z }
                                    .average()
                                    .toFloat()

                            val center3d =
                                rotatedVerts.reduce { a, b ->

                                    Vec3(
                                        a.x + b.x,
                                        a.y + b.y,
                                        a.z + b.z
                                    )
                                }.let {

                                    Vec3(
                                        it.x / 4f,
                                        it.y / 4f,
                                        it.z / 4f
                                    )
                                }

                            val center2d =
                                project(center3d)

                            val layer =
                                when {

                                    abs(face.normal.x) > 0.5f ->
                                        cube.pos.x

                                    abs(face.normal.y) > 0.5f ->
                                        cube.pos.y

                                    else ->
                                        cube.pos.z
                                }

                            visibleFaces.add(

                                VisibleFace(
                                    screenCenter = center2d,
                                    normal = normal,
                                    layer = layer
                                )
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
                }

                facesToDraw
                    .sortedBy { it.second }
                    .forEach { (points, _, color) ->

                        val path =
                            Path().apply {

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

                        drawPath(
                            path,
                            color
                        )
                    }

            }

        }
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start
            ) {


                Text(
                    text = "$qq", color = Color.Green
                )
            }


        Row(
            Modifier
                .fillMaxWidth()
                .padding(4.dp),

            horizontalArrangement =
                Arrangement.SpaceEvenly
        ) {

            Button(
                onClick = {
                    startRotation(
                        Vec3(1f,0f,0f),
                        1f,
                        1f
                    )
                }
            ) {
                Text("R")
            }

            Button(
                onClick = {
                    startRotation(
                        Vec3(1f,0f,0f),
                        -1f,
                        1f
                    )
                }
            ) {
                Text("L")
            }

            Button(
                onClick = {
                    startRotation(
                        Vec3(0f,1f,0f),
                        1f,
                        1f
                    )
                }
            ) {
                Text("U")
            }

            Button(
                onClick = {
                    startRotation(
                        Vec3(0f,1f,0f),
                        -1f,
                        1f
                    )
                }
            ) {
                Text("D")
            }

            Button(
                onClick = {
                    startRotation(
                        Vec3(0f,0f,1f),
                        1f,
                        1f
                    )
                }
            ) {
                Text("F")
            }

            Button(
                onClick = {
                    startRotation(
                        Vec3(0f,0f,1f),
                        -1f,
                        1f
                    )
                }
            ) {
                Text("B")
            }
        }}
    }
}

/* ---------------- LOGIC ---------------- */

fun rotateAroundAxis(
    v: Vec3,
    axis: Vec3,
    angle: Float
): Vec3 {

    val c = cos(angle)
    val s = sin(angle)

    val dot =
        v.x * axis.x +
                v.y * axis.y +
                v.z * axis.z

    return Vec3(

        v.x * c +
                (axis.y * v.z - axis.z * v.y) * s +
                axis.x * dot * (1 - c),

        v.y * c +
                (axis.z * v.x - axis.x * v.z) * s +
                axis.y * dot * (1 - c),

        v.z * c +
                (axis.x * v.y - axis.y * v.x) * s +
                axis.z * dot * (1 - c)
    )
}

suspend fun rotateLayer(
    cubelets: SnapshotStateList<Cubelet>,

    axis: Vec3,
    layer: Float,
    dir: Float,

    onStart: () -> Unit,
    onStep: (Float) -> Unit,
    onEnd: () -> Unit
) {

    onStart()

    val steps = 20

    val step =
        (PI.toFloat() / 2f) /
                steps *
                dir

    var angle = 0f

    repeat(steps) {

        angle += step

        onStep(angle)

        delay(16)
    }

    val finalAngle =
        dir * PI.toFloat() / 2f

    cubelets.forEach {

        if (
            onLayer(
                it.pos,
                axis,
                layer
            )
        ) {

            it.pos =
                snap(
                    rotateVec(
                        it.pos,
                        axis,
                        finalAngle
                    )
                )

            it.axisX =
                rotateAroundAxis(
                    it.axisX,
                    axis,
                    finalAngle
                )

            it.axisY =
                rotateAroundAxis(
                    it.axisY,
                    axis,
                    finalAngle
                )

            it.axisZ =
                rotateAroundAxis(
                    it.axisZ,
                    axis,
                    finalAngle
                )
        }
    }

    onEnd()
}

fun onLayer(
    pos: Vec3,
    axis: Vec3,
    layer: Float
): Boolean {

    return when {

        axis.x != 0f ->
            pos.x == layer

        axis.y != 0f ->
            pos.y == layer

        else ->
            pos.z == layer
    }
}

fun rotateVec(
    v: Vec3,
    axis: Vec3,
    angle: Float
): Vec3 {

    return rotateAroundAxis(
        v,
        axis,
        angle
    )
}

fun snap(
    v: Vec3
): Vec3 {

    fun s(x: Float) =
        when {

            x > 0.5f -> 1f

            x < -0.5f -> -1f

            else -> 0f
        }

    return Vec3(
        s(v.x),
        s(v.y),
        s(v.z)
    )
}

fun rotateDrag(dx: Float, dy: Float, rotY: Float): Pair<Float, Float> {

    val c = cos(-rotY)
    val s = sin(-rotY)

    val lx = dx * c - dy * s
    val ly = dx * s + dy * c

    return Pair(lx, ly)
}

/* ---------------- DATA ---------------- */

data class Vec3(
    var x: Float,
    var y: Float,
    var z: Float
)

data class Cubelet(
    var pos: Vec3,

    var up: Color?,
    var down: Color?,
    var left: Color?,
    var right: Color?,
    var front: Color?,
    var back: Color?,

    var axisX: Vec3 = Vec3(1f, 0f, 0f),
    var axisY: Vec3 = Vec3(0f, 1f, 0f),
    var axisZ: Vec3 = Vec3(0f, 0f, 1f),
)

data class Face(
    val verts: List<Vec3>,
    val normal: Vec3,
    val color: Color
)

data class HitFace(
    val axis: Vec3,
    val layer: Float
)

data class VisibleFace(
    val screenCenter: Offset,
    val normal: Vec3,
    val layer: Float
)
