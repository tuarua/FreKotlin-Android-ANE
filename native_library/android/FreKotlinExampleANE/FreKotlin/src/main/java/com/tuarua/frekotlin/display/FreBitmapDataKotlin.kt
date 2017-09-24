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

import java.nio.ByteBuffer

class FreBitmapDataKotlin {
    var rawValue: FREBitmapData? = null
    var width = 0
    var height = 0
    var hasAlpha: Boolean = false
    var isPremultiplied: Boolean = false
    var lineStride32 = 0
    var isInvertedY: Boolean = false
    var bits32: ByteBuffer? = null

    constructor() {}

    constructor(value: FreBitmapDataKotlin) {
        rawValue = value.rawValue
    }

    constructor(value: FREObject) {
        rawValue = value as FREBitmapData
    }

    constructor(value: FREBitmapData) {
        rawValue = value
    }

    @Throws(FREASErrorException::class, FREWrongThreadException::class, FREInvalidObjectException::class)
    constructor(bitmap: Bitmap, swapColors: Boolean = true) {
        val fillColor = arrayOf<Byte>(0, 0, 0, 0)
        rawValue = FREBitmapData.newBitmapData(bitmap.width,
                bitmap.height, bitmap.hasAlpha(), fillColor)

        val bmp: Bitmap?
        bmp = when {
            swapColors -> Companion.doSwapColors(bitmap)
            else -> bitmap
        }
        acquire()
        bmp.copyPixelsToBuffer(bits32)
        invalidateRect(0, 0, bmp.width, bmp.height)
        release()
        bmp.recycle()
    }

    @Throws(FREWrongThreadException::class, FREInvalidObjectException::class)
    fun acquire() {
        if (rawValue is FREBitmapData) {
            rawValue?.acquire()
            width = (rawValue as FREBitmapData).width
            height = (rawValue as FREBitmapData).height
            bits32 = (rawValue as FREBitmapData).bits
            hasAlpha = (rawValue as FREBitmapData).hasAlpha()
            isInvertedY = (rawValue as FREBitmapData).isInvertedY
            isPremultiplied = (rawValue as FREBitmapData).isPremultiplied
            lineStride32 = (rawValue as FREBitmapData).lineStride32
        }
    }

    @Throws(FREWrongThreadException::class, FREInvalidObjectException::class)
    fun release() {
        rawValue?.release()
    }

    @Throws(FREWrongThreadException::class, FREInvalidObjectException::class)
    fun invalidateRect(x: Int, y: Int, width: Int, height: Int) {
        rawValue?.invalidateRect(x, y, width, height)
    }

    companion object {
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

@Throws(FreException::class)
fun Bitmap(freObject: FREObject?, swapColors: Boolean = true): Bitmap? {
    var ret: Bitmap? = null
    if (freObject != null) {
        try {
            val bmd = FreBitmapDataKotlin(freObject)
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
        } catch (e: FreException) {
            throw e
        } catch (e: Exception) {
            throw FreException(e)
        }
    }
    return ret
}