package com.quicklydone.nt.cube_new

import androidx.compose.ui.geometry.Offset
import com.quicklydone.nt.common.Vec3

data class VisibleFaceNew(

    val polygon: List<Offset>,

    val normal: Vec3,

    val depth: Float,

    val cubePos: Vec3,

    val side: SideNew,

    val uAxis: Vec3,

    val vAxis: Vec3,

    val screenU: Offset,

    val screenV: Offset,
)