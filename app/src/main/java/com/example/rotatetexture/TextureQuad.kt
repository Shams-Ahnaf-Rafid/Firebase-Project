package com.example.rotatetexture

import android.opengl.GLES20.*
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

const val BYTES_PER_FLOAT_QUAD = 4

class TextureQuad (private val aspectRatio: Float = 1f) {

    private val COORDS_PER_VERTEX = 2 // X, Y for position
    private val TEXTURE_COORDS_PER_VERTEX = 2 // S, T for texture

    private val VERTEX_STRIDE = (COORDS_PER_VERTEX + TEXTURE_COORDS_PER_VERTEX) * BYTES_PER_FLOAT_QUAD

    private val Width = aspectRatio
    private val Height = 1f

    private val quadVertexData = floatArrayOf(
        -1f,  1f, 0f, 0f,  // Top-left     -> T=0 (top)
        -1f, 0f, 0f, 1f,  // Bottom-left  -> T=1 (bottom)
        0f, 1f, 1f, 0f,  // Top-right    -> T=0 (top)
        0f, 0f, 1f, 1f   // Bottom-right -> T=1 (bottom)
    )


    private val vertexBuffer: FloatBuffer

    init {
        Log.d("Check", "Aspect Ratio: $aspectRatio")
        vertexBuffer = ByteBuffer.allocateDirect(quadVertexData.size * BYTES_PER_FLOAT_QUAD).run {
            order(ByteOrder.nativeOrder())
            asFloatBuffer().apply {
                put(quadVertexData)
                position(0)
            }
        }
    }

    fun bindData(textureProgram: TextureShaderProgram) {
        vertexBuffer.position(0)
        glVertexAttribPointer(
            textureProgram.aPositionLocation,
            COORDS_PER_VERTEX,
            GL_FLOAT,
            false,
            VERTEX_STRIDE,
            vertexBuffer
        )
        glEnableVertexAttribArray(textureProgram.aPositionLocation)

        // Bind texture coordinates
        vertexBuffer.position(COORDS_PER_VERTEX) // Offset for texture coordinates
        glVertexAttribPointer(
            textureProgram.aTextureCoordinatesLocation,
            TEXTURE_COORDS_PER_VERTEX,
            GL_FLOAT,
            false,
            VERTEX_STRIDE,
            vertexBuffer
        )
        glEnableVertexAttribArray(textureProgram.aTextureCoordinatesLocation)
    }

    fun draw() {
        glDrawArrays(GL_TRIANGLE_STRIP, 0, quadVertexData.size / (COORDS_PER_VERTEX + TEXTURE_COORDS_PER_VERTEX))
    }
}