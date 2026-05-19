package com.quicklydone.nt.cube

data class CubeConfig(

    val size: Int,

    val layers: List<Float>,

    val cubeletSpacing: Float = 1f
)