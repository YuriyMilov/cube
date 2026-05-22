package com.quicklydone.nt.cube

import androidx.compose.ui.graphics.Color
import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.cube_new.CubeletNew

object CubeFactory {

    fun createCubelets(
        config: CubeConfig
    ): List<CubeletNew> {

        val result = mutableListOf<CubeletNew>()

        for (x in config.layers)
            for (y in config.layers)
                for (z in config.layers) {

                    result.add(

                        CubeletNew(

                            pos = Vec3(x, y, z),

                            up =
                                if (y == config.layers.last())
                                    Color.White
                                else null,

                            down =
                                if (y == config.layers.first())
                                    Color.Yellow
                                else null,

                            left =
                                if (x == config.layers.first())
                                    Color(0xFFFF8800)
                                else null,

                            right =
                                if (x == config.layers.last())
                                    Color.Red
                                else null,

                            front =
                                if (z == config.layers.last())
                                    Color.Green
                                else null,

                            back =
                                if (z == config.layers.first())
                                    Color.Blue
                                else null,
                        )
                    )
                }

        return result
    }
}