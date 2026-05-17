package com.quicklydone.nt.gesture

import GestureState
import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntSize
import com.quicklydone.nt.input.InputCube
import com.quicklydone.nt.model.Vec3

fun Modifier.cubeGestures(
    state: GestureState,
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

                // выбираем ячейку
                state.selectedCell =

                    InputCube.pickCell(
                        touch = offset,
                        yaw = state.yaw,
                        pitch = state.pitch,
                        w = canvasSize.width.toFloat(),
                        h = canvasSize.height.toFloat()
                    )
                //Log.d("qq", "${state.selectedCell}" )
              //  Log.d("qq", "face (${state.selectedCell?.face})  cell (${state.selectedCell?.row}) -${state.selectedCell?.col}" )

                /*
                state.selectedFace = InputCube.detectFace(
                    touch = offset,
                    yaw = state.yaw,
                    pitch = state.pitch,
                    w = canvasSize.width.toFloat(),
                    h = canvasSize.height.toFloat()
                )

                 */

               // Log.d("qq", "${state.selectedFace}" )
            },

            // =====================================================
            // DRAG
            // =====================================================

            onDrag = { change, drag ->

                change.consume()

                val dx = drag.x
                val dy = drag.y

                val cell = state.selectedCell

                // =====================================================
                // 1. SWIPE IN AIR
                // =====================================================

                if (cell == null) {

                    state.rotateAll(dx, dy)
                    return@detectDragGestures
                }

                // =====================================================
                // 2. FACE SWIPE
                // =====================================================

                // уже обработали
                if (state.dragLocked) {
                    return@detectDragGestures
                }

                val horizontal =
                    kotlin.math.abs(dx) >
                            kotlin.math.abs(dy)

                val swipe = InputCube.detectFaceSwipe(
                    face = cell.face,
                    dx = dx,
                    dy = dy,
                    yaw = state.yaw,
                    pitch = state.pitch
                )


    //val (axis, layer, dir) = mapInputToRotation(cell = cell,dx = dx,dy = dy,horizontal = horizontal)
    // state.startRotation(Vec3(1f, 0f, 0f), layer, dir)

                Log.d("qq", "${state.selectedCell?.face}  ${state.selectedCell?.row}:${state.selectedCell?.col} (${swipe}) ")
                //Log.d("qq", "---------- $${swipe}  {cell.face} layer ${cell.row} dir ${cell.col} swipe  ")



                val (axis, layer, dir) =
                    mapInputToRotation(cell, swipe)

                state.startRotation(axis, layer, dir)

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
fun mapInputToRotation(
    cell: InputCube.InputCell,
    swipe: InputCube.SwipeDirection
): Triple<Vec3, Float, Float> {

    return when (cell.face) {

        // =====================================================
        // FRONT
        // =====================================================

        InputCube.Face.FRONT -> {

            when (swipe) {

                InputCube.SwipeDirection.RIGHT ->
                    Triple(
                        Vec3(0f, 1f, 0f),
                        if (cell.row == 0) -1f else 1f,
                        1f
                    )

                InputCube.SwipeDirection.LEFT ->
                    Triple(
                        Vec3(0f, 1f, 0f),
                        if (cell.row == 0) -1f else 1f,
                        -1f
                    )

                InputCube.SwipeDirection.UP ->
                    Triple(
                        Vec3(1f, 0f, 0f),
                        if (cell.col == 0) -1f else 1f,
                        -1f
                    )

                InputCube.SwipeDirection.DOWN ->
                    Triple(
                        Vec3(1f, 0f, 0f),
                        if (cell.col == 0) -1f else 1f,
                        1f
                    )
            }
        }

        // =====================================================
        // BACK
        // =====================================================

        InputCube.Face.BACK -> {

            when (swipe) {

                InputCube.SwipeDirection.RIGHT ->
                    Triple(
                        Vec3(0f, 1f, 0f),
                        if (cell.row == 0) -1f else 1f,
                        1f
                    )

                InputCube.SwipeDirection.LEFT ->
                    Triple(
                        Vec3(0f, 1f, 0f),
                        if (cell.row == 0) -1f else 1f,
                        -1f
                    )

                InputCube.SwipeDirection.UP ->
                    Triple(
                        Vec3(1f, 0f, 0f),
                        if (cell.col == 0) 1f else -1f,
                        1f
                    )

                InputCube.SwipeDirection.DOWN ->
                    Triple(
                        Vec3(1f, 0f, 0f),
                        if (cell.col == 0) 1f else -1f,
                        -1f
                    )
            }
        }

        // =====================================================
        // RIGHT
        // =====================================================

        InputCube.Face.RIGHT -> {

            when (swipe) {

                InputCube.SwipeDirection.RIGHT ->
                    Triple(
                        Vec3(0f, 1f, 0f),
                        1f,
                        1f
                    )

                InputCube.SwipeDirection.LEFT ->
                    Triple(
                        Vec3(0f, 1f, 0f),
                        1f,
                        -1f
                    )

                InputCube.SwipeDirection.UP ->
                    Triple(
                        Vec3(0f, 0f, 1f),
                        -1f,
                        1f
                    )

                InputCube.SwipeDirection.DOWN ->
                    Triple(
                        Vec3(0f, 0f, 1f),
                        -1f,
                        -1f
                    )
            }
        }

        // =====================================================
        // LEFT
        // =====================================================

        InputCube.Face.LEFT -> {

            when (swipe) {

                InputCube.SwipeDirection.RIGHT ->
                    Triple(
                        Vec3(0f, 1f, 0f),
                        1f,
                        1f
                    )

                InputCube.SwipeDirection.LEFT ->
                    Triple(
                        Vec3(0f, 1f, 0f),
                        1f,
                        -1f
                    )

                InputCube.SwipeDirection.UP ->
                    Triple(
                        Vec3(0f, 0f, 1f),
                        -1f,
                        -1f
                    )

                InputCube.SwipeDirection.DOWN ->
                    Triple(
                        Vec3(0f, 0f, 1f),
                        -1f,
                        1f
                    )
            }
        }

        // =====================================================
        // TOP
        // =====================================================

        InputCube.Face.TOP -> {

            when (swipe) {

                InputCube.SwipeDirection.RIGHT ->
                    Triple(
                        Vec3(0f, 0f, 1f),
                        1f,
                        1f
                    )

                InputCube.SwipeDirection.LEFT ->
                    Triple(
                        Vec3(0f, 0f, 1f),
                        1f,
                        -1f
                    )

                InputCube.SwipeDirection.UP ->
                    Triple(
                        Vec3(1f, 0f, 0f),
                        1f,
                        -1f
                    )

                InputCube.SwipeDirection.DOWN ->
                    Triple(
                        Vec3(1f, 0f, 0f),
                        1f,
                        1f
                    )
            }
        }

        // =====================================================
        // BOTTOM
        // =====================================================

        InputCube.Face.BOTTOM -> {

            when (swipe) {

                InputCube.SwipeDirection.RIGHT ->
                    Triple(
                        Vec3(0f, 0f, 1f),
                        -1f,
                        1f
                    )

                InputCube.SwipeDirection.LEFT ->
                    Triple(
                        Vec3(0f, 0f, 1f),
                        -1f,
                        -1f
                    )

                InputCube.SwipeDirection.UP ->
                    Triple(
                        Vec3(1f, 0f, 0f),
                        -1f,
                        -1f
                    )

                InputCube.SwipeDirection.DOWN ->
                    Triple(
                        Vec3(1f, 0f, 0f),
                        -1f,
                        1f
                    )
            }
        }
    }
}