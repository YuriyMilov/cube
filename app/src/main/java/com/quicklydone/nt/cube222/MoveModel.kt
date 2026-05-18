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

fun mapInputToRotation(
    cell: InputCube.InputCell,
    swipe: InputCube.SwipeDirection
): RotationMove {

    return when (cell.face) {

        // =====================================================
        // FRONT
        // =====================================================

        InputCube.Face.FRONT -> {

            when (swipe) {

                InputCube.SwipeDirection.RIGHT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = 1f
                    )

                InputCube.SwipeDirection.LEFT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = -1f
                    )

                InputCube.SwipeDirection.UP ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = colToLayer(cell.col),
                        dir = -1f
                    )

                InputCube.SwipeDirection.DOWN ->
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

        InputCube.Face.BACK -> {

            when (swipe) {

                InputCube.SwipeDirection.RIGHT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = 1f
                    )

                InputCube.SwipeDirection.LEFT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = -1f
                    )

                InputCube.SwipeDirection.UP ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = -colToLayer(cell.col),
                        dir = 1f
                    )

                InputCube.SwipeDirection.DOWN ->
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

        InputCube.Face.RIGHT -> {

            when (swipe) {

                InputCube.SwipeDirection.RIGHT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = 1f
                    )

                InputCube.SwipeDirection.LEFT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = -1f
                    )

                InputCube.SwipeDirection.UP ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = rightFaceZLayer(cell.col),
                        dir = 1f
                    )

                InputCube.SwipeDirection.DOWN ->
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

        InputCube.Face.LEFT -> {

            when (swipe) {

                InputCube.SwipeDirection.RIGHT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = 1f
                    )

                InputCube.SwipeDirection.LEFT ->
                    RotationMove(
                        axis = Y_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = -1f
                    )

                InputCube.SwipeDirection.UP ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = leftFaceZLayer(cell.col),
                        dir = -1f
                    )

                InputCube.SwipeDirection.DOWN ->
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

        InputCube.Face.TOP -> {

            when (swipe) {

                InputCube.SwipeDirection.RIGHT ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = topBottomLayer(cell.row),
                        dir = -1f
                    )

                InputCube.SwipeDirection.LEFT ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = topBottomLayer(cell.row),
                        dir = 1f
                    )

                InputCube.SwipeDirection.UP ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = leftRightLayer(cell.col),
                        dir = -1f
                    )

                InputCube.SwipeDirection.DOWN ->
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

        InputCube.Face.BOTTOM -> {

            when (swipe) {

                InputCube.SwipeDirection.RIGHT ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = 1f
                    )

                InputCube.SwipeDirection.LEFT ->
                    RotationMove(
                        axis = Z_AXIS,
                        layer = rowToLayer(cell.row),
                        dir = -1f
                    )

                InputCube.SwipeDirection.UP ->
                    RotationMove(
                        axis = X_AXIS,
                        layer = leftRightLayer(cell.col),
                        dir = -1f
                    )

                InputCube.SwipeDirection.DOWN ->
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
// HELPERS
// =========================================================

private val X_AXIS =
    Vec3(1f, 0f, 0f)

private val Y_AXIS =
    Vec3(0f, 1f, 0f)

private val Z_AXIS =
    Vec3(0f, 0f, 1f)

private fun rowToLayer(
    row: Int
): Float {

    return if (row == 0)
        -1f
    else
        1f
}

private fun colToLayer(
    col: Int
): Float {

    return if (col == 0)
        -1f
    else
        1f
}

private fun rightFaceZLayer(
    col: Int
): Float {

    return if (col == 0)
        1f
    else
        -1f
}

private fun leftFaceZLayer(
    col: Int
): Float {

    return if (col == 0)
        -1f
    else
        1f
}

private fun leftRightLayer(
    col: Int
): Float {

    return if (col == 0)
        -1f
    else
        1f
}

private fun topBottomLayer(
    row: Int
): Float {

    return if (row == 0)
        1f
    else
        -1f
}