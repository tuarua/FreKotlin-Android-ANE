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
import android.graphics.Point
import android.graphics.Rect
import com.adobe.fre.FREContext
import com.adobe.fre.FREObject
import com.tuarua.frekotlin.geom.FrePointKotlin
import com.tuarua.frekotlin.geom.FreRectangleKotlin
import java.util.*

typealias FREArgv = ArrayList<FREObject>
fun FREContext.sendEvent(name: String, value: String) {
    this.dispatchStatusEventAsync(value, name)
}

fun FREContext.trace(TAG: String, args: Array<out Any?>) {
    val TRACE = "TRACE"
    var traceStr = "$TAG: "
    for (v in args)
        traceStr = traceStr + "$v" + " "
    this.sendEvent(TRACE, traceStr)
}

// Declare an extension function that calls a lambda called block if the value is null
inline fun <T> T.guard(block: T.() -> Unit): T {
    if (this == null) block(); return this
}

fun Double(freObject: FREObject?): Double? {
    val v = FreObjectKotlin(freObject = freObject).value
    return (v as? Int)?.toDouble() ?: v as Double?
}
fun Double(freObjectKotlin: FreObjectKotlin?): Double? {
    val v = freObjectKotlin?.value
    return (v as? Int)?.toDouble() ?: v as Double?
}

fun Float(freObject: FREObject?): Float? {
    val v = FreObjectKotlin(freObject = freObject).value
    return (v as? Int)?.toFloat() ?: (v as Double?)?.toFloat()
}

fun Float(freObjectKotlin: FreObjectKotlin?): Float? {
    val v = freObjectKotlin?.value
    return (v as? Int)?.toFloat() ?: (v as Double?)?.toFloat()
}

fun Int(freObject: FREObject?): Int? = FreObjectKotlin(freObject = freObject).value as Int?
fun Int(freObjectKotlin: FreObjectKotlin?): Int? = freObjectKotlin?.value as Int?
fun String(freObject: FREObject?): String? = FreObjectKotlin(freObject = freObject).value?.toString()
fun String(freObjectKotlin: FreObjectKotlin?): String? = freObjectKotlin?.value?.toString()
fun Boolean(freObject: FREObject?): Boolean? = FreObjectKotlin(freObject = freObject).value as Boolean?
fun Boolean(freObjectKotlin: FreObjectKotlin?): Boolean? = freObjectKotlin?.value as Boolean?
fun Point(freObject: FREObject?): Point? = FrePointKotlin(value = freObject).value
fun Point(frePointKotlin: FrePointKotlin?): Point? = frePointKotlin?.value
fun Rect(freObject: FREObject?): Rect? = FreRectangleKotlin(value = freObject).value
fun Rect(freRectangleObject: FreRectangleKotlin?): Rect? = freRectangleObject?.value
fun Date(freObject: FREObject?): Date? = FreObjectKotlin(freObject = freObject).value as Date?
fun Date(freObjectKotlin: FreObjectKotlin?): Date? = freObjectKotlin?.value as Date?