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

import android.util.Log
import com.adobe.fre.FREASErrorException
import com.adobe.fre.FREInvalidObjectException
import com.adobe.fre.FRENoSuchNameException
import com.adobe.fre.FREObject
import com.adobe.fre.FRETypeMismatchException
import com.adobe.fre.FREWrongThreadException

@Suppress("unused", "PrivatePropertyName", "MemberVisibilityCanBePrivate")
/**
 * FreException.
 */
open class FreException : Exception {
    private val TAG = "com.tuarua.frekotlin.FreException"
    private var _aneError: FreObjectKotlin? = null
    private var exception: Exception? = null
    /**
     * @property [stackTrace] the stack trace.
     */
    var stackTrace = ""
    /**
     * @property [message] the message.
     */
    final override var message = ""
    private var type = ""

    /**
     * @param [exception] the exception.
     * @param message the name of this group.
     * @constructor Creates a FreException.
     */
    constructor(exception: Any, message: String? = null) : super() {
        this.exception = exception as Exception
        type = exception.javaClass.simpleName
        this.message = when {
            message != null -> message
            else -> exception.message.toString()
        }

        stackTrace = if (exception is FREASErrorException) {
            getActionscriptException(exception.thrownException)
        } else {
            Log.getStackTraceString(exception)
        }
    }

    /**
     * @param [message] the message.
     * @param [type] the type.
     * @param [stackTrace] the stack trace.
     * @constructor Creates a FreException.
     */
    constructor(message: String, type: String = "", stackTrace: String = "") {
        this.message = message
        this.type = type
        this.stackTrace = stackTrace
    }

    private fun getActionscriptException(thrownException: FREObject): String {
        var ret = ""
        try {
            ret = thrownException.callMethod("getStackTrace", null).asString
        } catch (e: FRETypeMismatchException) {
        } catch (e: FREWrongThreadException) {
        } catch (e: FRENoSuchNameException) {
        } catch (e: FREASErrorException) {
        } catch (e: FREInvalidObjectException) {
        }

        return ret
    }

    /**
     * Gets the error
     *
     * @return returns the Exception as a FREObject to be passed back to AS3.
     */
    fun getError(): FREObject? {
        var className = ""
        var methodName = ""
        var lineNumber = 0

        val e = exception
        if (e != null) {
            val stackTraceElements = e.stackTrace
            if (stackTraceElements.size > 2) {
                val fullClassName = stackTraceElements[2].className
                className = fullClassName.substring(fullClassName.lastIndexOf("") + 1)
                methodName = stackTraceElements[2].methodName
                lineNumber = stackTraceElements[2].lineNumber
            }
        } else {
            val st = Throwable().fillInStackTrace().stackTrace[1]
            className = st.className
            methodName = st.methodName
            lineNumber = st.lineNumber
        }

        try {
            _aneError = FreObjectKotlin(FREObject("com.tuarua.fre.ANEError", message, 0, "FreKotlin.Exceptions.$type",
                    "$className.$methodName():$lineNumber", stackTrace))
        } catch (e: FREWrongThreadException) {
        }

        return _aneError?.rawValue
    }

}
