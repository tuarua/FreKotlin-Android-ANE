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
import android.view.ViewGroup
import android.widget.FrameLayout
import com.adobe.fre.FREContext
import com.adobe.fre.FREObject
import com.tuarua.frekotlin.*
import com.tuarua.frekotlin.display.FreBitmapDataKotlin
import com.tuarua.frekotlin.geom.FrePointKotlin
import com.tuarua.frekotlin.geom.FreRectangleKotlin
import java.nio.ByteBuffer
import java.util.*

@Suppress("unused", "UNUSED_PARAMETER", "UNCHECKED_CAST")
class KotlinController : FreKotlinMainController {
    private var airView: ViewGroup? = null
    private var container: FrameLayout? = null


    fun runStringTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start String test***********")
        argv.takeIf { argv.size == 1 } ?: return ArgCountException().getError(Thread.currentThread().stackTrace)
        val inFRE0 = argv[0].guard {
            //ensures String passed is not null
            trace("String passed to runStringTests cannot be null");return null
        }
        val airString: String = (FreObjectKotlin(freObject = inFRE0).value as String).guard {
            trace("Cannot convert passed string");return null
        }

        trace(airString)
        sendEvent("MY_EVENT", "this is a test")

        val kotlinString = "I am a string from Kotlin"
        return FreObjectKotlin(kotlinString).rawValue.guard { return null }
    }

    fun runIntTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Int Uint test***********")
        val airInt: Int = FreObjectKotlin(argv[0]).value as Int
        val airUInt: Int = FreObjectKotlin(argv[1]).value as Int

        trace("Int passed from AIR:", airInt)
        trace("UInt passed from AIR:", airUInt)

        val kotlinInt: Int = -666
        val kotlinUInt = 888

        val test: Any? = FreObjectKotlin(kotlinUInt).value
        if (test is Int) { //to test for null
            trace("test is an Int")
        }
        return FreObjectKotlin(kotlinInt).rawValue
    }

    fun runNumberTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Number test***********")
        val airNumber: Double = FreObjectKotlin(argv[0]).value as Double
        trace("Number passed from AIR:", airNumber)
        val kotlinDouble = 34343.31
        return FreObjectKotlin(kotlinDouble).rawValue
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

    fun runDateTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Date test ***********")
        return try {
            val date = FreObjectKotlin(argv[0]).value as Date
            trace("unix time stamp:", date.time.toString())
            FreObjectKotlin(date).rawValue
        } catch (e: FreException) {
            e.getError(Thread.currentThread().stackTrace)
        } catch (e: Exception) {
            FreException(e).getError(Thread.currentThread().stackTrace)
        }
    }

    fun runArrayTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Array test ***********")

        val airArray: FreArrayKotlin? = FreArrayKotlin(argv[0])
        val airArrayLen = airArray?.length
        trace("Array passed from AIR:", airArray?.value)
        trace("AIR Array length:", airArrayLen)

        val airVector: FreArrayKotlin? = FreArrayKotlin(argv[1])
        val airVectorLen = airVector?.length

        trace("Vector.<String> passed from AIR:", airVector?.value)
        trace("AIR Vector.<String> length:", airVectorLen)

        val kotArr: IntArray = intArrayOf(1, 2, 3)
        val kotArrayFre = FreArrayKotlin(kotArr)
        trace("Kotlin array converted:", kotArrayFre.value)

        val itemZero: FreObjectKotlin? = airArray?.getObjectAt(0)
        Log.d(TAG, "itemZero is FreObjectKotlin")
        val itemZeroVal: Int? = itemZero?.value as Int?
        if (itemZeroVal is Int) {
            trace("AIR Array elem at 0 type:", "value:", itemZeroVal)
            val newVal = FreObjectKotlin(56)
            airArray?.setObjectAt(0, newVal)
            return airArray?.rawValue.guard { return null }
        }
        return null
    }

    fun runBitmapTests(ctx: FREContext, argv: FREArgv): FREObject? {
        val bmd = FreBitmapDataKotlin(argv[0])
        bmd.acquire()
        trace("bmd", bmd.width, bmd.height)
        if (bmd.bits32 is ByteBuffer) {
            val width = bmd.width
            val height = bmd.height

            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
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
        argv.takeIf { argv.size == 1 } ?: return ArgCountException().getError(Thread.currentThread().stackTrace)
        val inFRE0 = argv[0].guard {
            trace("Rectangle passed to runExtensibleTests cannot be null");return null
        }
        val point = Point(10, 88)
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
            Log.e(TAG, e.message)
        }

        return ret
    }

    fun runByteArrayTests(ctx: FREContext, argv: FREArgv): FREObject? {
        return null //TODO
    }

    fun runErrorTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Error Handling test***********")
        argv.takeIf { argv.size == 1 } ?: return ArgCountException().getError(Thread.currentThread().stackTrace)
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
            person.getProperty("doNotExist") //calling a property that doesn't exist
        } catch (e: FreException) {
            return e.getError(Thread.currentThread().stackTrace) //return the error as an actionscript error
        }

        return null
    }

    fun runErrorTests2(ctx: FREContext, argv: FREArgv): FREObject? {
        argv.takeIf { argv.size == 1 } ?: return ArgCountException().getError(Thread.currentThread().stackTrace) //check number of args is 1
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

    override fun onStarted() {
        super.onStarted()
    }

    override fun onRestarted() {
        super.onRestarted()
    }

    override fun onResumed() {
        super.onResumed()
    }

    override fun onPaused() {
        super.onPaused()
    }

    override fun onStopped() {
        super.onStopped()
    }

    override fun onDestroyed() {
        super.onDestroyed()
    }

    override val TAG: String
        get() = this::class.java.canonicalName
    private var _context: FREContext? = null
    override var context: FREContext?
        get() = _context
        set(value) {
            _context = value
        }

}





