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

@file:Suppress("unused")

package com.tuarua.frekotlin

import android.util.Log
import com.adobe.fre.*

/**
 * Logger utility for logging any FRE errors which occur
 */
object FreKotlinLogger {
    /** Sets the FREContext */
    var context: FREContext? = null

    /**
     * Traces the message to the console.
     * @param [message] message to error.
     * @param [exception] exception to error.
     */
    fun error(message: String, exception: Exception) {
        val ctx = context ?: return
        val type = exception.javaClass.simpleName
        ctx.dispatchStatusEventAsync("[FreKotlin] ‼️ $type $message", "TRACE")
        val stackTrace = if (exception is FREASErrorException) {
            getActionscriptException(exception.thrownException)
        } else {
            Log.getStackTraceString(exception)
        }

        if (stackTrace.isNotEmpty()) {
            ctx.dispatchStatusEventAsync("[FreKotlin] ‼️ $stackTrace", "TRACE")
        }
    }

    /**
     * Traces the message to the console.
     * @param [message] message to error.
     */
    fun info(message: String) {
        val ctx = context ?: return
        ctx.dispatchStatusEventAsync("[FreKotlin] ⚠️ $message", "TRACE")
    }

    private fun getActionscriptException(thrownException: FREObject): String {
        try {
            return thrownException.callMethod("getStackTrace", null).asString
        } catch (e: FRETypeMismatchException) {
        } catch (e: FREWrongThreadException) {
        } catch (e: FRENoSuchNameException) {
        } catch (e: FREASErrorException) {
        } catch (e: FREInvalidObjectException) {
        }
        return ""
    }
}