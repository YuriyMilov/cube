package com.quicklydone.nt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.Canvas
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.quicklydone.nt.animation.rotateLayer
import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.gesture.cubeGestures
import com.quicklydone.nt.cube222.InputCube
import com.quicklydone.nt.render.CubeRenderer
import com.quicklydone.nt.render.CubeRenderer.createInitialCubelets
import com.quicklydone.nt.render.Cubelet
import com.quicklydone.nt.render.VisibleFace
import kotlinx.coroutines.launch



// ======================================================
// 2x2 SCREEN
// ======================================================

@Composable
fun Cube222Screen(
    goMenu: () -> Unit
) {

    val cubelets = rememberCubelets()

    var rotX by remember { mutableStateOf(0.8f) }
    var rotY by remember { mutableStateOf(-0.8f) }

    var canvasSize by remember { mutableStateOf(IntSize.Zero) }

    var animAxis by remember { mutableStateOf<Vec3?>(null) }
    var animLayer by remember { mutableStateOf(0f) }
    var animAngle by remember { mutableStateOf(0f) }

    val visibleFaces = remember {
        mutableStateListOf<VisibleFace>()
    }

    val scope = rememberCoroutineScope()

    fun resetCube() {

        cubelets.clear()
        cubelets.addAll(createInitialCubelets())

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
                startRotation(axis, layer, dir)
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

                CubeRenderer.draw(
                    cubelets = cubelets,
                    rotX = rotX,
                    rotY = rotY,
                    animAxis = animAxis,
                    animLayer = animLayer,
                    animAngle = animAngle,
                    visibleFaces = visibleFaces,
                    drawScope = this
                )

               /* InputCube.drawInputCube(
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

@Composable
private fun TopBar(
    goMenu: () -> Unit,
    onReset: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Button(onClick = goMenu) {
            Text("MENU")
        }

        Button(onClick = onReset) {
            Text("RESET")
        }
    }
}

@Composable
private fun rememberCubelets() =
    remember {
        mutableStateListOf<Cubelet>().apply {
            addAll(createInitialCubelets())
        }
    }

// ======================================================
// GESTURE STATE
// ======================================================

@Immutable
data class GestureState(

    var selectedCell: InputCube.InputCell? = null,
    var selectedFace: InputCube.Face? = null,

    var dragStart: Offset? = null,
    var dragLocked: Boolean = false,

    val rotateAll: (Float, Float) -> Unit,
    val startRotation: (Vec3, Float, Float) -> Unit,

    var yaw: Float = 0f,
    var pitch: Float = 0f,
)