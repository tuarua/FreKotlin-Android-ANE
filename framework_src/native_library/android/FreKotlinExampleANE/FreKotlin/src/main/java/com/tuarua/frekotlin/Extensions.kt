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

@file:Suppress("unused", "FunctionName")

package com.tuarua.frekotlin

import android.graphics.Color
import com.adobe.fre.FREArray
import com.adobe.fre.FREContext
import com.adobe.fre.FREObject
import java.util.*

typealias FREArgv = ArrayList<FREObject>
fun FREContext.sendEvent(name: String, value: String) {
    this.dispatchStatusEventAsync(value, name)
}

fun FREContext.trace(TAG: String, args: Array<out Any?>) {
    @Suppress("LocalVariableName")
    val TRACE = "TRACE"
    var traceStr = "$TAG: "
    for (v in args)
        traceStr = traceStr + "$v" + " "
    this.sendEvent(TRACE, traceStr)
}

// Declare an extension function that calls a lambda called block if the value is null
inline fun <T> T.guard(block: T.() -> Unit): T {
    if (this == null) block(); return this
}

/**
 * converts a FREObject to a Double
 *
 */
fun Double(freObject: FREObject?): Double? =FreKotlinHelper.getAsDouble(freObject)

/**
 * converts a FREObject to a Long
 *
 */
fun Long(freObject: FREObject?): Long? = FreKotlinHelper.getAsDouble(freObject)?.toLong()

/**
 * converts a FREObject to a Float
 *
 */
fun Float(freObject: FREObject?): Float? = FreKotlinHelper.getAsDouble(freObject)?.toFloat()


/**
 * converts a FREArray to a BooleanArray
 *
 */
@Throws(FreException::class)
fun BooleanArray(freArray: FREArray?): BooleanArray? {
    if (freArray != null) {
        val count = freArray.length.toInt()
        val kotArr: BooleanArray = kotlin.BooleanArray(count)
        for (i in 0 until count) {
            val b = Boolean(freArray.getObjectAt(i.toLong()))
            when {
                b != null -> kotArr[i] = b
                else -> return null
            }
        }
        return kotArr
    }
    return null
}

/**
 * converts a FREObject to a BooleanArray
 *
 */
@Throws(FreException::class)
fun BooleanArray(freObject: FREObject?): BooleanArray? {
    if (freObject != null) {
        return BooleanArray(FREArray(freObject))
    }
    return null
}

/**
 * converts a FREArray to a DoubleArray
 *
 */
@Throws(FreException::class)
fun DoubleArray(freArray: FREArray?): DoubleArray? {
    if (freArray != null) {
        val count = freArray.length.toInt()
        val kotArr: DoubleArray = kotlin.DoubleArray(count)
        for (i in 0 until count) {
            val b = Double(freArray.getObjectAt(i.toLong()))
            when {
                b != null -> kotArr[i] = b
                else -> return null
            }
        }
        return kotArr
    }
    return null
}

/**
 * converts a FREObject to a DoubleArray
 *
 */
@Throws(FreException::class)
fun DoubleArray(freObject: FREObject?): DoubleArray? {
    if (freObject != null) {
        return DoubleArray(FREArray(freObject))
    }
    return null
}

/**
 * converts a FREArray to an IntArray
 *
 */
@Throws(FreException::class)
fun IntArray(freArray: FREArray?): IntArray? {
    if (freArray != null) {
        val count = freArray.length.toInt()
        val kotArr: IntArray = kotlin.IntArray(count)
        for (i in 0 until count) {
            val b = Int(freArray.getObjectAt(i.toLong()))
            when {
                b != null -> kotArr[i] = b
                else -> return null
            }
        }
        return kotArr
    }
    return null
}

/**
 * converts a FREObject to an IntArray
 *
 */
@Throws(FreException::class)
fun IntArray(freObject: FREObject?): IntArray? {
    if (freObject != null) {
        return IntArray(FREArray(freObject))
    }
    return null
}

/**
 * converts a FREArray to an List<String>
 *
 */
@Suppress("UNCHECKED_CAST", "unused")
fun <String> List(freArray: FREArray?): List<String> {
    if (freArray != null) {
        return freArray.let { FreKotlinHelper.getAsObject(it) as List<String> }
    }
    return listOf()
}
/**
 * converts a FREObject to an List<String>
 *
 */
