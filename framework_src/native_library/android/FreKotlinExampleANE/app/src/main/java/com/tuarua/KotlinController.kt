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
import android.graphics.PointF
import android.util.Base64
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import com.adobe.fre.FREContext
import com.adobe.fre.FREObject
import com.tuarua.frekotlin.*
import com.tuarua.frekotlin.display.FreBitmapDataKotlin
import com.tuarua.frekotlin.geom.RectF
import com.tuarua.frekotlin.geom.toFREObject
import java.nio.ByteBuffer
import java.nio.charset.Charset

@Suppress("unused", "UNUSED_PARAMETER", "UNCHECKED_CAST")
class KotlinController : FreKotlinMainController {
    private var airView: ViewGroup? = null
    private var container: FrameLayout? = null

    fun runStringTests(ctx: FREContext, argv: FREArgv): FREObject? {

        warning("I am a test warning")
        info("I am a test info")

        trace("***********Start String test***********")
        argv.takeIf { argv.size > 0 } ?: return FreArgException("runStringTests")
        val airString = String(argv[0]) ?: return null

        trace("This is AIR string", airString)
        dispatchEvent("MY_EVENT", "this is a test")

        val kotlinString = "I am a string from Kotlin with UTF-8: Björk Guðmundsdóttir Sinéad O’Connor " +
                "久保田  利伸 Михаил Горбачёв Садриддин Айнӣ Tor Åge Bringsværd 章子怡 €"
        return kotlinString.toFREObject()
    }

    fun runIntTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Int Uint test***********")
        val airInt = Int(argv[0])
        val airIntAsDouble = Double(argv[0])
        val airUInt: Int? = Int(argv[1])

        val testInt = -54
        val testDouble = -54.0
        val testUint = 66

        trace("Number passed from AIR as Int:", airInt, if (testInt == airInt) "✅" else "❌")
        trace("Number passed from AIR as Int to Double:", airIntAsDouble, if (testDouble == airIntAsDouble) "✅" else "❌")
        trace("Number passed from AIR as UInt:", airUInt, if (testUint == airUInt) "✅" else "❌")

