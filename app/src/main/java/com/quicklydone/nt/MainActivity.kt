package com.quicklydone.nt

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            androidx.compose.material3.Surface(
                modifier = Modifier.fillMaxSize(), color = Color.Black
            ) {
                AppRoot()
                //Cube2x2Screen()
                //Cube3x3Screen()
            }
        }
    }
}

//////////////////////////////////////////////////////////
// MATH
//////////////////////////////////////////////////////////

data class V3(val x: Float, val y: Float, val z: Float)

fun rotY(v: V3, a: Float): V3 {
    val c = cos(a)
    val s = sin(a)
    return V3(v.x * c + v.z * s, v.y, -v.x * s + v.z * c)
}

fun rotX(v: V3, a: Float): V3 {
    val c = cos(a)
    val s = sin(a)
    return V3(v.x, v.y * c - v.z * s, v.y * s + v.z * c)
}

fun dot(a: V3, b: V3) = a.x * b.x + a.y * b.y + a.z * b.z

fun project(v: V3, c: Offset, s: Float) = Offset(c.x + v.x * s, c.y - v.y * s)

//////////////////////////////////////////////////////////
// FACES
//////////////////////////////////////////////////////////

data class Face2(val id: Int, val n: V3, val r: V3, val u: V3)

val faces2 = listOf(
    Face2(0, V3(0f, 1f, 0f), V3(1f, 0f, 0f), V3(0f, 0f, -1f)),
    Face2(1, V3(0f, -1f, 0f), V3(1f, 0f, 0f), V3(0f, 0f, 1f)),
    Face2(2, V3(0f, 0f, 1f), V3(1f, 0f, 0f), V3(0f, 1f, 0f)),
    Face2(3, V3(0f, 0f, -1f), V3(1f, 0f, 0f), V3(0f, 1f, 0f)),
    Face2(4, V3(-1f, 0f, 0f), V3(0f, 0f, 1f), V3(0f, 1f, 0f)),
    Face2(5, V3(1f, 0f, 0f), V3(0f, 0f, -1f), V3(0f, 1f, 0f))
)

val colors = listOf(
    Color.White, Color.Yellow, Color.Green, Color.Blue, Color(0xFFFF9800), Color.Red
)