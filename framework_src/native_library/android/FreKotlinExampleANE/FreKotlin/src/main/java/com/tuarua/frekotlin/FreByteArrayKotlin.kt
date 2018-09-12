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

import com.adobe.fre.*
import java.nio.ByteBuffer

/**
 * A wrapper for handling FREByteArray.
 *
 * @property rawValue the raw FREByteArray value.
 * @property length an Int that specifies the number of bytes in the byte array.
 * @property bytes a ByteBuffer of the ByteArray.
 *
 */
class FreByteArrayKotlin {
    var rawValue: FREByteArray? = null
    var length: Int = 0
    var bytes: ByteBuffer? = null

    /** */
    constructor()

    /** */
    constructor(value: FreByteArrayKotlin) {
        rawValue = value.rawValue
    }

    /** */
    constructor(value: FREObject) {
        rawValue = value as FREByteArray
    }

    /** */
    constructor(value: FREByteArray) {
        rawValue = value
    }

    /**
     * @param byteArray the ByteArray to create this class from.
     * @constructor
     */
    constructor(byteArray: ByteArray) {
        try {
            rawValue = FREByteArray.newByteArray()
        } catch (e: Exception) {
            FreKotlinLogger.log("Cannot create FREByteArray from ByteArray", e)
        }
        val rv = rawValue ?: return
        rv["length"] = byteArray.size.toFREObject()
        acquire()
        rv.bytes.put(byteArray)
        release()
    }

    /**
     * See the original [Adobe documentation](https://help.adobe.com/en_US/air/extensions/WS39e706a46ad531be2664b82132a8ec9029-8000.html)
     */
    fun acquire() {
        val rv = rawValue ?: return
        try {
            rv.acquire()
            length = rv.length.toInt()
            bytes = rv.bytes
        } catch (e: Exception) {
            FreKotlinLogger.log("Cannot acquire the FREByteArray", e)
        }
    }

    /**
     * See the original [Adobe documentation](https://help.adobe.com/en_US/air/extensions/WS39e706a46ad531be2664b82132a8ec9029-8000.html)
     */
    fun release() {
        try {
            rawValue?.release()
        } catch (e: Exception) {
            FreKotlinLogger.log("Cannot release the FREByteArray", e)
        }
    }
}

/**
 * Converts a FREObject of type ByteArray to a ByteArray.
 */
@Suppress("FunctionName")
fun ByteArray(freObject: FREObject?): ByteArray? {
    var ret: ByteArray? = null
    val fre = freObject ?: return null
    try {
        val ba = FreByteArrayKotlin(fre)
        ba.acquire()
        val buffer = ba.bytes
        if (buffer != null) {
            ret = ByteArray(buffer.remaining())
            buffer.get(ret, 0, ret.size)
        }
        ba.release()
        return ret
    } catch (e: Exception) {
        FreKotlinLogger.log("Cannot create ByteArray from FREObject", e)
    }
    return ret
}

/**
 * Converts a ByteArray to a FREObject of type ByteArray.
 */
fun ByteArray.toFREObject(): FREObject? {
    try {
        return FreByteArrayKotlin(this).rawValue
    } catch (e: Exception) {
        FreKotlinLogger.log("Cannot create FREObject from ByteArray", e)
    }
    return null
}