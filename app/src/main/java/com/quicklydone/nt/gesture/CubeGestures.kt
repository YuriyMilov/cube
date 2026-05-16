package com.quicklydone.nt.gesture

import GestureState
import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.abs
import com.quicklydone.nt.model.Vec3
import com.quicklydone.nt.render.VisibleFace

fun Modifier.cubeGestures(
    state: GestureState,
    visibleFaces: List<VisibleFace>,
): Modifier {

    val logic = GestureLogic()

    return pointerInput(state) {
        detectDragGestures(

            onDragStart = { offset ->

                val facesSnapshot = visibleFaces.toList()

                val face = logic.detectFaceHit(offset, facesSnapshot)

                state.lockedFace = face
                state.dragLocked = false

               // val face3 = state.pick()
               // Log.d("cube", "----- ContolCube  ---->  $face3")
                val face4 = state.pick2(offset)
                Log.d("cube", "----- InputCube face 2  ---->  $face4")

            },

            onDragEnd = {
                state.lockedFace = null
                state.dragLocked = false


            },

            onDragCancel = {
                state.lockedFace = null
                state.dragLocked = false
            },

            onDrag = { change, drag ->
                change.consume()
                if (state.dragLocked) return@detectDragGestures
                val face = state.lockedFace
                if (face == null) {
                    state.rotateAll(drag.x, drag.y)
                    return@detectDragGestures
                }
                val p = logic.projectDrag(
                    dx = drag.x,
                    dy = drag.y,
                    face = face,
                )
                val du = p.u
                val dv = p.v
                state.dragLocked = true
                val horizontal = abs(du) > abs(dv)
                val result = when (face.side) {
                    Side.FRONT,
                    Side.BACK -> {
                        if (horizontal) {
                            if (du > 0f)
                                Triple(Vec3(0f, 1f, 0f), face.cubePos.y, 1f)
                            else
                                Triple(Vec3(0f, 1f, 0f), face.cubePos.y, -1f)
                        } else {
                            if (dv > 0f)
                                Triple(Vec3(1f, 0f, 0f), face.cubePos.x, -1f)
                            else
                                Triple(Vec3(1f, 0f, 0f), face.cubePos.x, 1f)
                        }
                    }

                    Side.LEFT,
                    Side.RIGHT -> {

                        if (horizontal) {
                            if (du > 0f)
                                Triple(Vec3(0f, 0f, 1f), face.cubePos.z, -1f)
                            else
                                Triple(Vec3(0f, 0f, 1f), face.cubePos.z, 1f)
                        } else {
                            if (dv > 0f)
                                Triple(Vec3(0f, 1f, 0f), face.cubePos.y, -1f)
                            else
                                Triple(Vec3(0f, 1f, 0f), face.cubePos.y, 1f)
                        }
                    }

                    Side.TOP,
                    Side.BOTTOM -> {
                        if (horizontal) {
                            if (du > 0f)
                                Triple(Vec3(1f, 0f, 0f), face.cubePos.x, 1f)
                            else
                                Triple(Vec3(1f, 0f, 0f), face.cubePos.x, -1f)

                        } else {
                            if (dv > 0f)
                                Triple(Vec3(0f, 0f, 1f), face.cubePos.z, 1f)
                            else
                                Triple(Vec3(0f, 0f, 1f), face.cubePos.z, -1f)
                        }
                    }
                }

                state.startRotation(
                    result.first,
                    result.second,
                    result.third,
                )
            },
        )
    }
}