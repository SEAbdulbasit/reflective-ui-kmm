package org.example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.CanvasBasedWindow
import org.w3c.dom.Text


/**
 * Created by abdulbasit on 11/12/2023.
 */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("Transparent UI KMP Web") {
        Box(modifier = Modifier.fillMaxWidth().background(Color.Red)) {
            Text("abac")
        }
    }
}