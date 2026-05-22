package com.quicklydone.nt.animation

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.common.onLayer
import com.quicklydone.nt.common.rotateAroundAxis
import com.quicklydone.nt.common.snap222
import com.quicklydone.nt.cube_new.CubeletNew
import kotlinx.coroutines.delay
import kotlin.math.PI

/**
 * Анимация поворота слоя куба
 */
suspend fun rotateLayer222(
    cubelets: SnapshotStateList<CubeletNew>,
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

        Log.d(
            "qq",
            "axis=$axis layer=$layer pos=${cube.pos}"
        )
        if (onLayer(cube.pos, axis, layer)) {

            cube.pos = snap222(
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
/*

fun onLayer444(
    pos: Vec3,
    axis: Vec3,
    layer: Float
): Boolean {

    //Log.d("qq", "pos=${pos.x} layer=$layer")
    val value =
        when {
            axis.x != 0f -> pos.x
            axis.y != 0f -> pos.y
            else -> pos.z
        }


    */
/*
        val value =
            when {
                kotlin.math.abs(axis.x) > 0.9f -> pos.x
                kotlin.math.abs(axis.y) > 0.9f -> pos.y
                else -> pos.z
            }
    *//*


    return abs(value - layer) < 0.01f
}
*/
