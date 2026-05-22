package com.quicklydone.nt.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.quicklydone.nt.cube.CubeConfig
import com.quicklydone.nt.cube.CubeFactory
import com.quicklydone.nt.cube_new.CubeletNew

//import com.quicklydone.nt.cube.Cubelet

@Composable
fun rememberCubelets(
    config: CubeConfig
) =
    remember {

        mutableStateListOf<CubeletNew>().apply {

            addAll(
                CubeFactory.createCubelets(config)
            )
        }
    }