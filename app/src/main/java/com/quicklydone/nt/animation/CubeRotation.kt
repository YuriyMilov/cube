package com.quicklydone.nt.animation

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.common.rotateAroundAxis
import com.quicklydone.nt.common.snap
import com.quicklydone.nt.render.Cubelet
import kotlinx.coroutines.delay
import kotlin.math.PI

/**
 * Анимация поворота слоя куба
 */
suspend fun rotateLayer(
    cubelets: SnapshotStateList<Cubelet>,
    axis: Vec3,
    layer: Float,
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

        if (onLayer(cube.pos, axis, layer)) {

            cube.pos = snap(
                rotateAroundAxis(
                    cube.pos,
                    axis,
                    finalAngle
                )
            )

            cube.axisX =
                rotateAroundAxis(cube.axisX, axis, finalAngle)

            cube.axisY =
                rotateAroundAxis(cube.axisY, axis, finalAngle)

            cube.axisZ =
                rotateAroundAxis(cube.axisZ, axis, finalAngle)
        }
    }

    onEnd()
}

fun onLayer(
    pos: Vec3,
    axis: Vec3,
    layer: Float
): Boolean {
    return when {
        axis.x != 0f -> pos.x == layer
        axis.y != 0f -> pos.y == layer
        else -> pos.z == layer
    }
}