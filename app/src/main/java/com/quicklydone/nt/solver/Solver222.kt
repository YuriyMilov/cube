package com.quicklydone.nt.solver


import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.common.GestureState222


object Solver222 {

    fun righta(
        rotate: (Vec3, Float, Float) -> Unit
    ) {
            rotate(
                Vec3(1f, 0f, 0f),
                0.5f,
                -1f
            )
        }

    fun rightb(rotate: (Vec3, Float, Float) -> Unit) {
        rotate(
            Vec3(1f, 0f, 0f),
            0.5f,
            1f
        )
    }

    fun lefta(
        rotate: (Vec3, Float, Float) -> Unit
    ) {
        rotate(
            Vec3(1f, 0f, 0f),
            -0.5f,
            1f
        )
    }

    fun leftb(rotate: (Vec3, Float, Float) -> Unit) {
        rotate(
            Vec3(1f, 0f, 0f),
            -0.5f,
            -1f
        )
    }


    fun upa(rotate: (Vec3, Float, Float) -> Unit) {
        rotate(
            Vec3(0f, 1f, 0f),
            0.5f,
            -1f
        )
    }



    fun upb(rotate: (Vec3, Float, Float) -> Unit) {
        rotate(
            Vec3(0f, 1f, 0f),
            0.5f,
            1f
        )
    }


    fun downa(rotate: (Vec3, Float, Float) -> Unit) {
        rotate(
            Vec3(0f, 1f, 0f),
            -0.5f,
            1f
        )
    }



    fun downb(rotate: (Vec3, Float, Float) -> Unit) {
        rotate(
            Vec3(0f, 1f, 0f),
            -0.5f,
            -1f
        )
    }


    fun forwarda(rotate: (Vec3, Float, Float) -> Unit) {
        rotate(
            Vec3(0f, 0f, 1f),
            0.5f,
            -1f
        )
    }


    fun forwardb(rotate: (Vec3, Float, Float) -> Unit) {
        rotate(
            Vec3(0f, 0f, 1f),
            0.5f,
            1f
        )
    }

    fun backa(rotate: (Vec3, Float, Float) -> Unit) {
        rotate(
            Vec3(0f, 0f, 1f),
            -0.5f,
            1f
        )
    }


    fun backb(rotate: (Vec3, Float, Float) -> Unit) {
        rotate(
            Vec3(0f, 0f, 1f),
            -0.5f,
            -1f
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

