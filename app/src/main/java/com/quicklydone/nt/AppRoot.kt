package com.quicklydone.nt


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.quicklydone.nt.cube222.Cube222Screen
import com.quicklydone.nt.cube333.Cube333Screen
import com.quicklydone.nt.cube444.Cube444Screen

// ======================================================
// APP ROOT
// ======================================================

private enum class Screen {
    MENU,
    CUBE_2,
    CUBE_3,
    CUBE_4
}

@Composable
fun AppRoot() {

    var screen by remember {
        mutableStateOf(Screen.MENU)
    }

    when (screen) {

        Screen.MENU -> {
            MenuScreen(
                open2x2 = { screen = Screen.CUBE_2 },
                open3x3 = { screen = Screen.CUBE_3 },
                open4x4 = { screen = Screen.CUBE_4 }
            )
        }



        Screen.CUBE_2 -> {
            Cube222Screen(
                goMenu = {
                    screen = Screen.MENU
                }
            )
        }

        Screen.CUBE_3 -> {
           // StartCube3x3(
            Cube333Screen(
                goMenu = {
                    screen = Screen.MENU
                }
            )
        }

        Screen.CUBE_4 -> {
            // StartCube3x3(
            Cube444Screen(
                goMenu = {
                    screen = Screen.MENU
                }
            )
        }


    }
}

@Composable
private fun MenuScreen(open2x2: () -> Unit, open3x3: () -> Unit, open4x4: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Button(onClick = open2x2) {
            Text("2x2")
        }

        Button(onClick = open3x3) {
            Text("3x3")
        }

        Button(onClick = open4x4) {
            Text("4x4")
        }

    }
}
