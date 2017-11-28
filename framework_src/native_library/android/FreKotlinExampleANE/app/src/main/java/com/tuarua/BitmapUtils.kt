package com.tuarua
import android.graphics.*
// Credit https://github.com/anthonyjburch/final_project/blob/807f4a294ca8c50ff65f6269e1118fde2b6eda9c/Swipe_Fragments/src/com/project/utils/Filters.java
object BitmapUtils {
    fun sepiaFilter(image: Bitmap): Bitmap {
        // Values for a sepia tone
        val sepiaValues = floatArrayOf(0.393f, 0.769f, 0.189f, 0f, 0f, 0.349f, 0.686f, 0.168f, 0f, 0f, 0.272f, 0.534f, 0.131f, 0f, 0f, 0f, 0f, 0f, 1f, 0f)
        val filteredImage = image.copy(image.config, true)
        val canvas = Canvas(filteredImage)
        val paint = Paint()
        val sepiaMatrix = ColorMatrix()
        sepiaMatrix.set(sepiaValues)
        val sepiaFilter = ColorMatrixColorFilter(sepiaMatrix)
        paint.colorFilter = sepiaFilter
        canvas.drawBitmap(image, 0f, 0f, paint)
        return filteredImage
    }
}
