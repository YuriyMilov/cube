package com.quicklydone.nt.solver

import androidx.compose.ui.graphics.Color
import com.quicklydone.nt.cube222.InputCube222
import com.quicklydone.nt.cube_new.ArrowDirNew
import com.quicklydone.nt.cube_new.FaceMarkerNew

object Arrows222 {
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

