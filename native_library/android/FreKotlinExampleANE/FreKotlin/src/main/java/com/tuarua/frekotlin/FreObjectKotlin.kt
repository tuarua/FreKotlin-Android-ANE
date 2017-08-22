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

import android.graphics.Color
import android.util.Log
import com.adobe.fre.FREObject
import com.adobe.fre.FREWrongThreadException

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

        if (any is Boolean) {
            //Log.d(TAG,"item is a Boolean");
            rawValue = FREObject.newObject(any)
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

    @Throws(FreException::class)
    constructor(value: String) {
        try {
            rawValue = FREObject.newObject(value)
        } catch (e: Exception) {
            throw FreException(e, "cannot create new object from String")
        }
    }

    @Throws(FreException::class)
    constructor(value: Int) {
        try {
            rawValue = FREObject.newObject(value)
        } catch (e: Exception) {
            throw FreException(e, "cannot create new object from Int")
        }
    }

    @Throws(FreException::class)
    constructor(value: Double) {
        try {
            rawValue = FREObject.newObject(value)
        } catch (e: Exception) {
            throw FreException(e, "cannot create new object from Double")
        }
    }

    @Throws(FreException::class)
    constructor(value: Boolean) {
        try {
            rawValue = FREObject.newObject(value)
        } catch (e: Exception) {
            throw FreException(e, "cannot create new object from Boolean")
        }
    }

    constructor(freObjectKotlin: FreObjectKotlin?) {
        rawValue = freObjectKotlin?.rawValue
    }

    constructor(freObject: FREObject?) {
        rawValue = freObject
    }

    @Throws(FreException::class)
    constructor(name: String, vararg args: Any) {
        val argsArr = arrayOfNulls<FREObject>(args.size)
        for (i in args.indices) {
            argsArr[i] = FreObjectKotlin(args[i]).rawValue
        }
        try {
            rawValue = FREObject.newObject(name, argsArr)
        } catch (e: Exception) {
            throw FreException(e, "cannot create new object from Boolean")
        }
    }

    @Throws(FreException::class)
    fun getProperty(name: String): FreObjectKotlin? {
        val rv = rawValue ?: return null
        try {
            val ret = FreKotlinHelper.getProperty(rv, name)
            return FreObjectKotlin(ret)
        } catch (e: Exception) {
            throw FreException(e, "cannot create get property $name")
        }
    }

    @Throws(FreException::class)
    fun setProperty(name: String, prop: FreObjectKotlin?): FreObjectKotlin? {
        val rv = rawValue ?: return null
        val prv: FREObject?
        if (prop is FreObjectKotlin) {
            prv = prop.rawValue
        } else {
            prv = null
        }
        try {
            val ret = FreKotlinHelper.setProperty(rv, name, prv)
            return FreObjectKotlin(ret)
        } catch (e: Exception) {
            throw FreException(e, "cannot create set property $name")
        }
    }

    fun getType(): FreObjectTypeKotlin {
        return FreKotlinHelper.getType(rawValue)
    }

    @Throws(FreException::class)
    fun callMethod(name: String, vararg args: Any): FreObjectKotlin? {
        val rv = rawValue ?: return null
        val ret = FreKotlinHelper.callMethod(rv, name, args)
        return if (ret is FREObject) {
            FreObjectKotlin(ret)
        } else null
    }

    @Throws(FreException::class)
    fun callMethod(name: String, vararg args: FREObject): FreObjectKotlin? {
        val rv = rawValue ?: return null
        val ret = FreKotlinHelper.callMethod(rv, name, args)
        return if (ret is FREObject) {
            FreObjectKotlin(ret)
        } else null
    }

    fun callMethod(name: String): FreObjectKotlin? {
        val rv = rawValue ?: return null
        val ret = FreKotlinHelper.callMethod(rv, name)
        return if (ret is FREObject) {
            FreObjectKotlin(ret)
        } else null
    }

    fun toColor(alpha: Int = 255): Int {
        val freColor = this.value as Int
        val ret:Int = Color.argb(alpha, Color.red(freColor), Color.green(freColor), Color.blue(freColor))
        return ret
    }

    fun toHSV(alpha: Int = 255): Float {
        val hsv = FloatArray(3)
        Color.colorToHSV(toColor(alpha), hsv)
        return hsv[0]
    }

    companion object {
        internal var TAG = "com.tuarua.FreObjectKotlin"
    }

}


