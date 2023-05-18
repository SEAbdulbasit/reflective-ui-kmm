import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            CameraView(modifier = Modifier.fillMaxSize())
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(
                    color = Color.Black,
                    size = size
                )
            }
            TransparentBox(modifier = Modifier.align(alignment = Alignment.Center))
        }
    }
}

@Composable
fun TransparentBox(modifier: Modifier = Modifier) {
    val transparentBoxSize = 60.dp

    Canvas(modifier = modifier) {
        drawRect(
            color = Color.Red, // Specify the desired transparency here
            size = Size(transparentBoxSize.toPx(), transparentBoxSize.toPx()),
            blendMode = BlendMode.Clear
        )
    }
}

@Composable
fun UserListTile(user: User) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(50.dp).clip(CircleShape).background(Color.White)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = user.name, style = TextStyle(
                    color = Color.White, fontWeight = FontWeight.W600, fontSize = 18.sp
                )
            )
            Text(
                text = user.username, style = TextStyle(
                    color = Color.White, fontWeight = FontWeight.W400, fontSize = 14.sp
                )
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )
    }
}


data class User(
    val name: String, val username: String
)
