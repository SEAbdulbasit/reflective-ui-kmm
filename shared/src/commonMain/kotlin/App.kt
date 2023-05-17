import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun App() {
    MaterialTheme {
        Box(Modifier.fillMaxSize().background(Color.Black)) {
            CameraView(Modifier.fillMaxSize())
            Button(content = {
                Text(text = "sdfsdf")
            }, onClick = {})

        }
    }
}