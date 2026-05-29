package com.quicklydone.nt.solver

import com.quicklydone.nt.common.GestureState222
import com.quicklydone.nt.common.Vec3


object Moves222 {
    /*fun test(gestureState: GestureState222, rotate: (Vec3, Float, Float) -> Unit) {

        Solver222.scrambleMoves.clear()
        rotateMove("R",rotate)
        Solver222.scrambleMoves += "R"
        rotateMove("U",rotate)
        Solver222.scrambleMoves += "U"
        rotateMove("F",rotate)
        Solver222.scrambleMoves += "F"

    }*/

    fun upa(rotate: (Vec3, Float, Float) -> Unit) {
        rotate(
            Vec3(0f, 1f, 0f),
            0.5f,
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

        repeat(10) {

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
}



