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

@Suppress("PropertyName", "unused")
/** Subclass your Main KotlinController class to allow us to dispatch events back to AIR. */
interface FreKotlinMainController {
    /** This tag prefixes strings that are traced. */
    val TAG: String
    /** the FREContext. */
    var context: FREContext?
    /** Called when context is disposed. */
    fun dispose() {}
    /** @suppress */
    fun onStarted() {}
    /** @suppress */
    fun onRestarted() {}
    /** @suppress */
    fun onResumed() {}
    /** @suppress */
    fun onPaused() {}
    /** @suppress */
    fun onStopped() {}
    /** @suppress */
    fun onDestroyed() {}
    /** Sends StatusEvent to our swc with a level of "TRACE".
     * @param [args] value passed with event.
     */
    fun trace(vararg value: Any?) {
        context?.trace(TAG, value)
    }

    /** Sends StatusEvent to our swc with a level of "TRACE".
     * The output string is prefixed with WARNING:
     * @param [args] value passed with event.
     */
    fun warning(vararg value: Any?) {
        context?.warning(TAG, value)
    }

    /** Sends StatusEvent to our swc with a level of "TRACE".
     * The output string is prefixed with INFO:
     * @param [args] value passed with event.
     */
    fun info(vararg value: Any?) {
        context?.info(TAG, value)
    }
    /** Sends an asynchronous event to the ANE.
     * @param [name] name of event
     * @param [value] value passed with event.
     */
    fun dispatchEvent(name: String, value: String) {
        context?.dispatchEvent(name, value)
    }

}