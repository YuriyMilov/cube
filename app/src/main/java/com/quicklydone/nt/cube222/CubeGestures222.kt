package com.quicklydone.nt.cube222

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntSize
import com.quicklydone.nt.common.GestureState222

fun Modifier.cubeGestures222(
    state: GestureState222,
    canvasSize: IntSize
): Modifier {

    return pointerInput(state) {

        detectDragGestures(

            onDragStart = { offset ->

                state.dragStart = offset
                state.dragLocked = false

                state.selectedCell = InputCube222.pickCell(
                    touch = offset,
                    yaw = state.yaw,
                    pitch = state.pitch,
                    w = canvasSize.width.toFloat(),
                    h = canvasSize.height.toFloat()
                )
            },

            onDrag = { change, drag ->

                change.consume()

                val dx = drag.x
                val dy = drag.y

                val cell = state.selectedCell

                // ROTATE WHOLE CUBE
                if (cell == null) {
                    state.rotateAll(dx, dy)
                    return@detectDragGestures
                }

                if (state.dragLocked) return@detectDragGestures

                val swipe = InputCube222.detectFaceSwipe(
                    face = cell.face,
                    dx = dx,
                    dy = dy,
                    yaw = state.yaw,
                    pitch = state.pitch
                )

                val move = mapInputToRotation222(cell, swipe)

                // 👉 запускаем поворот + передаём имя хода
                state.startRotation(
                    move.axis,
                    move.layer,
                    move.dir,
                    //move.name
                )

                state.dragLocked = true
            },

            onDragEnd = {

                state.selectedCell = null
                state.dragStart = null
                state.dragLocked = false
            }
        )
    }
}