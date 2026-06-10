package com.quicklydone.nt.solver

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.quicklydone.nt.common.GestureState222
import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.cube_new.CubeletNew
import com.quicklydone.nt.solver.Solver222.applyRotation


object Moves222 {

    fun upa(
        rotate: (Vec3, Float, Float, Float) -> Unit
    ) {
        rotate(
            Vec3(0f, 1f, 0f),
            0.5f,
            -0.5f,
            -1f
        )
    }

    fun test(
        gestureState: GestureState222,
        rotate: (Vec3, Float, Float) -> Unit
    ) {

        Solver222.scrambleMoves.clear()

        val allMoves = listOf(
            "R", "R'",
            "L", "L'",
            "U", "U'",
            "D", "D'",
            "F", "F'",
            "B", "B'"
        )

        repeat(5) {

            val move =
                allMoves.random()

            rotateMove(
                move,
                rotate
            )

            Solver222.scrambleMoves += move
        }

        Solver222.getSolution()
    }

    fun rotateMove(
        move: String,
        rotate: (
            Vec3,
            Float,
            Float,
            String?
        ) -> Unit
    ) {

        when (move) {

            "R" ->
                rotate(
                    Vec3(1f,0f,0f),
                    0.5f,
                    -1f,
                    "R"
                )

            "R'" ->
                rotate(
                    Vec3(1f,0f,0f),
                    0.5f,
                    1f,
                    "R'"
                )

            "U" ->
                rotate(
                    Vec3(0f,1f,0f),
                    0.5f,
                    -1f,
                    "U"
                )

            "U'" ->
                rotate(
                    Vec3(0f,1f,0f),
                    0.5f,
                    1f,
                    "U'"
                )
        }
    }

    fun rotateMove(
        move: String,
        rotate: (Vec3, Float, Float) -> Unit
    ) {

        when (move) {

            "R" ->
                rotate(
                    Vec3(1f, 0f, 0f),
                    0.5f,
                    -1f
                )

            "R'" ->
                rotate(
                    Vec3(1f, 0f, 0f),
                    0.5f,
                    1f
                )

            "L" ->
                rotate(
                    Vec3(1f, 0f, 0f),
                    -0.5f,
                    1f
                )

            "L'" ->
                rotate(
                    Vec3(1f, 0f, 0f),
                    -0.5f,
                    -1f
                )

            "U" ->
                rotate(
                    Vec3(0f, 1f, 0f),
                    0.5f,
                    -1f
                )

            "U'" ->
                rotate(
                    Vec3(0f, 1f, 0f),
                    0.5f,
                    1f
                )

            "D" ->
                rotate(
                    Vec3(0f, 1f, 0f),
                    -0.5f,
                    1f
                )

            "D'" ->
                rotate(
                    Vec3(0f, 1f, 0f),
                    -0.5f,
                    -1f
                )

            "F" ->
                rotate(
                    Vec3(0f, 0f, 1f),
                    0.5f,
                    -1f
                )

            "F'" ->
                rotate(
                    Vec3(0f, 0f, 1f),
                    0.5f,
                    1f
                )

            "B" ->
                rotate(
                    Vec3(0f, 0f, 1f),
                    -0.5f,
                    1f
                )

            "B'" ->
                rotate(
                    Vec3(0f, 0f, 1f),
                    -0.5f,
                    -1f
                )
        }
    }

    fun scramble(
        cubelets: SnapshotStateList<CubeletNew>,
        movesCount: Int = 20
    ) {

        Solver222.scrambleMoves.clear()

        val allMoves = listOf(
            "R", "R'",
            "L", "L'",
            "U", "U'",
            "D", "D'",
            "F", "F'",
            "B", "B'"
        )

        repeat(movesCount) {

            val move = allMoves.random()

            when (move) {

                "R" -> applyRotation(cubelets, Vec3(1f, 0f, 0f), 0.5f, -1f)
                "R'" -> applyRotation(cubelets, Vec3(1f, 0f, 0f), 0.5f, 1f)

                "L" -> applyRotation(cubelets, Vec3(1f, 0f, 0f), -0.5f, 1f)
                "L'" -> applyRotation(cubelets, Vec3(1f, 0f, 0f), -0.5f, -1f)

                "U" -> applyRotation(cubelets, Vec3(0f, 1f, 0f), 0.5f, -1f)
                "U'" -> applyRotation(cubelets, Vec3(0f, 1f, 0f), 0.5f, 1f)

                "D" -> applyRotation(cubelets, Vec3(0f, 1f, 0f), -0.5f, 1f)
                "D'" -> applyRotation(cubelets, Vec3(0f, 1f, 0f), -0.5f, -1f)

                "F" -> applyRotation(cubelets, Vec3(0f, 0f, 1f), 0.5f, -1f)
                "F'" -> applyRotation(cubelets, Vec3(0f, 0f, 1f), 0.5f, 1f)

                "B" -> applyRotation(cubelets, Vec3(0f, 0f, 1f), -0.5f, 1f)
                "B'" -> applyRotation(cubelets, Vec3(0f, 0f, 1f), -0.5f, -1f)
            }

            Solver222.scrambleMoves += move
        }
    }
}


