// ============================================================
// FILE: cube_new/FaceNew.kt
// ============================================================

package com.quicklydone.nt.cube_new

import androidx.compose.ui.graphics.Color
import com.quicklydone.nt.common.Vec3

data class FaceNew(

    val verts: List<Vec3>,

    val normal: Vec3,

    val color: Color,

    val side: SideNew
)