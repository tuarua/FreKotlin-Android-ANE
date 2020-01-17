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

/** Alias for param in fun */
typealias FREArgv = ArrayList<FREObject>

/** @suppress */
fun FREContext.dispatchEvent(name: String, value: String) {
    this.dispatchStatusEventAsync(value, name)
}

/** @suppress */
fun FREContext.trace(TAG: String, args: Array<out Any?>) {
    var traceStr = "$TAG: "
    for (v in args)
        traceStr = "$traceStr$v "
    this.dispatchEvent("TRACE", traceStr)
}

/** @suppress */
fun FREContext.warning(TAG: String, args: Array<out Any?>) {
    var traceStr = "$TAG: "
    for (v in args)
        traceStr = "$traceStr$v "
    this.dispatchEvent("TRACE", "⚠️WARNING: $traceStr")
}

/** @suppress */
fun FREContext.info(TAG: String, args: Array<out Any?>) {
    var traceStr = "$TAG: "
    for (v in args)
        traceStr = "$traceStr$v "
    this.dispatchEvent("TRACE", "ℹ️INFO: $traceStr")
}

/** Declare an extension function that calls a lambda called block if the value is null. */
inline fun <T> T.guard(block: T.() -> Unit): T {
    if (this == null) block(); return this
}

/** Converts a FREObject to a [Double]. */
fun Double(freObject: FREObject?): Double? = FreKotlinHelper.getAsDouble(freObject)

/** Converts a FREObject to a [Long]. */
fun Long(freObject: FREObject?): Long? = FreKotlinHelper.getAsDouble(freObject)?.toLong()

/** Converts a FREObject to a [Float]. */
fun Float(freObject: FREObject?): Float? = FreKotlinHelper.getAsDouble(freObject)?.toFloat()

/** Converts a FREObject to a Map<String, Any>. */
@Suppress("UNCHECKED_CAST", "unused")
fun <String, Any> Map(freObject: FREObject?): Map<String, Any>? {
    if (freObject != null) {
        return FreKotlinHelper.getAsObject(freObject) as Map<String, Any>?
    }
    return null
}

/** Converts a FREObject to a [Int]. */
fun Int(freObject: FREObject?): Int? = FreKotlinHelper.getAsInt(freObject)

/** Converts a FREObject to a [Short]. */
fun Short(freObject: FREObject?): Short? = FreKotlinHelper.getAsShort(freObject)

/** Converts a FREObject to a [String]. */
fun String(freObject: FREObject?): String? = FreKotlinHelper.getAsString(freObject)

/** Converts a FREObject to a [Boolean]. */
fun Boolean(freObject: FREObject?): Boolean? = FreKotlinHelper.getAsBoolean(freObject)

/** Converts a FREObject to a [Date]. */
fun Date(freObject: FREObject?): Date? = FreKotlinHelper.getAsDate(freObject)

/**
 * Calls the given method on a FREObject.
 * @param [method] name of AS3 method to call
 * @param [args] arguments to pass to the method
 */
fun FREObject?.call(method: String, vararg args: Any): FREObject? {
    val rv = this ?: return null
    return FreKotlinHelper.callMethod(rv, method, args)
}

/**
 * Calls the given method on a FREObject.
 * @param [method] name of AS3 method to call
 * */
fun FREObject?.call(method: String): FREObject? {
    val rv = this ?: return null
    return FreKotlinHelper.callMethod(rv, method)
}

/**
 * Calls the given method on a FREObject.
 * @param [method] name of AS3 method to call.
 * @param [args] arguments to pass to the method.
 */
fun FREObject?.call(method: String, vararg args: FREObject): FREObject? {
    val rv = this ?: return null
    return FreKotlinHelper.callMethod(rv, method, args)
}

@Suppress("UNUSED_PARAMETER")
/**  Returns the type of an FREObject. */
var FREObject?.type: FreObjectTypeKotlin
    get() {
        return when {
            this == null -> FreObjectTypeKotlin.NULL
            else -> FreKotlinHelper.getType(this)
        }
    }
    set(value) = Unit

@Suppress("UNUSED_PARAMETER")
/** returns the className of the FREObject */
var FREObject?.className: String?
    get() {
        val aneUtils = FREObject.newObject("com.tuarua.fre.ANEUtils", null)
        val args = arrayOfNulls<FREObject>(1)
        args[0] = this
        return String(aneUtils.callMethod("getClassType", args))
    }
    set(value) = Unit

/** Creates a FREObject */
/**
 * Calls the given method on a FREObject.
 * @param [className] name of AS3 class to create.
 * @param [args] arguments to use. These are automatically converted to FREObjects.
 */
fun FREObject(className: String, vararg args: Any?): FREObject? {
    val argsArr = arrayOfNulls<FREObject>(args.size)
    for (i in args.indices) {
        argsArr[i] = FreObjectKotlin(args[i]).rawValue
    }
    return try {
        FREObject.newObject(className, argsArr)
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot create new class $className", e)
        null
    }
}

/** Gets the named property of a FREObject */
operator fun FREObject?.get(name: String): FREObject? {
    val rv = this ?: return null
    return FreKotlinHelper.getProperty(rv, name)
}

/** Sets the named property of a FREObject to a FREObject */
operator fun FREObject?.set(name: String, value: FREObject?) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value)
}

