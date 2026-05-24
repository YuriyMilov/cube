package com.quicklydone.nt.solver

import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.cube_new.CubeletNew

object Solver222 {
    var qq=true
    fun makeMove(
        cubelets: List<CubeletNew>,
        rotate: (Vec3, Float, Float) -> Unit
    ) {

        // анализ ситуации
        val solved = checkSolved(cubelets)

        if (!solved) {

        if(qq)
            rotate(
                Vec3(0f, 1f, 0f),
                0.5f,
                -1f
            )
            else
    rotate(
        Vec3(1f, 0f, 0f),
        0.5f,
        -1f
    )
        }
    }

    private fun checkSolved(
        cubelets: List<CubeletNew>
    ): Boolean {
        qq=!qq
        // потом сделаешь настоящую проверку
        return false
    }
}