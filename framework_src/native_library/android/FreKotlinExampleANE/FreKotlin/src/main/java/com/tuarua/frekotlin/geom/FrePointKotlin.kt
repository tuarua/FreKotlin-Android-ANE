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

package com.tuarua.frekotlin.geom

import android.util.Log
import com.adobe.fre.FREObject
import com.tuarua.frekotlin.*

open class FrePointKotlin() : FreObjectKotlin() {
    private var TAG = "com.tuarua.FrePointKotlin"

    constructor(value: FREObject?) : this() {
        this.rawValue = value
    }

    constructor(value: Point) : this() {
        rawValue = FREObject("flash.geom.Point", value.x, value.y)
    }

    override val value: Point
        get() {
            var x = 0.0
            var y = 0.0
            try {
                val rv =  rawValue
                if (rv != null) {
                    x = Double(FreKotlinHelper.getProperty(rv, "x")) ?: 0.0
                    y = Double( FreKotlinHelper.getProperty(rv, "y")) ?: 0.0
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message)
            }
            return Point(x, y)
        }
}


class Point() {
    var x: Double = 0.0
    var y: Double = 0.0

    constructor(x: Double, y: Double) : this() {
        this.x = x
        this.y = y
    }

    constructor(x: Int, y: Int) : this() {
        this.x = x.toDouble()
        this.y = y.toDouble()
    }

    fun toPoint():android.graphics.Point{
        return android.graphics.Point(this.x.toInt(),this.y.toInt())
    }

    fun set(x: Double, y: Double){
        this.x = x
        this.y = y
    }

    fun set(x: Int, y: Int){
        this.x = x.toDouble()
        this.y = y.toDouble()
    }

}

fun Point(freObject: FREObject?): Point? = FrePointKotlin(value = freObject).value
fun Point(frePointObject: FrePointKotlin?): Point? = frePointObject?.value

@Throws(FreException::class)
fun Point.toFREObject():FREObject? {
    return try {
        FrePointKotlin(this).rawValue
    } catch (e: FreException) {
        e.getError(Thread.currentThread().stackTrace)
    } catch (e: Exception) {
        FreException(e).getError(Thread.currentThread().stackTrace)
    }
}
