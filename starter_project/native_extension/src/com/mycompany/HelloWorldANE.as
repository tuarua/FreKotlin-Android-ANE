package com.mycompany {
import com.tuarua.fre.ANEError;
import flash.events.EventDispatcher;

public class HelloWorldANE extends EventDispatcher {
    private static var _helloWorld:HelloWorldANE;

    public function HelloWorldANE() {
        if (HelloWorldANEContext.context) {
            var theRet:* = HelloWorldANEContext.context.call("init");
            if (theRet is ANEError) throw theRet as ANEError;
        }
        _helloWorld = this;
    }

    public static function get helloWorld():HelloWorldANE {
        if (!_helloWorld) {
            new HelloWorldANE();
        }
        return _helloWorld;
    }

    public function sayHello(myString:String, uppercase:Boolean, numRepeats:int):String {
        HelloWorldANEContext.validate();
        var theRet:* = HelloWorldANEContext.context.call("sayHello", myString, uppercase, numRepeats);
        if (theRet is ANEError) throw theRet as ANEError;
        return theRet as String;
    }

    public static function dispose():void {
        if (HelloWorldANEContext.context) {
            HelloWorldANEContext.dispose();
        }
    }


}
}