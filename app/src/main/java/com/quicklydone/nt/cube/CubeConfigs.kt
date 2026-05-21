package com.quicklydone.nt.cube

object CubeConfigs {

    val Cube222 = CubeConfig(

        size = 2,

        layers = listOf(
            -1f,
            1f
        )
    )

    val Cube333 = CubeConfig(

        size = 3,

        layers = listOf(
            -1f,
            0f,
            1f
        )
    )

    val Cube444 = CubeConfig444(

        size = 4, //size нигде не применяется

        layers = listOf(
          -1.5f,-0.5f,0.5f,1.5f
           // -2f,-1f,0f,1f,2f
           // -1f,0f,1f
        )
    )
}