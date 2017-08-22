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
import com.adobe.fre.FREArray
import com.adobe.fre.FREInvalidObjectException
import com.adobe.fre.FRENoSuchNameException
import com.adobe.fre.FREObject
import com.adobe.fre.FRETypeMismatchException
import com.adobe.fre.FREWrongThreadException

import java.util.ArrayList
import java.util.HashMap

object FreKotlinHelper {
    internal var TAG = "com.tuarua.FreKotlinHelper"

    @Throws(FreException::class)
    private fun getAsString(rawValue: FREObject): String {
        val ret: String
        try {
            ret = rawValue.asString
        } catch (e: Exception) {
            throw FreException(e, "cannot get FREObject as String")
        }
        return ret
    }

    @Throws(FreException::class)
    private fun getAsDouble(rawValue: FREObject): Double {
        val ret: Double
        try {
            ret = rawValue.asDouble
        } catch (e: Exception) {
            throw FreException(e, "cannot get FREObject as Double")
        }
        return ret
    }

    @Throws(FreException::class, FREASErrorException::class, FREInvalidObjectException::class,
            FREWrongThreadException::class, FRENoSuchNameException::class, FRETypeMismatchException::class)
    private fun getAsDictionary(rawValue: FREObject): Map<String, Any> {
        val ret = HashMap<String, Any>()
        try {
            val aneUtils = FREObject.newObject("com.tuarua.fre.ANEUtils", null)
            val args = arrayOfNulls<FREObject>(1)
            args[0] = rawValue
            val classProps1 = aneUtils.callMethod("getClassProps", args)

            if (classProps1 is FREArray) {
                var i = 0
                val numProps = classProps1.length.toInt()
                while (i < numProps) {
                    val elem = classProps1.getObjectAt(i.toLong())
                    if (elem != null) {
                        val propName = elem.getProperty("name").asString
                        if (propName != null && !propName.isEmpty()) {
                            val propval: FREObject? = rawValue.getProperty(propName)
                            if (propval is FREObject) {
                                FreObjectKotlin(propval).value?.let { ret.put(propName, it) }
                            }
                        }
                    }
                    i++
                }
            }
        }catch (e:Exception) {
            throw FreException(e)
        }
        return ret
    }

    @Throws(FreException::class)
    private fun getAsInt(rawValue: FREObject): Int {
        val ret: Int
        try {
            ret = rawValue.asInt
        } catch (e: Exception) {
            throw FreException(e, "cannot get FREObject as Int")
        }
        return ret
    }

    @Throws(FreException::class)
    private fun getAsBoolean(rawValue: FREObject): Boolean {
        val ret: Boolean
        try {
            ret = rawValue.asBool
        } catch (e: Exception) {
            throw FreException(e, "cannot get FREObject as Boolean")
        }
        return ret
    }

    fun getAsObject(rawValue: FREObject): Any? {
        //Log.d(TAG, "getAsObject: ")
        try {
            when (getType(rawValue)) {
                FreObjectTypeKotlin.STRING -> return getAsString(rawValue)
                FreObjectTypeKotlin.NUMBER -> return getAsDouble(rawValue)
                FreObjectTypeKotlin.BYTEARRAY -> {
                    TODO()
                }
                FreObjectTypeKotlin.ARRAY, FreObjectTypeKotlin.VECTOR -> return getAsArrayList(rawValue as FREArray)
                FreObjectTypeKotlin.BITMAPDATA -> {
                    TODO()
                }
                FreObjectTypeKotlin.BOOLEAN -> return getAsBoolean(rawValue)
                FreObjectTypeKotlin.NULL -> return null
                FreObjectTypeKotlin.INT -> return getAsInt(rawValue)
                FreObjectTypeKotlin.CLASS, FreObjectTypeKotlin.OBJECT -> return getAsDictionary(rawValue)
                FreObjectTypeKotlin.RECTANGLE -> TODO()
            }
        } catch (e: FREInvalidObjectException) {
            Log.e(TAG, "getAsObject Error: " + e.message)
        } catch (e: FREASErrorException) {
            Log.e(TAG, "getAsObject Error: " + e.message)
        } catch (e: FRENoSuchNameException) {
            Log.e(TAG, "getAsObject Error: " + e.message)
        } catch (e: FRETypeMismatchException) {
            Log.e(TAG, "getAsObject Error: " + e.message)
        } catch (e: FREWrongThreadException) {
            Log.e(TAG, "getAsObject Error: " + e.message)
        }

        return null
    }


