package com.quicklydone.nt.common

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
import com.quicklydone.nt.cube222.CubeRenderer.createInitialCubelets
import com.quicklydone.nt.cube222.Cubelet
import com.quicklydone.nt.cube222.InputCube

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