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

package com.tuarua
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import android.util.Log
import com.adobe.fre.FREContext
import com.adobe.fre.FREObject
import com.tuarua.frekotlin.FreArrayKotlin
import com.tuarua.frekotlin.FreException
import com.tuarua.frekotlin.FreObjectKotlin
import com.tuarua.frekotlin.FreObjectTypeKotlin
import com.tuarua.frekotlin.display.FreBitmapDataKotlin
import com.tuarua.frekotlin.geom.FrePointKotlin
import com.tuarua.frekotlin.geom.FreRectangleKotlin
import java.nio.ByteBuffer
import java.util.*


typealias FREArgv = ArrayList<FREObject>

@Suppress("unused", "UNUSED_PARAMETER")
class KotlinController {
    private var context: FREContext? = null
    private val TRACE = "TRACE"

    fun runStringTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start String test***********")
        argv.takeIf { argv.size == 1 } ?: return null //check number of args is 1
        val inFRE0 = argv[0].guard {
            //ensures String passed is not null
            trace("String passed to runStringTests cannot be null");return null
        }
        val airString: String = (FreObjectKotlin(freObject = inFRE0).value as String).guard {
            trace("Cannot convert passed string");return null
        }
        trace(airString)
        val kotlinString: String = "I am a string from Kotlin"
        return FreObjectKotlin(kotlinString).rawValue.guard { return null }
    }

    fun runIntTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Int Uint test***********")
        val airInt: Int = FreObjectKotlin(argv[0]).value as Int
        val airUInt: Int = FreObjectKotlin(argv[1]).value as Int

        trace("Int passed from AIR:", airInt)
        trace("UInt passed from AIR:", airUInt)

        val kotlinInt: Int = -666
        val kotlinUInt: Int = 888

        val test: Any? = FreObjectKotlin(kotlinUInt).value
        if (test is Int) { //to test for null
            trace("test is an Int")
        }
        return FreObjectKotlin(kotlinInt).rawValue
    }

    fun runNumberTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Number test***********")
        try {
            val airNumber: Double = FreObjectKotlin(argv[0]).value as Double
            trace("Number passed from AIR:", airNumber)
            val kotlinDouble: Double = 34343.31
            return FreObjectKotlin(kotlinDouble).rawValue
        } catch (e: Exception) {
            Log.e(Companion.TAG, "ERROR: " + e.message)
        }
        return null
    }

    fun runObjectTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Object test***********")
        val person = FreObjectKotlin(argv[0])
        val freAge = person.getProperty("age")
        val oldAge = freAge?.value

        trace("current person age is", oldAge)

        if (oldAge is Int) {
            val newAge = FreObjectKotlin(oldAge + 10)
            person.setProperty("age", newAge)
        }

        val addition: FreObjectKotlin? = person.callMethod("add", 100, 31)
        if (addition is FreObjectKotlin) {
            val sum = addition.value
            if (sum is Int) {
                trace("addition result:", sum)
            }
        }

        try {
            val dictionary: Map<String, Any>?
            if (person.value is Map<*, *>) {
                dictionary = person.value as Map<String, Any>
                Log.d(TAG, dictionary.keys.toString() + dictionary.values.toString())
            }
        } catch (e: Exception) {
            Log.e(TAG, "ERROR: " + e.message)
        }

        return person.rawValue
    }

    fun runArrayTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Array test NEW ***********")

        val airArray: FreArrayKotlin? = FreArrayKotlin(argv[0])
        val airArrayLen = airArray?.length
        trace("Array passed from AIR:", airArray?.value)
        trace("AIR Array length:", airArrayLen)

        val itemZero: FreObjectKotlin? = airArray?.getObjectAt(0);
        if (itemZero is FreObjectKotlin) {
            Log.d(Companion.TAG, "itemZero is FreObjectKotlin")
            val itemZeroVal: Int? = itemZero.value as Int?
            if (itemZeroVal is Int) {
                trace("AIR Array elem at 0 type:", "value:", itemZeroVal)
                val newVal = FreObjectKotlin(56)
                airArray.setObjectAt(0, newVal)
                return airArray.rawValue
            }
        } else {
            Log.d(Companion.TAG, "itemZero is null")
        }
        return null
    }

    fun runBitmapTests(ctx: FREContext, argv: FREArgv): FREObject? {
        val bmd = FreBitmapDataKotlin(argv[0]);
        bmd.acquire()
        trace("bmd", bmd.width, bmd.height)
        if (bmd.bits32 is ByteBuffer) {
            val width = bmd.width
            val height = bmd.height

            var bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            bmp.copyPixelsFromBuffer(bmd.bits32)


            val bmpSepia = BitmapUtils.sepiaFilter(image = bmp)
            bmd.release()
            bmp.recycle()

            bmd.acquire()
            bmpSepia.copyPixelsToBuffer(bmd.bits32)
            bmd.invalidateRect(0, 0, width, height)
            bmd.release()
            bmpSepia.recycle()
        }
        return null
    }

    fun runExtensibleTests(ctx: FREContext, argv: FREArgv): FREObject? {
        argv.takeIf { argv.size == 1 } ?: return null //check number of args is 1
        val inFRE0 = argv[0].guard {
            trace("Rectangle passed to runExtensibleTests cannot be null");return null
        }
        val point: Point = Point(10, 88)
        var ret: FREObject? = null
        try {
            val rectangle: Rect = FreRectangleKotlin(inFRE0).value
            rectangle.set(0, 0, 999, 111)
            ret = FreRectangleKotlin(rectangle).rawValue

            val frePoint = FrePointKotlin(point)
            trace("frePoint is", frePoint.value.x, frePoint.value.y)
            val targetPoint = FrePointKotlin(Point(100, 444))
            frePoint.copyFrom(targetPoint)
            trace("frePoint is now", frePoint.value.x, frePoint.value.y)
        } catch (e: Exception) {
            Log.e(Companion.TAG, e.message)
        }

        return ret
    }

    fun runByteArrayTests(ctx: FREContext, argv: FREArgv): FREObject? {
        return null //TODO
    }

    fun runErrorTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Error Handling test***********")
        argv.takeIf { argv.size == 1 } ?: return null //check number of args is 1
        val inFRE0 = argv[0].guard {
            trace("Object passed to runErrorTests cannot be null");return null
        }
        val person = FreObjectKotlin(freObject = inFRE0).guard {
            trace("Cannot convert person string");return null
        }

        try {
            person.callMethod("add", 100) //not passing enough args
        } catch (e: FreException) {
            Log.e(TAG, e.message)
            Log.e(TAG, e.stackTrace)
        }

        try {
            person.getProperty("doNotExist")
        } catch (e: FreException) {
            return e.getError(Thread.currentThread().stackTrace)
        }

        return null
    }

    fun runErrorTests2(ctx: FREContext, argv: FREArgv): FREObject? {
        argv.takeIf { argv.size == 1 } ?: return null //check number of args is 1
        val inFRE0 = argv[0].guard {
            trace("value passed to runErrorTests2 cannot be null");return null
        }
        val expectInt = FreObjectKotlin(inFRE0)
        if (expectInt.getType() != FreObjectTypeKotlin.INT) {
            trace("Oops, we expected the FREObject to be passed as an int but it's not")
        }
        return null
    }

    fun runDataTests(ctx: FREContext, argv: FREArgv): FREObject? {
        return null
    }
    
    fun dispose() {
    }

    fun setFREContext(ctx: FREContext) {
        context = ctx
    }

    private fun trace(vararg value: Any?) {
        var traceStr: String = "${Companion.TAG}: "
        for (v in value)
            traceStr = traceStr + "$v" + " "
        context?.dispatchStatusEventAsync(traceStr, TRACE)
    }

    // https://android.jlelse.eu/a-few-ways-to-implement-a-swift-like-guard-in-kotlin-ffd94027864e
    // Declare an extension function that calls a lambda called block if the value is null
    inline fun <T> T.guard(block: T.() -> Unit): T {
        if (this == null) block(); return this
    }

    companion object {
        private var TAG = "com.tuarua.FreKotlinExampleANE"
    }

}

