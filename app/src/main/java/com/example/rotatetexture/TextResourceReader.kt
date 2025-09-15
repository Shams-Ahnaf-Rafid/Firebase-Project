package com.example.rotatetexture

import android.content.Context

object TextResourceReader {
    fun readTextFileFromRawResource(context: Context, resourceId: Int): String {
        val inputStream = context.resources.openRawResource(resourceId)
        return inputStream.bufferedReader().use { it.readText() }
    }
}
