package com.quicklydone.nt.solver


import androidx.compose.ui.graphics.Color
import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.common.GestureState222
import com.quicklydone.nt.cube222.InputCube222
import com.quicklydone.nt.cube_new.ArrowDirNew
import com.quicklydone.nt.cube_new.FaceMarkerNew


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
    fun addRingHintR(
        markers: MutableList<FaceMarkerNew>
    ) {

        markers.clear()

        // TOP
        markers += FaceMarkerNew(
            face = InputCube222.Face.TOP,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.TOP,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        // FRONT
        markers += FaceMarkerNew(
            face = InputCube222.Face.FRONT,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.FRONT,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        // BOTTOM
        markers += FaceMarkerNew(
            face = InputCube222.Face.BOTTOM,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.BOTTOM,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        // BACK
        markers += FaceMarkerNew(
            face = InputCube222.Face.BACK,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.BACK,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )
    }

}

