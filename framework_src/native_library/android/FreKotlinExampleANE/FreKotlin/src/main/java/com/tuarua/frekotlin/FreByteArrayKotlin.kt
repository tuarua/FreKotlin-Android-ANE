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
 * A wrapper for handling FREByteArray
 *
 * @property rawValue the raw FREByteArray value
 * @property length An Int that specifies the number of bytes in the byte array.
 * @property bytes A ByteBuffer of the ByteArray.
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
    @Throws(FREASErrorException::class, FREWrongThreadException::class, FREInvalidObjectException::class)
    constructor(byteArray: ByteArray) {
        rawValue = FREByteArray.newByteArray()
        rawValue?.setProp("length", byteArray.size)
        acquire()
        (rawValue as FREByteArray).bytes.put(byteArray)
        release()
    }

    /**
     * See the original [Adobe documentation](https://help.adobe.com/en_US/air/extensions/WS39e706a46ad531be2664b82132a8ec9029-8000.html)
     *
     * @throws
     * @exception FREWrongThreadException
     *
     */
    @Throws(FREWrongThreadException::class, FREInvalidObjectException::class)
    fun acquire() {
        if (rawValue is FREByteArray) {
            rawValue?.acquire()
            length = (rawValue as FREByteArray).length.toInt()
            bytes = (rawValue as FREByteArray).bytes
        }
    }

    /**
     * See the original [Adobe documentation](https://help.adobe.com/en_US/air/extensions/WS39e706a46ad531be2664b82132a8ec9029-8000.html)
     *
     * @throws
     * @exception FREWrongThreadException
     * @exception FREInvalidObjectException
     */
    @Throws(FREWrongThreadException::class, FREInvalidObjectException::class)
    fun release() {
        rawValue?.release()
    }
}
/**
 * converts a FREObject of type ByteArray to a ByteArray
 */
@Suppress("FunctionName")
@Throws(FreException::class)
fun ByteArray(freObject: FREObject?): ByteArray? {
    var ret: ByteArray? = null
    if (freObject != null) {
        try {
            val ba = FreByteArrayKotlin(freObject)
            ba.acquire()
            val buffer = ba.bytes
            if (buffer != null) {
                ret = ByteArray(buffer.remaining())
                buffer.get(ret, 0, ret.size)
            }
            ba.release()
            return ret
        } catch (e: FreException) {
            throw e
        } catch (e: Exception) {
            throw FreException(e)
        }
    }
    return ret
}

/**
 * converts a ByteArray a FREObject of type ByteArray
 */
fun ByteArray.toFREObject(): FREObject? {
    return try {
        FreByteArrayKotlin(this).rawValue
    } catch (e: Exception) {
        FreException(e, "cannot create new object from ByteArray").getError(Thread.currentThread().stackTrace)
    }
}