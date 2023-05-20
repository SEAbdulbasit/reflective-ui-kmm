import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.vector.PathParser
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
        val backgroundColor = remember { mutableStateOf(Color.White) }

        Box(modifier = Modifier.fillMaxSize()) {
            CameraView(modifier = Modifier.fillMaxSize())
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(backgroundColor.value)
            )

            Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TransparentText(
                        text = "Contacts",
                        height = 40,
                        textStyle = TextStyle(fontWeight = FontWeight.W600, fontSize = 36.sp)
                    )

                    if (backgroundColor.value == Color.White) {
                        drawNightModeIcon {
                            backgroundColor.value = Color.Black
                        }
                    } else {
                        drawLightModeIcon {
                            backgroundColor.value = Color.White
                        }

                    }
                }
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

@Composable
private fun drawLightModeIcon(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(32.dp)
        ) {
            val pathData =
                "M 24.90625 3.96875 C 24.863281 3.976563 24.820313 3.988281 24.78125 4 C 24.316406 4.105469 23.988281 4.523438 24 5 L 24 11 C 23.996094 11.359375 24.183594 11.695313 24.496094 11.878906 C 24.808594 12.058594 25.191406 12.058594 25.503906 11.878906 C 25.816406 11.695313 26.003906 11.359375 26 11 L 26 5 C 26.011719 4.710938 25.894531 4.433594 25.6875 4.238281 C 25.476563 4.039063 25.191406 3.941406 24.90625 3.96875 Z M 10.65625 9.84375 C 10.28125 9.910156 9.980469 10.183594 9.875 10.546875 C 9.769531 10.914063 9.878906 11.304688 10.15625 11.5625 L 14.40625 15.8125 C 14.648438 16.109375 15.035156 16.246094 15.410156 16.160156 C 15.78125 16.074219 16.074219 15.78125 16.160156 15.410156 C 16.246094 15.035156 16.109375 14.648438 15.8125 14.40625 L 11.5625 10.15625 C 11.355469 9.933594 11.054688 9.820313 10.75 9.84375 C 10.71875 9.84375 10.6875 9.84375 10.65625 9.84375 Z M 39.03125 9.84375 C 38.804688 9.875 38.59375 9.988281 38.4375 10.15625 L 34.1875 14.40625 C 33.890625 14.648438 33.753906 15.035156 33.839844 15.410156 C 33.925781 15.78125 34.21875 16.074219 34.589844 16.160156 C 34.964844 16.246094 35.351563 16.109375 35.59375 15.8125 L 39.84375 11.5625 C 40.15625 11.265625 40.246094 10.800781 40.0625 10.410156 C 39.875 10.015625 39.460938 9.789063 39.03125 9.84375 Z M 25 15 C 19.484375 15 15 19.484375 15 25 C 15 30.515625 19.484375 35 25 35 C 30.515625 35 35 30.515625 35 25 C 35 19.484375 30.515625 15 25 15 Z M 4.71875 24 C 4.167969 24.078125 3.78125 24.589844 3.859375 25.140625 C 3.9375 25.691406 4.449219 26.078125 5 26 L 11 26 C 11.359375 26.003906 11.695313 25.816406 11.878906 25.503906 C 12.058594 25.191406 12.058594 24.808594 11.878906 24.496094 C 11.695313 24.183594 11.359375 23.996094 11 24 L 5 24 C 4.96875 24 4.9375 24 4.90625 24 C 4.875 24 4.84375 24 4.8125 24 C 4.78125 24 4.75 24 4.71875 24 Z M 38.71875 24 C 38.167969 24.078125 37.78125 24.589844 37.859375 25.140625 C 37.9375 25.691406 38.449219 26.078125 39 26 L 45 26 C 45.359375 26.003906 45.695313 25.816406 45.878906 25.503906 C 46.058594 25.191406 46.058594 24.808594 45.878906 24.496094 C 45.695313 24.183594 45.359375 23.996094 45 24 L 39 24 C 38.96875 24 38.9375 24 38.90625 24 C 38.875 24 38.84375 24 38.8125 24 C 38.78125 24 38.75 24 38.71875 24 Z M 15 33.875 C 14.773438 33.90625 14.5625 34.019531 14.40625 34.1875 L 10.15625 38.4375 C 9.859375 38.679688 9.722656 39.066406 9.808594 39.441406 C 9.894531 39.8125 10.1875 40.105469 10.558594 40.191406 C 10.933594 40.277344 11.320313 40.140625 11.5625 39.84375 L 15.8125 35.59375 C 16.109375 35.308594 16.199219 34.867188 16.039063 34.488281 C 15.882813 34.109375 15.503906 33.867188 15.09375 33.875 C 15.0625 33.875 15.03125 33.875 15 33.875 Z M 34.6875 33.875 C 34.3125 33.941406 34.011719 34.214844 33.90625 34.578125 C 33.800781 34.945313 33.910156 35.335938 34.1875 35.59375 L 38.4375 39.84375 C 38.679688 40.140625 39.066406 40.277344 39.441406 40.191406 C 39.8125 40.105469 40.105469 39.8125 40.191406 39.441406 C 40.277344 39.066406 40.140625 38.679688 39.84375 38.4375 L 35.59375 34.1875 C 35.40625 33.988281 35.148438 33.878906 34.875 33.875 C 34.84375 33.875 34.8125 33.875 34.78125 33.875 C 34.75 33.875 34.71875 33.875 34.6875 33.875 Z M 24.90625 37.96875 C 24.863281 37.976563 24.820313 37.988281 24.78125 38 C 24.316406 38.105469 23.988281 38.523438 24 39 L 24 45 C 23.996094 45.359375 24.183594 45.695313 24.496094 45.878906 C 24.808594 46.058594 25.191406 46.058594 25.503906 45.878906 C 25.816406 45.695313 26.003906 45.359375 26 45 L 26 39 C 26.011719 38.710938 25.894531 38.433594 25.6875 38.238281 C 25.476563 38.039063 25.191406 37.941406 24.90625 37.96875 Z"
            val path = PathParser().parsePathString(pathData).toPath()
            val scale = 1.2f // Adjust the scale factor as needed
            scale(scale) {
                drawPath(
                    path = path,
                    color = Color.White,
                    style = Stroke(width = (2.5).dp.toPx()),
                    blendMode = BlendMode.Clear
                )
            }
        }
    }
}

