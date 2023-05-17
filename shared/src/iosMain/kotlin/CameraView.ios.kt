import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ObjCAction
import platform.AVFoundation.*
import platform.AVFoundation.AVCaptureDeviceDiscoverySession.Companion.discoverySessionWithDeviceTypes
import platform.AVFoundation.AVCaptureDeviceInput.Companion.deviceInputWithDevice
import platform.CoreGraphics.CGRect
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSNotification
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSSelectorFromString
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceOrientation
import platform.UIKit.UIView
import platform.darwin.NSObject

private sealed interface CameraAccess {
    object Undefined : CameraAccess
    object Denied : CameraAccess
    object Authorized : CameraAccess
}

private val deviceTypes = listOf(
    AVCaptureDeviceTypeBuiltInWideAngleCamera,
    AVCaptureDeviceTypeBuiltInDualWideCamera,
    AVCaptureDeviceTypeBuiltInDualCamera,
    AVCaptureDeviceTypeBuiltInUltraWideCamera,
    AVCaptureDeviceTypeBuiltInDuoCamera
)

@Composable
actual fun CameraView(
    modifier: Modifier,
) {
    var cameraAccess: CameraAccess by remember { mutableStateOf(CameraAccess.Undefined) }
    LaunchedEffect(Unit) {
        when (AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)) {
            AVAuthorizationStatusAuthorized -> {
                cameraAccess = CameraAccess.Authorized
            }

            AVAuthorizationStatusDenied, AVAuthorizationStatusRestricted -> {
                cameraAccess = CameraAccess.Denied
            }

            AVAuthorizationStatusNotDetermined -> {
                AVCaptureDevice.requestAccessForMediaType(
                    mediaType = AVMediaTypeVideo
                ) { success ->
                    cameraAccess = if (success) CameraAccess.Authorized else CameraAccess.Denied
                }
            }
        }
    }
    Box(
        Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        when (cameraAccess) {
            CameraAccess.Undefined -> {
                // Waiting for the user to accept permission
            }

            CameraAccess.Denied -> {
                Text("Camera access denied", color = Color.White)
            }

            CameraAccess.Authorized -> {
                AuthorizedCamera()
            }
        }
    }
}

@Composable
private fun BoxScope.AuthorizedCamera() {
    val camera: AVCaptureDevice? = remember {
        discoverySessionWithDeviceTypes(
            deviceTypes = deviceTypes,
            mediaType = AVMediaTypeVideo,
            position = AVCaptureDevicePositionFront,
        ).devices.firstOrNull() as? AVCaptureDevice
    }
    if (camera != null) {
        RealDeviceCamera(camera)
    } else {
        Text(
            """
            Camera is not available on simulator.
            Please try to run on a real iOS device.
        """.trimIndent(), color = Color.White
        )
    }
}

@Composable
private fun BoxScope.RealDeviceCamera(
    camera: AVCaptureDevice,
) {
    val capturePhotoOutput = remember { AVCapturePhotoOutput() }
    var actualOrientation by remember {
        mutableStateOf(
            AVCaptureVideoOrientationPortrait
        )
    }
    val locationManager = remember {
        CLLocationManager().apply {
            desiredAccuracy = kCLLocationAccuracyBest
            requestWhenInUseAuthorization()
        }
    }
    var capturePhotoStarted by remember { mutableStateOf(false) }
    val captureSession: AVCaptureSession = remember {
        AVCaptureSession().also { captureSession ->
            captureSession.sessionPreset = AVCaptureSessionPresetPhoto
            val captureDeviceInput: AVCaptureDeviceInput =
                deviceInputWithDevice(device = camera, error = null)!!
            captureSession.addInput(captureDeviceInput)
            captureSession.addOutput(capturePhotoOutput)
        }
    }
    val cameraPreviewLayer = remember {
        AVCaptureVideoPreviewLayer(session = captureSession)
    }

    DisposableEffect(Unit) {
        class OrientationListener : NSObject() {
            @Suppress("UNUSED_PARAMETER")
            @ObjCAction
            fun orientationDidChange(arg: NSNotification) {
                val cameraConnection = cameraPreviewLayer.connection
                if (cameraConnection != null) {
                    actualOrientation = when (UIDevice.currentDevice.orientation) {
                        UIDeviceOrientation.UIDeviceOrientationPortrait ->
                            AVCaptureVideoOrientationPortrait

                        UIDeviceOrientation.UIDeviceOrientationLandscapeLeft ->
                            AVCaptureVideoOrientationLandscapeRight

                        UIDeviceOrientation.UIDeviceOrientationLandscapeRight ->
                            AVCaptureVideoOrientationLandscapeLeft

                        UIDeviceOrientation.UIDeviceOrientationPortraitUpsideDown ->
                            AVCaptureVideoOrientationPortrait

                        else -> cameraConnection.videoOrientation
                    }
                    cameraConnection.videoOrientation = actualOrientation
                }
                capturePhotoOutput.connectionWithMediaType(AVMediaTypeVideo)
                    ?.videoOrientation = actualOrientation
            }
        }

        val listener = OrientationListener()
        val notificationName = platform.UIKit.UIDeviceOrientationDidChangeNotification
        NSNotificationCenter.defaultCenter.addObserver(
            observer = listener,
            selector = NSSelectorFromString(
                OrientationListener::orientationDidChange.name + ":"
            ),
            name = notificationName,
            `object` = null
        )
        onDispose {
            NSNotificationCenter.defaultCenter.removeObserver(
                observer = listener,
                name = notificationName,
                `object` = null
            )
        }
    }
    UIKitView(
        modifier = Modifier.fillMaxSize(),
        background = Color.Black,
        factory = {
            val cameraContainer = UIView()
            cameraContainer.layer.addSublayer(cameraPreviewLayer)
            cameraPreviewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
            captureSession.startRunning()
            cameraContainer
        },
        onResize = { view: UIView, rect: CValue<CGRect> ->
            CATransaction.begin()
            CATransaction.setValue(true, kCATransactionDisableActions)
            view.layer.setFrame(rect)
            cameraPreviewLayer.setFrame(rect)
            CATransaction.commit()
        },
    )
}