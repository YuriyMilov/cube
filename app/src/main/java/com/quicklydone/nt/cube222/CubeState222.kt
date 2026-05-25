package com.quicklydone.nt.cube222

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.cube_new.CubeletNew
import com.quicklydone.nt.animation.rotateLayer222
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
class CubeState222(
    val cubelets: SnapshotStateList<CubeletNew>,
    val scope: CoroutineScope
) {

    // =========================
    // INPUT STATE
    // =========================

    var selectedCell: InputCube222.InputCell? = null

    var dragStart by mutableStateOf<Offset?>(null)
    var dragLocked by mutableStateOf(false)

    // =========================
    // ANIMATION STATE
    // =========================

    var animAxis by mutableStateOf<Vec3?>(null)
        private set

    var animLayer by mutableFloatStateOf(0f)
        private set

    var animAngle by mutableFloatStateOf(0f)
        private set

    val isRotating: Boolean
        get() = animAxis != null

    // =========================
    // ROTATION
    // =========================

    fun startRotation(axis: Vec3, layer: Float, dir: Float) {

        if (isRotating) return

        scope.launch {

            rotateLayer222(
                cubelets = cubelets,
                axis = axis,
                layer = layer,
                dir = dir,

                onStart = {
                    animAxis = axis
                    animLayer = layer
                    animAngle = 0f
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

    // =========================
    // INPUT
    // =========================

    fun onDragStart(
        offset: Offset,
        yaw: Float,
        pitch: Float,
        canvasW: Float,
        canvasH: Float,
        //pickCell: (Offset, Float, Float, Float, Float) -> InputCube222.InputCell?
    ) {
        dragStart = offset
        dragLocked = false

        //selectedCell = pickCell(offset, yaw, pitch, canvasW, canvasH)
    }

    fun lockDrag() {
        dragLocked = true
    }

    fun resetDrag() {
        dragStart = null
        dragLocked = false
        selectedCell = null
    }
}