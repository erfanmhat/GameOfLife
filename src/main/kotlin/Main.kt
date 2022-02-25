// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import Constants.DELAY_TIME
import Constants.WORLD_X_SIZE
import Constants.WORLD_Y_SIZE
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay

@Composable
@Preview
fun App() {
    var world by remember { mutableStateOf(createEmptyWorld()) }
    var gameState by remember { mutableStateOf<GameState>(GameState.Pause) }
    var cellsHavePadding by remember { mutableStateOf(true) }

    LaunchedEffect(gameState) {
        while (gameState is GameState.Running) {
            delay(DELAY_TIME)
            world = createNextGeneration(world)
        }
    }

    MaterialTheme {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()
        ) {
            Column {
                for (row in 0 until WORLD_Y_SIZE) {
                    Row {
                        for (column in 0 until WORLD_X_SIZE) {
                            Cell(world, row, column, cellsHavePadding, modifier = Modifier.clickable {
                                world[row][column] = !world[row][column]
                            })
                        }
                    }
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Button(modifier = Modifier.padding(5.dp), colors = ButtonDefaults.buttonColors(
                    backgroundColor = when (gameState) {
                        is GameState.Running -> Color.Red
                        is GameState.Pause -> Color.Green
                    }
                ), onClick = {
                    gameState = when (gameState) {
                        is GameState.Running -> GameState.Pause
                        is GameState.Pause -> GameState.Running
                    }
                }) {
                    Text(
                        text = when (gameState) {
                            is GameState.Running -> "Pause"
                            is GameState.Pause -> "Run"
                        }, fontSize = 24.sp, color = Color.White
                    )
                }
                Button(modifier = Modifier.padding(5.dp), onClick = {
                    world = createEmptyWorld()
                }) {
                    Text(
                        text = "Clear", fontSize = 24.sp, color = Color.White
                    )
                }
                Button(modifier = Modifier.padding(5.dp), onClick = {
                    cellsHavePadding = !cellsHavePadding
                }) {
                    Text(
                        text = if (cellsHavePadding) "Hide cell paddings" else "Show cell padding",
                        fontSize = 24.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
