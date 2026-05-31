package com.quicklydone.nt.cube_new

import androidx.compose.ui.graphics.Color
import com.quicklydone.nt.common.Vec3
import com.quicklydone.nt.cube222.InputCube222

data class FaceNew(

    val verts: List<Vec3>,

    val normal: Vec3,

    val color: Color,

    val side: SideNew
)

data class FaceMarkerNew(
    val side: SideNew? = null,
    val cubePos: Vec3? = null,

    val face: InputCube222.Face? = null,
    val row: Int? = null,
    val col: Int? = null,

    val color: Color = Color.Black,
    val radius: Float = 10f,
    val arrow: ArrowDirNew? = null,
    val layer: Float? = null,
    val count: Int = 1,
    val spacing: Float = 1.8f
)