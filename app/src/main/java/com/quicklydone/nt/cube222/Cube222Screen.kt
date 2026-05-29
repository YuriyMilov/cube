package com.quicklydone.nt.cube222

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
import com.quicklydone.nt.animation.anima
import com.quicklydone.nt.animation.rotateLayer222
import com.quicklydone.nt.common.GestureState222
import com.quicklydone.nt.common.TopBar
import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.cube.CubeConfig
import com.quicklydone.nt.cube.CubeFactory.createCubelets
import com.quicklydone.nt.cube.rememberCubelets
import com.quicklydone.nt.cube_new.ArrowDirNew
import com.quicklydone.nt.cube_new.CubeRenderer222
import com.quicklydone.nt.cube_new.FaceMarkerNew
import com.quicklydone.nt.cube_new.SideNew
import com.quicklydone.nt.cube_new.VisibleFaceNew
import com.quicklydone.nt.solver.Arrows222
import com.quicklydone.nt.solver.Moves222
import com.quicklydone.nt.solver.Solver222
import kotlinx.coroutines.launch

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

    var animLayer by remember {
        mutableFloatStateOf(0f)
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

        markers.clear()

        markers += FaceMarkerNew(
            side = SideNew.RIGHT, color = Color.White, radius = 24f
        )

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
                    animLayer = layer
                },

                onStep = {
                    animAngle = it
                },

                onEnd = {

                    animAxis = null
                    animLayer = 0f
                    animAngle = 0f

                    markers.clear()

                    // 👇 показать следующий хинт автоматически
                    Solver222.currentStep++

                    Solver222.showNextHint(
                        markers
                    )
                })
        }
    }

    val gestureState = remember {

        GestureState222(

            rotateAll = { dx, dy ->

                rotY += dx * 0.01f
                rotX -= dy * 0.01f
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    fun jumpRotation(
        axis: Vec3, layer: Float, dir: Float
    ) {

        if (animAxis != null) return

        scope.launch {

            anima(
                cubelets = cubelets, axis = axis, layer = layer, dir = dir,

                onStart = {

                    animAxis = axis
                    animLayer = layer
                },

                onStep = {
                    animAngle = it
                },

                onEnd = {

                    animAxis = null
                    animLayer = 0f
                    animAngle = 0f
                })
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {

        TopBar(
            goMenu = goMenu, onReset = ::resetCube//, goMove = ::onMove
        )

        Row {
            Button(
                onClick = {
                    Moves222.test(
                        gestureState,// cubelets = cubelets,
                        rotate = ::jumpRotation
                    )
                    Solver222.getSolution()

                }) {
                Text("Scramble")
            }

            Button(
                onClick = {

                    Solver222.showNextHint(
                        markers
                    )
                    // Solver222.currentStep++

                }
            ) {
                Text("SOLVE")
            }
        }

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
                    animLayer = animLayer,
                    animAngle = animAngle,
                    //visibleFaces = visibleFaces,
                    drawScope = this,
                    markers = markers   // 👈 ВОТ ЭТО
                )

                /* InputCube222.drawInputCube(
                      drawScope = this, yaw = rotY, pitch = rotX, w = size.width, h = size.height
                  )*/

            }


        }

        Row {
            Button(
                onClick = {
                    Arrows222.addRingHintR(markers)
                }
            ) {
                Text("R")
            }

            Button(
                onClick = {
                    Arrows222.addRingHintRPrime(markers)
                }
            ) {
                Text("R'")
            }
            Button(
                onClick = {
                    Arrows222.addRingHintL(markers)
                }
            ) {
                Text("L")
            }

            Button(
                onClick = {
                    Arrows222.addRingHintLPrime(markers)
                }
            ) {
                Text("L'")
            }
            Button(
                onClick = {
                    Moves222.upa(rotate = ::startRotation)

                }) {
                Text("Move")
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
        ///////////////////////////////


        Row {
            Button(
                onClick = {
                    Arrows222.addRingHintU(markers)
                    // Solver222.upa(
                    //    rotate = ::startRotation
                    //)
                }) {
                Text("U")
            }
            Button(
                onClick = {
                    Arrows222.addRingHintUPrime(markers)
                    //Solver222.upb(
                    //   rotate = ::startRotation
                    //)
                }) {
                Text("U'")
            }
            Button(
                onClick = {
                    Arrows222.addRingHintD(markers)
                }
            ) {
                Text("D")
            }

            Button(
                onClick = {
                    Arrows222.addRingHintDPrime(markers)
                }
            ) {
                Text("D'")
            }


            Text(" ")
            Button(
                onClick = {
                    markers.clear()

                    markers += FaceMarkerNew(
                        side = SideNew.RIGHT, color = Color.White, radius = 24f
                    )
                }) {
                Text("Clear")
            }
        }
        ///////////////////////////////

        Row {

            Button(
                onClick = {
                    Arrows222.addRingHintF(markers)
                }
            ) {
                Text("F")
            }

            Button(
                onClick = {
                    Arrows222.addRingHintFPrime(markers)
                }
            ) {
                Text("F'")
            }

            Button(
                onClick = {
                    Arrows222.addRingHintB(markers)
                }
            ) {
                Text("B")
            }

            Button(
                onClick = {
                    Arrows222.addRingHintBPrime(markers)
                }
            ) {
                Text("B'")
            }

            Text("  ")


            Button(

                onClick = {

                    markers.clear()

                    markers += FaceMarkerNew(

                        face = InputCube222.Face.RIGHT,
                        // face = InputCube222.Face.FRONT,

                        row = 1, col = 0,

                        //arrow = ArrowDirNew.POS_U,
                        arrow = ArrowDirNew.NEG_V,

                        color = Color.Black,

                        count = 1
                    )
                }) {

                Text("Arrow")
            }
        }
    }
}

