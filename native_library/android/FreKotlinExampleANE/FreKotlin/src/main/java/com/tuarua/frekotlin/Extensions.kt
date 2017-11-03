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

fun Double(freObject: FREObject?): Double? {
    val v = FreKotlinHelper.getAsObject(freObject)
    return (v as? Int)?.toDouble() ?: v as Double?
}

fun Long(freObject: FREObject?): Long? {
    val v = FreKotlinHelper.getAsObject(freObject)
    return (v as? Int)?.toLong() ?: v as Long?
}

fun Float(freObject: FREObject?): Float? {
    val v = FreKotlinHelper.getAsObject(freObject)
    return (v as? Int)?.toFloat() ?: (v as Double?)?.toFloat()
}

@Throws(FreException::class)
fun BooleanArray(freArray: FREArray?): BooleanArray? {
    if (freArray != null) {
        val count = freArray.length.toInt()
        val kotArr: BooleanArray = kotlin.BooleanArray(count)
        for (i in 0 until count) {
            val fre = freArray.getObjectAt(i.toLong())
            val b = Boolean(FreObjectKotlin(fre).rawValue)
            when {
                b != null -> kotArr[i] = b
                else -> return null
            }
        }
        return kotArr
    }
    return null
}

@Throws(FreException::class)
fun BooleanArray(freObject: FREObject?): BooleanArray? {
    if (freObject != null) {
        return BooleanArray(FREArray(freObject))
    }
    return null
}

@Throws(FreException::class)
fun DoubleArray(freArray: FREArray?): DoubleArray? {
    if (freArray != null) {
        val count = freArray.length.toInt()
        val kotArr: DoubleArray = kotlin.DoubleArray(count)
        for (i in 0 until count) {
            val fre = freArray.getObjectAt(i.toLong())
            val b = Double(FreObjectKotlin(fre).rawValue)
            when {
                b != null -> kotArr[i] = b
                else -> return null
            }
        }
        return kotArr
    }
    return null
}

@Throws(FreException::class)
fun DoubleArray(freObject: FREObject?): DoubleArray? {
    if (freObject != null) {
        return DoubleArray(FREArray(freObject))
    }
    return null
}

@Throws(FreException::class)
fun IntArray(freArray: FREArray?): IntArray? {
    if (freArray != null) {
        val count = freArray.length.toInt()
        val kotArr: IntArray = kotlin.IntArray(count)
        for (i in 0 until count) {
            val fre = freArray.getObjectAt(i.toLong())
            val b = Int(FreObjectKotlin(fre).rawValue)
            when {
                b != null -> kotArr[i] = b
                else -> return null
            }
        }
        return kotArr
    }
    return null
}

@Throws(FreException::class)
fun IntArray(freObject: FREObject?): IntArray? {
    if (freObject != null) {
        return IntArray(FREArray(freObject))
    }
    return null
}

@Suppress("UNCHECKED_CAST", "unused")
fun <String> List(freArray: FREArray?): List<String> {
    if (freArray != null) {
        return freArray.let { FreKotlinHelper.getAsObject(it) as List<String> }
    }
    return listOf()
}

fun <String> List(freObject: FREObject?): List<String> {
    if (freObject != null) {
        return List(FREArray(freObject))
    }
    return listOf()
}

@Suppress("UNCHECKED_CAST", "unused")
fun <String, Any>Map(freObject: FREObject?): Map<String, Any>? {
    if (freObject != null) {
        return FreKotlinHelper.getAsObject(freObject) as Map<String, Any>?
    }
    return null
}

fun Int(freObject: FREObject?): Int? = FreKotlinHelper.getAsObject(freObject) as Int?

fun String(freObject: FREObject?): String? {
    val obj = FreKotlinHelper.getAsObject(freObject)
    return when {
        obj != null -> FreKotlinHelper.getAsObject(freObject).toString()
        else -> null
    }
}

fun Boolean(freObject: FREObject?): Boolean? = FreKotlinHelper.getAsObject(freObject) as Boolean?

fun Date(freObject: FREObject?): Date? = FreKotlinHelper.getAsObject(freObject) as Date?

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

var FREObject.type: FreObjectTypeKotlin
    get() {
        return FreKotlinHelper.getType(this)
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
        rv.set(i, value[i])
    }
    return rv
}

@Throws(FreException::class)
fun FREArray(value: DoubleArray): FREArray {
    val rv = FREArray.newArray(value.size)
    for (i in value.indices) {
        rv.set(i, value[i])
    }
    return rv
}

fun FREArray(value: BooleanArray): FREArray {
    val rv = FREArray.newArray(value.size)
    for (i in value.indices) {
        rv.set(i, value[i])
    }
    return rv
}

@Throws(FreException::class)
fun FREArray(value: List<String>): FREArray {
    val rv = FREArray.newArray(value.size)
    for (i in value.indices) {
        rv.set(i, value[i])
    }
    return rv
}

@Throws(FreException::class)
fun FREArray.at(index: Int): FREObject? {
    return this.getObjectAt(index.toLong())
}

@Throws(FreException::class)
fun FREArray.set(index: Int, value: Any?) {
    this.setObjectAt(index.toLong(), FreObjectKotlin(value).rawValue)
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

fun FREObject.toColor(alpha: Int = 255): Int {
    if (this.type != FreObjectTypeKotlin.INT) return 0
    val freColor = Int(this) ?: return 0
    return Color.argb(alpha, Color.red(freColor), Color.green(freColor), Color.blue(freColor))
}

fun FREObject.toHSV(alpha: Int = 255): Float {
    if (this.type != FreObjectTypeKotlin.INT) return 0.0F
    val hsv = FloatArray(3)
    Color.colorToHSV(toColor(alpha), hsv)
    return hsv[0]
}

// ************ toFREObject **************//
fun Int.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this)
    } catch (e: Exception) {
        FreException(e, "cannot create new object from Int").getError(Thread.currentThread().stackTrace)
    }
}

fun Short.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this.toInt())
    } catch (e: Exception) {
        FreException(e, "cannot create new object from Short").getError(Thread.currentThread().stackTrace)
    }
}

fun Boolean.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this)
    } catch (e: Exception) {
        FreException(e, "cannot create new object from Boolean").getError(Thread.currentThread().stackTrace)
    }
}

fun String.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this)
    } catch (e: Exception) {
        FreException(e, "cannot create new object from String").getError(Thread.currentThread().stackTrace)
    }
}

fun Double.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this)
    } catch (e: Exception) {
        FreException(e, "cannot create new object from Double").getError(Thread.currentThread().stackTrace)
    }
}

fun Long.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this.toDouble())
    } catch (e: Exception) {
        FreException(e, "cannot create new object from Long").getError(Thread.currentThread().stackTrace)
    }
}

@Throws(FreException::class)
fun Float.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this.toDouble())
    } catch (e: Exception) {
        FreException(e, "cannot create new object from Float").getError(Thread.currentThread().stackTrace)
    }
}

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

@Throws(FreException::class)
fun IntArray.toFREArray(): FREArray? {
    try {
        return FREArray(this)
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from IntArray")
    }
}

@Throws(FreException::class)
fun BooleanArray.toFREArray(): FREArray? {
    try {
        return FREArray(this)
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from BooleanArray")
    }
}


@Throws(FreException::class)
fun DoubleArray.toFREArray(): FREArray? {
    try {
        return FREArray(this)
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from BooleanArray")
    }
}

@Throws(FreException::class)
fun List<String>.toFREArray(): FREArray? {
    try {
        return FREArray(this)
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from List<String>")
    }
}
