package com.quicklydone.nt

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.quicklydone.nt.gestures2x2.CubeGestureController2x2
import com.quicklydone.nt.model2x2.Cube2x2
import com.quicklydone.nt.model2x2.Cube2x2Rotator
import com.quicklydone.nt.model2x2.Move2x2
import com.quicklydone.nt.render2x2.Cube2x2View

@Composable
fun Cube2x2Screen(cube: Cube2x2) {

    var yaw by remember { mutableStateOf(0.8f) }
    var pitch by remember { mutableStateOf(0.5f) }
    var qq by remember { mutableStateOf("") }
    val controller = remember { CubeGestureController2x2(cube) }

    fun qq() {
        if (qq == "") qq = " "
        else qq = ""
    }

    fun resetAll() {
        cube.reset()
        qq()
        yaw = 0.8f
        pitch = 0.5f
        //movesCount = 0
    }
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {

                Cube2x2View(
                    cube = cube,
                    yaw = yaw,
                    pitch = pitch,
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {

                            detectDragGestures(
                                onDragStart = { offset ->
                                    controller.onDragStart(
                                        offset,
                                        yaw,
                                        pitch,
                                        size.width.toFloat(),
                                        size.height.toFloat()
                                    )
                                },

                                onDrag = { change, dragAmount ->

                                    controller.onDrag(
                                        change.position,   // ✔ OK
                                        dragAmount.x,
                                        dragAmount.y,
                                        yaw,
                                        pitch,
                                        size.width.toFloat(),
                                        size.height.toFloat()
                                    ) { dx, dy ->
                                        yaw -= dx * 0.01f
                                        pitch += dy * 0.01f
                                    }

                                    // qq()
                                },

                                onDragEnd = {
                                    controller.onDragEnd()
                                    //movesCount++
                                    qq()

                                })
                        })
            }


            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

                Row(
                     Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start
                ) {

                    Button(onClick = { resetAll() }) {
                        Text("RESET")
                    }
                    Text(
                        text = qq, color = Color.Green
                    )
                }

                Row(
                    Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Button(
                        {
                            Cube2x2Rotator.apply(cube, Move2x2(3, 0, 1))
                            qq()
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red, contentColor = Color.White
                        )
                    ) { Text("↻") }

                    Button(
                        {
                            Cube2x2Rotator.apply(cube, Move2x2(3, 0, -1))
                            qq()
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red, contentColor = Color.White
                        )
                    ) { Text("↺") }

                    Button(
                        {
                            Cube2x2Rotator.apply(cube, Move2x2(2, 0, 1))
                            qq()
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF7500), contentColor = Color.Black
                        )
                    ) { Text("↻") }

                    Button(
                        {
                            Cube2x2Rotator.apply(cube, Move2x2(2, 0, -1))
                            qq()
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF7500), contentColor = Color.Black
                        )
                    ) { Text("↺") }
                }

                // кнопки оставляем как есть
                Row(
                    Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            Cube2x2Rotator.apply(cube, Move2x2(1, 0, 1))
                            qq()
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Blue, contentColor = Color.White
                        )
                    ) {
                        Text("↻")
                    }
                    Button(
                        {
                            Cube2x2Rotator.apply(cube, Move2x2(1, 0, -1))
                            qq()
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Blue, contentColor = Color.White
                        )
                    ) { Text("↺") }
                    Button(
                        {
                            Cube2x2Rotator.apply(cube, Move2x2(0, 0, 1))
                            qq()
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Green, contentColor = Color.Black
                        )
                    ) { Text("↻") }
                    Button(
                        {
                            Cube2x2Rotator.apply(cube, Move2x2(0, 0, -1))
                            qq()
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Green, contentColor = Color.Black
                        )
                    ) { Text("↺") }
                }

                Row(
                    Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        {
                            Cube2x2Rotator.apply(cube, Move2x2(4, 0, 1))
                            qq()
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White, contentColor = Color.Black
                        )
                    ) { Text("↻") }
                    Button(
                        {
                            Cube2x2Rotator.apply(cube, Move2x2(4, 0, -1))
                            qq()
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White, contentColor = Color.Black
                        )
                    ) { Text("↺") }
                    Button(
                        {
                            Cube2x2Rotator.apply(cube, Move2x2(5, 0, 1))
                            qq()
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Yellow, contentColor = Color.Black
                        )
                    ) { Text("↻") }
                    Button(
                        {
                            Cube2x2Rotator.apply(cube, Move2x2(5, 0, -1))
                            qq()
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Yellow, contentColor = Color.Black
                        )
                    ) { Text("↺") }
                }
            }
        }
    }
}
//0 -> Color.Green
//1 -> Color(0xFF1C5CF0) //Color.Blue
//2 -> Color(0xFFFFA500) // Orange
//3 -> Color.Red
//4 -> Color.White
//5 -> Color.Yellow