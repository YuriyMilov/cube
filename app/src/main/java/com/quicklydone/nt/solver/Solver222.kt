package com.quicklydone.nt.solver

import android.util.Log
import com.quicklydone.nt.cube_new.FaceMarkerNew

object Solver222 {

    val scrambleMoves =
        mutableListOf<String>()

    val solutionMoves =
        mutableListOf<String>()

    var currentStep = 0

   /* fun getSolution(){
        solutionMoves.clear()
        solutionMoves += "F'"
        solutionMoves += "U'"
        solutionMoves += "R'"
        currentStep = 0
    }
*/
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

            Log.d("qq","------>   УРА!")

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
}