fun <String> List(freObject: FREObject?): List<String> {
    if (freObject != null) {
        return List(FREArray(freObject))
    }
    return listOf()
}

@Suppress("UNCHECKED_CAST", "unused")
fun <String, Any> Map(freObject: FREObject?): Map<String, Any>? {
    if (freObject != null) {
        return FreKotlinHelper.getAsObject(freObject) as Map<String, Any>?
    }
    return null
}

/**
 * converts a FREObject to a Int
 */
fun Int(freObject: FREObject?): Int? = FreKotlinHelper.getAsInt(freObject)

/**
 * converts a FREObject to a String
 */
fun String(freObject: FREObject?): String? = FreKotlinHelper.getAsString(freObject)

/**
 * converts a FREObject to a Boolean
 */
fun Boolean(freObject: FREObject?): Boolean? = FreKotlinHelper.getAsBoolean(freObject)

/**
 * converts a FREObject to a Date
 */
fun Date(freObject: FREObject?): Date? = FreKotlinHelper.getAsDate(freObject)

@Throws(FreException::class)
fun FREObject?.call(method: String, vararg args: Any): FREObject? {
    val rv = this ?: return null
    return FreKotlinHelper.call(rv, method, args)
}

@Throws(FreException::class)
fun FREObject?.call(method: String): FREObject? {
    val rv = this ?: return null
    return FreKotlinHelper.call(rv, method)
}

@Throws(FreException::class)
fun FREObject?.call(method: String, vararg args: FREObject): FREObject? {
    val rv = this ?: return null
    return FreKotlinHelper.call(rv, method, args)
}

var FREObject?.type: FreObjectTypeKotlin
    get() {
        return when {
            this == null -> FreObjectTypeKotlin.NULL
            else -> FreKotlinHelper.getType(this)
        }
    }
    set(value) = Unit

@Throws(FreException::class)
fun FREObject(name: String, vararg args: Any?): FREObject {
    val argsArr = arrayOfNulls<FREObject>(args.size)
    for (i in args.indices) {
        argsArr[i] = FreObjectKotlin(args[i]).rawValue
    }
    try {
        return FREObject.newObject(name, argsArr)
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object named $name")
    }
}

fun FREArray(freObject: FREObject): FREArray {
    return freObject as FREArray
}

@Throws(FreException::class)
fun FREArray(value: IntArray): FREArray {
    val rv = FREArray.newArray(value.size)
    for (i in value.indices) {
        rv[i] = value[i].toFREObject()
    }
    return rv
}

@Throws(FreException::class)
fun FREArray(value: DoubleArray): FREArray {
    val rv = FREArray.newArray(value.size)
    for (i in value.indices) {
        rv[i] = value[i].toFREObject()
    }
    return rv
}

fun FREArray(value: BooleanArray): FREArray {
    val rv = FREArray.newArray(value.size)
    for (i in value.indices) {
        rv[i] = value[i].toFREObject()
    }
    return rv
}

@Throws(FreException::class)
fun FREArray(value: List<String>): FREArray {
    val rv = FREArray.newArray(value.size)
    for (i in value.indices) {
        rv[i] = value[i].toFREObject()
    }
    return rv
}

@Throws(FreException::class)
fun FREArray.at(index: Int): FREObject? {
    return this.getObjectAt(index.toLong())
}

@Throws(FreException::class)
operator fun FREArray.set(index: Int, value: FREObject?) {
    this.setObjectAt(index.toLong(), value)
}

operator fun FREArray.get(index: Int): FREObject? {
    return try {
        this.getObjectAt(index.toLong())
    } catch (e: Exception) {
        null
    }
}

@Throws(FreException::class)
fun FREObject.setProp(name: String, value: Any?) {
    if (value is FREObject) {
        FreKotlinHelper.setProperty(this, name, value)
        return
    }

    if (value is FreObjectKotlin) {
        FreKotlinHelper.setProperty(this, name, value.rawValue)
        return
    }

    if (value is String) {
        FreKotlinHelper.setProperty(this, name, value.toFREObject())
        return
    }
    if (value is Int) {
        FreKotlinHelper.setProperty(this, name, value.toFREObject())
        return
    }

    if (value is Double) {
        FreKotlinHelper.setProperty(this, name, value.toFREObject())
        return
    }

    if (value is Long) {
        FreKotlinHelper.setProperty(this, name, value.toFREObject())
        return
    }

    if (value is Short) {
        FreKotlinHelper.setProperty(this, name, value.toFREObject())
        return
    }

    if (value is Boolean) {
        FreKotlinHelper.setProperty(this, name, value.toFREObject())
        return
    }

    if (value is Date) {
        FreKotlinHelper.setProperty(this, name, value.toFREObject())
        return
    }

    if (value is Any) {
        //Log.e(TAG, "any is an Any - NOT FOUND")
        return
    }
}

