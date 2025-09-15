package com.example.rotatetexture

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGLRenderer(private val context: Context) : GLSurfaceView.Renderer {

    private lateinit var texturedQuad: TextureQuad
    private lateinit var textureProgram: TextureShaderProgram
    private var textureId: Int = 0
    private var currentRotationAngleDegrees: Float = 0f
    private var cnt: Float = 0f
    private var aspectRatio: Float = 1f
    private var screenAspectRatio: Float = 1f

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0f, 0f, 0f, 1.0f)
        textureProgram = TextureShaderProgram(context)
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.image1)
        val aspectRatio = bitmap.width.toFloat() / bitmap.height
        texturedQuad = TextureQuad(aspectRatio)
        textureId = TextureHelper.loadTexture(context, R.drawable.image1)
        Log.d("MyGLRenderer", "Texture ID: $textureId")
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
        screenAspectRatio = width.toFloat()/ height
        Log.d("Check", "${screenAspectRatio}")
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)

        cnt += 0.005f;
        Log.d("Check", "${cnt}")

        textureProgram.useProgram()
        textureProgram.setUniforms(textureId, currentRotationAngleDegrees, aspectRatio, screenAspectRatio, cnt)

        texturedQuad.bindData(textureProgram)
        texturedQuad.draw()

    }

    fun setRotationAngle(angleInDegrees: Float) {
        currentRotationAngleDegrees = angleInDegrees
    }
}
