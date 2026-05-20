package com.quicklydone.nt.cube444

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.quicklydone.nt.animation.rotateLayer
import com.quicklydone.nt.animation.rotateLayer444
import com.quicklydone.nt.common.GestureState
import com.quicklydone.nt.common.TopBar
import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.common.rememberCubelets
import com.quicklydone.nt.common.rememberCubelets444
import com.quicklydone.nt.cube.CubeConfigs
import com.quicklydone.nt.cube.CubeFactory444
import com.quicklydone.nt.cube.rememberCubelets444
//import com.quicklydone.nt.cube.rememberCubelets
import com.quicklydone.nt.cube222.cubeGestures
import kotlinx.coroutines.launch

@Composable
fun Cube444Screen(
    goMenu: () -> Unit
) {

    val config = CubeConfigs.Cube444

    val cubelets = rememberCubelets444(config)

    var rotX by remember { mutableStateOf(0.8f) }
    var rotY by remember { mutableStateOf(-0.8f) }

    var canvasSize by remember {
        mutableStateOf(IntSize.Zero)
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

    val visibleFaces = remember {
        mutableStateListOf<VisibleFace>()
    }

    val scope = rememberCoroutineScope()

    fun resetCube() {

        cubelets.clear()

        cubelets.addAll(
            CubeFactory444
                .createCubelets444(config)
        )

        rotX = 0.8f
        rotY = -0.8f
    }

    fun startRotation(
        axis: Vec3,
        layer: Float,
        dir: Float
    ) {

        if (animAxis != null) return

        scope.launch {

            rotateLayer444(
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
                    animLayer = 0f
                    animAngle = 0f
                }
            )
        }
    }

    val gestureState = remember {

        GestureState(

            rotateAll = { dx, dy ->

                rotY += dx * 0.01f
                rotX -= dy * 0.01f
            },

            startRotation = { axis, layer, dir ->

                startRotation(
                    axis,
                    layer,
                    dir
                )
            }
        )
    }

    gestureState.yaw = rotY
    gestureState.pitch = rotX

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
    ) {

        TopBar(
            goMenu = goMenu,
            onReset = ::resetCube
        )

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

                    .cubeGestures(
                        state = gestureState,
                        canvasSize = canvasSize
                    )
            ) {

                CubeRenderer444.draw(
                    cubelets = cubelets,

                    rotX = rotX,
                    rotY = rotY,

                    animAxis = animAxis,
                    animLayer = animLayer,
                    animAngle = animAngle,

                    visibleFaces = visibleFaces,

                    drawScope = this
                )



                     /*InputCube333.drawInputCube(
                                   drawScope = this,
                                   yaw = rotY,
                                   pitch = rotX,
                                   w = size.width,
                                   h = size.height
                               )*/


            }
        }
    }
}