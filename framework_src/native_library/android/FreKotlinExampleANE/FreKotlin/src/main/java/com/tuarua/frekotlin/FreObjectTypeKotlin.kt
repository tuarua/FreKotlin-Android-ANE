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

/** Provides Kotlin mappings for FREObjectType.
 * Adds additional AS3 types (CLASS, RECTANGLE, POINT, DATE)
 */
enum class FreObjectTypeKotlin {
    /**
     * @property OBJECT Object
     */
    OBJECT,
    /**
     * @property NUMBER Number
     */
    NUMBER,
    /**
     * @property STRING String
     */
    STRING,
    /**
     * @property BYTEARRAY Bytearray
     */
    BYTEARRAY,
    /**
     * @property ARRAY Array
     */
    ARRAY,
    /**
     * @property VECTOR Vector
     */
    VECTOR,
    /**
     * @property BITMAPDATA BitmapData
     */
    BITMAPDATA,
    /**
     * @property BOOLEAN Boolean
     */
    BOOLEAN,
    /**
     * @property NULL Null
     */
    NULL,
     /**
     * @property INT int
     */
    INT,
     /**
     * @property CLASS Class
     */
    CLASS,
    /**
     * @property RECTANGLE flash.geom.Rectangle
     */
    RECTANGLE,
    /**
     * @property POINT flash.geom.Point
     */
    POINT,
    /**
     * @property DATE Date
     */
    DATE
}
