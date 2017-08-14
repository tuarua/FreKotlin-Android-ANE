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

interface FreKotlinController {

    fun dispose() {}

    fun setFREContext(context: FREContext) {}

    fun onStarted() {}

    fun onRestarted() {}

    fun onResumed() {}

    fun onPaused() {}

    fun onStopped() {}

    fun onDestroyed() {}

    fun freTrace(context: FREContext?, TAG: String, args: Array<out Any?>) {
        val ctx: FREContext = context ?: return
        var traceStr: String = "$TAG: "
        for (v in args)
            traceStr = traceStr + "$v" + " "
        ctx.dispatchStatusEventAsync(traceStr, "TRACE")
    }
}