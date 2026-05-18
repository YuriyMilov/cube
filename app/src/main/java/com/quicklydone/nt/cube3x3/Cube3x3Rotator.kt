package com.quicklydone.nt.model3x3


data class Move3x3(
    val face: Int,   // 0..5
    val layer: Int = 0, // для 4x4+
    val dir: Int   // 1 или -1
)


object Cube3x3Rotator {

    fun apply(cube: Cube3x3, move: Move3x3) {

        // Red U
        if (move.face == 3 && move.dir == 1) cube.applyMove33(Cube3x3.U_PERM)
        if (move.face == 3 && move.dir == -1) cube.applyMove33(Cube3x3.U_PERM_BACK)

        // Orange D
        if (move.face == 2 && move.dir == 1) cube.applyMove33(Cube3x3.D_PERM)
        if (move.face == 2 && move.dir == -1) cube.applyMove33(Cube3x3.D_PERM_BACK)


        // Blue L
        if (move.face == 1 && move.dir == 1) cube.applyMove33(Cube3x3.L_PERM)
        if (move.face == 1 && move.dir == -1) cube.applyMove33(Cube3x3.L_PERM_BACK)

        // Green R
        if (move.face == 0 && move.dir == 1) cube.applyMove33(Cube3x3.R_PERM)
        if (move.face == 0 && move.dir == -1) cube.applyMove33(Cube3x3.R_PERM_BACK)


        // White F
        if (move.face == 4 && move.dir == 1) cube.applyMove33(Cube3x3.F_PERM)
        if (move.face == 4 && move.dir == -1) cube.applyMove33(Cube3x3.F_PERM_BACK)

        // Yellow B
        if (move.face == 5 && move.dir == 1) cube.applyMove33(Cube3x3.B_PERM)
        if (move.face == 5 && move.dir == -1) cube.applyMove33(Cube3x3.B_PERM_BACK)



        if (move.dir == 0) return


        //   0 -> Color.Green
        //   1 -> Color(0xFF1C5CF0) //Color.Blue
        //   2 -> Color(0xFFFFA500) // ← оранжевый
        //   3 -> Color.Red
        //  4 -> Color.White
        //  5 -> Color.Yellow


    }
}




