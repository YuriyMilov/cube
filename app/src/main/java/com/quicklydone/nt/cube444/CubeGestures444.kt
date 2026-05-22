package com.quicklydone.nt.cube444

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntSize
import com.quicklydone.nt.common.GestureState444
import com.quicklydone.nt.cube444.InputCube444
import com.quicklydone.nt.cube444.mapInputToRotation444

fun Modifier.cubeGestures444(
    state: GestureState444,
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

                state.selectedCell =

                    InputCube444.pickCell(
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

                // already handled
                if (state.dragLocked) {
                    return@detectDragGestures
                }

                val swipe =
                    InputCube444.detectFaceSwipe(
                        face = cell.face,
                        dx = dx,
                        dy = dy,
                        yaw = state.yaw,
                        pitch = state.pitch
                    )

                val layerSwipe = InputCube444.buildLayerSwipe(
                    face = cell.face,
                    cell = cell,
                    dx = dx,
                    dy = dy,
                    yaw = state.yaw,
                    pitch = state.pitch
                )



                val move =
                    mapInputToRotation444(cell, swipe)

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
