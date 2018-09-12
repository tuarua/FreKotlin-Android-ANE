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
        FreKotlinLogger.log("cannot create new class $className", e)
        null
    }
}

@Deprecated("Use accessor  instead", ReplaceWith("FREObject.set(name: String)"))
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
    if (value is Float) {
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
        FreKotlinLogger.log("FREObject.setProp - no automatic conversion type found")
        return
    }
}

@Deprecated("Use accessor  instead", ReplaceWith("FREObject.get(name: String)"))
fun FREObject.getProp(name: String): FREObject? {
    return FreKotlinHelper.getProperty(this, name)
}

/** Gets the named property of a FREObject */
operator fun FREObject?.get(name: String): FREObject? {
    val rv = this ?: return null
    return FreKotlinHelper.getProperty(rv, name)
}

/** Sets the named property of a FREObject */
operator fun FREObject?.set(name: String, value: FREObject?) {
    val rv = this ?: return
    FreKotlinHelper.setProperty(rv, name, value)
}

/** Converts a FREObject of type uint to a Color */
fun FREObject.toColor(hasAlpha: Boolean = true): Int {
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
        FreKotlinLogger.log("cannot create FREObject from $this", e)
        null
    }
}

/** Converts a Short to a FREObject of type int */
fun Short.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this.toInt())
    } catch (e: Exception) {
        FreKotlinLogger.log("cannot create FREObject from $this", e)
        null
    }
}

/** Converts a Boolean to a FREObject of type Boolean */
fun Boolean.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this)
    } catch (e: Exception) {
        FreKotlinLogger.log("cannot create FREObject from $this", e)
        null
    }
}

/** Converts a String to a FREObject of type String */
fun String.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this)
    } catch (e: Exception) {
        FreKotlinLogger.log("cannot create FREObject from $this", e)
        null
    }
}

/** Converts a Double to a FREObject of type Number */
fun Double.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this)
    } catch (e: Exception) {
        FreKotlinLogger.log("cannot create FREObject from $this", e)
        null
    }
}

/** Converts a Long to a FREObject of type Number */
fun Long.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this.toDouble())
    } catch (e: Exception) {
        FreKotlinLogger.log("cannot create FREObject from $this", e)
        null
    }
}

/** Converts a Float to a FREObject of type Number */
fun Float.toFREObject(): FREObject? {
    return try {
        FREObject.newObject(this.toDouble())
    } catch (e: Exception) {
        FreKotlinLogger.log("cannot create FREObject from $this", e)
        null
    }
}

/** Converts a Date to a FREObject of type Date */
fun Date.toFREObject(): FREObject? {
    return try {
        FREObject("Date", this.time)
    } catch (e: Exception) {
        FreKotlinLogger.log("cannot create FREObject from $this", e)
        null
    }
}

/**  returns an ANEError stating which variable could not be converted */
fun FreConversionException(variableName: String, stackTrace: kotlin.Array<java.lang.StackTraceElement> = arrayOf()): FREObject? {
    return try {
        FreException("[FreKotlin] Cannot convert $variableName").getError(stackTrace)
    } catch (e: Exception) {
        null
    }
}

/**  returns an ANEError stating which function has not received enough parameters */
fun FreArgException(functionName: String, stackTrace: kotlin.Array<java.lang.StackTraceElement> = arrayOf()): FREObject? {
    return try {
        FreException("[FreKotlin] Not enough arguments passed to $functionName").getError(stackTrace)
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
