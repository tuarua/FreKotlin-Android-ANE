package com.tuarua {
import com.tuarua.fre.ANEError;

import flash.display.BitmapData;
import flash.events.EventDispatcher;
import flash.events.StatusEvent;
import flash.external.ExtensionContext;
import flash.geom.Rectangle;
import flash.utils.ByteArray;

public class FreKotlinExampleANE extends EventDispatcher {
    private static const NAME:String = "frekotlin.example";
    private var ctx:ExtensionContext;

    public function FreKotlinExampleANE() {
        initiate();
    }

    private function initiate():void {
        trace("[" + NAME + "] Initalizing ANE...");
        try {
            ctx = ExtensionContext.createExtensionContext("com.tuarua." + NAME, null);
            ctx.addEventListener(StatusEvent.STATUS, gotEvent);
        } catch (e:Error) {
            trace("[" + NAME + "] ANE Not loaded properly.  Future calls will fail.");
        }
    }

    private function gotEvent(event:StatusEvent):void {
        //trace(event);
        var pObj:Object;
        switch (event.level) {
            case "TRACE":
                trace(event.code);
                break;
        }
    }

    public function runStringTests(value:String):String {
        return ctx.call("runStringTests", value) as String;
    }

    public function runNumberTests(value:Number):Number {
        return ctx.call("runNumberTests", value) as Number;
    }

    public function runIntTests(value:int, value2:uint):int {
        return ctx.call("runIntTests", value, value2) as int;
    }

    public function runColorTests(value:uint, value2:uint):uint {
        return uint(ctx.call("runColorTests", value, value2)) as uint; // important Kotlin has no uint must convert here to uint
    }

    public function runArrayTests(value:Array, value2:Vector.<String>):Array {
        return ctx.call("runArrayTests", value, value2) as Array;
    }

    public function runObjectTests(value:Person):Person {
        return ctx.call("runObjectTests", value) as Person;
    }

    public function runExtensibleTests(value:Rectangle):Rectangle {
        return ctx.call("runExtensibleTests", value) as Rectangle;
    }

    public function runBitmapTests(bmd:BitmapData):void {
        ctx.call("runBitmapTests", bmd);
    }

    public function runByteArrayTests(byteArray:ByteArray):void {
        ctx.call("runByteArrayTests", byteArray);
    }

    public function runDataTests(value:String):String {
        return ctx.call("runDataTests", value) as String;
    }

    public function runDateTests(value:Date):Date {
        return ctx.call("runDateTests", value) as Date;
    }

    public function runErrorTests(value:Person, string:String):void {
        var theRet:* = ctx.call("runErrorTests", value, string);
        if (theRet is ANEError) {
            throw theRet as ANEError;
        }
    }

    public function dispose():void {
        if (!ctx) {
            trace("[" + NAME + "] Error. ANE Already in a disposed or failed state...");
            return;
        }

        trace("[" + NAME + "] Unloading ANE...");
        ctx.removeEventListener(StatusEvent.STATUS, gotEvent);
        ctx.dispose();
        ctx = null;
    }
}
}
