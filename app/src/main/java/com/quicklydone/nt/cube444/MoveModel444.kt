package com.quicklydone.nt.cube444

import android.util.Log
import com.quicklydone.nt.common.Vec3

// =========================================================
// MOVE MODEL
// =========================================================

data class RotationMove444(
    val axis: Vec3,
    val layer: Float,
    val dir: Float
)

// =========================================================
// AXES
// =========================================================

private val X_AXIS =
    Vec3(1f, 0f, 0f)

private val Y_AXIS =
    Vec3(0f, 1f, 0f)

private val Z_AXIS =
    Vec3(0f, 0f, 1f)

// =========================================================
// 4x4 LAYERS
// =========================================================

private val LAYERS = listOf(
    -1.5f,
    -0.5f,
    0.5f,
    1.5f
)

private fun rowToLayer(
    row: Int
): Float {

    return LAYERS[row.coerceIn(0, 3)]
}

private fun colToLayer(
    col: Int
): Float {

    return LAYERS[col.coerceIn(0, 3)]
}

// =========================================================
// INPUT -> ROTATION
// =========================================================

fun mapInputToRotation444(
    cell: InputCube444.InputCell,
    swipe: InputCube444.SwipeDirection
): RotationMove444 {

    return when (cell.face) {

        // =====================================================
        // FRONT
        // =====================================================

        InputCube444.Face.FRONT -> {

            when (swipe) {

                InputCube444.SwipeDirection.RIGHT ->
                    RotationMove444(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = 1f
                    )

                InputCube444.SwipeDirection.LEFT ->
                    RotationMove444(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = -1f
                    )

                InputCube444.SwipeDirection.UP ->
                    RotationMove444(
                        axis = X_AXIS,
                        layer = colToLayer(cell.col),
                        dir = -1f
                    )

                InputCube444.SwipeDirection.DOWN ->
                    RotationMove444(
                        axis = X_AXIS,
                        layer = colToLayer(cell.col),
                        dir = 1f
                    )
            }
        }

        // =====================================================
        // BACK
        // =====================================================

        InputCube444.Face.BACK -> {

            when (swipe) {

                InputCube444.SwipeDirection.RIGHT ->
                    RotationMove444(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = 1f
                    )

                InputCube444.SwipeDirection.LEFT ->
                    RotationMove444(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = -1f
                    )

                InputCube444.SwipeDirection.UP ->
                    RotationMove444(
                        axis = X_AXIS,
                        layer = -colToLayer(cell.col),
                        dir = 1f
                    )

                InputCube444.SwipeDirection.DOWN ->
                    RotationMove444(
                        axis = X_AXIS,
                        layer = -colToLayer(cell.col),
                        dir = -1f
                    )
            }
        }

        // =====================================================
        // RIGHT
        // =====================================================

        InputCube444.Face.RIGHT -> {

            when (swipe) {

                InputCube444.SwipeDirection.RIGHT ->
                    RotationMove444(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = 1f
                    )

                InputCube444.SwipeDirection.LEFT ->
                    RotationMove444(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = -1f
                    )

                InputCube444.SwipeDirection.UP ->
                    RotationMove444(
                        axis = Z_AXIS,
                        layer = -colToLayer(cell.col),
                        dir = 1f
                    )

                InputCube444.SwipeDirection.DOWN ->
                    RotationMove444(
                        axis = Z_AXIS,
                        layer = -colToLayer(cell.col),
                        dir = -1f
                    )
            }
        }

        // =====================================================
        // LEFT
        // =====================================================

        InputCube444.Face.LEFT -> {
            Log.d("qq","${cell} ")

            when (swipe) {

                InputCube444.SwipeDirection.RIGHT ->
                    RotationMove444(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = 1f
                    )

                InputCube444.SwipeDirection.LEFT ->
                    RotationMove444(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = -1f
                    )

                InputCube444.SwipeDirection.UP ->
                    RotationMove444(
                        axis = Z_AXIS,
                        layer = colToLayer(cell.col),
                        dir = -1f
                    )

                InputCube444.SwipeDirection.DOWN ->
                    RotationMove444(
                        axis = Z_AXIS,
                        layer = colToLayer(cell.col),
                        dir = 1f
                    )
            }
        }

        // =====================================================
        // TOP
        // =====================================================

        InputCube444.Face.TOP -> {

            when (swipe) {

                InputCube444.SwipeDirection.RIGHT ->
                    RotationMove444(
                        axis = Z_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = -1f
                    )

                InputCube444.SwipeDirection.LEFT ->
                    RotationMove444(
                        axis = Z_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = 1f
                    )

                InputCube444.SwipeDirection.UP ->
                    RotationMove444(
                        axis = X_AXIS,
                        layer = colToLayer(cell.col),
                        dir = -1f
                    )

                InputCube444.SwipeDirection.DOWN ->
                    RotationMove444(
                        axis = X_AXIS,
                        layer = colToLayer(cell.col),
                        dir = 1f
                    )
            }
        }

        // =====================================================
        // BOTTOM
        // =====================================================

        InputCube444.Face.BOTTOM -> {

            when (swipe) {

                InputCube444.SwipeDirection.RIGHT ->
                    RotationMove444(
                        axis = Z_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = 1f
                    )

                InputCube444.SwipeDirection.LEFT ->
                    RotationMove444(
                        axis = Z_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = -1f
                    )

                InputCube444.SwipeDirection.UP ->
                    RotationMove444(
                        axis = X_AXIS,
                        layer = colToLayer(cell.col),
                        dir = -1f
                    )

                InputCube444.SwipeDirection.DOWN ->
                    RotationMove444(
                        axis = X_AXIS,
                        layer = colToLayer(cell.col),
                        dir = 1f
                    )
            }
        }
    }
}