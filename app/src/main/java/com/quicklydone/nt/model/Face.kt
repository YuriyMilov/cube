package com.quicklydone.nt.model

import androidx.compose.ui.graphics.Color
import com.quicklydone.nt.gesture.HitFace
import com.quicklydone.nt.gesture.Side


data class Face(
    val verts: List<Vec3>,
    val normal: Vec3,
    val color: Color,

    val side: Side,   // 👈 ВОТ ЭТО КЛЮЧЕВО
)

