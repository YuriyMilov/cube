package com.quicklydone.nt.animation

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.common.onLayer
import com.quicklydone.nt.common.rotateAroundAxis
import com.quicklydone.nt.common.snap222
import com.quicklydone.nt.cube_new.CubeletNew
import kotlinx.coroutines.delay
import kotlin.math.PI

suspend fun rotateLayer222TwoLayers(
    cubelets: SnapshotStateList<CubeletNew>,
    axis: Vec3,
    layer1: Float,
    layer2: Float,
    dir: Float,
    onStart: () -> Unit,
    onStep: (Float) -> Unit,
    onEnd: () -> Unit
) {

    onStart()

    val steps = 20
    val stepAngle =
        (PI.toFloat() / 2f) / steps * dir

    var angle = 0f

    repeat(steps) {

        angle += stepAngle
        onStep(angle)

        delay(16)
    }

    val finalAngle =
        dir * (PI.toFloat() / 2f)

    cubelets.forEach { cube ->

        if (
            onLayer(cube.pos, axis, layer1) ||
            onLayer(cube.pos, axis, layer2)
        ) {

            cube.pos = snap222(
                rotateAroundAxis(
                    cube.pos,
                    axis,
                    finalAngle
                )
            )

            cube.axisX =
                rotateAroundAxis(
                    cube.axisX,
                    axis,
                    finalAngle
                )

            cube.axisY =
                rotateAroundAxis(
                    cube.axisY,
                    axis,
                    finalAngle
                )

            cube.axisZ =
                rotateAroundAxis(
                    cube.axisZ,
                    axis,
                    finalAngle
                )
        }
    }

    onEnd()
}