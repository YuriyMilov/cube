package com.quicklydone.nt.solver

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.common.onLayer
import com.quicklydone.nt.common.rotateAroundAxis
import com.quicklydone.nt.common.snap222
import com.quicklydone.nt.cube_new.CubeletNew
import com.quicklydone.nt.cube_new.FaceMarkerNew
import com.quicklydone.nt.solver.Moves222.rotateMove

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
        cubelets: SnapshotStateList<CubeletNew>,
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



    fun onCubeChanged(
        state: CubeState222
    ) {
        Log.d(
            "SOLVER",
            state.cornersPos.joinToString()
        )

        Log.d(
            "SOLVER",
            state.cornersAxes.joinToString()
        )
    }

    fun applyRotation(
        cubelets: SnapshotStateList<CubeletNew>,
        axis: Vec3,
        layer: Float,
        dir: Float
    ) {

        val finalAngle =
            dir * (Math.PI.toFloat() / 2f)

        cubelets.forEachIndexed { index, cube ->

            if (onLayer(cube.pos, axis, layer)) {

                cubelets[index] = cube.copy(
                    pos = snap222(
                        rotateAroundAxis(
                            cube.pos,
                            axis,
                            finalAngle
                        )
                    ),
                    axisX = rotateAroundAxis(
                        cube.axisX,
                        axis,
                        finalAngle
                    ),
                    axisY = rotateAroundAxis(
                        cube.axisY,
                        axis,
                        finalAngle
                    ),
                    axisZ = rotateAroundAxis(
                        cube.axisZ,
                        axis,
                        finalAngle
                    )
                )
            }
        }
    }


    fun showNextHintRGW(
        cubelets: SnapshotStateList<CubeletNew>,
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




    fun getSolutionRGW(state: CubeState222) {
        solutionMoves.clear()


        Log.d(
            "SOLVER",
            state.cornersPos.joinToString()
        )

        Log.d(
            "SOLVER",
            state.cornersAxes.joinToString()
        )


        solutionMoves += "F'"
        solutionMoves += "U'"
        solutionMoves += "R'"
        currentStep = 0
    }



    fun executeAlgorithm(
        algorithm: String,
        cubelets: SnapshotStateList<CubeletNew>
    ) {
        algorithm.split(" ")
            .filter { it.isNotBlank() }
            .forEach { move ->
                rotateMove(move, cubelets)
            }
    }

    fun rotateMove(
        move: String,
        cubelets: SnapshotStateList<CubeletNew>
    ) {
        rotateMove(move) { axis, layer, dir ->
            applyRotation(cubelets, axis, layer, dir)
        }
    }
    fun pre(state: CubeState222, algorithm: String, cubelets: SnapshotStateList<CubeletNew>) {

        Log.d(
            "SOLVER",
            state.cornersPos.joinToString()
        )

        Log.d(
            "SOLVER",
            state.cornersAxes.joinToString()
        )


        executeAlgorithm(
            algorithm,
            cubelets
        )

        Log.d(
            "SOLVER",
            "-----------------------------------------"        )

    }

    data class State(
        val pos: IntArray,
        val ori: IntArray
    )



    //private fun moveRPrime(state: State): State {}

   /* fun inversePerm(p: IntArray): IntArray {
        val inv = IntArray(p.size)
        for (i in p.indices) {
            inv[p[i]] = i
        }
        return inv
    }*/



    fun inversePerm(p: IntArray): IntArray {
        val inv = IntArray(p.size)
        for (i in p.indices) {
            inv[p[i]] = i
        }
        return inv
    }

    val moveR = intArrayOf(
        0,
        3,
        2,
        7,
        4,
        1,
        6,
        5
    )

    fun moveR(state: State): State {

        val newPos = IntArray(8)

        for (i in 0 until 8) {
            newPos[i] = state.pos[moveR[i]]
        }

        return state.copy(pos = newPos)
    }


    val moveRPrime = inversePerm(moveR)
    fun moveRPrime(state: State): State {

        val newPos = IntArray(8)

        for (i in 0 until 8) {
            newPos[i] = state.pos[moveRPrime[i]]
        }

        return state.copy(pos = newPos)
    }


    val moveL = intArrayOf(
        4, // 0
        1, // 1
        0, // 2
        3, // 3
        6, // 4
        5, // 5
        2, // 6
        7  // 7
    )

    fun moveL(state: State): State {

        val newPos = IntArray(8)

        for (i in 0 until 8) {
            newPos[i] = state.pos[moveL[i]]
        }

        return state.copy(pos = newPos)
    }

    val moveLPrime = inversePerm(moveL)

    fun moveLPrime(state: State): State {

        val newPos = IntArray(8)

        for (i in 0 until 8) {
            newPos[i] = state.pos[moveLPrime[i]]
        }

        return state.copy(pos = newPos)
    }





    /*


   val moveDPrime = inversePerm(permD)

   val moveBPrime = inversePerm(permB)*/



    val moveU = intArrayOf(
        0, // 0
        1, // 1
        6, // 2
        2, // 3
        4, // 4
        5, // 5
        7, // 6
        3  // 7
    )

    fun moveU(state: State): State {

        val newPos = IntArray(8)

        for (i in 0 until 8) {
            newPos[i] = state.pos[moveU[i]]
        }

        return state.copy(pos = newPos)
    }


    val moveUPrime = inversePerm(moveU)

    fun moveUPrime(state: State): State {

        val newPos = IntArray(8)

        for (i in 0 until 8) {
            newPos[i] = state.pos[moveUPrime[i]]
        }

        return state.copy(pos = newPos)
    }

   val moveD = intArrayOf(
        1, // 0
        5, // 1
        2, // 2
        3, // 3
        0, // 4
        4, // 5
        6, // 6
        7  // 7
    )

    fun moveD(state: State): State {

        val newPos = IntArray(8)

        for (i in 0 until 8) {
            newPos[i] = state.pos[moveD[i]]
        }

        return state.copy(pos = newPos)
    }

    val moveDPrime = inversePerm(moveD)

    fun moveDPrime(state: State): State {

        val newPos = IntArray(8)

        for (i in 0 until 8) {
            newPos[i] = state.pos[moveDPrime[i]]
        }

        return state.copy(pos = newPos)
    }


    val moveF = intArrayOf(
        0, // 0
        1, // 1
        2, // 2
        3, // 3
        5, // 4
        7, // 5
        4, // 6
        6  // 7
    )

    fun moveF(state: State): State {

        val newPos = IntArray(8)

        for (i in 0 until 8) {
            newPos[i] = state.pos[moveF[i]]
        }

        return state.copy(pos = newPos)
    }


    val permFPrime = inversePerm(moveF)

    fun moveFPrime(state: State): State {

        val newPos = IntArray(8)

        for (i in 0 until 8) {
            newPos[i] = state.pos[permFPrime[i]]
        }

        return state.copy(pos = newPos)
    }


    val moveB = intArrayOf(
        2, // 0
        0, // 1
        3, // 2
        1, // 3
        4, // 4
        5, // 5
        6, // 6
        7  // 7
    )

    fun moveB(state: State): State {

        val newPos = IntArray(8)

        for (i in 0 until 8) {
            newPos[i] = state.pos[moveB[i]]
        }

        return state.copy(pos = newPos)
    }


    val moveBPrime = inversePerm(moveB)

    fun moveBPrime(state: State): State {

        val newPos = IntArray(8)

        for (i in 0 until 8) {
            newPos[i] = state.pos[moveBPrime[i]]
        }

        return state.copy(pos = newPos)
    }


    fun solve(start: State): List<String> {


/*

        Log.d("TEST", compose(permR, moveRPrime).joinToString())
        Log.d("TEST", compose(moveRPrime, permR).joinToString())

        Log.d("TEST", compose(moveL, moveLPrime).joinToString())
        Log.d("TEST", compose(moveLPrime, moveL).joinToString())

        Log.d("TEST", compose(moveF, moveFPrime).joinToString())
        Log.d("TEST", compose(moveFPrime, moveF).joinToString())
*/



        val moves = listOf(
            "R", "R'",
            "L", "L'",
            "U", "U'",
            "D", "D'",
            "F", "F'",
            "B", "B'"
        )

        val queue = ArrayDeque<Pair<State, List<String>>>()

        // храним всё состояние, а не только targetPos
        val visited = mutableSetOf<String>()

        queue.add(start to emptyList())

        while (queue.isNotEmpty()) {

            val (state, path) = queue.removeFirst()

            val key = state.pos.joinToString(",")

            if (!visited.add(key)) {
                continue
            }

            // цель: кублет 7 стоит в позиции 7
            if (state.pos[7] == 7) {
                Log.d(
                    "SOLVER",
                    "FOUND: ${path.joinToString(" ")} -> ${state.pos.joinToString()}"
                )
                return path
            }

            // защита от слишком глубокого поиска
            if (path.size >= 14) {
                continue
            }

            for (move in moves) {

              //Log.d("SOLVER", "===========       for (move in moves)         ======")
                val next = applyMove(state, move)
                queue.add(next to (path + move))
            }
        }

        Log.d("SOLVER", "NO SOLUTION")
        return emptyList()
    }

    fun applyMove(state: State, move: String): State {
        val result = when(move) {
            "R"  -> moveR(state)
            "R'" -> moveRPrime(state)

            "L"  -> moveL(state)
            "L'" -> moveLPrime(state)

            "U"  -> moveU(state)
            "U'" -> moveUPrime(state)

            "D"  -> moveD(state)
            "D'" -> moveDPrime(state)

            "F"  -> moveF(state)
            "F'" -> moveFPrime(state)

            "B"  -> moveB(state)
            "B'" -> moveBPrime(state)

            else -> state
        }

        Log.d(
            "SOLVER",
            "-->>   $move : ${state.pos.joinToString()} -> ${result.pos.joinToString()}"
        )
        /* Log.d(
             "MOVE_DEBUG",
             "$move : ${state.pos.joinToString()} -> ${result.pos.joinToString()}"
         )*/
        return result
    }
    fun compose(a: IntArray, b: IntArray): IntArray {
        val r = IntArray(8)

        for (i in 0 until 8) {
            r[i] = a[b[i]]
        }

        return r
    }
}


data class CubeState222(
    val cubelets: SnapshotStateList<CubeletNew>,
    val cornersPos: IntArray,
    val cornersAxes: IntArray
)