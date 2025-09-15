package com.example.rotatetexture

import android.os.Bundle
import android.opengl.GLSurfaceView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RotateTextureApp()
        }
    }
}

@Composable
fun RotateTextureApp() {
    var rotationAngleDegrees by remember { mutableFloatStateOf(0f) }
    val rendererRef = remember { mutableStateOf<MyGLRenderer?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                GLSurfaceView(context).apply {
                    setEGLContextClientVersion(2)

                    val renderer = MyGLRenderer(context)
                    rendererRef.value = renderer
                    setRenderer(renderer)
                    renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
                }
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )

//        Button(
//            onClick = {
//                rotationAngleDegrees = (rotationAngleDegrees + 90f) % 360f
//                rendererRef.value?.setRotationAngle(rotationAngleDegrees)
//            },
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .padding(16.dp)
//        ) {
//            Text("Rotate 90°")
//        }
    }
}
