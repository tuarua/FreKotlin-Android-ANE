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

import com.adobe.fre.FREObject
import com.adobe.fre.FREWrongThreadException
import java.util.*

@Suppress("unused")
open class FreObjectKotlin {
    var rawValue: FREObject? = null

    constructor()

    @Throws(FREWrongThreadException::class)
    constructor(any: Any?) { //TODO others
        //Log.d(TAG, "any.toString()" + any.toString())

        if (any is FREObject) {
            //Log.d(TAG, "any is a FREObject")
            rawValue = any
            return
        }

        if (any is FreObjectKotlin) {
            //Log.d(TAG, "any is a FreObjectKotlin")
            rawValue = any.rawValue
            return
        }

        if (any is String) {
            //Log.d(TAG, "any is a String")
            rawValue = FREObject.newObject(any)
            return
        }
        if (any is Int) {
            //Log.d(TAG, "any is a Integer")
            rawValue = FREObject.newObject(any)
            return
        }

        if (any is Double) {
            //Log.d(TAG, "any is a Double")
            rawValue = FREObject.newObject(any)
            return
        }

        if (any is Long) {
            //Log.d(TAG, "any is a Long")
            rawValue = FREObject.newObject(any.toDouble())
            return
        }

        if (any is Short) {
            //Log.d(TAG, "any is a Long")
            rawValue = FREObject.newObject(any.toInt())
            return
        }

        if (any is Boolean) {
            //Log.d(TAG,"item is a Boolean")
            rawValue = FREObject.newObject(any)
            return
        }

        if (any is Date) {
            //Log.d(TAG,"item is a Date")
            rawValue = FREObject("Date", any.time)
            return
        }

        if (any is Any) {
            //Log.e(TAG, "any is an Any - NOT FOUND")
            return
        }
        // Log.d(TAG, "can't find type")
        return
    }

    open val value: Any?
        get() {
            val rv = rawValue ?: return null
            return rv.let { FreKotlinHelper.getAsObject(it) } as Any
        }



    constructor(freObjectKotlin: FreObjectKotlin?) {
        rawValue = freObjectKotlin?.rawValue
    }

    constructor(freObject: FREObject?) {
        rawValue = freObject
    }

}


