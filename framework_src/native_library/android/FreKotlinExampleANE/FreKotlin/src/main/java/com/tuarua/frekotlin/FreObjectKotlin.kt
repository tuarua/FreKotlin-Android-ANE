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
/**
 * @suppress
 */
open class FreObjectKotlin {
    var rawValue: FREObject? = null
    constructor(any: Any?) {
        if (any == null) {
            rawValue = null
            return
        }
        if (any is FREObject) {
            rawValue = any
            return
        }
        if (any is FreObjectKotlin) {
            rawValue = any.rawValue
            return
        }
        if (any is String) {
            rawValue = FREObject.newObject(any)
            return
        }
        if (any is Int) {
            rawValue = FREObject.newObject(any)
            return
        }
        if (any is Double) {
            rawValue = FREObject.newObject(any)
            return
        }
        if (any is Float) {
            rawValue = FREObject.newObject(any.toDouble())
            return
        }
        if (any is Long) {
            rawValue = FREObject.newObject(any.toDouble())
            return
        }
        if (any is Short) {
            rawValue = FREObject.newObject(any.toInt())
            return
        }
        if (any is Boolean) {
            rawValue = FREObject.newObject(any)
            return
        }
        if (any is Date) {
            rawValue = FREObject("Date", any.time)
            return
        }
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


