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

@file:Suppress("FunctionName", "unused")

package com.tuarua.frekotlin

import com.adobe.fre.FREArray
import com.adobe.fre.FREObject

fun FREArray(freObject: FREObject): FREArray {
    return freObject as FREArray
}

fun FREArray(value: IntArray): FREArray? {
    return try {
        val rv = FREArray.newArray(value.size)
        for (i in value.indices) {
            rv[i] = value[i].toFREObject()
        }
        rv
    } catch (e: Exception) {
        FreKotlinLogger.log("cannot create FREArray from $value", e)
        null
    }
}

fun FREArray(value: LongArray): FREArray? {
    return try {
        val rv = FREArray.newArray(value.size)
        for (i in value.indices) {
            rv[i] = value[i].toFREObject()
        }
        rv
    } catch (e: Exception) {
        FreKotlinLogger.log("cannot create FREArray from $value", e)
        null
    }
}

fun FREArray(value: DoubleArray): FREArray? {
    return try {
        val rv = FREArray.newArray(value.size)
        for (i in value.indices) {
            rv[i] = value[i].toFREObject()
        }
        rv
    } catch (e: Exception) {
        FreKotlinLogger.log("cannot create FREArray from $value", e)
        null
    }
}

fun FREArray(value: BooleanArray): FREArray? {
    return try {
        val rv = FREArray.newArray(value.size)
        for (i in value.indices) {
            rv[i] = value[i].toFREObject()
        }
        rv
    } catch (e: Exception) {
        FreKotlinLogger.log("cannot create FREArray from $value", e)
        null
    }
}

fun FREArray(value: List<String>): FREArray? {
    return try {
        val rv = FREArray.newArray(value.size)
        for (i in value.indices) {
            rv[i] = value[i].toFREObject()
        }
        rv
    } catch (e: Exception) {
        FreKotlinLogger.log("cannot create FREArray from $value", e)
        null
    }
}

fun FREArray(className: String, length: Int = 0, fixed: Boolean = false): FREArray? {
    return try {
        FREArray.newArray(className, length, fixed)
    } catch (e: Exception) {
        FreKotlinLogger.log("cannot create FREArray of $className", e)
        null
    }
}

operator fun FREArray.set(index: Int, value: FREObject?) {
    try {
        this.setObjectAt(index.toLong(), value)
    } catch (e: Exception) {
        FreKotlinLogger.log("cannot set FREArray at index $index to value ${value.toStr()}", e)
    }
}

fun FREArray.append(any: Any?) {
    try {
        this.setObjectAt(this.length, FreObjectKotlin(any).rawValue)
    } catch (e: Exception) {
        FreKotlinLogger.log("cannot append ${FreObjectKotlin(any).rawValue.toStr()} to FREArray", e)
    }
}

fun FREArray.append(value: FREObject?) {
    try {
        this.setObjectAt(this.length, value)
    } catch (e: Exception) {
        FreKotlinLogger.log("cannot append ${value.toStr()} to FREArray", e)
    }
}

operator fun FREArray.get(index: Int): FREObject? {
    return try {
        this.getObjectAt(index.toLong())
    } catch (e: Exception) {
        FreKotlinLogger.log("cannot get FREArray at index $index", e)
        null
    }
}

operator fun FREArray?.iterator(): Iterator<FREObject?> {
    this ?: return emptySequence<FREObject?>().iterator()
    val l = this.length
    return (0 until l).asSequence().map {
        this[it.toInt()]
    }.iterator()
}

/** Converts a IntArray to a FREArray */
@Deprecated("Use toFREObject instead", ReplaceWith("IntArray.toFREObject()"))
fun IntArray.toFREArray(): FREArray? {
    return FREArray(this)
}

/** Converts a IntArray to a FREArray */
fun IntArray.toFREObject(): FREArray? {
    return FREArray(this)
}

/** Converts a BooleanArray to a FREArray */
@Deprecated("Use toFREObject instead", ReplaceWith("BooleanArray.toFREObject()"))
fun BooleanArray.toFREArray(): FREArray? {
    return FREArray(this)
}

/** Converts a BooleanArray to a FREArray */
fun BooleanArray.toFREObject(): FREArray? {
    return FREArray(this)
}

