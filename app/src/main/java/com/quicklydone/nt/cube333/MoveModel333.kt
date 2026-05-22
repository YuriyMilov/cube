package com.quicklydone.nt.cube333

import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.cube222.InputCube

// =========================================================
// MOVE MODEL
// =========================================================

data class RotationMove(
    val axis: Vec3,
    val layer: Float,
    val dir: Float
)

// =========================================================
// INPUT -> ROTATION
// =========================================================

fun mapInputToRotation333(
    cell: InputCube333.InputCell,
    swipe: InputCube333.SwipeDirection
): RotationMove {

    return when (cell.face) {

        // =====================================================
        // FRONT
        // =====================================================

        InputCube333.Face.FRONT -> {

            when (swipe) {

                InputCube333.SwipeDirection.RIGHT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = 1f
                    )

                InputCube333.SwipeDirection.LEFT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = -1f
                    )

                InputCube333.SwipeDirection.UP ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = colToLayer(cell.col),
                        dir = -1f
                    )

                InputCube333.SwipeDirection.DOWN ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = colToLayer(cell.col),
                        dir = 1f
                    )
            }
        }

        // =====================================================
        // BACK
        // =====================================================

        InputCube333.Face.BACK -> {

            when (swipe) {

                InputCube333.SwipeDirection.RIGHT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = 1f
                    )

                InputCube333.SwipeDirection.LEFT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = -1f
                    )

                InputCube333.SwipeDirection.UP ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = -colToLayer(cell.col),
                        dir = 1f
                    )

                InputCube333.SwipeDirection.DOWN ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = -colToLayer(cell.col),
                        dir = -1f
                    )
            }
        }

        // =====================================================
        // RIGHT
        // =====================================================

        InputCube333.Face.RIGHT -> {

            when (swipe) {

                InputCube333.SwipeDirection.RIGHT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = 1f
                    )

                InputCube333.SwipeDirection.LEFT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = -1f
                    )

                InputCube333.SwipeDirection.UP ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = rightFaceZLayer(cell.col),
                        dir = 1f
                    )

                InputCube333.SwipeDirection.DOWN ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = rightFaceZLayer(cell.col),
                        dir = -1f
                    )
            }
        }

        // =====================================================
        // LEFT
        // =====================================================

        InputCube333.Face.LEFT -> {

            when (swipe) {

                InputCube333.SwipeDirection.RIGHT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = 1f
                    )

                InputCube333.SwipeDirection.LEFT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = -1f
                    )

                InputCube333.SwipeDirection.UP ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = leftFaceZLayer(cell.col),
                        dir = -1f
                    )

                InputCube333.SwipeDirection.DOWN ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = leftFaceZLayer(cell.col),
                        dir = 1f
                    )
            }
        }

        // =====================================================
        // TOP
        // =====================================================

        InputCube333.Face.TOP -> {

            when (swipe) {

                InputCube333.SwipeDirection.RIGHT ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = topBottomLayer(cell.row),
                        dir = -1f
                    )

                InputCube333.SwipeDirection.LEFT ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = topBottomLayer(cell.row),
                        dir = 1f
                    )

                InputCube333.SwipeDirection.UP ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = leftRightLayer(cell.col),
                        dir = -1f
                    )

                InputCube333.SwipeDirection.DOWN ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = leftRightLayer(cell.col),
                        dir = 1f
                    )
            }
        }

        // =====================================================
        // BOTTOM
        // =====================================================

        InputCube333.Face.BOTTOM -> {

            when (swipe) {

                InputCube333.SwipeDirection.RIGHT ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = topBottomLayer(cell.row),
                        dir = 1f
                    )

                InputCube333.SwipeDirection.LEFT ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = topBottomLayer(cell.row),
                        dir = -1f
                    )

                InputCube333.SwipeDirection.UP ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = leftRightLayer(cell.col),
                        dir = -1f
                    )

                InputCube333.SwipeDirection.DOWN ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = leftRightLayer(cell.col),
                        dir = 1f
                    )
            }
        }
    }
}

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
// HELPERS
// =========================================================

private fun rowToLayer(
    row: Int
): Float {

    return when (row) {
        0 -> -1f
        1 -> 0f
        else -> 1f
    }
}

private fun colToLayer(
    col: Int
): Float {

    return when (col) {
        0 -> -1f
        1 -> 0f
        else -> 1f
    }
}

private fun rightFaceZLayer(
    col: Int
): Float {

    return when (col) {
        0 -> 1f
        1 -> 0f
        else -> -1f
    }
}

private fun leftFaceZLayer(
    col: Int
): Float {

    return when (col) {
        0 -> -1f
        1 -> 0f
        else -> 1f
    }
}

private fun leftRightLayer(
    col: Int
): Float {

    return when (col) {
        0 -> -1f
        1 -> 0f
        else -> 1f
    }
}

private fun topBottomLayer(
    row: Int
): Float {

    return when (row) {
        0 -> 1f
        1 -> 0f
        else -> -1f
    }
}