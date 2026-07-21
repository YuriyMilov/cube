package com.quicklydone.nt.solver

import android.util.Log
import com.quicklydone.nt.solver.Solver222.applyMove

object Solver2a {


    data class Orientation(
        val x: Int,
        val y: Int,
        val z: Int
    )

    data class CornerInfo(
        val ps: Int,
        val pos: Int,
        val ori: Orientation
    )


    class CubeAnalysis(val state: SolverState) {

        fun isSolved(piece: Int): Boolean {
            return false
        }

        fun isOnTop(piece: Int): Boolean {
            return false
        }

        fun isOnBottom(piece: Int): Boolean {
            return false
        }

        fun isSolved2a(): Boolean {


            return state.ori[21] == 0 && state.ori[22] == 2 && state.ori[23] == 4 &&
                    state.ori[18] == 0 && state.ori[19] == 2 && state.ori[20] == 4 &&
                    state.ori[15] == 0 && state.ori[16] == 2 && state.ori[17] == 4 &&
                    state.ori[12] == 0 && state.ori[13] == 2 && state.ori[14] == 4
        }

        fun position(piece: Int): Int = state.pos[piece]

        fun orientation(piece: Int): Int = state.ori[piece]

        private val corners = Array(8) { ps ->
            CornerInfo(
                ps = ps,
                pos = state.pos[ps],
                ori = Orientation(
                    state.ori[ps * 3],
                    state.ori[ps * 3 + 1],
                    state.ori[ps * 3 + 2]
                )
            )
        }

        fun ps(piece: Int): CornerInfo {
            return corners[piece]
        }

    }


    fun applyMoves(state: SolverState, moves: String): SolverState {
        var s = state

        for (move in moves.split(" ")) {
            if (move.isNotBlank()) {
                s = Solver222.applyMove(s, move)
            }
        }

        return s
    }

    fun rotateTopUntil(
        state: SolverState,
        piece: Int,
        target: Int
    ): SolverState {

        var s = state

        repeat(4) {

            val a = CubeAnalysis(s)

            if (a.ps(piece).pos == target)
                return s

            s = applyMove(s, "U")
        }

        return s
    }

    fun get2a(state: CubeState222) {
        Solver222.solutionMoves.clear()
        /*        Log.d("SOLVER", "get2a()"+state.cornersPos.joinToString())
                Log.d("SOLVER", "get2a()"+state.cornersAxes.joinToString())*/
        val st = SolverState(
            pos = state.cornersPos.copyOf(),
            ori = state.cornersAxes.copyOf()
        )
        val result = slv2a(st)
        Solver222.solutionMoves.addAll(result)
        Solver222.currentStep = 0
    }


    fun slv2a(state: SolverState): List<String> {
        Solver222.numLog("solve3")


        val path = mutableListOf<String>()

     val st = SolverState(
            pos = state.pos.copyOf(),
            ori = state.ori.copyOf()
        )
        Log.d(
            "SOLVER",
            "...........    " + st.pos.joinToString()
        )


        val a = CubeAnalysis(st)

        // Log.d("SOLVER", "state.pos.indexOf($i) ...........  " + n)

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        when {

            state.pos.indexOf(6) == 3
                    && a.ps(3).ori.x == 0
                    && a.ps(3).ori.y == 2
                    && a.ps(3).ori.z == 4
                -> {

                Log.d(
                    "SOLVER", "ORI(6) в POS[3] ....... " + a.ps(
                        3
                    ).ori.x + a.ps(3).ori.y + a.ps(3).ori.z
                )

                Log.d(
                    "SOLVER",
                    "кублет (6) на позиции [3] и повернут правильно - больше ничего не делаем"
                )
            }

            state.pos.indexOf(6) == 3
                    && a.ps(3).ori.x == 5
                    && a.ps(3).ori.y == 0
                    && a.ps(3).ori.z == 3
                -> {
                path.addMoves("B'")
                path.addMoves("D")
                path.addMoves("B")
                path.addMoves("B")
                Log.d(
                    "SOLVER", "ORI(6) в POS[3] ....... " + a.ps(
                        3
                    ).ori.x + a.ps(3).ori.y + a.ps(3).ori.z
                )

                Log.d(
                    "SOLVER",
                    "кублет (6) на позиции [3] и повернут 503 "
                )
            }

            state.pos.indexOf(6) == 3
                    && a.ps(3).ori.x == 2
                    && a.ps(3).ori.y == 5
                    && a.ps(3).ori.z == 1
                -> {
                path.addMoves("B'")
                path.addMoves("B'")
                path.addMoves("D'")
                path.addMoves("B")
                Log.d(
                    "SOLVER", "ORI(6) в POS[3] ....... " + a.ps(
                        3
                    ).ori.x + a.ps(3).ori.y + a.ps(3).ori.z
                )

                Log.d(
                    "SOLVER",
                    "кублет (6) на позиции [3] и повернут 251 "
                )
            }

            state.pos.indexOf(6) == 2 -> {
                path.addMoves("B'")
                Log.d(
                    "SOLVER", "ORI(6) в POS[2]  ....... " + a.ps(
                        2
                    ).ori.x + a.ps(2).ori.y + a.ps(2).ori.z
                )
            }

            state.pos.indexOf(6) == 6 -> {
                path.addMoves("L'")
                path.addMoves("B'")
                Log.d(
                    "SOLVER", "ORI(6) в POS[6] ....... " + a.ps(
                        6
                    ).ori.x + a.ps(6).ori.y + a.ps(6).ori.z
                )
            }

            else -> Log.d("SOLVER", "state.pos.indexOf(6) ..... КОНЕЦ анализа ......  ")

        }


        /*    val a = CubeAnalysis(state)

            Log.d(
                "SOLVER",
                "........... POS(3)    " + a.piece(3).position
            )*/


        /*    path.addMoves("R U'")
            path.addMoves("F R' U")
            path.addMoves("L")
    */
        /*    Log.d(
                "SOLVER",
                "slv2a() FOUND: ${path.joinToString(" ")}"
            )*/


        Solver222.numLog(" slv2a\n ${path.joinToString(" ")}")
        return path
    }
//        return emptyList()
//}


