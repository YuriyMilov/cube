package com.quicklydone.nt.cube

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

    if (size == 2) {
        return listOf(
            -0.5f,
            0.5f
        )
    }

    val offset =
        (size - 1) / 2f

    return List(size) { i ->
        i - offset
    }
}