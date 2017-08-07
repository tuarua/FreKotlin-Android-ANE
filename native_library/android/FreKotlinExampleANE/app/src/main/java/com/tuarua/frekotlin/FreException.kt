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
import com.adobe.fre.FREReadOnlyException
import com.adobe.fre.FRETypeMismatchException
import com.adobe.fre.FREWrongThreadException

@Suppress("unused")
class FreException : Exception {
    private val TAG = "com.tuarua.frekotlin.FreException"
    private var _aneError: FreObjectKotlin? = null
    var stackTrace = ""
    override var message = ""
    var type = ""

    constructor(e: Any, msg:String? = null) : super() {
        //Log.e(TAG, "type?: " + e.getClass().getName());
        val exception = e as Exception
        type = e.javaClass.simpleName
        when {
            msg != null -> message = msg
            else -> message = exception.message.toString()
        }

        if (e is FREASErrorException) {
            stackTrace = getActionscriptException(e.thrownException)
        } else if (e is FREInvalidObjectException || e is FRENoSuchNameException || e is FREReadOnlyException || e is FRETypeMismatchException || e is FREWrongThreadException) {
            val st = exception.stackTrace
            for (i in st.indices) {
                val elem:StackTraceElement = st[i]
                stackTrace = stackTrace + "\n" + elem.toString()
            }
        }
    }

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

    fun getError(stackTraceElements: Array<StackTraceElement>): FREObject? {
        val fullClassName = stackTraceElements[2].className
        val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
        val methodName = stackTraceElements[2].methodName
        val lineNumber = stackTraceElements[2].lineNumber
        try {
            _aneError = FreObjectKotlin("com.tuarua.fre.ANEError", message, 0, "FreKotlin.Exceptions." + type, "$className.$methodName():$lineNumber", stackTrace)
        } catch (e: FREWrongThreadException) {
            // e.printStackTrace();
        }

        return _aneError?.rawValue
    }

}
