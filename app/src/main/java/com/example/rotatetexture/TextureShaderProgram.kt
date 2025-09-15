package com.example.rotatetexture

import android.content.Context
import android.opengl.GLES20.*

class TextureShaderProgram(context: Context) :
    ShaderProgram(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader) {

    val uTextureUnitLocation: Int = glGetUniformLocation(program, "u_TextureUnit")
    val aPositionLocation: Int = glGetAttribLocation(program, "a_Position")
    val aTextureCoordinatesLocation: Int = glGetAttribLocation(program, "a_TextureCoordinate")
    private val uTextureRotationAngleLocation: Int = glGetUniformLocation(program, "u_TextureRotationAngle")
    private val uTextureRotationCenterLocation: Int = glGetUniformLocation(program, "u_TextureRotationCenter")
    private val uAspectRatioLocation: Int = glGetUniformLocation(program, "u_AspectRatio")
    private val uScreenAspectRatioLocation: Int = glGetUniformLocation(program, "u_ScreenAspectRatio")
    private val uCntLocation : Int = glGetUniformLocation(program, "u_Cnt")

    fun setUniforms(textureId: Int, rotationAngleRadians: Float, aspectRatio: Float, screenAspectRatio: Float, cnt: Float) {
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, textureId)
        glUniform1i(uTextureUnitLocation, 0)
        glUniform1f(uCntLocation, cnt)
        glUniform1f(uTextureRotationAngleLocation, rotationAngleRadians)
        glUniform2f(uTextureRotationCenterLocation, 0.5f, 0.5f)
        glUniform1f(uAspectRatioLocation,aspectRatio)
        glUniform1f(uScreenAspectRatioLocation, screenAspectRatio)
    }
}
