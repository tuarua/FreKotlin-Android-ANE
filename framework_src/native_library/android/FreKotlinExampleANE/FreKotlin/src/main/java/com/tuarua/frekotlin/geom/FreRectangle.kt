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

@file:Suppress("unused", "FunctionName", "PrivatePropertyName")

package com.tuarua.frekotlin.geom

import android.graphics.Rect
import android.graphics.RectF
import com.adobe.fre.FREObject
import com.tuarua.frekotlin.*

/**
 * Initialise a Rect from a FREObject.
 * @param [freObject] the FREObject to convert.
 * @return a new Rect.
 */
fun Rect(freObject: FREObject?): Rect {
    val x = Int(freObject["x"]) ?: 0
    val y = Int(freObject["y"]) ?: 0
    val w = Int(freObject["width"]) ?: 0
    val h = Int(freObject["height"]) ?: 0
    return Rect(x, y, x + w, y + h)
}

/**
 * Converts a Rect into a FREObject of AS3 type flash.geom.Rectangle.
 * @receiver The Rect
 * @return A new FREObject
 */
fun Rect.toFREObject(): FREObject? {
    return FREObject("flash.geom.Rectangle", this.left, this.top, this.width(), this.height())
}

/**
 * Initialise a RectF from a FREObject.
 * @param [freObject] the FREObject to convert.
 * @return a new RectF.
 */
fun RectF(freObject: FREObject?): RectF {
    val x = Float(freObject["x"]) ?: 0f
    val y = Float(freObject["y"]) ?: 0f
    val w = Float(freObject["width"]) ?: 0f
    val h = Float(freObject["height"]) ?: 0f
    return RectF(x, y, x + w, y + h)
}

/**
 * Converts a RectF into a FREObject of AS3 type flash.geom.Rectangle.
 * @receiver The RectF
 * @return A new FREObject
 */
fun RectF.toFREObject(): FREObject? {
    return FREObject("flash.geom.Rectangle", this.left, this.top, this.width(), this.height())
}