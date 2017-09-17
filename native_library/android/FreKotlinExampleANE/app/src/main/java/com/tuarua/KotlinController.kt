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
import android.graphics.Color
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import com.adobe.fre.FREArray
import com.adobe.fre.FREContext
import com.adobe.fre.FREObject
import com.tuarua.frekotlin.*
import com.tuarua.frekotlin.display.FreBitmapDataKotlin
import com.tuarua.frekotlin.geom.FrePointKotlin
import com.tuarua.frekotlin.geom.Point
import com.tuarua.frekotlin.geom.Rect
import com.tuarua.frekotlin.geom.toFREObject
import java.nio.ByteBuffer

@Suppress("unused", "UNUSED_PARAMETER", "UNCHECKED_CAST")
class KotlinController : FreKotlinMainController {
    private var airView: ViewGroup? = null
    private var container: FrameLayout? = null

    fun runStringTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start String test***********")
        argv.takeIf { argv.size > 0 } ?: return ArgCountException().getError(Thread.currentThread().stackTrace)
        val airString = String(argv[0]).guard {
            return ArgException().getError(Thread.currentThread().stackTrace)
        }

        trace("This is AIR string", airString)
        sendEvent("MY_EVENT", "this is a test")

        val kotlinString = "I am a string from Kotlin"
        return kotlinString.toFREObject()
    }

    fun runIntTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Int Uint test***********")
        val airInt = Int(argv[0])
        val airIntAsDouble = Double(argv[0])
        val airUInt: Int? = Int(argv[1])
        val airColor = argv[2].toColor(255)
        val airHSV = argv[2].toHSV(255)

        trace("Int passed from AIR:", airInt)
        trace("UInt passed from AIR:", airUInt)
        trace("Int passed from AIR as Double:", airIntAsDouble)
        trace("Colour passed from AIR as Int:", airColor)
        trace("Colour passed from AIR as Color: ${Color.red(airColor)} ${Color.green(airColor)} ${Color.blue(airColor)}")
        trace("Colour passed from AIR as HSV: $airHSV")

        val kotlinInt: Int = -666
        return kotlinInt.toFREObject()
    }

    fun runNumberTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Number test***********")
        val airNumber = Double(argv[0])
        val airNumberAsFloat = Float(argv[0])
        trace("Number passed from AIR:", airNumber)
        trace("Number passed from AIR as Float:", airNumberAsFloat)
        val kotlinDouble = 34343.31
        return kotlinDouble.toFREObject()
    }

    fun runObjectTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Object test***********")
        val person = argv[0]
        val oldAge = Int(person.getProp("age"))

        val newPerson = FREObject("com.tuarua.Person")
        trace("We created a new person. type = ${newPerson.type}")
        trace("current person age is $oldAge")

        if (oldAge is Int) {
            person.setProp("age", oldAge + 10)
        }

        try {
            val addition = person.call("add", 100, 31)
            if (addition != null) {
                trace("addition result: ${Int(addition)}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "ERROR: ${e.message}")
        }

        try {
            val dictionary: Map<String, Any>? = Map(person)
            trace("keys: ${dictionary?.keys.toString()} values: ${dictionary?.values.toString()}")
        } catch (e: Exception) {
            Log.e(TAG, "ERROR: ${e.message}")
        }

        return person
    }

    fun runDateTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Date test ***********")
        val date = Date(argv[0])
        trace("unix time stamp:", date?.time.toString())
        return date?.toFREObject()
    }

    fun runArrayTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Array test ***********")
        try {
            val airArray: FREArray? = FREArray(freObject = argv[0])
            val airArrayLen = airArray?.length
            trace("AIR Array length:", airArrayLen)

            val itemZero: FREObject? = airArray?.at(0)
            val itemZeroVal: Int? = Int(itemZero)
            if (itemZeroVal is Int) {
                trace("AIR Array elem at 0 type:", "value:", itemZeroVal)
                airArray?.set(0, 56)
            }

            val airVector: FREArray? = FREArray(freObject = argv[1])
            val airVectorLen = airVector?.length

            trace("air vector len: ", airVectorLen)

            val al = List<String>(airVector)
            for (s in al) {
                trace("Vector.<String> passed from AIR elem:", s)
            }

            val kotArr: IntArray = intArrayOf(1, 2, 3)
            val kotArrayFre = FREArray(kotArr)
            val kotArrBack = IntArray(kotArrayFre)

            return airArray

        } catch (e: FreException) {
            trace(e.message)
            trace(e.stackTrace)
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
        argv.takeIf { argv.size > 0 } ?: return ArgCountException().getError(Thread.currentThread().stackTrace)
        val inFRE0 = argv[0].guard {
            trace("Rectangle passed to runExtensibleTests cannot be null");return null
        }
        val point = Point(0.0, 50.0)
        try {
            val rectangle = Rect(inFRE0)
            rectangle?.set(0, 0, 999, 111)
            val frePoint = FrePointKotlin(point)
            trace("frePoint is", frePoint.value.x, frePoint.value.y)
            return rectangle?.toFREObject()
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
        return null
    }

    fun runByteArrayTests(ctx: FREContext, argv: FREArgv): FREObject? {
        return null //TODO
    }

    fun runErrorTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Error Handling test***********")
        argv.takeIf { argv.size > 0 } ?: return ArgCountException().getError(Thread.currentThread().stackTrace)
        val person = argv[0].guard {
            return ArgException().getError(Thread.currentThread().stackTrace)
        }

        try {
            person.call("add", 100) //not passing enough args
        } catch (e: FreException) {
            Log.e(TAG, e.message)
            Log.e(TAG, e.stackTrace)
        }

        try {
            val p: FREObject? = person.getProp("doNotExist") //calling a property that doesn't exist
        } catch (e: FreException) {
            return e.getError(Thread.currentThread().stackTrace) //return the error as an actionscript error
        }

        return null
    }

    fun runErrorTests2(ctx: FREContext, argv: FREArgv): FREObject? {
        argv.takeIf { argv.size > 0 } ?: return ArgCountException().getError(Thread.currentThread().stackTrace)
        val inFRE0 = argv[0].guard {
            return ArgException().getError(Thread.currentThread().stackTrace)
        }

        if (inFRE0.type != FreObjectTypeKotlin.INT) {
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
        get() = "FreKotlinExampleANE"
    private var _context: FREContext? = null
    override var context: FREContext?
        get() = _context
        set(value) {
            _context = value
        }
}
















