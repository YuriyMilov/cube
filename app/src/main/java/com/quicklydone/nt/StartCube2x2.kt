package com.quicklydone.nt

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.quicklydone.nt.model2x2.Cube2x2

@Composable
fun StartCube2x2(
    goMenu: () -> Unit
) {
    val cube = remember { mutableStateOf(Cube2x2()) }
    Box(Modifier.fillMaxSize()) {

        Cube2x2Screen(
            cube = cube.value,
        )

        Button(
            onClick = goMenu,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Text("MENU")
        }
    }
}