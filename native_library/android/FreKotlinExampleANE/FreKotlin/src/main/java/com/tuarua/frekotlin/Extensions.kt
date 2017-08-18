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
import com.adobe.fre.FREContext

fun FREContext.sendEvent(name: String, value: String) {
    this.dispatchStatusEventAsync(value, name)
}

fun FREContext.trace(TAG: String, args: Array<out Any?>) {
    val TRACE = "TRACE"
    var traceStr = "$TAG: "
    for (v in args)
        traceStr = traceStr + "$v" + " "
    this.sendEvent(traceStr, TRACE)
}

// Declare an extension function that calls a lambda called block if the value is null
inline fun <T> T.guard(block: T.() -> Unit): T {
    if (this == null) block(); return this
}