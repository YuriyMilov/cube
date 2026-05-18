package com.quicklydone.nt.gestures3x3

import android.util.Log
import androidx.compose.ui.geometry.Offset

import com.quicklydone.nt.model3x3.Cube3x3
import com.quicklydone.nt.model3x3.Cube3x3Rotator
import com.quicklydone.nt.model3x3.Move3x3
import com.quicklydone.nt.render3x3.Cube3x3Renderer
import com.quicklydone.nt.render3x3.Face
import com.quicklydone.nt.render3x3.PickResult
import kotlin.math.abs


class CubeGestureController3x3(
    val cube: Cube3x3
) {
    var startCell: Cell? = null
    var currentCell: Cell? = null
    var startYaw: Float = 0f
    var startPitch: Float = 0f
    var touchedPick: PickResult? = null
    var mode: ControlMode? = null

    fun onDragStart(
        offset: Offset, yaw: Float, pitch: Float, w: Float, h: Float
    ) {
        startYaw = yaw
        startPitch = pitch


        touchedPick = Cube3x3Renderer.pickFace(offset, yaw, pitch, w, h)

        startCell = touchedPick?.let {
            getCell(it.face, it.u, it.v)
        }

        //  debugStart = startCell


        currentCell = startCell

        mode = if (touchedPick != null) {
            ControlMode.FACES
        } else {
            ControlMode.CUBE
        }
    }


    fun onDrag(
        offset: Offset,
        dx: Float,
        dy: Float,
        yaw: Float,
        pitch: Float,
        w: Float,
        h: Float,
        onRotateCube: (dx: Float, dy: Float) -> Unit
    ) {
        when (mode) {

            ControlMode.CUBE -> {
                onRotateCube(dx, dy)
            }

            ControlMode.FACES -> {

                val pick = Cube3x3Renderer.pickFace(offset, yaw, pitch, w, h)

                Log.d("qq", "------------> $pick")


                // ❗ фиксируем движение только внутри той же грани, с которой стартовали
                val startFace = touchedPick?.face

                if (pick != null && startFace != null && pick.face == startFace) {
                    currentCell = getCell(pick.face, pick.u, pick.v)
                }
            }

            null -> Unit
        }
    }

    fun onDragEnd(): Move3x3? {

        if (mode != ControlMode.FACES) {
            reset()
            return null
        }

        val start = startCell
        val end = currentCell
        val face = touchedPick?.face

        if (start == null || end == null || face == null) {
            reset()
            return null
        }

        val dRow = end.row - start.row
        val dCol = end.col - start.col

        if (abs(dRow) + abs(dCol) == 0) {
            reset()
            return null
        }

        val move = FaceDragInterpreter3x3.toMove(
            face, start.row, start.col, end.row, end.col
        )

        if (move.face == -1) {
            reset()
            return null
        }

        Cube3x3Rotator.apply(cube, move)

        reset()

        return move
    }

    fun reset() {
        touchedPick = null
        mode = null
        startCell = null
        currentCell = null
    }
}

enum class ControlMode {
    FACES, CUBE
}

data class Cell(val row: Int, val col: Int)

fun getCell(face: Face, u: Float, v: Float): Cell {
    val (nu, nv) = normalizeUV(face, u, v)

    val col = (nu * 3).toInt().coerceIn(0, 2)
    val row = ((1f - nv) * 3).toInt().coerceIn(0, 2)

    return Cell(row, col)
}

fun normalizeUV(face: Face, u: Float, v: Float): Pair<Float, Float> {
    return when (face) {
        Face.FRONT -> u to v
        Face.BACK -> (1f - u) to v
        Face.LEFT -> v to (1f - u)
        Face.RIGHT -> (1f - v) to u
        Face.TOP -> u to (1f - v)
        Face.BOTTOM -> u to v
    }
}