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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
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
import com.quicklydone.nt.solver.Algorithm222
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

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

    var customAlgorithmMode by remember { mutableStateOf(false) }
    var firstLayerMode by remember { mutableStateOf(false) }
    var customCase by remember { mutableStateOf("") }
    var firstLayerCase by remember { mutableStateOf("") }
    var customMove by remember { mutableStateOf<String?>(null) }
    var wrongMoveMessage by remember { mutableStateOf("") }
    var manualSolutionLog by remember { mutableStateOf("") }

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
        customAlgorithmMode = false
        firstLayerMode = false
        customCase = ""
        firstLayerCase = ""
        customMove = null
        wrongMoveMessage = ""
        manualSolutionLog = ""
        Solver222.currentStep = 0
        Solver222.solutionMoves.clear()
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

    fun updateCustomCase() {
        val state = CubeState222(
            cubelets = cubelets,
            cornersPos = buildCornersPos(cubelets),
            cornersAxes = buildCornerAxes(cubelets)
        )

        customCase = Algorithm222.detectCase(state)
        val step = Algorithm222.stepFor(state)
        customMove = step?.move
        wrongMoveMessage = ""

        markers.clear()
        if (step != null) {
            Algorithm222.showMoveHint(step.move, markers)
        }

        Log.d("CUSTOM222", "case=$customCase move=${step?.move}")
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
                    wrongMoveMessage = ""

                    if (firstLayerMode) {
                        Solver222.currentStep++
                        if (Solver222.currentStep < Solver222.solutionMoves.size) {
                            Solver222.showNextHint(markers)
                        } else {
                            firstLayerMode = false
                            customAlgorithmMode = true
                            updateCustomCase()
                        }
                    } else if (customAlgorithmMode) {
                        updateCustomCase()
                    } else {
                        Solver222.currentStep++
                        Solver222.showNextHint(markers)
                    }


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

            },

            expectedMove = {
                when {
                    firstLayerMode -> Solver222.solutionMoves.getOrNull(Solver222.currentStep)
                    customAlgorithmMode -> customMove ?: "__NO_CUSTOM_MOVE__"
                    else -> null
                }
            },

            onWrongMove = { actual, expected ->
                wrongMoveMessage = if (expected == "__NO_CUSTOM_MOVE__") {
                    "Set a CUSTOM MOVE first"
                } else {
                    "Wrong move: $actual   Expected: ${expected ?: "—"}"
                }
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

                    if (firstLayerMode) {
                        Solver222.currentStep++
                        if (Solver222.currentStep < Solver222.solutionMoves.size) {
                            Solver222.showNextHint(markers)
                        } else {
                            firstLayerMode = false
                            customAlgorithmMode = true
                            updateCustomCase()
                        }
                    } else if (customAlgorithmMode) {
                        updateCustomCase()
                    } else {
                        Solver222.currentStep++
                        Solver222.showNextHint(markers)
                    }
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

    // Manual teaching set for the second layer.
    // ALG2 is the user's second algorithm; we call it ALG2 here so it
    // is not confused with the B-face rotation buttons.
    val algorithmA = listOf("D", "L", "B", "L'", "B'", "D'")
    val algorithm2 = listOf("L'", "B'", "L", "B", "L", "D'", "B'", "D")
    val algorithmC = listOf(
        "F", "L", "F", "L",
        "F", "L", "F", "L",
        "F", "L", "F", "L",
        "F", "L", "F", "L",
        "L", "R", "R", "U'", "D"
    )

    suspend fun runManualMove(move: String) = suspendCancellableCoroutine<Unit> { cont ->
        val finish = {
            animAxis = null
            animLayers = emptyList()
            animAngle = 0f
            markers.clear()
            Solver222.onCubeChanged(
                CubeState222(
                    cubelets = cubelets,
                    cornersPos = buildCornersPos(cubelets),
                    cornersAxes = buildCornerAxes(cubelets)
                )
            )
            if (cont.isActive) cont.resume(Unit) {}
        }

        val rotate: (Vec3, Float, Float) -> Unit = { axis, layer, dir ->
            scope.launch {
                rotateLayer222(
                    cubelets = cubelets,
                    axis = axis,
                    layer = layer,
                    dir = dir,
                    onStart = {
                        animAxis = axis
                        animLayers = listOf(layer)
                    },
                    onStep = { animAngle = it },
                    onEnd = finish
                )
            }
        }

        when (move) {
            "R" -> rotate(Vec3(1f,0f,0f), 0.5f, -1f)
            "R'" -> rotate(Vec3(1f,0f,0f), 0.5f, 1f)
            "L" -> rotate(Vec3(1f,0f,0f), -0.5f, 1f)
            "L'" -> rotate(Vec3(1f,0f,0f), -0.5f, -1f)
            "U" -> rotate(Vec3(0f,1f,0f), 0.5f, -1f)
            "U'" -> rotate(Vec3(0f,1f,0f), 0.5f, 1f)
            "D" -> rotate(Vec3(0f,1f,0f), -0.5f, 1f)
            "D'" -> rotate(Vec3(0f,1f,0f), -0.5f, -1f)
            "F" -> rotate(Vec3(0f,0f,1f), 0.5f, -1f)
            "F'" -> rotate(Vec3(0f,0f,1f), 0.5f, 1f)
            "B" -> rotate(Vec3(0f,0f,1f), -0.5f, 1f)
            "B'" -> rotate(Vec3(0f,0f,1f), -0.5f, -1f)
            else -> if (cont.isActive) cont.resume(Unit) {}
        }
    }

    fun runManualSequence(name: String, sequence: List<String>) {
        if (animAxis != null) return

        val text = sequence.joinToString(" ")
        manualSolutionLog = if (manualSolutionLog.isBlank()) name else "$manualSolutionLog $name"

        Log.d("ALG222", "USER SOLUTION STEP: $name = $text")
        Log.d("ALG222", "USER SOLUTION SO FAR: $manualSolutionLog")

        scope.launch {
            sequence.forEachIndexed { index, move ->
                Log.d("ALG222", "$name step ${index + 1}/${sequence.size}: $move")
                runManualMove(move)
            }

            val solvedState = CubeState222(
                cubelets = cubelets,
                cornersPos = buildCornersPos(cubelets),
                cornersAxes = buildCornerAxes(cubelets)
            )

            if (Solver222.isFullySolved(solvedState)) {
                val finalLog = "CUSTOM CASE: ${firstLayerCase.ifBlank { customCase }}  SOLUTION: $manualSolutionLog"
                Solver222.numLog(finalLog)
                Log.d("ALG222", finalLog)
            } else {
                Solver222.numLog("CUSTOM CASE: ${firstLayerCase.ifBlank { customCase }}  SOLUTION: $manualSolutionLog")
            }
        }
    }

    suspend fun solveFirstLayerAutomatically() {
        if (animAxis != null) return

        markers.clear()
        wrongMoveMessage = ""
        customAlgorithmMode = false

        val state = CubeState222(
            cubelets = cubelets,
            cornersPos = buildCornersPos(cubelets),
            cornersAxes = buildCornerAxes(cubelets)
        )

        // solve3() searches only with L/L', D/D' and B/B', and its goal is
        // exactly the first assembled layer (isSolved3).
        val firstLayerSolution = withContext(Dispatchers.Default) {
            Solver222.getFirstLayerSolution(state)
        }

        if (firstLayerSolution.isEmpty()) {
            // Empty means the first layer is already assembled, or no path
            // was found. In both cases inspect the current state below.
            val after = CubeState222(
                cubelets = cubelets,
                cornersPos = buildCornersPos(cubelets),
                cornersAxes = buildCornerAxes(cubelets)
            )
            if (!Solver222.isFirstLayerSolved(after)) {
                Solver222.numLog("Solve 1st: solution not found")
                return
            }
        }

        // Execute the solver's moves, one animated move at a time. No arrow
        // hints are used for this stage.
        firstLayerSolution.forEach { move ->
            runManualMove(move)
        }

        val after = CubeState222(
            cubelets = cubelets,
            cornersPos = buildCornersPos(cubelets),
            cornersAxes = buildCornerAxes(cubelets)
        )

        if (!Solver222.isFirstLayerSolved(after)) {
            Solver222.numLog("Solve 1st: first layer was not completed")
            return
        }

        firstLayerCase = Algorithm222.detectCase(after)
        customCase = firstLayerCase
        customAlgorithmMode = true
        markers.clear()
        Solver222.numLog("CUSTOM CASE: $firstLayerCase  SOLUTION: —")
        Log.d("CUSTOM222", "CUSTOM CASE: $firstLayerCase")
    }

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


        // Compact bottom controls. Every button gets an equal share of the
        // row width, so none of the bottom buttons can collapse into a strip.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 4.dp, vertical = 2.dp)
        ) {
            Row(Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.weight(1f).padding(2.dp),
                    onClick = {
                        val n = 60
                        Solver222.numLog("Scramble depth: $n")
                        cubelets.clear()
                        markers.clear()
                        cubelets.addAll(createCubelets(config))
                        Moves222.scramble(cubelets, n)
                        customAlgorithmMode = false
                        firstLayerMode = false
                        customCase = ""
                        firstLayerCase = ""
                        customMove = null
                        wrongMoveMessage = ""
                        manualSolutionLog = ""
                    }
                ) { Text("Scrmbl") }

                Button(
                    modifier = Modifier.weight(1f).padding(2.dp),
                    onClick = {
                        scope.launch(Dispatchers.Default) {
                            // This is the original working 1st-layer flow.
                            // init() prepares the cube orientation/state before
                            // getSolutionRGW3() searches for the arrow solution.
                            init()

                            val state = CubeState222(
                                cubelets,
                                cornersPos = buildCornersPos(cubelets),
                                cornersAxes = buildCornerAxes(cubelets)
                            )

                            Solver222.getSolutionRGW3(state)

                            withContext(Dispatchers.Main) {
                                firstLayerMode = true
                                customAlgorithmMode = false
                                Solver222.currentStep = 0
                                markers.clear()

                                if (Solver222.solutionMoves.isEmpty()) {
                                    firstLayerMode = false
                                    Solver222.numLog("1st layer: solution not found")
                                } else {
                                    Solver222.showNextHint(markers)
                                    Solver222.numLog(
                                        "1st layer: ${Solver222.solutionMoves.joinToString(" \u200b")}"
                                            .replace("\u200b", "")
                                    )
                                }
                            }
                        }
                    }
                ) { Text("Solve") }

                Button(
                    modifier = Modifier.weight(1f).padding(2.dp),
                    onClick = {

                        scope.launch {
                            init()
                            solveFirstLayerAutomatically()
                        }
                    }
                ) { Text("Auto") }
            }

            if (wrongMoveMessage.isNotBlank()) {
                Text(text = wrongMoveMessage, color = Color.Red)
            }

            if (customCase.isNotBlank()) {
                Text(text = "CUSTOM CASE: $customCase", color = Color.White)
            }

            Row(Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.weight(1f).padding(2.dp),
                    onClick = { runManualSequence("6", algorithmA) }
                ) { Text("6") }
                Button(
                    modifier = Modifier.weight(1f).padding(2.dp),
                    onClick = { runManualSequence("7", algorithm2) }
                ) { Text("8") }
                Button(
                    modifier = Modifier.weight(1f).padding(2.dp),
                    onClick = { runManualSequence("15", algorithmC) }
                ) { Text("15") }
            }

            Row(Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.weight(1f).padding(2.dp),
                    onClick = { runManualSequence("B", listOf("B")) }
                ) { Text("B") }
                Button(
                    modifier = Modifier.weight(1f).padding(2.dp),
                    onClick = { runManualSequence("B'", listOf("B'")) }
                ) { Text("B'") }
                Button(
                    modifier = Modifier.weight(1f).padding(2.dp),
                    onClick = { runManualSequence("B2", listOf("B", "B")) }
                ) { Text("B2") }
            }

            Text(
                text = "SOLUTION: ${manualSolutionLog.ifBlank { "—" }}",
                color = Color.Yellow
            )

            Text(
                text = Solver222.logText.value,
                color = Color.LightGray
            )
        }
    }

}
