package com.quicklydone.nt

import CubletsScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppRoot() {

    var screen by remember { mutableStateOf("menu") }

    Spacer(Modifier.width(18.dp))

    when (screen) {

        "menu" -> Column(Modifier.fillMaxSize()) {
            Spacer(Modifier.width(18.dp))
            Button(onClick = { screen = "Cublets" }) {
                Text("--- CubletsScreen ---")
            }
            Spacer(Modifier.width(18.dp))
            Button(onClick = { screen = "Cube2" }) {
                Text("2x2")
            }
            Spacer(Modifier.width(18.dp))
            Button(onClick = { screen = "Cube3" }) {
                Text("3x3")

            }

        }



        "Cublets" -> {
            CubletsScreen(
                goMenu = {
                    screen = "menu"
                })
        }


        "Cube2" -> {
            StartCube2x2(
                goMenu = {
                    screen = "menu"
                })
        }


        "Cube3" -> {
            StartCube3x3(
                goMenu = {
                    screen = "menu"
                })
        }
    }
}
