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

            // =====================================================
            // START
            // =====================================================

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

            // =====================================================
            // DRAG
            // =====================================================

            onDrag = { change, drag ->

                change.consume()

                val dx = drag.x
                val dy = drag.y

                val cell =
                    state.selectedCell

                // -------------------------------------------------
                // rotate whole cube
                // -------------------------------------------------

                if (cell == null) {

                    state.rotateAll(dx, dy)

                    return@detectDragGestures
                }

                if (state.dragLocked) {
                    return@detectDragGestures
                }

                val swipe =  InputCube222.detectFaceSwipe(
                    face = cell.face,
                    dx = dx,
                    dy = dy,
                    yaw = state.yaw,
                    pitch = state.pitch
                )

                val move =
                    mapInputToRotation222(
                        cell,
                        swipe
                    )


                state.startRotation(
                    move.axis,
                    move.layer,
                    move.dir
                )


                state.dragLocked = true
            },

            // =====================================================
            // END
            // =====================================================

            onDragEnd = {

                state.selectedCell = null
                state.dragStart = null
                state.dragLocked = false
            }
        )
    }
}