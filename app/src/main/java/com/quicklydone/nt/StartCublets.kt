package com.quicklydone.nt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun StartCublets(goMenu: () -> Unit) {

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {

        Text("HELLO", color = Color.White)

        Button(onClick = goMenu) {
            Text("MENU")
        }
    }
}