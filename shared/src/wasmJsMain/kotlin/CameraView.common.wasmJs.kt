import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLVideoElement
import org.w3c.dom.mediacapture.MediaStreamConstraints

@Composable
actual fun CameraView(modifier: Modifier) {
    val videoElement = document.createElement("video") as HTMLVideoElement
    videoElement.width = window.innerWidth
    videoElement.translate=true
    (window.navigator.getUserMedia(
        MediaStreamConstraints(video = true.toJsBoolean(), audio = false.toJsBoolean()),
        successCallback = {
            videoElement.srcObject = it
            videoElement.play()
        },
        errorCallback = {
            println("Issue is $it")
        }))

    document.getElementById("fullScreenElement")!!.replaceWith(videoElement)
}