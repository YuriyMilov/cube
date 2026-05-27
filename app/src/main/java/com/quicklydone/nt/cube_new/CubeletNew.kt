// ============================================================
// FILE: cube_new/CubeletNew.kt
// ============================================================

package com.quicklydone.nt.cube_new

import androidx.compose.ui.graphics.Color
import com.quicklydone.nt.common.Vec3

data class CubeletNew(
    val id: Int,
    var pos: Vec3,

    var up: Color?,
    var down: Color?,

    var left: Color?,
    var right: Color?,

    var front: Color?,
    var back: Color?,

    var axisX: Vec3 = Vec3(1f, 0f, 0f),
    var axisY: Vec3 = Vec3(0f, 1f, 0f),
    var axisZ: Vec3 = Vec3(0f, 0f, 1f),
)
/*
data class CubeletNew(
    val id: Int,

    val pos: Vec3,

    val up: Color?,
    val down: Color?,
    val left: Color?,
    val right: Color?,
    val front: Color?,
    val back: Color?
)*/
