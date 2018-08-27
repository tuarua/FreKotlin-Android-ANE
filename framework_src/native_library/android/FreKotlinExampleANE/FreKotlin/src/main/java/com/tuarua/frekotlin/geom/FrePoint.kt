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

import android.graphics.Point
import android.graphics.PointF
import com.adobe.fre.FREObject
import com.tuarua.frekotlin.*

fun Point(freObject: FREObject?): Point {
    return Point(Int(freObject["x"]) ?: 0,
            Int(freObject["y"]) ?: 0)
}

fun Point.toFREObject(): FREObject? {
    return FREObject("flash.geom.Point", this.x, this.y)
}

fun PointF(freObject: FREObject?): PointF {
    return PointF(Float(freObject["x"]) ?: 0f,
            Float(freObject["y"]) ?: 0f)
}

fun PointF.toFREObject(): FREObject? {
    return FREObject("flash.geom.Point", this.x, this.y)
}