        val kotlinInt: Int = -666
        return kotlinInt.toFREObject()
    }

    fun runNumberTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Number test***********")
        val airDouble = Double(argv[0])
        val airFloat = Float(argv[0])

        val testDouble = 31.99
        val testFloat = 31.99f

        trace("Number passed from AIR as Double:", airDouble, if (testDouble == airDouble) "✅" else "❌")
        trace("Number passed from AIR as Float:", airFloat, if (testFloat == airFloat) "✅" else "❌")

        val kotlinDouble = 34343.31
        return kotlinDouble.toFREObject()
    }

    fun runObjectTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Object test***********")
        val person = argv[0]
        val oldAge = Int(person["age"])

        val newPerson = FREObject("com.tuarua.Person")
        trace("New Person is of type CLASS:", newPerson.type,
                if (newPerson.type == FreObjectTypeKotlin.CLASS) "✅" else "❌")
        trace("Get property as Int :", oldAge, if (21 == oldAge) "✅" else "❌")
        if (oldAge is Int) {
            person["age"] = (oldAge + 10).toFREObject()
            trace("Set property to Int :", Int(person["age"]), if (31 == Int(person["age"])) "✅" else "❌")
        }
        val addition = person.call("add", 100, 31)
        if (addition != null) {
            trace("Call add :", 131, if (131 == Int(addition)) "✅" else "❌")
        }

        val cityName = String(person["city"]["name"])
        trace("Get property as String :", cityName, if ("Boston" == cityName) "✅" else "❌")

        try {
            val dictionary: Map<String, Any>? = Map(person)
            trace("keys: ${dictionary?.keys.toString()} values: ${dictionary?.values.toString()}")
        } catch (e: Exception) {
            Log.e(TAG, "ERROR: ${e.message}")
        }

        return person
    }

    fun runDateTests(ctx: FREContext, argv: FREArgv): FREObject? {
        val date = Date(argv[0])
        return date?.toFREObject()
    }

    fun runArrayTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Array test ***********")
        val airArray = FREArray(argv[0])
        airArray.push(77.toFREObject(), 88)
        trace("Get FREArray length :", airArray.length, if (8L == airArray.length) "✅" else "❌")
        for (fre: FREObject? in airArray) {
            trace("iterate over FREArray", Int(fre))
        }

        val itemZero: FREObject? = airArray[0]
        val itemZeroVal = Int(itemZero)
        if (itemZeroVal is Int) {
            airArray[0] = 56.toFREObject()
        }

        val newFreArray = FREArray("Object", 5, true)
        trace("New FREArray of fixed length:", newFreArray?.length, if (5L == newFreArray?.length) "✅" else "❌")

        val airVector = FREArray(argv[1])
        trace("Get FREArray length :", airVector.length, if (2L == airVector.length) "✅" else "❌")
        val al = List<String>(airVector)
        for (s in al) {
            trace("Vector.<String> passed from AIR elem:", s)
        }

        val kotArr: IntArray = intArrayOf(1, 2, 3)
        val kotArrayFre = FREArray(kotArr)
        val kotArrBack = IntArray(kotArrayFre)
        trace("Kotlin IntArray :", if (3 == kotArrBack[2]) "✅" else "❌")
        return airArray

    }

    fun runBitmapTests(ctx: FREContext, argv: FREArgv): FREObject? {
        argv.takeIf { argv.size > 0 } ?: return FreArgException("runBitmapTests")
        //var icon: Bitmap? = Bitmap(argv[0]) //to convert bitmapdata into Android Bitmap
        //To manipulate the bitmapdata passed in
        val bmd = FreBitmapDataKotlin(argv[0])
        bmd.acquire()
        if (bmd.bits32 is ByteBuffer) {
            val width = bmd.width
            val height = bmd.height

            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            bmp.copyPixelsFromBuffer(bmd.bits32)

            var bmpSepia = BitmapUtils.sepiaFilter(image = bmp)
            bmpSepia = FreBitmapDataKotlin.doSwapColors(bmpSepia)
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
        argv.takeIf { argv.size > 0 } ?: return FreArgException("runExtensibleTests")
        val rectangleF = RectF(argv[0])
        val point = PointF(0f, 50.2f)
        trace("RectF :", rectangleF, if (rectangleF.left == 50.1f) "✅" else "❌")
        val frePoint = point.toFREObject()
        if (frePoint != null) {
            trace("Point :", point, if (Float(frePoint["x"]) == 0f && Float(frePoint["y"]) == 50.2f) "✅" else "❌")
        }
        return rectangleF.toFREObject()
    }

    fun runByteArrayTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start ByteArray test***********")
        argv.takeIf { argv.size > 0 } ?: return FreArgException("runByteArrayTests")
        val byteArray = ByteArray(argv[0])
        if (byteArray != null) {
            val str = String(Base64.encode(byteArray, Base64.NO_WRAP), Charset.forName("utf-8"))
            trace("ByteArray passed from AIR to base64:", str,
                    if ("S290bGluIGluIGFuIEFORS4gU2F5IHdoYWFhYXQh" == str) "✅" else "❌")
        }
        return null
    }

    fun runErrorTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Error Handling test***********")
        argv.takeIf { argv.size > 1 } ?: return FreArgException("runErrorTests")
        val person = argv[0]
        val inFRE1 = argv[1]
        person.call("add", 100) //not passing enough args

        @Suppress("UNUSED_VARIABLE")
        val p: FREObject? = person["doNotExist"] //calling a property that doesn't exist

        if (inFRE1.type != FreObjectTypeKotlin.INT) {
            return FreException("Oops, we expected the FREObject to be passed as an int but it's not").getError();
        }
        return null
    }

    fun runDataTests(ctx: FREContext, argv: FREArgv): FREObject? {
        return null
    }

    fun runColorTests(ctx: FREContext, argv: FREArgv): FREObject? {
        trace("***********Start Color test***********")
        val airColor = argv[0].toColor(false)
        val airHSV = argv[0].toHSV(false)
        val airColorWithAlpha = argv[1].toColor()

        val testColor = Color.GREEN

        trace("Colour passed from AIR as Color (RGB):", airColor,
                if (255 == Color.alpha(airColor)
                        && 0 == Color.red(airColor)
                        && 255 == Color.green(airColor)
                        && 0 == Color.blue(airColor)
                ) "✅" else "❌")


        trace("Colour passed from AIR as Color (RGB):", airColor,
                if (testColor.equals(airColor)) "✅" else "❌")

        trace("Number passed from AIR as Float:", airHSV, if (120.0f == airHSV) "✅" else "❌")

        trace("Colour passed from AIR as Color (ARGB):", airColorWithAlpha,
                if (128 == Color.alpha(airColorWithAlpha)
                        && 0 == Color.red(airColorWithAlpha)
                        && 255 == Color.green(airColorWithAlpha)
                        && 0 == Color.blue(airColorWithAlpha)
                ) "✅" else "❌")

        return airColorWithAlpha.toFREObject()
    }

    @Suppress("PropertyName")
    override val TAG: String
        get() = "FreKotlinExampleANE"
    private var _context: FREContext? = null
    override var context: FREContext?
        get() = _context
        set(value) {
            _context = value
            FreKotlinLogger.context = _context
        }
}
