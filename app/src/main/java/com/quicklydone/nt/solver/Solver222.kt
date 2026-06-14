package com.quicklydone.nt.solver

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.mutableStateOf
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

    fun getArrows() {
        solutionMoves.clear()
        solutionMoves += "F'"
        solutionMoves += "U'"
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
        //cubelets: SnapshotStateList<CubeletNew>,
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
        /*
                Log.d(
                    "SOLVER",
                    state.cornersPos.joinToString()
                )*/

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

        Log.d("SOLVER", state.cornersPos.joinToString())
        Log.d("SOLVER", state.cornersAxes.joinToString())

        val start = SolverState(
            //pos = state.cornersPos.copyOf(),
            ori = state.cornersAxes.copyOf()
        )

        val result = solve(start)

        solutionMoves.addAll(result)

        currentStep = 0
    }

    fun getSolutionRGW2(state: CubeState222) {

        solutionMoves.clear()

        Log.d("SOLVER", state.cornersPos.joinToString())
        Log.d("SOLVER", state.cornersAxes.joinToString())

        val start = SolverState(
            //pos = state.cornersPos.copyOf(),
            ori = state.cornersAxes.copyOf()
        )

        val result = solve2(start)

        // solutionMoves.addAll(result)

        currentStep = 0
    }

    fun getSolutionRGW1(state: CubeState222) {
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

        /*   Log.d(
               "SOLVER",
               state.cornersAxes.joinToString()
           )
   */

        executeAlgorithm(
            algorithm,
            cubelets
        )

        Log.d(
            "SOLVER",
            "-----------------------------------------"
        )

    }


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

    fun rotateDirR(dir: Int): Int =
        when (dir) {
            0 -> 0
            1 -> 1

            2 -> 5
            5 -> 3
            3 -> 4
            4 -> 2

            else -> error("bad dir")
        }

    fun moveR(state: SolverState): SolverState =
        applyPermWithOri(
            state,
            moveR,
            intArrayOf(1, 3, 5, 7),
            ::rotateDirR
        )

    fun rotateDirRPrime(dir: Int): Int =
        when (dir) {
            0 -> 0
            1 -> 1

            5 -> 2
            3 -> 5
            4 -> 3
            2 -> 4

            else -> error("bad dir")
        }

    val moveRPrime = inversePerm(moveR)
    fun moveRPrime(state: SolverState): SolverState =
        applyPermWithOri(
            state,
            moveRPrime,
            intArrayOf(1, 3, 5, 7),
            ::rotateDirRPrime
        )


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

    fun rotateDirL(dir: Int): Int =
        when (dir) {
            0 -> 0
            1 -> 1

            2 -> 4
            4 -> 3
            3 -> 5
            5 -> 2

            else -> error("bad dir")
        }

    fun moveL(state: SolverState): SolverState =
        applyPermWithOri(
            state,
            moveL,
            intArrayOf(0, 2, 4, 6),
            ::rotateDirL
        )

    val moveLPrime = inversePerm(moveL)
    fun rotateDirLPrime(dir: Int): Int =
        when (dir) {
            0 -> 0
            1 -> 1

            4 -> 2
            3 -> 4
            5 -> 3
            2 -> 5

            else -> error("bad dir")
        }

    fun moveLPrime(state: SolverState): SolverState =
        applyPermWithOri(
            state,
            moveLPrime,
            intArrayOf(0, 2, 4, 6),
            ::rotateDirLPrime
        )


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

    fun rotateDirU(dir: Int): Int =
        when (dir) {
            0 -> 4
            4 -> 1
            1 -> 5
            5 -> 0

            2 -> 2
            3 -> 3

            else -> error("bad dir")
        }

    fun moveU(state: SolverState): SolverState =
        applyPermWithOri(
            state,
            moveU,
            intArrayOf(2, 3, 6, 7),
            ::rotateDirU
        )


    val moveUPrime = inversePerm(moveU)
    fun rotateDirUPrime(dir: Int): Int =
        when (dir) {
            0 -> 5
            5 -> 1
            1 -> 4
            4 -> 0

            2 -> 2
            3 -> 3

            else -> error("bad dir")
        }

    fun moveUPrime(state: SolverState): SolverState =
        applyPermWithOri(
            state,
            moveUPrime,
            intArrayOf(2, 3, 6, 7),
            ::rotateDirUPrime
        )

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

    fun rotateDirD(dir: Int): Int =
        when (dir) {
            0 -> 5
            5 -> 1
            1 -> 4
            4 -> 0

            2 -> 2
            3 -> 3

            else -> error("bad dir")
        }

    fun moveD(state: SolverState): SolverState =
        applyPermWithOri(
            state,
            moveD,
            intArrayOf(0, 1, 4, 5),
            ::rotateDirD
        )

    val moveDPrime = inversePerm(moveD)
    fun rotateDirDPrime(dir: Int): Int =
        when (dir) {
            0 -> 4
            4 -> 1
            1 -> 5
            5 -> 0

            2 -> 2
            3 -> 3

            else -> error("bad dir")
        }

    fun moveDPrime(state: SolverState): SolverState =
        applyPermWithOri(
            state,
            moveDPrime,
            intArrayOf(0, 1, 4, 5),
            ::rotateDirDPrime
        )


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
    val moveF2 = intArrayOf(
        0,
        1,
        2,
        3,
        7,
        6,
        5,
        4
    )

    fun rotateDirF(dir: Int): Int =
        when (dir) {
            0 -> 3
            3 -> 1
            1 -> 2
            2 -> 0

            4 -> 4
            5 -> 5

            else -> error("bad dir")
        }

    fun moveF(state: SolverState): SolverState =
        applyPermWithOri(
            state,
            moveF,
            intArrayOf(4, 5, 6, 7),
            ::rotateDirF
        )


    val moveFPrime = inversePerm(moveF)

    fun rotateDirFPrime(dir: Int): Int =
        when (dir) {
            0 -> 2
            2 -> 1
            1 -> 3
            3 -> 0

            4 -> 4
            5 -> 5

            else -> error("bad dir")
        }

    fun moveFPrime(state: SolverState): SolverState =
        applyPermWithOri(
            state,
            moveFPrime,
            intArrayOf(4, 5, 6, 7),
            ::rotateDirFPrime
        )


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

    fun rotateDirB(dir: Int): Int =
        when (dir) {
            2 -> 1
            1 -> 3
            3 -> 0
            0 -> 2

            4 -> 4
            5 -> 5

            else -> error("bad dir")
        }

    fun moveB(state: SolverState): SolverState =
        applyPermWithOri(
            state,
            moveB,
            intArrayOf(0, 1, 2, 3),
            ::rotateDirB
        )


    val moveBPrime = inversePerm(moveB)
    fun rotateDirBPrime(dir: Int): Int =
        when (dir) {
            2 -> 0
            0 -> 3
            3 -> 1
            1 -> 2

            4 -> 4
            5 -> 5

            else -> error("bad dir")
        }

    fun moveBPrime(state: SolverState): SolverState =
        applyPermWithOri(
            state,
            moveBPrime,
            intArrayOf(0, 1, 2, 3),
            ::rotateDirBPrime
        )


    data class Node(
        val state: SolverState,
        val parent: Node?,
        val move: String?,
        val depth: Int
    )

    fun applyMove(state: SolverState, move: String): SolverState {
        val result = when (move) {
            "R" -> moveR(state)
            "R'" -> moveRPrime(state)

            "L" -> moveL(state)
            "L'" -> moveLPrime(state)

            "U" -> moveU(state)
            "U'" -> moveUPrime(state)

            "D" -> moveD(state)
            "D'" -> moveDPrime(state)

            "F" -> moveF(state)
            "F'" -> moveFPrime(state)

            "B" -> moveB(state)
            "B'" -> moveBPrime(state)

            else -> state
        }

        return result
    }


    fun applyMove2(state: SolverState, move: String): SolverState {
        val result = when (move) {


            "X" -> applyMove(applyMove(state, "R"), "L'")
            "X'" -> applyMove(applyMove(state, "R'"), "L")

            "Y" -> applyMove(applyMove(state, "U"), "D'")
            "Y'" -> applyMove(applyMove(state, "U'"), "D")

            "Z" -> applyMove(applyMove(state, "F"), "B'")
            "Z'" -> applyMove(applyMove(state, "F'"), "B")

            else -> state
        }

        return result
    }

    fun compose(a: IntArray, b: IntArray): IntArray {
        val r = IntArray(8)

        for (i in 0 until 8) {
            r[i] = a[b[i]]
        }

        return r
    }


    fun applyPermWithOri(
        state: SolverState,
        perm: IntArray,
        affected: IntArray,
        rotate: (Int) -> Int
    ): SolverState {

        val newPos = IntArray(8)
        val newOri = IntArray(24)

        // перестановка
        for (i in 0 until 8) {

            val src = perm[i]

            //  newPos[i] = state.pos[src]

            for (k in 0 until 3) {
                newOri[i * 3 + k] =
                    state.ori[src * 3 + k]
            }
        }

        // поворот локальных осей
        for (p in affected) {

            val base = p * 3

            newOri[base + 0] = rotate(newOri[base + 0])
            newOri[base + 1] = rotate(newOri[base + 1])
            newOri[base + 2] = rotate(newOri[base + 2])
        }

        //return SolverState(newPos, newOri)
        return SolverState(newOri)
    }

    //  fun rotateIdentity(dir: Int) = dir

    val RIGHT = intArrayOf(1, 3, 5, 7)
    val LEFT = intArrayOf(0, 2, 4, 6)

    val UP = intArrayOf(2, 3, 6, 7)
    val DOWN = intArrayOf(0, 1, 4, 5)

    val FRONT = intArrayOf(4, 5, 6, 7)
    val BACK = intArrayOf(0, 1, 2, 3)


    fun isSolved2(state: SolverState): Boolean {
        if (state.ori[21] == 0 && state.ori[22] == 2 && state.ori[23] == 4)
            return true
        return false
    }

    fun isSolved(state: SolverState): Boolean {
        val a = state.ori[0]
        val b = state.ori[1]
        val c = state.ori[2]

        for (i in 0 until 8) {

            val p = i * 3

            if (state.ori[p] != a) return false
            if (state.ori[p + 1] != b) return false
            if (state.ori[p + 2] != c) return false
        }

        return true
    }

    fun solve(start: SolverState): List<String> {

        //  numLog("solve")
        val startTime = System.currentTimeMillis()

        for (maxDepth in 0..8) {

            Log.d("SOLVER", "depth = $maxDepth")

            val path = mutableListOf<String>()

            if (
                dfs(
                    state = start,
                    depth = 0,
                    maxDepth = maxDepth,
                    path = path
                )
            ) {
                Log.d(
                    "SOLVER",
                    "FOUND: ${path.joinToString(" ")}"
                )
                numLog(" ${path.joinToString(" ")}")
                return path
            }
        }

        Log.d("SOLVER", "NO SOLUTION")

        return emptyList()
    }

    fun solve2(start: SolverState): List<String> {

        // numLog("solve2")
        for (maxDepth in 0..22) {

            Log.d("SOLVER", "depth(solve2) = $maxDepth")

            val path = mutableListOf<String>()

            if (
                dfs2(
                    state = start,
                    depth = 0,
                    maxDepth = maxDepth,
                    path = path
                )
            ) {

                Log.d(
                    "SOLVER",
                    "FOUND 2: ${path.joinToString(" ")}"
                )

                numLog(" ${path.joinToString(" ")}")
                return path
            }
        }

        Log.d("SOLVER", "NO SOLUTION")

        return emptyList()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun dfs(
        state: SolverState,
        depth: Int,
        maxDepth: Int,
        path: MutableList<String>
    ): Boolean {

        if (isSolved(state)) {


            return true
        }

        if (depth >= maxDepth) {
            return false
        }

        val moves = listOf(
            "R", "R'",
            "L", "L'",
            "U", "U'",
            "D", "D'",
            "F", "F'",
            "B", "B'"
        )

        val lastMove = path.lastOrNull()

        for (move in moves) {

            if (lastMove != null && isInverse(lastMove, move)) continue

            val next = applyMove(state, move)

            path.add(move)

            if (
                dfs(
                    state = next,
                    depth = depth + 1,
                    maxDepth = maxDepth,
                    path = path
                )
            ) {
                return true
            }

            path.removeAt(path.lastIndex)
        }

        return false
    }


    @SuppressLint("SuspiciousIndentation")
    private fun dfs2(
        state: SolverState,
        depth: Int,
        maxDepth: Int,
        path: MutableList<String>
    ): Boolean {

        if (isSolved2(state)) {
            return true
        }

        if (depth >= maxDepth) {
            return false
        }

        val moves = listOf(
            "X", "X'",
            "Y", "Y'",
            "Z", "Z'"
        )

        val lastMove = path.lastOrNull()

        for (move in moves) {
            // if (lastMove != null && isInverse(lastMove, move)) continue
            val next = applyMove2(state, move)
            path.add(move)

            Log.d(
                "SOLVER",
                "dfs2: ${path.joinToString(" ")}"
            )

            if (
                dfs2(
                    state = next,
                    depth = depth + 1,
                    maxDepth = maxDepth,
                    path = path
                )
            ) {
                return true
            }

            path.removeAt(path.lastIndex)
        }

        return false
    }


    private fun isInverse(a: String, b: String): Boolean {
        return a == "R" && b == "R'" ||
                a == "R'" && b == "R" ||
                a == "L" && b == "L'" ||
                a == "L'" && b == "L" ||
                a == "U" && b == "U'" ||
                a == "U'" && b == "U" ||
                a == "D" && b == "D'" ||
                a == "D'" && b == "D" ||
                a == "F" && b == "F'" ||
                a == "F'" && b == "F" ||
                a == "B" && b == "B'" ||
                a == "B'" && b == "B"
    }


    val logText = mutableStateOf("")

    fun numLog(text: String) {
        logText.value = "$text"
    }

}


data class CubeState222(
    val cubelets: SnapshotStateList<CubeletNew>,
    val cornersPos: IntArray,
    val cornersAxes: IntArray
)


data class SolverState(
    //   val pos: IntArray,
    val ori: IntArray
) {
    override fun equals(other: Any?): Boolean {
        return other is SolverState &&
                ori.contentEquals(other.ori)
    }

    override fun hashCode(): Int {
        return ori.contentHashCode()
    }
}
