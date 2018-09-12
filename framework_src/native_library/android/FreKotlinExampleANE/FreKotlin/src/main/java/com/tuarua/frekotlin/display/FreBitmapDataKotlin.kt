/*
 *  Copyright 2017 Tua Rua Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
@file:Suppress("unused")

package com.tuarua.frekotlin.display

import android.graphics.*
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.createBitmap

import com.adobe.fre.FREASErrorException
import com.adobe.fre.FREBitmapData
import com.adobe.fre.FREInvalidObjectException
import com.adobe.fre.FREObject
import com.adobe.fre.FREWrongThreadException
import com.tuarua.frekotlin.FreException
import com.tuarua.frekotlin.FreKotlinLogger

import java.nio.ByteBuffer

/**
 * A wrapper for handling FREBitmapData
 *
 * @param value the FREBitmapData to create this class from.
 * @param bitmap the Bitmap to create this class from.
 * @param swapColors swap the colors.
 *
 * @property rawValue the raw FREBitmapData value
 * @property width An Int that specifies the width, in pixels, of the bitmap. This value corresponds to the
 * width property of the ActionScript BitmapData class object. This field is read-only.
 * @property height An Int that specifies the height, in pixels, of the bitmap. This value corresponds to the height
 * property of the ActionScript BitmapData class object. This field is read-only.
 * @property hasAlpha A Bool that indicates whether the bitmap supports per-pixel transparency. This value corresponds
 * to the transparent property of the ActionScript BitmapData class object. If the value is non-zero, then the
 * pixel format is ARGB32. If the value is zero, the pixel format is _RGB32. Whether the value is big endian
 * or little endian depends on the host device. This field is read-only.
 * @property isPremultiplied A Bool that indicates whether the bitmap pixels are stored as premultiplied color
 * values. A true value means the values are premultipled. This field is read-only.
 * @property lineStride32 An Int that specifies the number of UInt values per scanline. This value is typically
 * the same as the width parameter. This field is read-only.
 * @property isInvertedY A Bool that indicates the order in which the rows of bitmap data in the image are stored. A
 * non-zero value means that the bottom row of the image appears first in the image data (in other words, the first
 * value in the bits32 array is the first pixel of the last row in the image). A zero value means that the top row of
 * the image appears first in the image data. This field is read-only.
 * @property bits32 A ByteBuffer of the bitmap.
 * @constructor
 */
class FreBitmapDataKotlin {
    var rawValue: FREBitmapData? = null
    var width = 0
    var height = 0
    var hasAlpha: Boolean = false
    var isPremultiplied: Boolean = false
    var lineStride32 = 0
    var isInvertedY: Boolean = false
    var bits32: ByteBuffer? = null

    /** */
    constructor()

    /** */
    constructor(value: FreBitmapDataKotlin) {
        rawValue = value.rawValue
    }

    /** */
    constructor(value: FREObject) {
        rawValue = value as FREBitmapData
    }

    /** */
    constructor(value: FREBitmapData) {
        rawValue = value
    }

    /** */
    constructor(bitmap: Bitmap, swapColors: Boolean = true) {
        val fillColor = arrayOf<Byte>(0, 0, 0, 0)
        try {
            rawValue = FREBitmapData.newBitmapData(bitmap.width,
                    bitmap.height, bitmap.hasAlpha(), fillColor)
        } catch (e: Exception) {
            FreKotlinLogger.log("Cannot create the FREBitmapData", e)
            return
        }

        val bmp: Bitmap = when {
            swapColors -> doSwapColors(bitmap)
            else -> bitmap
        }
        acquire()
        bmp.copyPixelsToBuffer(bits32)
        invalidateRect(0, 0, bmp.width, bmp.height)
        release()
        bmp.recycle()
    }

    /**
     * See the original [Adobe documentation](https://help.adobe.com/en_US/air/extensions/WS11d1def534ea1be0-67da1176132a8869db6-7ffe.html)
     */
    fun acquire() {
        val rv = rawValue ?: return
        try {
            rv.acquire()
            width = rv.width
            height = rv.height
            bits32 = rv.bits
            hasAlpha = rv.hasAlpha()
            isInvertedY = rv.isInvertedY
            isPremultiplied = rv.isPremultiplied
            lineStride32 = rv.lineStride32
        } catch (e: Exception) {
            FreKotlinLogger.log("Cannot acquire the FREByteArray", e)
        }
    }

    /**
     * See the original [Adobe documentation](https://help.adobe.com/en_US/air/extensions/WS11d1def534ea1be0-67da1176132a8869db6-7ffe.html)
     */
    fun release() {
        try {
            rawValue?.release()
        } catch (e: Exception) {
            FreKotlinLogger.log("Cannot release the FREBitmapData", e)
        }
    }

    /**
     * See the original [Adobe documentation](https://help.adobe.com/en_US/air/extensions/WS11d1def534ea1be0-67da1176132a8869db6-7ffe.html)
     */
    fun invalidateRect(x: Int, y: Int, width: Int, height: Int) {
        try {
            rawValue?.invalidateRect(x, y, width, height)
        } catch (e: Exception) {
            FreKotlinLogger.log("Cannot invalidateRect the FREBitmapData", e)
        }
    }

    companion object {
        /**
         * swaps the Red and Blue Colors of the Bitmap.
         */
        fun doSwapColors(inBitmap: Bitmap): Bitmap {
            val matrix = floatArrayOf(0f, 0f, 1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 1f, 0f)
            val rbSwap = ColorMatrix(matrix)
            val paint = Paint(Paint.FILTER_BITMAP_FLAG)
            paint.colorFilter = ColorMatrixColorFilter(rbSwap)

            val outBitmap = createBitmap(inBitmap.width, inBitmap.height, ARGB_8888)
            val canvas = Canvas(outBitmap)
            canvas.drawBitmap(inBitmap, 0.0F, 0.0F, paint)
            return outBitmap
        }
    }

}

/**
 * Converts a FREObject of type BitmapData to a Bitmap.
 * @param [swapColors] swaps the Red and Blue Colors of the Bitmap.
 */
@Suppress("FunctionName")
@JvmOverloads
fun Bitmap(freObject: FREObject?, swapColors: Boolean = true): Bitmap? {
    var ret: Bitmap? = null
    val fre = freObject ?: return null
    try {
        val bmd = FreBitmapDataKotlin(fre)
        bmd.acquire()
        if (bmd.bits32 is ByteBuffer) {
            ret = Bitmap.createBitmap(bmd.width, bmd.height, Bitmap.Config.ARGB_8888)
            ret.copyPixelsFromBuffer(bmd.bits32)
        }
        bmd.release()
        return if (swapColors && ret != null) {
            FreBitmapDataKotlin.doSwapColors(ret)
        } else {
            ret
        }
    } catch (e: Exception) {
        FreKotlinLogger.log("Cannot create Bitmap from FREObject", e)
    }
    return ret
}

/**
 * Converts a Bitmap to a FREObject of type BitmapData.
 */
fun Bitmap.toFREObject(): FREObject? {
    try {
        return FreBitmapDataKotlin(this, true).rawValue
    } catch (e: Exception) {
        FreKotlinLogger.log("Cannot create FREObject from Bitmap", e)
    }
    return null
}