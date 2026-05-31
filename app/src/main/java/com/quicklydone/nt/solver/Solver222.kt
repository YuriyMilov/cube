package com.quicklydone.nt.solver

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.common.onLayer
import com.quicklydone.nt.common.rotateAroundAxis
import com.quicklydone.nt.common.snap222
import com.quicklydone.nt.cube.rememberCubelets
import com.quicklydone.nt.cube_new.CubeletNew
import com.quicklydone.nt.cube_new.FaceMarkerNew

object Solver222 {

    val scrambleMoves =
        mutableListOf<String>()

    val solutionMoves =
        mutableListOf<String>()

    var currentStep = 0

    fun getSolution1(){
        solutionMoves.clear()
       // solutionMoves += "F'"
       // solutionMoves += "U'"
        solutionMoves += "R'"
        currentStep = 0
    }

    fun getSolution() {

        solutionMoves.clear()

        solutionMoves +=
            scrambleMoves
                .reversed()
                .map {
                    inverseMove(it)
                }

        currentStep = 0
    }

    fun showNextHint(
        markers: MutableList<FaceMarkerNew>
    ) {

        if (currentStep >= solutionMoves.size) {

            markers.clear()

         //   Log.d("qq","------>   УРА!")

            return
        }

        val move =
            solutionMoves[currentStep]

        when (move) {

            "R" ->
                Arrows222.addRingHintR(markers)

            "R'" ->
                Arrows222.addRingHintRPrime(markers)

            "L" ->
                Arrows222.addRingHintL(markers)

            "L'" ->
                Arrows222.addRingHintLPrime(markers)

            "U" ->
                Arrows222.addRingHintU(markers)

            "U'" ->
                Arrows222.addRingHintUPrime(markers)

            "D" ->
                Arrows222.addRingHintD(markers)

            "D'" ->
                Arrows222.addRingHintDPrime(markers)

            "F" ->
                Arrows222.addRingHintF(markers)

            "F'" ->
                Arrows222.addRingHintFPrime(markers)

            "B" ->
                Arrows222.addRingHintB(markers)

            "B'" ->
                Arrows222.addRingHintBPrime(markers)
        }
    }

    fun inverseMove(move: String): String {

        return when (move) {

            "R" -> "R'"
            "R'" -> "R"

            "L" -> "L'"
            "L'" -> "L"

            "U" -> "U'"
            "U'" -> "U"

            "D" -> "D'"
            "D'" -> "D"

            "F" -> "F'"
            "F'" -> "F"

            "B" -> "B'"
            "B'" -> "B"

            else -> move
        }
    }

    fun onUserMove(
        move: String,
        markers: MutableList<FaceMarkerNew>
    ) {

        if (currentStep >= solutionMoves.size)
            return

        val expected =
            solutionMoves[currentStep]

        if (move == expected) {

            currentStep++

            showNextHint(markers)
        }
    }


    fun onCubeChanged(
        state: CubeState222
    ) {

        /*Log.d(
            "SOLVER",
            state.cubelets.joinToString()
        )*/

        Log.d(
            "SOLVER",
            state.cornersPos.joinToString()
        )

        Log.d(
            "SOLVER",
            state.cornersAxes.joinToString()
        )



        // анализ состояния
    }

    fun applyRotation(
        cubelets: SnapshotStateList<CubeletNew>,
        axis: Vec3,
        layer: Float,
        dir: Float
    ) {

        val finalAngle =
            dir * (Math.PI.toFloat() / 2f)

        cubelets.forEach { cube ->

            if (onLayer(cube.pos, axis, layer)) {

                cube.pos = snap222(
                    rotateAroundAxis(
                        cube.pos,
                        axis,
                        finalAngle
                    )
                )

                cube.axisX =
                    rotateAroundAxis(
                        cube.axisX,
                        axis,
                        finalAngle
                    )

                cube.axisY =
                    rotateAroundAxis(
                        cube.axisY,
                        axis,
                        finalAngle
                    )

                cube.axisZ =
                    rotateAroundAxis(
                        cube.axisZ,
                        axis,
                        finalAngle
                    )
            }
        }
    }
}




data class CubeState222(
    //val cubelets: SnapshotStateList<CubeletNew>,
    val cornersPos: IntArray,
    val cornersAxes: IntArray
)