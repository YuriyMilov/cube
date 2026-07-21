package com.quicklydone.nt.cube222

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import com.quicklydone.nt.animation.rotateLayer222
import com.quicklydone.nt.animation.rotateLayer222TwoLayers
import com.quicklydone.nt.common.GestureState222
import com.quicklydone.nt.common.TopBar
import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.cube.CubeConfig
import com.quicklydone.nt.cube.CubeFactory.createCubelets
import com.quicklydone.nt.cube.rememberCubelets
import com.quicklydone.nt.cube_new.CubeRenderer222
import com.quicklydone.nt.cube_new.CubeletNew
import com.quicklydone.nt.cube_new.FaceMarkerNew
import com.quicklydone.nt.cube_new.SideNew
import com.quicklydone.nt.cube_new.VisibleFaceNew
import com.quicklydone.nt.solver.CubeState222
import com.quicklydone.nt.solver.Moves222
import com.quicklydone.nt.solver.Solver222
import com.quicklydone.nt.solver.Solver2a
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun Cube222Screen(
    goMenu: () -> Unit
) {
    val config = CubeConfig(2)

    val cubelets = rememberCubelets(config)

    var rotX by remember { mutableFloatStateOf(0.8f) }
    var rotY by remember { mutableFloatStateOf(-0.8f) }

    var canvasSize by remember {
        mutableStateOf(IntSize.Zero)
    }

    var animAxis by remember {
        mutableStateOf<Vec3?>(null)
    }

    var animLayers by remember {
        mutableStateOf<List<Float>>(emptyList())
    }

    var animAngle by remember {
        mutableFloatStateOf(0f)
    }

    remember {
        mutableStateListOf<VisibleFaceNew>()
    }

    val scope = rememberCoroutineScope()
    val markers = remember {
        mutableStateListOf<FaceMarkerNew>()
    }

    LaunchedEffect(Unit) {
        markers += FaceMarkerNew(
            side = SideNew.RIGHT, color = Color.White, radius = 24f
        )
    }

    fun resetCube() {
        cubelets.clear()
        cubelets.addAll(
            createCubelets(config)
        )

        rotX = 0.8f
        rotY = -0.8f
        // Solver222.n=0
        Solver222.numLog("")
        markers.clear()
        markers += FaceMarkerNew(
            side = SideNew.RIGHT, color = Color.White, radius = 24f
        )
    }

    fun buildCornersPos(
        cubelets: List<CubeletNew>
    ): IntArray {

        val positions = listOf(

            Vec3(-0.5f, -0.5f, -0.5f), // 0
            Vec3(0.5f, -0.5f, -0.5f), // 1

            Vec3(-0.5f, 0.5f, -0.5f), // 2
            Vec3(0.5f, 0.5f, -0.5f), // 3

            Vec3(-0.5f, -0.5f, 0.5f), // 4
            Vec3(0.5f, -0.5f, 0.5f), // 5

            Vec3(-0.5f, 0.5f, 0.5f), // 6
            Vec3(0.5f, 0.5f, 0.5f)  // 7
        )

        val cornersPos = IntArray(8)

        cubelets.forEach { cube ->

            val posIndex = positions.indexOf(cube.pos)

            cornersPos[posIndex] = cube.id
        }

        return cornersPos
    }

    val positions = listOf(
        Vec3(-0.5f, -0.5f, -0.5f), // 0
        Vec3(0.5f, -0.5f, -0.5f), // 1
        Vec3(-0.5f, 0.5f, -0.5f), // 2
        Vec3(0.5f, 0.5f, -0.5f), // 3
        Vec3(-0.5f, -0.5f, 0.5f), // 4
        Vec3(0.5f, -0.5f, 0.5f), // 5
        Vec3(-0.5f, 0.5f, 0.5f), // 6
        Vec3(0.5f, 0.5f, 0.5f)  // 7
    )

    val cornersPos = IntArray(8)

    cubelets.forEach { cube ->

        val posIndex = positions.indexOf(cube.pos)

        cornersPos[posIndex] = cube.id
    }


    fun dirCode(v: Vec3): Int = when {
        v.x > 0.9f -> 0   // +X
        v.x < -0.9f -> 1  // -X

        v.y > 0.9f -> 2   // +Y
        v.y < -0.9f -> 3  // -Y

        v.z > 0.9f -> 4   // +Z
        else -> 5         // -Z
    }

    fun buildCornerAxes(
        cubelets: List<CubeletNew>
    ): IntArray {

        val result = IntArray(8 * 3)

        cubelets.forEach { cube ->

            val posIndex = positions.indexOf(cube.pos)

            result[posIndex * 3 + 0] = dirCode(cube.axisX)

            result[posIndex * 3 + 1] = dirCode(cube.axisY)

            result[posIndex * 3 + 2] = dirCode(cube.axisZ)
        }

        return result
    }

    fun startRotation(
        axis: Vec3, layer: Float, dir: Float
    ) {

        if (animAxis != null) return

        scope.launch {

            rotateLayer222(
                cubelets = cubelets, axis = axis, layer = layer, dir = dir,

                onStart = {

                    animAxis = axis
                    animLayers = listOf(layer)

                    /*
                                       val cornersPos =  buildCornersPos(cubelets)
                                       Log.d("STATE",cornersPos.joinToString())

                                       val cornersAxes =  buildCornerAxes(cubelets)
                                       Log.d("STATE",cornersAxes.joinToString())

                                      val axes = buildCornerAxes(cubelets)

                                       for (i in 0 until 8) {

                                           Log.d(
                                               "STATE",
                                               "$i  ${axes[i*3]}  ${axes[i*3+1]}  ${axes[i*3+2]}"
                                           )

                                         *//*  Log.d(
                            "AXES",
                            "pos=$i  X=${axes[i*3]}  Y=${axes[i*3+1]}  Z=${axes[i*3+2]}"
                        )*//*
                    }*/


                },

                onStep = {
                    animAngle = it
                },

                onEnd = {

                    val pos = buildCornersPos(cubelets)
                    val ori = buildCornerAxes(cubelets)

                    // Log.d("SOLVER", "POS -> ${pos.joinToString()}")
                    // Log.d("SOLVER", "ORI -> ${ori.joinToString()}")


                    val state222 = CubeState222(
                        cubelets = cubelets,
                        cornersPos = buildCornersPos(cubelets),
                        cornersAxes = buildCornerAxes(cubelets)
                    )


                    Solver222.onCubeChanged(state222)
                    Solver2a.aLog(state222)


                    animAxis = null
                    animLayers = emptyList()
                    animAngle = 0f



                    markers.clear()

                    Solver222.currentStep++

                    //Solver222.showNextHint(cubelets, markers)
                    Solver222.showNextHint(markers)


                })
        }
    }

    val gestureState = remember {

        GestureState222(

            rotateAll = { dx, dy ->

                rotY += dx * 0.01f
                rotX += dy * 0.01f
            },

            startRotation = { axis, layer, dir ->

                startRotation(
                    axis, layer, dir
                )

            }

        )
    }
    gestureState.yaw = rotY
    gestureState.pitch = rotX

    fun startRotation2(
        axis: Vec3,
        layer1: Float,
        layer2: Float,
        dir: Float
    ) {

        if (animAxis != null) return

        scope.launch {

            rotateLayer222TwoLayers(
                cubelets = cubelets,
                axis = axis,
                layer1 = layer1,
                layer2 = layer2,
                dir = dir,

                onStart = {

                    animAxis = axis
                    animLayers = listOf(
                        layer1,
                        layer2
                    )
                },

                onStep = {
                    animAngle = it
                },

                onEnd = {

                    val state222 = CubeState222(
                        cubelets = cubelets,
                        cornersPos = buildCornersPos(cubelets),
                        cornersAxes = buildCornerAxes(cubelets)
                    )

                    Solver222.onCubeChanged(state222)

                    animAxis = null
                    animLayers = emptyList()
                    animAngle = 0f

                    markers.clear()

                    Solver222.currentStep++

                    Solver222.showNextHint(markers)
                }
            )
        }
    }

    suspend fun moveX() {

        rotateLayer222TwoLayers(
            cubelets = cubelets,
            axis = Vec3(1f, 0f, 0f),
            layer1 = 0.5f,
            layer2 = -0.5f,
            dir = -1f,

            onStart = {
                animAxis = Vec3(1f, 0f, 0f)
                animLayers = listOf(0.5f, -0.5f)
            },

            onStep = {
                animAngle = it
            },

            onEnd = {
                animAxis = null
                animLayers = emptyList()
                animAngle = 0f
            }
        )
    }

    suspend fun moveXprim() {

        rotateLayer222TwoLayers(
            cubelets = cubelets,
            axis = Vec3(1f, 0f, 0f),
            layer1 = 0.5f,
            layer2 = -0.5f,
            dir = 1f,

            onStart = {
                animAxis = Vec3(1f, 0f, 0f)
                animLayers = listOf(0.5f, -0.5f)
            },

            onStep = {
                animAngle = it
            },

            onEnd = {
                animAxis = null
                animLayers = emptyList()
                animAngle = 0f
            }
        )
    }


    suspend fun moveY() {

        rotateLayer222TwoLayers(
            cubelets = cubelets,
            axis = Vec3(0f, 1f, 0f),
            layer1 = 0.5f,
            layer2 = -0.5f,
            dir = -1f,

            onStart = {
                animAxis = Vec3(0f, 1f, 0f)
                animLayers = listOf(0.5f, -0.5f)
            },

            onStep = {
                animAngle = it
            },

            onEnd = {
                animAxis = null
                animLayers = emptyList()
                animAngle = 0f
            }
        )
    }
 //test

    suspend fun moveYprim() {

        rotateLayer222TwoLayers(
            cubelets = cubelets,
            axis = Vec3(0f, 1f, 0f),
            layer1 = 0.5f,
            layer2 = -0.5f,
            dir = 1f,

            onStart = {
                animAxis = Vec3(0f, 1f, 0f)
                animLayers = listOf(0.5f, -0.5f)
            },

            onStep = {
                animAngle = it
            },

            onEnd = {
                animAxis = null
                animLayers = emptyList()
                animAngle = 0f
            }
        )
    }


    suspend fun moveZ() {

        rotateLayer222TwoLayers(
            cubelets = cubelets,
            axis = Vec3(0f, 0f, 1f),
            layer1 = 0.5f,
            layer2 = -0.5f,
            dir = -1f,

            onStart = {
                animAxis = Vec3(0f, 0f, 1f)
                animLayers = listOf(0.5f, -0.5f)
            },

            onStep = {
                animAngle = it
            },

            onEnd = {
                animAxis = null
                animLayers = emptyList()
                animAngle = 0f
            }
        )
    }

    suspend fun moveZprim() {

        rotateLayer222TwoLayers(
            cubelets = cubelets,
            axis = Vec3(0f, 0f, 1f),
            layer1 = 0.5f,
            layer2 = -0.5f,
            dir = 1f,

            onStart = {
                animAxis = Vec3(0f, 0f, 1f)
                animLayers = listOf(0.5f, -0.5f)
            },

            onStep = {
                animAngle = it
            },

            onEnd = {
                animAxis = null
                animLayers = emptyList()
                animAngle = 0f
            }
        )
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        TopBar(goMenu = goMenu, onReset = ::resetCube)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()

                    .onSizeChanged {
                        canvasSize = it
                    }

                    .cubeGestures222(
                        state = gestureState, canvasSize = canvasSize
                    )) {
                CubeRenderer222.drawNew(
                    config = config,
                    cubelets = cubelets,
                    rotX = rotX,
                    rotY = rotY,
                    animAxis = animAxis,
                    animLayers = animLayers,
                    animAngle = animAngle,
                    drawScope = this,
                    markers = markers
                )

                /* InputCube222.drawInputCube(
                      drawScope = this, yaw = rotY, pitch = rotX, w = size.width, h = size.height
                  )*/
            }
        }

        suspend fun animateView(
            targetX: Float,
            targetY: Float
        ) {
            val startX = rotX
            val startY = rotY

            val steps = 20

            repeat(steps) { i ->
                val t = (i + 1).toFloat() / steps

                rotX = startX + (targetX - startX) * t
                rotY = startY + (targetY - startY) * t

                delay(26) // ~60 FPS
            }
        }

        suspend fun init() {

            // rotX = 0.8f
            //  rotY = -0.8f

            Solver222.numLog("")
            markers.clear()

            animateView(
                targetX = 0.8f,
                targetY = -0.8f
            )


            val state = CubeState222(
                cubelets,
                cornersPos = buildCornersPos(cubelets),
                cornersAxes = buildCornerAxes(cubelets)
            )

            Solver222.getSolutionRGW2(state)

            Solver222.mm.value
                .split(Regex("\\s+"))
                .filter { it.isNotBlank() }
                .forEach { move ->
                    when (move) {
                        "X" -> moveX()
                        "Y" -> moveY()
                        "Z" -> moveZ()
                        "X'" -> moveXprim()
                        "Y'" -> moveYprim()
                        "Z'" -> moveZprim()
                    }
                }
        }


        Row {


            Button(
                onClick = {
                    var n = 60
                    Solver222.numLog("Scramble depth: ${n}")
                    cubelets.clear()
                    markers.clear()
                    cubelets.addAll(createCubelets(config))
                    Moves222.scramble(cubelets, n)

                }) {
                Text("Scramble")
            }


            Button(
                onClick = {
                    scope.launch(Dispatchers.Default) {

                        init()

                        val state = CubeState222(
                            cubelets,
                            cornersPos = buildCornersPos(cubelets),
                            cornersAxes = buildCornerAxes(cubelets)
                        )

                        Solver222.getSolutionRGW3(state)
                        Solver222.getSolutionRGW4(state)

                        withContext(Dispatchers.Main) {
                            Solver222.showNextHintRGW(
                                cubelets,
                                markers
                            )
                        }
                    }
                }
            ) {
                Text("Solve")
            }
            Text(" ")


            Text(
                "   " + Solver222.logText.value + " \n ",
                color = Color.White
            )
        }

        Row {

            Button(
                onClick = {
                    scope.launch(Dispatchers.Default) {

                        init()

                        val state = CubeState222(
                            cubelets,
                            cornersPos = buildCornersPos(cubelets),
                            cornersAxes = buildCornerAxes(cubelets)
                        )
                        Log.d("SOLVER", "Test  ->  " + state.cornersPos.joinToString())
                        Log.d("SOLVER", "Test  ->  " + state.cornersAxes.joinToString())

                        // Solver222.getSolutionRGW3(state)
                        //  Solver2a.get2a (state)
                        // Solver222.getSolutionRGW4(state)

                        //  Solver2a.applyMoves(state,"R U R' U'")


                        withContext(Dispatchers.Main) {
                            Solver222.showNextHintRGW(
                                cubelets,
                                markers
                            )
                        }
                    }
                }
            ) {
                Text("Test")
            }
            Text(" ")
            Button(
                onClick = {
                    scope.launch(Dispatchers.Default) {
                        init()
                        val state = CubeState222(
                            cubelets,
                            cornersPos = buildCornersPos(cubelets),
                            cornersAxes = buildCornerAxes(cubelets)
                        )
                        Log.d("SOLVER", "INIT " + state.cornersPos.joinToString())
                        Log.d("SOLVER", "INIT " + state.cornersAxes.joinToString())

                    }
                }
            ) {
                Text("Init")
            }
            Text(" ")
            Button(
                onClick = {
                    scope.launch(Dispatchers.Default) {

                        val state = CubeState222(
                            cubelets,
                            cornersPos = buildCornersPos(cubelets),
                            cornersAxes = buildCornerAxes(cubelets)
                        )

                        Solver222.getSolutionRGW(state)

                        withContext(Dispatchers.Main) {
                            Solver222.showNextHintRGW(
                                cubelets,
                                markers
                            )
                        }
                    }
                }
            ) {
                Text("SLV")
            }

            Text(" ")


            Button(
                onClick = {
                    scope.launch(Dispatchers.Default) {

                        val state = CubeState222(
                            cubelets,
                            cornersPos = buildCornersPos(cubelets),
                            cornersAxes = buildCornerAxes(cubelets)
                        )

                        Solver222.getArrows()

                        withContext(Dispatchers.Main) {
                            Solver222.showNextHintRGW(
                                cubelets,
                                markers
                            )
                        }
                    }
                }
            ) {
                Text("Arr")
            }
        }

        Row {
            Button(
                onClick = {
                    scope.launch(Dispatchers.Default) {

                        init()

                        val state = CubeState222(
                            cubelets,
                            cornersPos = buildCornersPos(cubelets),
                            cornersAxes = buildCornerAxes(cubelets)
                        )

                        Solver222.getSolutionRGW3(state)
                        //Solver222.getSolutionRGW4(state)

                        withContext(Dispatchers.Main) {
                            Solver222.showNextHintRGW(
                                cubelets,
                                markers
                            )
                        }
                    }
                }
            ) {
                Text("1st Layer")
            }
            Text(" ")
            Button(
                onClick = {
                    scope.launch(Dispatchers.Default) {
                        val state = CubeState222(
                            cubelets,
                            cornersPos = buildCornersPos(cubelets),
                            cornersAxes = buildCornerAxes(cubelets)
                        )


                        Solver2a.get2a(state)


                        Log.d("SOLVER", "Button(slv2a)  " + state.cornersPos.joinToString())
                        Log.d("SOLVER", "Button(slv2a)  " + state.cornersAxes.joinToString())
                        withContext(Dispatchers.Main) {
                            Solver222.showNextHintRGW(
                                cubelets,
                                markers
                            )
                        }
                    }
                }
            ) {
                Text("slv2a")
            }

        }
    }

}
