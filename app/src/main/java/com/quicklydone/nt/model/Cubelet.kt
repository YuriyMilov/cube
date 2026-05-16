package com.quicklydone.nt.model


import androidx.compose.ui.graphics.Color

data class Cubelet(

    var pos: Vec3,

    var up: Color?,
    var down: Color?,
    var left: Color?,
    var right: Color?,
    var front: Color?,
    var back: Color?,

    var axisX: Vec3 = Vec3(1f,0f,0f),
    var axisY: Vec3 = Vec3(0f,1f,0f),
    var axisZ: Vec3 = Vec3(0f,0f,1f),
)


fun createInitialCubelets(): List<Cubelet> {

    val result = mutableListOf<Cubelet>()

    for (x in listOf(-1f, 1f))
        for (y in listOf(-1f, 1f))
            for (z in listOf(-1f, 1f)) {

                result.add(
                    Cubelet(
                        pos = Vec3(x, y, z),

                        up = if (y == 1f) Color.White else null,
                        down = if (y == -1f) Color.Yellow else null,

                        left = if (x == -1f) Color(0xFFFFA500) else null,
                        right = if (x == 1f) Color.Red else null,

                        front = if (z == 1f) Color.Green else null,
                        back = if (z == -1f) Color.Blue else null,
                    )
                )
            }

    return result
}


enum class Move {
    L, L_PRIME,
    R, R_PRIME,
    U, U_PRIME,
    D, D_PRIME,
    F, F_PRIME,
    B, B_PRIME
}