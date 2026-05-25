package com.quicklydone.nt.solver


import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.common.GestureState222


object Solver222 {

    fun makeMove1(
        rotate: (Vec3, Float, Float) -> Unit
    ) {
            rotate(
                Vec3(1f, 0f, 0f),
                0.5f,
                -1f
            )
        }

    fun makeMove2(rotate: (Vec3, Float, Float) -> Unit) {
        rotate(
            Vec3(1f, 0f, 0f),
            0.5f,
            1f
        )
    }

    fun makeMove3(
        rotate: (Vec3, Float, Float) -> Unit
    ) {
        rotate(
            Vec3(1f, 0f, 0f),
            -0.5f,
            -1f
        )
    }

    fun makeMove4(rotate: (Vec3, Float, Float) -> Unit) {
        rotate(
            Vec3(1f, 0f, 0f),
            -0.5f,
            1f
        )
    }

    fun test(gestureState: GestureState222, rotate: (Vec3, Float, Float) -> Unit) {


        //gestureState.dragLocked = true

        rotate(
            Vec3(1f, 0f, 0f),
            -0.5f,
            1f
        )

        rotate(
            Vec3(0f, 1f, 0f),
            0.5f,
            1f
        )

        rotate(
                Vec3(0f, 0f, 1f),
        0.5f,
        1f
        )

        //gestureState.dragLocked = false
    }


}