    private fun dfs3(
        state: SolverState,
        depth: Int,
        maxDepth: Int,
        path: MutableList<String>
    ): Boolean {

        val a = CubeAnalysis(state)

        if (a.isSolved2a()) {
            return true
        }

        if (depth >= maxDepth) {
            return false
        }

        val moves = listOf(
            // "R", "R'",
            "L", "L'",
            // "U", "U'",
            "D", "D'",
            // "F", "F'",
            "B", "B'"
        )

        val lastMove = path.lastOrNull()

        for (move in moves) {

            if (lastMove != null && Solver222.isInverse(lastMove, move)) continue

            val next = applyMove(state, move)

            path.add(move)

            if (
                dfs3(
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
    /*
        fun isSolvedAALL(state: SolverState): Boolean {
            if (
                state.ori[0] == 0 && state.ori[1] == 2 && state.ori[2] == 4
                && state.ori[3] == 0 && state.ori[4] == 2 && state.ori[5] == 4
                && state.ori[6] == 0 && state.ori[7] == 2 && state.ori[8] == 4
                &&
                state.ori[9] == 0 && state.ori[10] == 2 && state.ori[11] == 4

                && state.ori[12] == 0 && state.ori[13] == 2 && state.ori[14] == 4
                && state.ori[15] == 0 && state.ori[16] == 2 && state.ori[17] == 4
                && state.ori[18] == 0 && state.ori[19] == 2 && state.ori[20] == 4
                && state.ori[21] == 0 && state.ori[22] == 2 && state.ori[23] == 4

            )
                return true
            return false
        }    */


    fun MutableList<String>.addMoves(moves: String) {
        addAll(
            moves
                .trim()
                .split(Regex("\\s+"))
                .filter { it.isNotEmpty() }
        )
    }

    fun IntArray.indexOfSeq(vararg pattern: Int): Int {
        for (i in 0..size - pattern.size) {
            var ok = true
            for (j in pattern.indices) {
                if (this[i + j] != pattern[j]) {
                    ok = false
                    break
                }
            }
            if (ok) return i / 3
        }
        return -1
    }

    fun IntArray.findSeq(vararg pattern: Int): List<Int> {
        val result = mutableListOf<Int>()

        for (i in 0..size - pattern.size) {
            var ok = true
            for (j in pattern.indices) {
                if (this[i + j] != pattern[j]) {
                    ok = false
                    break
                }
            }
            if (ok) result += i / 3
        }

        return result
    }

    fun aLog(
        state: CubeState222
    ) {

        /*    var i = 7

            val st = SolverState(
                pos = state.cornersPos.copyOf(),
                ori = state.cornersAxes.copyOf()
            )
            val a = CubeAnalysis(st)

            Log.d(
                "SOLVER",
                "POS($i)    " + a.piece(i).position
            )

            Log.d(
                "SOLVER",
                "indexOf($i) " + st.pos.indexOf(7)   )

            Log.d(
                "SOLVER",
                "indexOfSeq($i) " + st.ori.indexOfSeq(0,2,4)   )

            val seq = st.ori.findSeq(0,2,4)
            Log.d(
                "SOLVER",
                "findSeq(0,2,4) >>>>>}}>    " +   seq.joinToString() )*/


        /*  Log.d(
              "SOLVER",
              "ORI($i) " + a.piece(i
              ).orientation.x + a.piece(i).orientation.y + a.piece(i).orientation.z
          )*/
    }


    const val RGW = 7
    const val RBW = 3
    const val RBY = 1
    const val RGY = 5
    const val OGW = 6
    const val OBW = 2
    const val OBY = 0
    const val OGY = 4

    const val URF = 7
    const val UFL = 6
    const val ULB = 2
    const val UBR = 3
    const val DFR = 5
    const val DLF = 4
    const val DBL = 0
    const val DRB = 1
}

