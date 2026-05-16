package com.quicklydone.nt.zzz.rubikgrade

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import com.quicklydone.nt.model.Vec3
import kotlin.math.abs
import com.quicklydone.nt.model.dot


class RubikGestureState(



    val detectFaceHit: (Offset) -> HitFace?,

    val rotateAll: (Float, Float) -> Unit,

    val startRotation: (
        Vec3,
        Float,
        Float
    ) -> Unit,

    val isAnimating: () -> Boolean,

    val getRotY: () -> Float
) {
    var lockedFace: HitFace? = null
}

data class HitFace(
    val axis: Vec3,
    val layer: Float,
    val uAxis: Vec3,
    val vAxis: Vec3
)

fun Modifier.rubikGestures(
    state: RubikGestureState
): Modifier = pointerInput(Unit) {

    detectDragGestures(

        onDragStart = { offset ->
            state.lockedFace = state.detectFaceHit(offset)
        },

        onDrag = { change, drag ->

            change.consume()

            if (state.isAnimating())
                return@detectDragGestures

            val face = state.lockedFace
                ?: return@detectDragGestures

            // -----------------------------
            // LEGACY CAMERA ROTATION
            // -----------------------------
            if (face.uAxis == null || face.vAxis == null) {
                state.rotateAll(drag.x, drag.y)
                return@detectDragGestures
            }

            // -----------------------------
            // swipe vector
            // -----------------------------
            val swipe = Vec3(drag.x, drag.y, 0f)

            val u = swipe.dot(face.uAxis)
            val v = swipe.dot(face.vAxis)

            val absU = abs(u)
            val absV = abs(v)

            if (absU < 5f && absV < 5f)
                return@detectDragGestures

            // -----------------------------
            // decide direction
            // -----------------------------
            val (axis, layer, dir) = if (absU > absV) {

                // swipe along U → rotate around V
                Triple(
                    face.vAxis,
                    face.layer,
                    if (u > 0f) 1f else -1f
                )

            } else {

                // swipe along V → rotate around U
                Triple(
                    face.uAxis,
                    face.layer,
                    if (v > 0f) 1f else -1f
                )
            }

            state.startRotation(axis, layer, dir)
        }
    )
}
