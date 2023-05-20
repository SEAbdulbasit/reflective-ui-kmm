import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun App() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            CameraView(modifier = Modifier.fillMaxSize())
            Box(modifier = Modifier.fillMaxSize().background(Color.Black))
            Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
                TransparentText(
                    text = "Contacts",
                    height = 60,
                    textStyle = TextStyle(fontWeight = FontWeight.W600, fontSize = 36.sp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(modifier = Modifier.weight(1f).fillMaxSize()) {
                    items(userDetails) { userDetails ->
                        UserListTile(userDetails)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalTextApi::class)
@Composable
fun TransparentText(text: String, textStyle: TextStyle, height: Int = 25) {
    val textMeasure = rememberTextMeasurer()
    Canvas(modifier = Modifier.fillMaxWidth().height(height = height.dp), onDraw = {
        drawText(
            textMeasurer = textMeasure, text = text, style = textStyle, blendMode = BlendMode.Clear
        )
    })
}

@Composable
fun TransparentCircle() {
    Canvas(modifier = Modifier.size(25.dp)) {
        val radius = 25.dp.toPx()
        drawCircle(
            color = Color.Transparent, radius = radius, blendMode = BlendMode.Clear
        )
    }
}

@Composable
fun UserListTile(user: User) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TransparentCircle(
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier.weight(1f).padding(start = 16.dp)
        ) {
            TransparentText(
                text = user.name, textStyle = TextStyle(
                    color = Color.White, fontWeight = FontWeight.W600, fontSize = 18.sp
                )
            )
            TransparentText(
                text = user.username, textStyle = TextStyle(
                    color = Color.White, fontWeight = FontWeight.W400, fontSize = 14.sp
                )
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        CanvasWithIcon()
    }
}

@Composable
fun CanvasWithIcon() {
    Canvas(modifier = Modifier.size(25.dp)) {
        val circleRadius = size.minDimension / 2
        val circleCenter = Offset(size.width / 2, size.height / 2)

        // Draw the circle
        drawCircle(
            color = Color.Red,
            radius = circleRadius,
            style = Stroke(width = (2.5).dp.toPx()),
            blendMode = BlendMode.Clear
        )

        val arrowSize = circleRadius * 0.6f // Adjust the size of the arrow
        val arrowPath = Path().apply {
            moveTo(
                circleCenter.x + circleRadius - arrowSize,
                circleCenter.y
            ) // Start from the adjusted position
            lineTo(circleCenter.x + circleRadius - arrowSize * 2, circleCenter.y - arrowSize / 2)
            lineTo(circleCenter.x + circleRadius - arrowSize * 2, circleCenter.y + arrowSize / 2)
            close()
        }
        drawPath(
            path = arrowPath,
            color = Color.White,
            blendMode = BlendMode.Clear
        )
    }
}

data class User(
    val name: String, val username: String
)
