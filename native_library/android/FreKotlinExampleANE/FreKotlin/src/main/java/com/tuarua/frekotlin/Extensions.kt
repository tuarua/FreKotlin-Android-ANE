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

import com.adobe.fre.FREArray
import com.adobe.fre.FREContext
import com.adobe.fre.FREObject
import com.tuarua.frekotlin.geom.FrePointKotlin
import com.tuarua.frekotlin.geom.Point
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
    val v = FreObjectKotlin(freObject = freObject).value
    return (v as? Int)?.toDouble() ?: v as Double?
}

fun Double(freObjectKotlin: FreObjectKotlin?): Double? {
    val v = freObjectKotlin?.value
    return (v as? Int)?.toDouble() ?: v as Double?
}

fun Float(freObject: FREObject?): Float? {
    val v = FreObjectKotlin(freObject = freObject).value
    return (v as? Int)?.toFloat() ?: (v as Double?)?.toFloat()
}

fun Float(freObjectKotlin: FreObjectKotlin?): Float? {
    val v = freObjectKotlin?.value
    return (v as? Int)?.toFloat() ?: (v as Double?)?.toFloat()
}

fun IntArray(freArray: FREArray?): IntArray? {
    if (freArray != null) {
        val al = FreArrayKotlin(freArray).value
        val count = al.count()
        val kotArr = IntArray(count)
        for (i in 0 until count) {
            val v = al[i]
            if (v is Int) {
                kotArr.set(index = i,value = v)
            }else{
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
                kotArr.set(index = i,value = v)
            }else{
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
                kotArr.set(index = i,value = v)
            }else{
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
@Throws(FreException::class)
fun Int.toFREObject():FREObject? {
    return try {
        FreObjectKotlin(this).rawValue
    } catch (e: FreException) {
        e.getError(Thread.currentThread().stackTrace)
    } catch (e: Exception) {
        FreException(e).getError(Thread.currentThread().stackTrace)
    }
}

@Throws(FreException::class)
fun Boolean.toFREObject():FREObject? {
    return try {
        FreObjectKotlin(this).rawValue
    } catch (e: FreException) {
        e.getError(Thread.currentThread().stackTrace)
    } catch (e: Exception) {
        FreException(e).getError(Thread.currentThread().stackTrace)
    }
}

@Throws(FreException::class)
fun String.toFREObject():FREObject? {
    return try {
        FreObjectKotlin(this).rawValue
    } catch (e: FreException) {
        e.getError(Thread.currentThread().stackTrace)
    } catch (e: Exception) {
        FreException(e).getError(Thread.currentThread().stackTrace)
    }
}

@Throws(FreException::class)
fun Double.toFREObject():FREObject? {
    return try {
        FreObjectKotlin(this).rawValue
    } catch (e: FreException) {
        e.getError(Thread.currentThread().stackTrace)
    } catch (e: Exception) {
        FreException(e).getError(Thread.currentThread().stackTrace)
    }
}

@Throws(FreException::class)
fun Float.toFREObject():FREObject? {
    return try {
        FreObjectKotlin(this).rawValue
    } catch (e: FreException) {
        e.getError(Thread.currentThread().stackTrace)
    } catch (e: Exception) {
        FreException(e).getError(Thread.currentThread().stackTrace)
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

fun IntArray.toFREObject():FREArray? {
    return FreArrayKotlin(this).rawValue
}

fun Int(freObject: FREObject?): Int? = FreObjectKotlin(freObject = freObject).value as Int?
fun Int(freObjectKotlin: FreObjectKotlin?): Int? = freObjectKotlin?.value as Int?
fun String(freObject: FREObject?): String? = FreObjectKotlin(freObject = freObject).value?.toString()
fun String(freObjectKotlin: FreObjectKotlin?): String? = freObjectKotlin?.value?.toString()
fun Boolean(freObject: FREObject?): Boolean? = FreObjectKotlin(freObject = freObject).value as Boolean?
fun Boolean(freObjectKotlin: FreObjectKotlin?): Boolean? = freObjectKotlin?.value as Boolean?
fun Date(freObject: FREObject?): Date? = FreObjectKotlin(freObject = freObject).value as Date?
fun Date(freObjectKotlin: FreObjectKotlin?): Date? = freObjectKotlin?.value as Date?