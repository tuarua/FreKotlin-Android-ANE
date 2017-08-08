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

package com.tuarua.frekotlin.geom
import android.graphics.Rect
import android.util.Log
import com.adobe.fre.FREObject
import com.tuarua.frekotlin.FreObjectKotlin
@Suppress("unused")
open class FreRectangleKotlin() : FreObjectKotlin() {
    private var TAG = "com.tuarua.FreRectangleKotlin"

    constructor(value: FreObjectKotlin) : this() {
        this.rawValue = value.rawValue
    }

    constructor(value: FREObject?) : this() {
        this.rawValue = value
    }

    constructor(value: Rect) : this() {
        rawValue = FreObjectKotlin("flash.geom.Rectangle",
                value.left,
                value.top,
                value.width(),
                value.height()).rawValue
    }

    override val value: Rect
        get() {
            var x = 0
            var y = 0
            var w = 0
            var h = 0
            try {
                x = this.getProperty("x")?.value as Int
                y = this.getProperty("y")?.value as Int
                w = this.getProperty("width")?.value as Int
                h = this.getProperty("width")?.value as Int
            } catch (e: Exception) {
                Log.e(TAG, e.message)
            }
            return Rect(x, y, x + w, y + h)
        }

}