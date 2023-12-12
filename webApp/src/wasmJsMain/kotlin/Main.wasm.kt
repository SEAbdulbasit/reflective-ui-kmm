import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.CanvasBasedWindow
import org.w3c.dom.Text


@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("Transparent UI KMP Web") {
        App()
    }
}