    private fun getAsArrayList(rawValue: FREArray): ArrayList<Any> {
        val al = ArrayList<Any>()
        try {
            val len = rawValue.length
            for (i in 0..len - 1) {
                getAsObject(rawValue.getObjectAt(i))?.let { al.add(it) }
            }
        } catch (e: FREWrongThreadException) {
            Log.e(TAG, e.message)
        } catch (e: FREInvalidObjectException) {
            Log.e(TAG, e.message)
        }

        return al
    }

    fun getType(rawValue: FREObject?): FreObjectTypeKotlin {
        try {
            val aneUtils = FREObject.newObject("com.tuarua.fre.ANEUtils", null)
            val args = arrayOfNulls<FREObject>(1)
            args[0] = rawValue
            val classType = aneUtils.callMethod("getClassType", args)
            val type = getAsString(classType).toLowerCase()
            when (type) {
                "object" -> return FreObjectTypeKotlin.OBJECT
                "number" -> return FreObjectTypeKotlin.NUMBER
                "string" -> return FreObjectTypeKotlin.STRING
                "bytearray" -> return FreObjectTypeKotlin.BYTEARRAY
                "array" -> return FreObjectTypeKotlin.ARRAY
                "vector" -> return FreObjectTypeKotlin.VECTOR
                "bitmapdata" -> return FreObjectTypeKotlin.BITMAPDATA
                "boolean" -> return FreObjectTypeKotlin.BOOLEAN
                "null" -> return FreObjectTypeKotlin.NULL
                "int" -> return FreObjectTypeKotlin.INT
                "flash.geom::rectangle" -> return FreObjectTypeKotlin.RECTANGLE
                else -> return FreObjectTypeKotlin.CLASS
            }
        } catch (e: FRETypeMismatchException) {
            return FreObjectTypeKotlin.NULL
        } catch (e: FREWrongThreadException) {
            return FreObjectTypeKotlin.NULL
        } catch (e: FRENoSuchNameException) {
            return FreObjectTypeKotlin.NULL
        } catch (e: FREASErrorException) {
            return FreObjectTypeKotlin.NULL
        } catch (e: FREInvalidObjectException) {
            return FreObjectTypeKotlin.NULL
        }
    }

    @Throws(FreException::class)
    fun getProperty(rawValue: FREObject, name: String): FREObject? {
        var ret: FREObject?
        try {
            ret = rawValue.getProperty(name)
        } catch (e: Exception) {
            throw FreException(e)
        }
        return ret
    }

    @Throws(FreException::class)
    fun setProperty(rawValue: FREObject, name: String, prop: FREObject?): FREObject? {
        try {
            rawValue.setProperty(name, prop)
        } catch (e: Exception) {
            throw FreException(e)
        }
        return null
    }

    @Throws(FreException::class)
    fun callMethod(rawValue: FREObject, name: String, args: Array<out Any>): FREObject? {
        val argsArray = arrayOfNulls<FREObject>(args.size)
        for ((i, item) in args.withIndex()) {
            argsArray[i] = FreObjectKotlin(item).rawValue
        }
        var ret: FREObject?
        try {
            ret = rawValue.callMethod(name, argsArray)
        } catch (e: Exception) {
            throw FreException(e)
        }
        return ret
    }

    @Throws(FreException::class)
    fun callMethod(rawValue: FREObject, name: String): FREObject? {
        Log.d(TAG, "call method: " + name);
        val argsArray = arrayOfNulls<FREObject>(0)
        var ret: FREObject?
        try {
            ret = rawValue.callMethod(name, argsArray)
        } catch (e: Exception) {
            throw FreException(e)
        }
        return ret
    }

}
