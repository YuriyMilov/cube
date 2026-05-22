package com.quicklydone.nt.common

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.quicklydone.nt.cube222.InputCube
import com.quicklydone.nt.cube333.InputCube333
import com.quicklydone.nt.cube444.InputCube444
import com.quicklydone.nt.cube_new.CubeRendererNew.createInitialCubelets
import com.quicklydone.nt.cube_new.CubeletNew

@Composable
fun TopBar(
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
fun rememberCubelets() =
    remember {
        mutableStateListOf<CubeletNew>().apply {
            addAll(createInitialCubelets())
        }
    }

// ======================================================
// GESTURE STATE
// ======================================================

@Immutable
data class GestureState222(

    var selectedCell: InputCube.InputCell? = null,
    var selectedFace: InputCube.Face? = null,

    var dragStart: Offset? = null,
    var dragLocked: Boolean = false,

    val rotateAll: (Float, Float) -> Unit,
    val startRotation: (Vec3, Float, Float) -> Unit,

    var yaw: Float = 0f,
    var pitch: Float = 0f,
)

@Immutable
data class GestureState333(

    var selectedCell: InputCube333.InputCell? = null,
    var selectedFace: InputCube333.Face? = null,

    var dragStart: Offset? = null,
    var dragLocked: Boolean = false,

    val rotateAll: (Float, Float) -> Unit,
    val startRotation: (Vec3, Float, Float) -> Unit,

    var yaw: Float = 0f,
    var pitch: Float = 0f,
)

@Immutable
data class GestureState444(

    var selectedCell: InputCube444.InputCell? = null,
    var selectedFace: InputCube444.Face? = null,

    var dragStart: Offset? = null,
    var dragLocked: Boolean = false,

    val rotateAll: (Float, Float) -> Unit,
    val startRotation: (Vec3, Float, Float) -> Unit,

    var yaw: Float = 0f,
    var pitch: Float = 0f,
)



fun cubeCenter(
    pos: Vec3
): Vec3 {

    val spacing = 2.05f

    return Vec3(
        pos.x * spacing,
        pos.y * spacing,
        pos.z * spacing
    )
}


fun onLayer(
    pos: Vec3,
    axis: Vec3,
    layer: Float
): Boolean {

    val value = when {
        kotlin.math.abs(axis.x) > 0.9f -> pos.x
        kotlin.math.abs(axis.y) > 0.9f -> pos.y
        else -> pos.z
    }

    Log.d(
        "qq",
        "value=$value layer=$layer diff=${kotlin.math.abs(value - layer)}"
    )

    return kotlin.math.abs(value - layer) < 0.01f
}