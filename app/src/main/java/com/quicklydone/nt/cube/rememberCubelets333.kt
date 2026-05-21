package com.quicklydone.nt.cube

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.quicklydone.nt.cube333.Cubelet

@Composable
fun rememberCubelets333(
    config: CubeConfig
) =
    remember {

        mutableStateListOf<Cubelet>().apply {

            addAll(
                CubeFactory333.createCubelets(config)
            )
        }
    }