package com.quicklydone.nt.cube

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.quicklydone.nt.cube444.Cubelet

@Composable
fun rememberCubelets444(
    config: CubeConfig444
) =
    remember {

        mutableStateListOf<Cubelet>().apply {

            addAll(
                CubeFactory444.createCubelets444(config)
            )
        }
    }
