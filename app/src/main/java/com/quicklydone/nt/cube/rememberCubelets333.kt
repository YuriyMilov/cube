package com.quicklydone.nt.cube

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.quicklydone.nt.cube.Cubelet
import com.quicklydone.nt.cube_new.CubeletNew

@Composable
fun rememberCubelets333(
    config: CubeConfig
) =
    remember {

        mutableStateListOf<CubeletNew>().apply {

            addAll(
                CubeFactory.createCubelets(config)
            )
        }
    }