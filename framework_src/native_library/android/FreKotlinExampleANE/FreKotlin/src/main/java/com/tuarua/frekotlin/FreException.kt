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

import com.adobe.fre.FREASErrorException
import com.adobe.fre.FREInvalidObjectException
import com.adobe.fre.FRENoSuchNameException
import com.adobe.fre.FREObject
import com.adobe.fre.FRETypeMismatchException
import com.adobe.fre.FREWrongThreadException

@Suppress("unused")

open class FreException : Exception {
    private val TAG = "com.tuarua.frekotlin.FreException"
    private var _aneError: FreObjectKotlin? = null
    var stackTrace = ""
    override var message = ""
    var type = ""

    constructor(e: Any, message: String? = null) : super() {
        val exception = e as Exception
        type = e.javaClass.simpleName
        this.message = when {
            message != null -> message
            else -> exception.message.toString()
        }

        if (e is FREASErrorException) {
            stackTrace = getActionscriptException(e.thrownException)
        } else {
            val st = exception.stackTrace
            st.indices.forEach { i ->
                val elem: StackTraceElement = st[i]
                stackTrace = stackTrace + "\n" + elem.toString()
            }
        }
    }

    constructor(message: String, type: String = "", stackTrace: String = "") {
        this.message = message
        this.type = type
        this.stackTrace = stackTrace
    }

    /**
     *
     * @param [thrownException] the FREObject for the exception
     * @return returns the Exception as a String.
     */

    private fun getActionscriptException(thrownException: FREObject): String {
        var ret = ""
        try {
            ret = thrownException.callMethod("getStackTrace", null).asString
        } catch (e: FRETypeMismatchException) {
            // e.printStackTrace();
        } catch (e: FREWrongThreadException) {
        } catch (e: FRENoSuchNameException) {
        } catch (e: FREASErrorException) {
        } catch (e: FREInvalidObjectException) {
        }

        return ret
    }

    /**
     *
     * @param [stackTraceElements] the java stack trace
     * @return returns the Exception as a FREObject to be passed back to AS3.
     */
    fun getError(stackTraceElements: Array<StackTraceElement>): FREObject? {
        val fullClassName = stackTraceElements[2].className
        val className = fullClassName.substring(fullClassName.lastIndexOf("") + 1)
        val methodName = stackTraceElements[2].methodName
        val lineNumber = stackTraceElements[2].lineNumber
        try {
            _aneError = FreObjectKotlin(FREObject("com.tuarua.fre.ANEError", message, 0, "FreKotlin.Exceptions." + type,
                    "$className.$methodName():$lineNumber", stackTrace))
        } catch (e: FREWrongThreadException) {
            // e.printStackTrace();
        }

        return _aneError?.rawValue
    }

}
