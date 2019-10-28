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
/**
 * Creates a FREArray.
 * @param [freObject] the FREObject.
 * @return a new FREArray.
 */
fun FREArray(freObject: FREObject): FREArray {
    return freObject as FREArray
}
/**
 * Creates a FREArray.
 * @param [value] the [IntArray] to convert.
 * @return a new FREArray.
 */
fun FREArray(value: IntArray): FREArray? {
    return try {
        val rv = FREArray("int") ?: return null
        for (i in value.indices) {
            rv[i] = value[i].toFREObject()
        }
        rv
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot create FREArray from $value", e)
        null
    }
}
/**
 * Creates a FREArray.
 * @param [value] the [LongArray] to convert.
 * @return a new FREArray.
 */
fun FREArray(value: LongArray): FREArray? {
    return try {
        val rv = FREArray("Number") ?: return null
        for (i in value.indices) {
            rv[i] = value[i].toFREObject()
        }
        rv
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot create FREArray from $value", e)
        null
    }
}
/**
 * Creates a FREArray.
 * @param [value] the [DoubleArray] to convert.
 * @return a new FREArray.
 */
fun FREArray(value: DoubleArray): FREArray? {
    return try {
        val rv = FREArray("Number") ?: return null
        for (i in value.indices) {
            rv[i] = value[i].toFREObject()
        }
        rv
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot create FREArray from $value", e)
        null
    }
}

/**
 * Creates a FREArray.
 * @param [value] the [FloatArray] to convert.
 * @return a new FREArray.
 */
fun FREArray(value: FloatArray): FREArray? {
    return try {
        val rv = FREArray("Number") ?: return null
        for (i in value.indices) {
            rv[i] = value[i].toFREObject()
        }
        rv
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot create FREArray from $value", e)
        null
    }
}

/**
 * Creates a FREArray.
 * @param [value] the [ShortArray] to convert.
 * @return a new FREArray.
 */
fun FREArray(value: ShortArray): FREArray? {
    return try {
        val rv = FREArray("int") ?: return null
        for (i in value.indices) {
            rv[i] = value[i].toFREObject()
        }
        rv
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot create FREArray from $value", e)
        null
    }
}

/**
 * Creates a FREArray.
 * @param [value] the [BooleanArray] to convert.
 * @return a new FREArray.
 */
fun FREArray(value: BooleanArray): FREArray? {
    return try {
        val rv = FREArray("Boolean") ?: return null
        for (i in value.indices) {
            rv[i] = value[i].toFREObject()
        }
        rv
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot create FREArray from $value", e)
        null
    }
}

/**
 * Creates a FREArray.
 * @param [value] the [List<String>] to convert.
 * @return a new FREArray.
 */
fun FREArray(value: List<String>): FREArray? {
    return try {
        val rv = FREArray("String") ?: return null
        for (i in value.indices) {
            rv[i] = value[i].toFREObject()
        }
        rv
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot create FREArray from $value", e)
        null
    }
}

/**
 * Creates a FREArray.
 * @param [className] name of AS3 class to create.
 * @param [length] number of elements in the array.
 * @param [fixed] whether the array is fixed
 * @return a new FREArray.
 */
fun FREArray(className: String, length: Int = 0, fixed: Boolean = false): FREArray? {
    return try {
        FREArray.newArray(className, length, fixed)
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot create FREArray of $className", e)
        null
    }
}

/**
 * Sets FREObject at position index.
 * @receiver The FREArray.
 * @param [index] index of item.
 * @param [value] value to set.
 */
operator fun FREArray.set(index: Int, value: FREObject?) {
    try {
        this.setObjectAt(index.toLong(), value)
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot set FREArray at index $index to value ${value.toStr()}", e)
    }
}

/**
 * Adds one or more elements to the end of a [FREArray] and returns the new length of the array.
 * @receiver The FREArray.
 * @property args One or more values to append to the [FREArray].
 */
fun FREArray.push(vararg args: Any?) : Long {
    return Long(FreKotlinHelper.callMethod(this, "push", args)) ?: 0
}

/**
 * Insert a single element into the [FREArray].
 * @param [value] An [FREObject]
 * @param [at] An [Int] that specifies the position in the [FREArray] where the element is to be inserted.
 * You can use a negative [Int] to specify a position relative to the end of the [FREArray]
 * (for example, -1 for the last element of the [FREArray])
 */
fun FREArray.insert(value: FREObject, at: Int) {
    this.call("insertAt", at, value)
}

/**
 * Remove a single element from the [FREArray]. This method modifies the [FREArray] without making a copy.
 * @param [at] An [Int] that specifies the index of the element in the [FREArray] that is to be deleted.
 * You can use a negative [Int] to specify a position relative to the end of the [FREArray]
 * (for example, -1 for the last element of the Vector).
 * @return the element that was removed from the original FREArray
 */
fun FREArray.remove(at: Int) : FREObject? {
    return this.call("removeAt", at)
}

@Suppress("UNUSED_PARAMETER")
var FREArray.isEmpty: Boolean
    get() = this.length == 0L
    set(value) = Unit

/**
 * Gets FREObject at position index.
 * @receiver The FREArray.
 * @param [index] index of item.
 */