@Composable
private fun drawNightModeIcon(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(32.dp)
        ) {
            val pathData =
                "M 87.823 60.7 c -0.463 -0.423 -1.142 -0.506 -1.695 -0.214 c -15.834 8.398 -35.266 2.812 -44.232 -12.718 c -8.966 -15.53 -4.09 -35.149 11.101 -44.665 c 0.531 -0.332 0.796 -0.963 0.661 -1.574 c -0.134 -0.612 -0.638 -1.074 -1.259 -1.153 c -9.843 -1.265 -19.59 0.692 -28.193 5.66 C 13.8 12.041 6.356 21.743 3.246 33.35 S 1.732 57.08 7.741 67.487 c 6.008 10.407 15.709 17.851 27.316 20.961 C 38.933 89.486 42.866 90 46.774 90 c 7.795 0 15.489 -2.044 22.42 -6.046 c 8.601 -4.966 15.171 -12.43 18.997 -21.586 C 88.433 61.79 88.285 61.123 87.823 60.7 z"
            val path = PathParser().parsePathString(pathData).toPath()

            val scale = 0.7f
            scale(scale) {
                drawPath(
                    path = path,
                    color = Color.Black,
                    style = Fill,
                    blendMode = BlendMode.Clear
                )
            }
        }
    }
}


@OptIn(ExperimentalTextApi::class)
@Composable
fun TransparentText(text: String, textStyle: TextStyle, height: Int = 25) {
    val textMeasure = rememberTextMeasurer()
    Canvas(modifier = Modifier.width(200.dp).height(height = height.dp), onDraw = {
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

        val arrowWidth = circleRadius * 0.6f
        val arrowHeight = circleRadius * 0.4f
        val arrowPath = Path().apply {
            moveTo(circleCenter.x - arrowWidth / 2, circleCenter.y - arrowHeight / 2)
            lineTo(circleCenter.x + arrowWidth / 2, circleCenter.y)
            lineTo(circleCenter.x - arrowWidth / 2, circleCenter.y + arrowHeight / 2)
            moveTo(circleCenter.x + arrowWidth / 2, circleCenter.y)
            lineTo(circleCenter.x - arrowWidth / 2 + arrowHeight / 2, circleCenter.y)
        }
        drawPath(
            path = arrowPath,
            color = Color.White,
            style = Stroke(width = (2.5).dp.toPx()),
            blendMode = BlendMode.Clear
        )
    }
}

data class User(
    val name: String, val username: String
)
