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
package com.tuarua.frekotlin

import android.util.Log

import com.adobe.fre.FREArray
import com.adobe.fre.FREInvalidObjectException
import com.adobe.fre.FREObject
import com.adobe.fre.FRETypeMismatchException
import com.adobe.fre.FREWrongThreadException
import java.util.ArrayList

@Suppress("UNCHECKED_CAST", "unused")
class FreArrayKotlin {
    var rawValue: FREArray? = null

    constructor(value: FREObject) {
        rawValue = value as FREArray
    }

    constructor(value: FREArray) {
        rawValue = value
    }

    //@Throws(FREWrongThreadException::class, FREInvalidObjectException::class)
    val length: Int
        get() = rawValue?.length?.toInt() as Int

    @Throws(FREWrongThreadException::class, FREInvalidObjectException::class)
    fun getObjectAt(index: Int): FreObjectKotlin = FreObjectKotlin(any = rawValue?.getObjectAt(index.toLong()))

    @Throws(FREInvalidObjectException::class, FRETypeMismatchException::class, FREWrongThreadException::class)
    fun setObjectAt(index: Int, value: FreObjectKotlin) {
        rawValue?.setObjectAt(index.toLong(), value.rawValue)
    }

    val value: ArrayList<Any>
        get() {
            val arrayList = rawValue?.let { FreKotlinHelper.getAsObject(it) }
            if (arrayList is ArrayList<*>) return arrayList as ArrayList<Any>
            return ArrayList()
        }

    companion object {
        internal var TAG = "com.tuarua.FreArrayKotlin"
    }
}
