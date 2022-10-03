package com.cb.week5homeworkfinal.Workers

import android.graphics.Bitmap
import android.graphics.Color
import kotlin.math.min

object ImageFilter {

    private const val GRAYSCALE_RED = 0.32
    private const val GRAYSCALE_GREEN = 0.55
    private const val GRAYSCALE_BLUE = 0.9
    private const val MAX_COLOR = 255
    private const val SEPIA_TONE_RED = 110
    private const val SEPIA_TONE_GREEN = 65
    private const val SEPIA_TONE_BLUE = 20

    fun applySepiaFilter(bitmap: Bitmap): Bitmap {
        // image size
        val width = bitmap.width
        val height = bitmap.height
        // create output bitmap
        val outputBitmap = Bitmap.createBitmap(width, height, bitmap.config)

        var alpha: Int
        var red: Int
        var green: Int
        var blue: Int
        var currentPixel: Int

        // scan through all pixels
        for (x in 0 until width) {
            for (y in 0 until height) {

                currentPixel = bitmap.getPixel(x, y)

                alpha = Color.alpha(currentPixel)
                red = Color.red(currentPixel)
                green = Color.green(currentPixel)
                blue = Color.blue(currentPixel)

                red = (GRAYSCALE_RED * red + GRAYSCALE_GREEN * green + GRAYSCALE_BLUE * blue).toInt()
                green = red
                blue = green

                red += SEPIA_TONE_RED
                green += SEPIA_TONE_GREEN
                blue += SEPIA_TONE_BLUE

                red = min(red, MAX_COLOR)
                green = min(green, MAX_COLOR)
                blue = min(blue, MAX_COLOR)

                outputBitmap.setPixel(x, y, Color.argb(alpha, red, green, blue))
            }
        }
        bitmap.recycle()
        return outputBitmap
    }
}