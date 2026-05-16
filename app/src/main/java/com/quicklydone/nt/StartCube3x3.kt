package com.quicklydone.nt

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.quicklydone.nt.model3x3.Cube3x3

@Composable
fun StartCube3x3(
goMenu: () -> Unit
) {
    val cube = remember { mutableStateOf(Cube3x3()) }
    Box(Modifier.fillMaxSize()) {

        Cube3x3Screen(
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





