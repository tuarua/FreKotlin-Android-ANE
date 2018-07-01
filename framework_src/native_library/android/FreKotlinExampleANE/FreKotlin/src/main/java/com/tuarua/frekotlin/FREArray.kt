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

@Throws(FreException::class)
fun FREArray(value: IntArray): FREArray {
    val rv = FREArray.newArray(value.size)
    for (i in value.indices) {
        rv[i] = value[i].toFREObject()
    }
    return rv
}

@Throws(FreException::class)
fun FREArray(value: LongArray): FREArray {
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

operator fun FREArray?.iterator(): Iterator<FREObject?> {
    this ?: return emptySequence<FREObject?>().iterator()
    val l = this.length
    return (0 until l).asSequence().map {
        this[it.toInt()]
    }.iterator()
}

/** Converts a IntArray to a FREArray */
@Throws(FreException::class)
fun IntArray.toFREArray(): FREArray? {
    try {
        return FREArray(this)
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from IntArray")
    }
}

/** Converts a BooleanArray to a FREArray */
@Throws(FreException::class)
fun BooleanArray.toFREArray(): FREArray? {
    try {
        return FREArray(this)
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from BooleanArray")
    }
}

/** Converts a DoubleArray to a FREArray */
@Throws(FreException::class)
fun DoubleArray.toFREArray(): FREArray? {
    try {
        return FREArray(this)
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from BooleanArray")
    }
}

/** Converts a List<String> to a FREArray */
@Throws(FreException::class)
fun List<String>.toFREArray(): FREArray? {
    try {
        return FREArray(this)
    } catch (e: Exception) {
        throw FreException(e, "cannot create new object from List<String>")
    }
}

/** Converts a FREArray to a BooleanArray */
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

/** Converts a FREObject to a BooleanArray */
@Throws(FreException::class)
fun BooleanArray(freObject: FREObject?): BooleanArray? {
    if (freObject != null) {
        return BooleanArray(FREArray(freObject))
    }
    return null
}

/** Converts a FREArray to a DoubleArray */
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

/** Converts a FREArray to a DoubleArray */
@Throws(FreException::class)
fun DoubleArray(freObject: FREObject?): DoubleArray? {
    if (freObject != null) {
        return DoubleArray(FREArray(freObject))
    }
    return null
}

/** Converts a FREArray to an IntArray */
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

/** Converts a FREObject to an IntArray */
@Throws(FreException::class)
fun IntArray(freObject: FREObject?): IntArray? {
    if (freObject != null) {
        return IntArray(FREArray(freObject))
    }
    return null
}

/** Converts a FREArray to a LongArray */
fun LongArray(freArray: FREArray?): LongArray? {
    if (freArray != null) {
        val count = freArray.length.toInt()
        val kotArr: LongArray = kotlin.LongArray(count)
        for (i in 0 until count) {
            val v = Long(freArray.getObjectAt(i.toLong()))
            when {
                v != null -> kotArr[i] = v
                else -> return null
            }
        }
        return kotArr
    }
    return null
}

/** Converts a FREObject to an LongArray */
fun LongArray(freObject: FREObject?): LongArray? {
    if (freObject != null) {
        return LongArray(FREArray(freObject))
    }
    return null
}

/** Converts a FREArray to an List<String> */
@Suppress("UNCHECKED_CAST", "unused")
fun <String> List(freArray: FREArray?): List<String> {
    if (freArray != null) {
        return freArray.let { FreKotlinHelper.getAsObject(it) as List<String> }
    }
    return listOf()
}

/** Converts a FREObject to an List<String>  */
fun <String> List(freObject: FREObject?): List<String> {
    if (freObject != null) {
        return List(FREArray(freObject))
    }
    return listOf()
}