/** Converts a DoubleArray to a FREArray */
@Deprecated("Use toFREObject instead", ReplaceWith("DoubleArray.toFREObject()"))
fun DoubleArray.toFREArray(): FREArray? {
    return FREArray(this)
}

/** Converts a DoubleArray to a FREArray */
fun DoubleArray.toFREObject(): FREArray? {
    return FREArray(this)
}

/** Converts a List<String> to a FREArray */
@Deprecated("Use toFREObject instead", ReplaceWith("List<String>.toFREObject()"))
fun List<String>.toFREArray(): FREArray? {
    return FREArray(this)
}

/** Converts a List<String> to a FREArray */
fun List<String>.toFREObject(): FREArray? {
    return FREArray(this)
}

/** Converts a FREArray to a BooleanArray */
@Throws(FreException::class)
fun BooleanArray(freArray: FREArray?): BooleanArray {
    if (freArray == null) return booleanArrayOf()
    val count = freArray.length.toInt()
    val kotArr: BooleanArray = kotlin.BooleanArray(count)
    for (i in 0 until count) {
        val b = Boolean(freArray.getObjectAt(i.toLong()))
        when {
            b != null -> kotArr[i] = b
            else -> return booleanArrayOf()
        }
    }
    return kotArr
}

/** Converts a FREObject to a BooleanArray */
@Throws(FreException::class)
fun BooleanArray(freObject: FREObject?): BooleanArray {
    if (freObject == null) return booleanArrayOf()
    return BooleanArray(FREArray(freObject))
}

/** Converts a FREArray to a DoubleArray */
@Throws(FreException::class)
fun DoubleArray(freArray: FREArray?): DoubleArray {
    if (freArray == null) return doubleArrayOf()
    val count = freArray.length.toInt()
    val kotArr: DoubleArray = kotlin.DoubleArray(count)
    for (i in 0 until count) {
        val b = Double(freArray.getObjectAt(i.toLong()))
        when {
            b != null -> kotArr[i] = b
            else -> return doubleArrayOf()
        }
    }
    return kotArr
}

/** Converts a FREArray to a DoubleArray */
@Throws(FreException::class)
fun DoubleArray(freObject: FREObject?): DoubleArray {
    if (freObject == null) return doubleArrayOf()
    return DoubleArray(FREArray(freObject))
}

/** Converts a FREArray to an IntArray */
@Throws(FreException::class)
fun IntArray(freArray: FREArray?): IntArray {
    if (freArray == null) return intArrayOf()
    val count = freArray.length.toInt()
    val kotArr: IntArray = kotlin.IntArray(count)
    for (i in 0 until count) {
        val b = Int(freArray.getObjectAt(i.toLong()))
        when {
            b != null -> kotArr[i] = b
            else -> return intArrayOf()
        }
    }
    return kotArr
}

/** Converts a FREObject to an IntArray */
@Throws(FreException::class)
fun IntArray(freObject: FREObject?): IntArray {
    if (freObject == null) return intArrayOf()
    return IntArray(FREArray(freObject))
}

/** Converts a FREArray to a LongArray */
fun LongArray(freArray: FREArray?): LongArray {
    if (freArray == null) return longArrayOf()
    val count = freArray.length.toInt()
    val kotArr: LongArray = kotlin.LongArray(count)
    for (i in 0 until count) {
        val v = Long(freArray.getObjectAt(i.toLong()))
        when {
            v != null -> kotArr[i] = v
            else -> return longArrayOf()
        }
    }
    return kotArr
}

/** Converts a FREObject to an LongArray */
fun LongArray(freObject: FREObject?): LongArray? {
    if (freObject == null) return null
    return LongArray(FREArray(freObject))
}

/** Converts a FREArray to an List<String> */
@Suppress("UNCHECKED_CAST", "unused")
fun <String> List(freArray: FREArray?): List<String> {
    if (freArray == null) return listOf()
    return freArray.let { FreKotlinHelper.getAsObject(it) as List<String> }
}

/** Converts a FREObject to an List<String>  */
fun <String> List(freObject: FREObject?): List<String> {
    if (freObject == null) return listOf()
    return List(FREArray(freObject))
}
