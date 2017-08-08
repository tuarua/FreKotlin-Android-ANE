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

import android.graphics.Point
import android.util.Log
import com.tuarua.frekotlin.FreObjectKotlin

open class FrePointKotlin() : FreObjectKotlin() {
    private var TAG = "com.tuarua.FrePointKotlin"

    constructor(value: Point) : this() {
        rawValue = FreObjectKotlin("flash.geom.Point", value.x, value.y).rawValue
    }

    override val value: Point
        get() {
            var x = 0
            var y = 0
            try {
                x = this.getProperty("x")?.value as Int
                y = this.getProperty("y")?.value as Int
            } catch (e: Exception) {
                Log.e(TAG, e.message)
            }
            return Point(x, y)
        }

    fun copyFrom(sourcePoint: FrePointKotlin) {
        sourcePoint.rawValue?.let { this.callMethod("copyFrom", it) }
    }

}
