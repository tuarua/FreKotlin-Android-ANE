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

package com.tuarua {
import com.tuarua.fre.ANEError;

import flash.display.BitmapData;
import flash.events.EventDispatcher;
import flash.geom.Rectangle;
import flash.utils.ByteArray;

public class FreKotlinExampleANE extends EventDispatcher {
    private static var _example:FreKotlinExampleANE;

    public function FreKotlinExampleANE() {
        initiate();
    }

    private function initiate():void {
        if (FreKotlinExampleANEContext.context) {
            var theRet:* = FreKotlinExampleANEContext.context.call("init");
            if (theRet is ANEError) throw theRet as ANEError;
        }
        _example = this;
    }

    public static function get example():FreKotlinExampleANE {
        if (!_example) {
            new FreKotlinExampleANE();
        }
        return _example;
    }

    public function runStringTests(value:String):String {
        FreKotlinExampleANEContext.validate();
        return FreKotlinExampleANEContext.context.call("runStringTests", value) as String;
    }

    public function runNumberTests(value:Number):Number {
        FreKotlinExampleANEContext.validate();
        return FreKotlinExampleANEContext.context.call("runNumberTests", value) as Number;
    }

    public function runIntTests(value:int, value2:uint):int {
        FreKotlinExampleANEContext.validate();
        return FreKotlinExampleANEContext.context.call("runIntTests", value, value2) as int;
    }

    public function runColorTests(value:uint, value2:uint):uint {
        FreKotlinExampleANEContext.validate();
        return uint(FreKotlinExampleANEContext.context.call("runColorTests", value, value2)) as uint; // important Kotlin has no uint must convert here to uint
    }

    public function runArrayTests(value:Array, value2:Vector.<String>):Vector.<int> {
        FreKotlinExampleANEContext.validate();
        return FreKotlinExampleANEContext.context.call("runArrayTests", value, value2) as Vector.<int>;
    }

    public function runObjectTests(value:Person):Person {
        FreKotlinExampleANEContext.validate();
        return FreKotlinExampleANEContext.context.call("runObjectTests", value) as Person;
    }

    public function runExtensibleTests(value:Rectangle):Rectangle {
        FreKotlinExampleANEContext.validate();
        return FreKotlinExampleANEContext.context.call("runExtensibleTests", value) as Rectangle;
    }

    public function runBitmapTests(bmd:BitmapData):void {
        FreKotlinExampleANEContext.validate();
        FreKotlinExampleANEContext.context.call("runBitmapTests", bmd);
    }

    public function runByteArrayTests(byteArray:ByteArray):void {
        FreKotlinExampleANEContext.validate();
        FreKotlinExampleANEContext.context.call("runByteArrayTests", byteArray);
    }

    public function runDataTests(value:String):String {
        FreKotlinExampleANEContext.validate();
        return FreKotlinExampleANEContext.context.call("runDataTests", value) as String;
    }

    public function runDateTests(value:Date):Date {
        FreKotlinExampleANEContext.validate();
        return FreKotlinExampleANEContext.context.call("runDateTests", value) as Date;
    }

    public function runErrorTests(value:Person, string:String):void {
        FreKotlinExampleANEContext.validate();
        var theRet:* = FreKotlinExampleANEContext.context.call("runErrorTests", value, string);
        if (theRet is ANEError) {
            throw theRet as ANEError;
        }
    }

    public static function dispose():void {
        if (FreKotlinExampleANEContext.context) {
            FreKotlinExampleANEContext.dispose();
        }
    }
}
}