/** Sets the named property of a FREObject to a FreObjectKotlin */
operator fun FREObject?.set(name: String, value: FreObjectKotlin?) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value?.rawValue)
}

/** Sets the named property of a FREObject to a FREArray */
operator fun FREObject?.set(name: String, value: FREArray?) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value)
}

/** Sets the named property of a FREObject to a [Int] */
operator fun FREObject?.set(name: String, value: Int) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value.toFREObject())
}

/** Sets the named property of a FREObject to a [IntArray] */
operator fun FREObject?.set(name: String, value: IntArray) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value.toFREObject())
}

/** Sets the named property of a FREObject to a [Double] */
operator fun FREObject?.set(name: String, value: Double) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value.toFREObject())
}

/** Sets the named property of a FREObject to a [DoubleArray] */
operator fun FREObject?.set(name: String, value: DoubleArray) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value.toFREObject())
}

/** Sets the named property of a FREObject to a [Float] */
operator fun FREObject?.set(name: String, value: Float) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value.toFREObject())
}

/** Sets the named property of a FREObject to a [FloatArray] */
operator fun FREObject?.set(name: String, value: FloatArray) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value.toFREObject())
}

/** Sets the named property of a FREObject to a [String] */
operator fun FREObject?.set(name: String, value: String?) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value?.toFREObject())
}

/** Sets the named property of a FREObject to a [String] */
operator fun FREObject?.set(name: String, value: List<String>) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value.toFREObject())
}

/** Sets the named property of a FREObject to a [Date] */
operator fun FREObject?.set(name: String, value: Date?) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value?.toFREObject())
}

/** Sets the named property of a FREObject to a [Boolean] */
operator fun FREObject?.set(name: String, value: Boolean) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value.toFREObject())
}

/** Sets the named property of a FREObject to a [BooleanArray] */
operator fun FREObject?.set(name: String, value: BooleanArray) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value.toFREObject())
}

/** Sets the named property of a FREObject to a [Short] */
operator fun FREObject?.set(name: String, value: Short) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value.toFREObject())
}

/** Sets the named property of a FREObject to a [ShortArray] */
operator fun FREObject?.set(name: String, value: ShortArray) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value.toFREObject())
}

/** Sets the named property of a FREObject to a [Long] */
operator fun FREObject?.set(name: String, value: Long) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value.toFREObject())
}

/** Sets the named property of a FREObject to a [LongArray] */
operator fun FREObject?.set(name: String, value: LongArray) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value.toFREObject())
}

/** Converts a FREObject of type uint to a Color */
fun FREObject.toColor(hasAlpha: Boolean = true): Int {
    val freColor = Long(this)
    if (freColor != null) {
        var alpha = 255
        if (hasAlpha) alpha = (freColor shr 24 and 0xff).toInt()
        val red = (freColor shr 16 and 0xff).toInt()
        val green = (freColor shr 8 and 0xff).toInt()
        val blue = (freColor and 0xff).toInt()
        return Color.argb(alpha, red, green, blue)
    }
    return Color.BLACK
}

/** Converts a FREObject of type uint to a HSV Color */
fun FREObject.toHSV(hasAlpha: Boolean = true): Float {
    val hsv = FloatArray(3)
    Color.colorToHSV(toColor(hasAlpha), hsv)
    return hsv[0]
}

/** Converts an Int to a FREObject of type int */
fun Int.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this)
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot create FREObject from $this", e)
        null
    }
}

/** Converts a Short to a FREObject of type int */
fun Short.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this.toInt())
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot create FREObject from $this", e)
        null
    }
}

/** Converts a Boolean to a FREObject of type Boolean */
fun Boolean.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this)
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot create FREObject from $this", e)
        null
    }
}

/** Converts a String to a FREObject of type String */
fun String.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this)
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot create FREObject from $this", e)
        null
    }
}

/** Converts a Double to a FREObject of type Number */
fun Double.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this)
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot create FREObject from $this", e)
        null
    }
}

/** Converts a Long to a FREObject of type Number */
fun Long.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this.toDouble())
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot create FREObject from $this", e)
        null
    }
}

/** Converts a Float to a FREObject of type Number */
fun Float.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this.toDouble())
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot create FREObject from $this", e)
        null
    }
}

/** Converts a Date to a FREObject of type Date */
fun Date.toFREObject(): FREObject? {
    return try {
        FREObject("Date", this.time)
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot create FREObject from $this", e)
        null
    }
}

/**  returns an ANEError stating which function has not received enough parameters */
fun FreArgException(): FREObject? {
    return try {
        FreException("[FreKotlin] Not enough or incorrect arguments passed to method").getError()
    } catch (e: Exception) {
        null
    }
}

/** Calls toString() on a FREObject */
fun FREObject?.toStr(suppressStrings: Boolean = false): String {
    if (suppressStrings && this.type == FreObjectTypeKotlin.STRING || this.type == FreObjectTypeKotlin.NULL) {
        return ""
    }
    val toString = this.call("toString")
    return String(toString) ?: ""
}

/** Indicates whether an object has a specified property defined. */
fun FREObject?.hasOwnProperty(name: String): Boolean {
    return Boolean(this.call("hasOwnProperty", name)) ?: false
}