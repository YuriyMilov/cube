package com.quicklydone.nt.cube

object Params {

    const val SCALE = 1200f
    const val CAMERA_DISTANCE_222 = 12f
    const val CAMERA_DISTANCE_333 = 18f
    const val CAMERA_DISTANCE_444 = 24f
}

data class CubeConfig(

    val size: Int,

    val cubeletSpacing: Float = 2f,

    val cubeletSize: Float = 1.85f,

    val scale: Float = 1200f,

    val cameraDistance: Float = when(size) {

        2 -> 12f

        3 -> 18f

        4 -> 24f

        else -> size * 6f
    }
) {

    val layers =
        generateLayers(size)

  }

fun generateLayers(
    size: Int
): List<Float> {

    val offset =
        (size - 1) / 2f

    return List(size) { i ->
        i - offset
    }
}

