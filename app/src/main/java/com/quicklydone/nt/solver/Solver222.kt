package com.quicklydone.nt.solver


import androidx.compose.ui.graphics.Color
import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.common.GestureState222
import com.quicklydone.nt.cube222.InputCube222
import com.quicklydone.nt.cube_new.ArrowDirNew
import com.quicklydone.nt.cube_new.FaceMarkerNew


object Solver222 {
/*Button(
                onClick = {

                    Solver222.downa(
                        rotate = ::startRotation
                    )
                }) {
                Text("D")
            }
            Button(
                onClick = {

                    Solver222.downb(
                        rotate = ::startRotation
                    )
                }) {
                Text("D'")
            }*/
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
    /*fun addRingHintR(
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
    }*/

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

    fun addRingHintRPrime(
        markers: MutableList<FaceMarkerNew>
    ) {

        markers.clear()

        // TOP
        markers += FaceMarkerNew(
            face = InputCube222.Face.TOP,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.TOP,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        // FRONT
        markers += FaceMarkerNew(
            face = InputCube222.Face.FRONT,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.FRONT,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        // BOTTOM
        markers += FaceMarkerNew(
            face = InputCube222.Face.BOTTOM,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.BOTTOM,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        // BACK
        markers += FaceMarkerNew(
            face = InputCube222.Face.BACK,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.BACK,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )
    }


    fun addRingHintL(
        markers: MutableList<FaceMarkerNew>
    ) {

        markers.clear()

        // TOP
        markers += FaceMarkerNew(
            face = InputCube222.Face.TOP,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.TOP,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        // FRONT
        markers += FaceMarkerNew(
            face = InputCube222.Face.FRONT,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.FRONT,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        // BOTTOM
        markers += FaceMarkerNew(
            face = InputCube222.Face.BOTTOM,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.BOTTOM,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        // BACK
        markers += FaceMarkerNew(
            face = InputCube222.Face.BACK,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.BACK,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )
    }

    fun addRingHintLPrime(
        markers: MutableList<FaceMarkerNew>
    ) {

        markers.clear()

        // TOP
        markers += FaceMarkerNew(
            face = InputCube222.Face.TOP,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.TOP,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        // FRONT
        markers += FaceMarkerNew(
            face = InputCube222.Face.FRONT,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.FRONT,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        // BOTTOM
        markers += FaceMarkerNew(
            face = InputCube222.Face.BOTTOM,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.BOTTOM,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        // BACK
        markers += FaceMarkerNew(
            face = InputCube222.Face.BACK,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.BACK,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )
    }


    fun addRingHintD(
        markers: MutableList<FaceMarkerNew>
    ) {

        markers.clear()

        // FRONT
        markers += FaceMarkerNew(
            face = InputCube222.Face.FRONT,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.FRONT,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        // RIGHT
        markers += FaceMarkerNew(
            face = InputCube222.Face.RIGHT,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.RIGHT,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        // BACK
        markers += FaceMarkerNew(
            face = InputCube222.Face.BACK,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.BACK,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        // LEFT
        markers += FaceMarkerNew(
            face = InputCube222.Face.LEFT,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.LEFT,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )
    }

    fun addRingHintDPrime(
        markers: MutableList<FaceMarkerNew>
    ) {

        markers.clear()

        // FRONT
        markers += FaceMarkerNew(
            face = InputCube222.Face.FRONT,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.FRONT,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        // RIGHT
        markers += FaceMarkerNew(
            face = InputCube222.Face.RIGHT,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.RIGHT,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        // BACK
        markers += FaceMarkerNew(
            face = InputCube222.Face.BACK,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.BACK,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        // LEFT
        markers += FaceMarkerNew(
            face = InputCube222.Face.LEFT,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.LEFT,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )
    }

    fun addRingHintU(
        markers: MutableList<FaceMarkerNew>
    ) {

        markers.clear()

        // FRONT
        markers += FaceMarkerNew(
            face = InputCube222.Face.FRONT,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.FRONT,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        // RIGHT
        markers += FaceMarkerNew(
            face = InputCube222.Face.RIGHT,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.RIGHT,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        // BACK
        markers += FaceMarkerNew(
            face = InputCube222.Face.BACK,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.BACK,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        // LEFT
        markers += FaceMarkerNew(
            face = InputCube222.Face.LEFT,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.LEFT,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )
    }

    fun addRingHintUPrime(
        markers: MutableList<FaceMarkerNew>
    ) {

        markers.clear()

        // FRONT
        markers += FaceMarkerNew(
            face = InputCube222.Face.FRONT,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.FRONT,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        // RIGHT
        markers += FaceMarkerNew(
            face = InputCube222.Face.RIGHT,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.RIGHT,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        // BACK
        markers += FaceMarkerNew(
            face = InputCube222.Face.BACK,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.BACK,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        // LEFT
        markers += FaceMarkerNew(
            face = InputCube222.Face.LEFT,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.LEFT,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )
    }

    fun addRingHintF(
        markers: MutableList<FaceMarkerNew>
    ) {

        markers.clear()

        // TOP
        markers += FaceMarkerNew(
            face = InputCube222.Face.TOP,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.TOP,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        // RIGHT
        markers += FaceMarkerNew(
            face = InputCube222.Face.RIGHT,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.RIGHT,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        // BOTTOM
        markers += FaceMarkerNew(
            face = InputCube222.Face.BOTTOM,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.BOTTOM,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        // LEFT
        markers += FaceMarkerNew(
            face = InputCube222.Face.LEFT,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.LEFT,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )
    }

    fun addRingHintFPrime(
        markers: MutableList<FaceMarkerNew>
    ) {

        markers.clear()

        // TOP
        markers += FaceMarkerNew(
            face = InputCube222.Face.TOP,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.TOP,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        // RIGHT
        markers += FaceMarkerNew(
            face = InputCube222.Face.RIGHT,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.RIGHT,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        // BOTTOM
        markers += FaceMarkerNew(
            face = InputCube222.Face.BOTTOM,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.BOTTOM,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        // LEFT
        markers += FaceMarkerNew(
            face = InputCube222.Face.LEFT,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.LEFT,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )
    }

    fun addRingHintB(
        markers: MutableList<FaceMarkerNew>
    ) {

        markers.clear()

        // TOP
        markers += FaceMarkerNew(
            face = InputCube222.Face.TOP,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.TOP,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        // RIGHT
        markers += FaceMarkerNew(
            face = InputCube222.Face.RIGHT,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.RIGHT,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        // BOTTOM
        markers += FaceMarkerNew(
            face = InputCube222.Face.BOTTOM,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.BOTTOM,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        // LEFT
        markers += FaceMarkerNew(
            face = InputCube222.Face.LEFT,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.LEFT,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )
    }

    fun addRingHintBPrime(
        markers: MutableList<FaceMarkerNew>
    ) {

        markers.clear()

        // TOP
        markers += FaceMarkerNew(
            face = InputCube222.Face.TOP,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.TOP,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.POS_U,
            color = Color.Black
        )

        // RIGHT
        markers += FaceMarkerNew(
            face = InputCube222.Face.RIGHT,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.RIGHT,
            row = 1,
            col = 1,
            arrow = ArrowDirNew.NEG_V,
            color = Color.Black
        )

        // BOTTOM
        markers += FaceMarkerNew(
            face = InputCube222.Face.BOTTOM,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.BOTTOM,
            row = 0,
            col = 1,
            arrow = ArrowDirNew.NEG_U,
            color = Color.Black
        )

        // LEFT
        markers += FaceMarkerNew(
            face = InputCube222.Face.LEFT,
            row = 0,
            col = 0,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )

        markers += FaceMarkerNew(
            face = InputCube222.Face.LEFT,
            row = 1,
            col = 0,
            arrow = ArrowDirNew.POS_V,
            color = Color.Black
        )
    }

}
/*Button( onClick = {  addRingHintR(markers)  } ) {  Text("R") }*/
