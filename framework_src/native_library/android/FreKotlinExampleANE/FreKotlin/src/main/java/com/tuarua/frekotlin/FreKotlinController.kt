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

@Suppress("unused", "PropertyName")
interface FreKotlinController {
    val TAG:String
    var context: FREContext?
    fun trace(vararg value: Any?) {
        context?.trace(TAG, value)
    }

    fun warn(vararg value: Any?) {
        context?.warning(TAG, value)
    }

    fun info(vararg value: Any?) {
        context?.info(TAG, value)
    }

    fun dispatchEvent(name: String, value: String) {
        context?.dispatchEvent(name, value)
    }
}