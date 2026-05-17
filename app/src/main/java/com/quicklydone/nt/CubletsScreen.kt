import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.quicklydone.nt.gesture.cubeGestures
import com.quicklydone.nt.input.InputCube
import com.quicklydone.nt.model.Cubelet
import com.quicklydone.nt.model.Vec3
import com.quicklydone.nt.model.createInitialCubelets
import com.quicklydone.nt.render.CubeRenderer
import com.quicklydone.nt.render.VisibleFace
import kotlinx.coroutines.launch

@Composable
fun CubletsScreen(
    goMenu: () -> Unit
) {

    // -------------------------
    // STATE
    // -------------------------

    val cubelets = remember {
        mutableStateListOf<Cubelet>().apply {
            addAll(createInitialCubelets())
        }
    }

    var rotX by remember { mutableStateOf(0.8f) }
    var rotY by remember { mutableStateOf(-0.8f) }

    var canvasSize by remember { mutableStateOf(IntSize.Zero) }

    var animAxis by remember { mutableStateOf<Vec3?>(null) }
    var animLayer by remember { mutableStateOf(0f) }
    var animAngle by remember { mutableStateOf(0f) }
    val visibleFaces = remember { mutableStateListOf<VisibleFace>() }
    val scope = rememberCoroutineScope()

    // -------------------------
    // ROTATION ENGINE
    // -------------------------

    fun startRotation(axis: Vec3, layer: Float, dir: Float) {

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

    // -------------------------
    // GESTURE STATE (CLEAN)
    // -------------------------

    val state = remember {

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

    state.yaw = rotY
    state.pitch = rotX




    // -------------------------
    // UI
    // -------------------------

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
    ) {

        // -------------------------
        // TOP BAR
        // -------------------------

        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(onClick = goMenu) {
                Text("MENU")
            }

            Button(onClick = {
                cubelets.clear()
                cubelets.addAll(createInitialCubelets())

                rotX = 0.8f
                rotY = -0.8f
            }) {
                Text("RESET")
            }
        }

        // -------------------------
        // CANVAS
        // -------------------------

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
                        state = state,
                        canvasSize = canvasSize
                    )
            ) {

               // Log.d("qq", "yaw = ${rotY} pitch = ${rotX}")



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




                /*


                                              InputCube.drawInputCube(
                                                  drawScope = this,
                                                  yaw = rotY,
                                                  pitch = rotX,
                                                  w = size.width,
                                                  h = size.height
                                              )



                             */




            }
        }

        // -------------------------
        // BUTTONS
        // -------------------------

        /*Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            Row(Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                Button(onClick = { startRotation(Vec3(1f, 0f, 0f), -1f, -1f) }) { Text("L") }
                Button(onClick = { startRotation(Vec3(1f, 0f, 0f), -1f, 1f) }) { Text("L'") }
                Button(onClick = { startRotation(Vec3(1f, 0f, 0f), 1f, 1f) }) { Text("R") }
                Button(onClick = { startRotation(Vec3(1f, 0f, 0f), 1f, -1f) }) { Text("R'") }
            }

            Row(Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                Button(onClick = { startRotation(Vec3(0f, 1f, 0f), 1f, 1f) }) { Text("U") }
                Button(onClick = { startRotation(Vec3(0f, 1f, 0f), 1f, -1f) }) { Text("U'") }
                Button(onClick = { startRotation(Vec3(0f, 1f, 0f), -1f, -1f) }) { Text("D") }
                Button(onClick = { startRotation(Vec3(0f, 1f, 0f), -1f, 1f) }) { Text("D'") }
            }

            Row(Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                Button(onClick = { startRotation(Vec3(0f, 0f, 1f), 1f, 1f) }) { Text("F") }
                Button(onClick = { startRotation(Vec3(0f, 0f, 1f), 1f, -1f) }) { Text("F'") }
                Button(onClick = { startRotation(Vec3(0f, 0f, 1f), -1f, -1f) }) { Text("B") }
                Button(onClick = { startRotation(Vec3(0f, 0f, 1f), -1f, 1f) }) { Text("B'") }
            }
        }*/
    }
}


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