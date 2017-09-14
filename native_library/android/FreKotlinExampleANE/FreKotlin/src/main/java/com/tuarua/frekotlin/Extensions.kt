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

package com.tuarua.frekotlin

import android.graphics.Color
import com.adobe.fre.FREArray
import com.adobe.fre.FREContext
import com.adobe.fre.FREObject
import java.util.*
import kotlin.collections.ArrayList

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

/*fun Double(freObjectKotlin: FreObjectKotlin?): Double? {
    val v = FreKotlinHelper.getAsObject(freObjectKotlin?.rawValue)
    return (v as? Int)?.toDouble() ?: v as Double?
}*/

fun Float(freObject: FREObject?): Float? {
    val v = FreKotlinHelper.getAsObject(freObject)
    return (v as? Int)?.toFloat() ?: (v as Double?)?.toFloat()
}

/*fun Float(freObjectKotlin: FreObjectKotlin?): Float? {
    val v = FreKotlinHelper.getAsObject(freObjectKotlin?.rawValue)
    return (v as? Int)?.toFloat() ?: (v as Double?)?.toFloat()
}*/

fun IntArray(freArray: FREArray?): IntArray? {
    if (freArray != null) {
        val al = FreArrayKotlin(freArray).value
        val count = al.count()
        val kotArr = IntArray(count)
        for (i in 0 until count) {
            val v = al[i]
            if (v is Int) {
                kotArr.set(index = i, value = v)
            } else {
                return null
            }
        }
        return kotArr
    }
    return null
}

fun IntArray(freObject: FREObject?): IntArray? {
    if (freObject != null) {
        val al = FreArrayKotlin(freObject).value
        val count = al.count()
        val kotArr = IntArray(count)
        for (i in 0 until count) {
            val v = al[i]
            if (v is Int) {
                kotArr.set(index = i, value = v)
            } else {
                return null
            }
        }
        return kotArr
    }
    return null
}

fun IntArray(freArrayKotlin: FreArrayKotlin?): IntArray? {
    if (freArrayKotlin != null) {
        val al = freArrayKotlin.value
        val count = al.count()
        val kotArr = IntArray(count)
        for (i in 0 until count) {
            val v = al[i]
            if (v is Int) {
                kotArr.set(index = i, value = v)
            } else {
                return null
            }
        }
        return kotArr
    }
    return null
}

fun DoubleArray(freArray: FREArray?): DoubleArray? {
    if (freArray != null) {
        val al = FreArrayKotlin(freArray).value
        val count = al.count()
        val kotArr: DoubleArray = kotlin.DoubleArray(count)
        for (i in 0 until count) if (al[i] is Double) {
            val v: Double = al[i] as Double
            kotArr[i] = v
        } else {
            return null
        }
        return kotArr
    }
    return null
}

fun DoubleArray(freObject: FREObject?): DoubleArray? {
    if (freObject != null) {
        val al = FreArrayKotlin(freObject).value
        val count = al.count()
        val kotArr: DoubleArray = kotlin.DoubleArray(count)
        for (i in 0 until count) if (al[i] is Double) {
            val v: Double = al[i] as Double
            kotArr[i] = v
        } else {
            return null
        }
        return kotArr
    }
    return null
}

fun DoubleArray(freArrayKotlin: FreArrayKotlin?): DoubleArray? {
    if (freArrayKotlin != null) {
        val al = freArrayKotlin.value
        val count = al.count()
        val kotArr: DoubleArray = kotlin.DoubleArray(count)
        for (i in 0 until count) if (al[i] is Double) {
            val v: Double = al[i] as Double
            kotArr[i] = v
        } else {
            return null
        }
        return kotArr
    }
    return null
}

fun BooleanArray(freArray: FREArray?): BooleanArray? {
    if (freArray != null) {
        val al = FreArrayKotlin(freArray).value
        val count = al.count()
        val kotArr = BooleanArray(count)
        for (i in 0 until count) if (al[i] is Boolean) {
            val v: Boolean = al[i] as Boolean
            kotArr[i] = v
        } else {
            return null
        }
        return kotArr
    }
    return null
}

fun BooleanArray(freObject: FREObject?): BooleanArray? {
    if (freObject != null) {
        val al = FreArrayKotlin(freObject).value
        val count = al.count()
        val kotArr = BooleanArray(count)
        for (i in 0 until count) if (al[i] is Boolean) {
            val v: Boolean = al[i] as Boolean
            kotArr[i] = v
        } else {
            return null
        }
        return kotArr
    }
    return null
}

fun BooleanArray(freArrayKotlin: FreArrayKotlin?): BooleanArray? {
    if (freArrayKotlin != null) {
        val al = freArrayKotlin.value
        val count = al.count()
        val kotArr = BooleanArray(count)
        for (i in 0 until count) if (al[i] is Boolean) {
            val v: Boolean = al[i] as Boolean
            kotArr[i] = v
        } else {
            return null
        }
        return kotArr
    }
    return null
}


fun Int(freObject: FREObject?): Int? = FreKotlinHelper.getAsObject(freObject) as Int?
//fun Int(freObjectKotlin: FreObjectKotlin?): Int? = FreKotlinHelper.getAsObject(freObjectKotlin?.rawValue) as Int?

fun String(freObject: FREObject?): String? = FreKotlinHelper.getAsObject(freObject).toString()
//fun String(freObjectKotlin: FreObjectKotlin?): String? = FreKotlinHelper.getAsObject(freObjectKotlin?.rawValue)
        .toString()

fun Boolean(freObject: FREObject?): Boolean? = FreKotlinHelper.getAsObject(freObject) as Boolean?
//fun Boolean(freObjectKotlin: FreObjectKotlin?): Boolean? = FreKotlinHelper.getAsObject(freObjectKotlin?.rawValue) as
        //Boolean?

fun Date(freObject: FREObject?): Date? = FreKotlinHelper.getAsObject(freObject) as Date?
//fun Date(freObjectKotlin: FreObjectKotlin?): Date? = FreKotlinHelper.getAsObject(freObjectKotlin?.rawValue) as Date?

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
    set(value) {}


fun FREObject(name: String, vararg args: Any): FREObject {
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
fun FREObject.getProp(name: String):FREObject? {
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
@Throws(FreException::class)
fun Int.toFREObject(): FREObject? {
    try {
        return FREObject.newObject(this)
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from Int")
    }
}

@Throws(FreException::class)
fun Short.toFREObject(): FREObject? {
    try {
        return FREObject.newObject(this.toInt())
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from Int")
    }
}

@Throws(FreException::class)
fun Boolean.toFREObject(): FREObject? {
    try {
        return FREObject.newObject(this)
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from Boolean")
    }
}

@Throws(FreException::class)
fun String.toFREObject(): FREObject? {
    try {
        return FREObject.newObject(this)
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from String")
    }
}

@Throws(FreException::class)
fun Double.toFREObject(): FREObject? {
    try {
        return FREObject.newObject(this)
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from Double")
    }
}

@Throws(FreException::class)
fun Long.toFREObject(): FREObject? {
    try {
        return FREObject.newObject(this.toDouble())
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from Double")
    }
}

@Throws(FreException::class)
fun Float.toFREObject(): FREObject? {
    try {
        return FREObject.newObject(this.toDouble())
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from Double")
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

fun IntArray.toFREObject(): FREArray? {
    return FreArrayKotlin(this).rawValue
}