operator fun FREArray.get(index: Int): FREObject? {
    return try {
        this.getObjectAt(index.toLong())
    } catch (e: Exception) {
        FreKotlinLogger.error("cannot get FREArray at index $index", e)
        null
    }
}

/** Iterates over a FREArray */
operator fun FREArray?.iterator(): Iterator<FREObject?> {
    this ?: return emptySequence<FREObject?>().iterator()
    val l = this.length
    return (0 until l).asSequence().map {
        this[it.toInt()]
    }.iterator()
}

/**
 * Converts a IntArray to a FREArray.
 * @receiver The [IntArray].
 * @return A new FREArray.
 */
fun IntArray.toFREObject(): FREArray? {
    return FREArray(this)
}

/**
 * Converts a [BooleanArray] to a FREArray.
 * @receiver The [BooleanArray].
 * @return A new FREArray.
 */
fun BooleanArray.toFREObject(): FREArray? {
    return FREArray(this)
}

/**
 * Converts a [DoubleArray] to a FREArray.
 * @receiver The [DoubleArray].
 */
fun DoubleArray.toFREObject(): FREArray? {
    return FREArray(this)
}

/**
 * Converts a [FloatArray] to a FREArray.
 * @receiver The [FloatArray].
 */
fun FloatArray.toFREObject(): FREArray? {
    return FREArray(this)
}

/**
 * Converts a [ShortArray] to a FREArray.
 * @receiver The [ShortArray].
 */
fun ShortArray.toFREObject(): FREArray? {
    return FREArray(this)
}

/**
 * Converts a List<String> to a FREArray.
 * @receiver The List<String>.
 */
fun List<String>.toFREObject(): FREArray? {
    return FREArray(this)
}

/**
 * Returns a list containing the results of applying the given [transform] function
 * to each element in the original array.
 */
fun <R> FREArray.map(transform: (FREObject?) -> R): List<R> {
    val list:MutableList<R> = mutableListOf()
    for (fre in this) {
        list.add(transform(fre))
    }
    return list.toList()
}

/** Converts a FREArray to a [BooleanArray]. */
fun BooleanArray(freArray: FREArray?): BooleanArray {
    if (freArray == null) return booleanArrayOf()
    return freArray.map { el -> Boolean(el) ?: false }.toBooleanArray()
}

/** Converts a FREObject to a [BooleanArray]. */
fun BooleanArray(freObject: FREObject?): BooleanArray {
    if (freObject == null) return booleanArrayOf()
    return BooleanArray(FREArray(freObject))
}

/** Converts a FREArray to a [DoubleArray]. */
fun DoubleArray(freArray: FREArray?): DoubleArray {
    if (freArray == null) return doubleArrayOf()
    return freArray.map { el -> Double(el) ?: 0.0 }.toDoubleArray()
}

/** Converts a FREArray to a [DoubleArray]. */
fun DoubleArray(freObject: FREObject?): DoubleArray {
    if (freObject == null) return doubleArrayOf()
    return DoubleArray(FREArray(freObject))
}

/** Converts a FREArray to an [IntArray]. */
fun IntArray(freArray: FREArray?): IntArray {
    if (freArray == null) return intArrayOf()
    return freArray.map { el -> Int(el) ?: 0 }.toIntArray()
}

/** Converts a FREObject to an [IntArray]. */
fun IntArray(freObject: FREObject?): IntArray {
    if (freObject == null) return intArrayOf()
    return IntArray(FREArray(freObject))
}

/** Converts a FREArray to an [ShortArray]. */
fun ShortArray(freArray: FREArray?): ShortArray {
    if (freArray == null) return shortArrayOf()
    return freArray.map { el -> Short(el) ?: 0 }.toShortArray()
}

/** Converts a FREObject to an [ShortArray]. */
fun ShortArray(freObject: FREObject?): ShortArray {
    if (freObject == null) return shortArrayOf()
    return ShortArray(FREArray(freObject))
}

/** Converts a FREArray to an [FloatArray]. */
fun FloatArray(freArray: FREArray?): FloatArray {
    if (freArray == null) return floatArrayOf()
    return freArray.map { el -> Float(el) ?: 0f }.toFloatArray()
}

/** Converts a FREObject to an [FloatArray]. */
fun FloatArray(freObject: FREObject?): FloatArray {
    if (freObject == null) return floatArrayOf()
    return FloatArray(FREArray(freObject))
}

/** Converts a FREArray to a [LongArray]. */
fun LongArray(freArray: FREArray?): LongArray {
    if (freArray == null) return longArrayOf()
    return freArray.map { el -> Long(el) ?: 0 }.toLongArray()
}

/** Converts a FREObject to an [LongArray]. */
fun LongArray(freObject: FREObject?): LongArray? {
    if (freObject == null) return null
    return LongArray(FREArray(freObject))
}

/** Converts a FREArray to an List<String>. */
@Suppress("UNCHECKED_CAST", "unused")
fun <String> List(freArray: FREArray?): List<String> {
    if (freArray == null) return listOf()
    return freArray.let { FreKotlinHelper.getAsObject(it) as List<String> }
}

/** Converts a FREObject to an List<String>.  */
fun <String> List(freObject: FREObject?): List<String> {
    if (freObject == null) return listOf()
    return List(FREArray(freObject))
}