@Throws(FreException::class)
fun FREObject.getProp(name: String): FREObject? {
    return FreKotlinHelper.getProperty(this, name)
}

operator fun FREObject.get(name: String): FREObject? {
    return try {
        this.getProp(name)
    } catch (e: Exception) {
        null
    }
}
/**
 * converts a FREObject of type uint to a Color
 *
 */
fun FREObject.toColor(hasAlpha: Boolean = false): Int {
    val freColor = Long(this)
    if (freColor != null) {
        var alpha = 255
        if (hasAlpha) alpha = (freColor shr 24 and 0xff).toInt()
        val red: Int = (freColor shr 16 and 0xff).toInt()
        val green: Int = (freColor shr 8 and 0xff).toInt()
        val blue: Int = (freColor and 0xff).toInt()
        return Color.argb(alpha, red, green, blue)
    }
    return Color.BLACK
}

fun FREObject.toHSV(hasAlpha: Boolean = false): Float {
    val hsv = FloatArray(3)
    Color.colorToHSV(toColor(hasAlpha), hsv)
    return hsv[0]
}

/**
 * converts a Int to a FREObject of type int
 *
 */
fun Int.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this)
    } catch (e: Exception) {
        FreException(e, "cannot create new object from Int").getError(Thread.currentThread().stackTrace)
    }
}

/**
 * converts a Short to a FREObject of type int
 */
fun Short.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this.toInt())
    } catch (e: Exception) {
        FreException(e, "cannot create new object from Short").getError(Thread.currentThread().stackTrace)
    }
}

/**
 * converts a Boolean to a FREObject of type Boolean
 */
fun Boolean.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this)
    } catch (e: Exception) {
        FreException(e, "cannot create new object from Boolean").getError(Thread.currentThread().stackTrace)
    }
}

/**
 * converts a String to a FREObject of type String
 */
fun String.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this)
    } catch (e: Exception) {
        FreException(e, "cannot create new object from String").getError(Thread.currentThread().stackTrace)
    }
}

/**
 * converts a Double to a FREObject of type Number
 */
fun Double.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this)
    } catch (e: Exception) {
        FreException(e, "cannot create new object from Double").getError(Thread.currentThread().stackTrace)
    }
}

/**
 * converts a Long to a FREObject of type Number
 */
fun Long.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this.toDouble())
    } catch (e: Exception) {
        FreException(e, "cannot create new object from Long").getError(Thread.currentThread().stackTrace)
    }
}

/**
 * converts a Float to a FREObject of type Number
 */
@Throws(FreException::class)
fun Float.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this.toDouble())
    } catch (e: Exception) {
        FreException(e, "cannot create new object from Float").getError(Thread.currentThread().stackTrace)
    }
}

/**
 * converts a Date to a FREObject of type Date
 */
@Throws(FreException::class)
fun Date.toFREObject(): FREObject? {
    return try {
        FreObjectKotlin(this).rawValue
    } catch (e: FreException) {
        e.getError(Thread.currentThread().stackTrace)
    } catch (e: Exception) {
        FreException(e).getError(Thread.currentThread().stackTrace)
    }
}

/**
 * converts a IntArray to a FREArray
 */
@Throws(FreException::class)
fun IntArray.toFREArray(): FREArray? {
    try {
        return FREArray(this)
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from IntArray")
    }
}

/**
 * converts a BooleanArray to a FREArray
 */
@Throws(FreException::class)
fun BooleanArray.toFREArray(): FREArray? {
    try {
        return FREArray(this)
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from BooleanArray")
    }
}

/**
 * converts a DoubleArray to a FREArray
 */
@Throws(FreException::class)
fun DoubleArray.toFREArray(): FREArray? {
    try {
        return FREArray(this)
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from BooleanArray")
    }
}

/**
 * converts a List<String> to a FREArray
 */
@Throws(FreException::class)
fun List<String>.toFREArray(): FREArray? {
    try {
        return FREArray(this)
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from List<String>")
    }
}

