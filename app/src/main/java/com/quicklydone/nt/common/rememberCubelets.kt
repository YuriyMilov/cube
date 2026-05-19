package com.quicklydone.nt.cube

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.quicklydone.nt.cube222.Cubelet

@Composable
fun rememberCubelets(
    config: CubeConfig
) =
    remember {

        mutableStateListOf<Cubelet>().apply {

            addAll(
                CubeFactory.createCubelets(config)
            )
        }
    }