package com.quicklydone.nt.cube222

import com.quicklydone.nt.common.Vec3

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

fun mapInputToRotation222(
    cell: InputCube222.InputCell,
    swipe: InputCube222.SwipeDirection
): RotationMove {

    return when (cell.face) {

        // =====================================================
        // FRONT
        // =====================================================

        InputCube222.Face.FRONT -> {

            when (swipe) {

                InputCube222.SwipeDirection.RIGHT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = -rowToLayer222(cell.row),
                        dir = 1f
                    )

                InputCube222.SwipeDirection.LEFT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = -rowToLayer222(cell.row),
                        dir = -1f
                    )

                InputCube222.SwipeDirection.UP ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = colToLayer222(cell.col),
                        dir = -1f
                    )

                InputCube222.SwipeDirection.DOWN ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = colToLayer222(cell.col),
                        dir = 1f
                    )
            }
        }

        // =====================================================
        // BACK
        // =====================================================

        InputCube222.Face.BACK -> {

            when (swipe) {

                InputCube222.SwipeDirection.RIGHT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = -rowToLayer222(cell.row),
                        dir = 1f
                    )

                InputCube222.SwipeDirection.LEFT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = -rowToLayer222(cell.row),
                        dir = -1f
                    )

                InputCube222.SwipeDirection.UP ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = -colToLayer222(cell.col),
                        dir = 1f
                    )

                InputCube222.SwipeDirection.DOWN ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = -colToLayer222(cell.col),
                        dir = -1f
                    )
            }
        }

        // =====================================================
        // RIGHT
        // =====================================================

        InputCube222.Face.RIGHT -> {

            when (swipe) {

                InputCube222.SwipeDirection.RIGHT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = -rowToLayer222(cell.row),
                        dir = 1f
                    )

                InputCube222.SwipeDirection.LEFT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = -rowToLayer222(cell.row),
                        dir = -1f
                    )

                InputCube222.SwipeDirection.UP ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = rightFaceZLayer222(cell.col),
                        dir = 1f
                    )

                InputCube222.SwipeDirection.DOWN ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = rightFaceZLayer222(cell.col),
                        dir = -1f
                    )
            }
        }

        // =====================================================
        // LEFT
        // =====================================================

        InputCube222.Face.LEFT -> {

            when (swipe) {

                InputCube222.SwipeDirection.RIGHT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = -rowToLayer222(cell.row),
                        dir = 1f
                    )

                InputCube222.SwipeDirection.LEFT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = -rowToLayer222(cell.row),
                        dir = -1f
                    )

                InputCube222.SwipeDirection.UP ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = leftFaceZLayer222(cell.col),
                        dir = -1f
                    )

                InputCube222.SwipeDirection.DOWN ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = leftFaceZLayer222(cell.col),
                        dir = 1f
                    )
            }
        }

        // =====================================================
        // TOP
        // =====================================================

        InputCube222.Face.TOP -> {

            when (swipe) {

                InputCube222.SwipeDirection.RIGHT ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = topBottomLayer222(cell.row),
                        dir = -1f
                    )

                InputCube222.SwipeDirection.LEFT ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = topBottomLayer222(cell.row),
                        dir = 1f
                    )

                InputCube222.SwipeDirection.UP ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = leftRightLayer222(cell.col),
                        dir = -1f
                    )

                InputCube222.SwipeDirection.DOWN ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = leftRightLayer222(cell.col),
                        dir = 1f
                    )
            }
        }

        // =====================================================
        // BOTTOM
        // =====================================================

        InputCube222.Face.BOTTOM -> {

            when (swipe) {

                InputCube222.SwipeDirection.RIGHT ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = -topBottomLayer222(cell.row),
                        dir = 1f
                    )

                InputCube222.SwipeDirection.LEFT ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = -topBottomLayer222(cell.row),
                        dir = -1f
                    )

                InputCube222.SwipeDirection.UP ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = leftRightLayer222(cell.col),
                        dir = -1f
                    )

                InputCube222.SwipeDirection.DOWN ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = leftRightLayer222(cell.col),
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
// LAYERS 2x2
// =========================================================

private fun rowToLayer222(
    row: Int
): Float {

    return if (row == 0)
        0.5f
    else
        -0.5f
}

private fun colToLayer222(
    col: Int
): Float {

    return if (col == 0)
        -0.5f
    else
        0.5f
}

private fun rightFaceZLayer222(
    col: Int
): Float {

    return if (col == 0)
        0.5f
    else
        -0.5f
}

private fun leftFaceZLayer222(
    col: Int
): Float {

    return if (col == 0)
        -0.5f
    else
        0.5f
}

private fun leftRightLayer222(
    col: Int
): Float {

    return if (col == 0)
        -0.5f
    else
        0.5f
}

private fun topBottomLayer222(
    row: Int
): Float {

    return if (row == 0)
        0.5f
    else
        -0.5f
}