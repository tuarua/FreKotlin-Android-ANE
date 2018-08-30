package {
import com.tuarua.FreKotlinExampleANE;
import com.tuarua.Person;
import com.tuarua.fre.ANEError;

import flash.desktop.NativeApplication;
import flash.display.Bitmap;
import flash.display.Loader;
import flash.display.Sprite;
import flash.display.StageAlign;
import flash.display.StageScaleMode;
import flash.events.Event;
import flash.geom.Rectangle;
import flash.net.URLRequest;
import flash.text.TextField;
import flash.text.TextFormat;
import flash.text.TextFormatAlign;
import flash.utils.ByteArray;

[SWF(width="640", height="640", frameRate="60", backgroundColor="#F1F1F1")]
public class Main extends Sprite {
    private var ane:FreKotlinExampleANE = new FreKotlinExampleANE();
    private var hasActivated:Boolean = false;
    private var textField:TextField = new TextField();
    private static const GREEN:uint = 0x00FF00;
    public static const HALF_GREEN:uint = 0x8000FF00;

    public function Main() {
        super();
        stage.align = StageAlign.TOP_LEFT;
        stage.scaleMode = StageScaleMode.NO_SCALE;

        NativeApplication.nativeApplication.addEventListener(Event.EXITING, onExiting);
        this.addEventListener(Event.ACTIVATE, onActivated);
    }

    private function onActivated(event:Event):void {
        if (!hasActivated) {
            var tf:TextFormat = new TextFormat();
            tf.size = 48;
            tf.color = 0x333333;
            tf.align = TextFormatAlign.LEFT;
            textField.defaultTextFormat = tf;
            textField.width = stage.fullScreenWidth;
            textField.height = 1200;
            textField.multiline = true;
            textField.wordWrap = true;

            var person:Person = new Person();
            person.age = 21;
            person.name = "Tom";
            person.city.name = "Boston";

            var resultString:String = ane.runStringTests("Björk Guðmundsdóttir Sinéad O’Connor 久保田  " +
                    "利伸 Михаил Горбачёв Садриддин Айнӣ Tor Åge Bringsværd 章子怡 €");
            textField.text += resultString + "\n";

            var resultNumber:Number = ane.runNumberTests(31.99);
            textField.text += "Number: " + resultNumber + "\n";

            var resultInt:int = ane.runIntTests(-54, 66);
            textField.text += "Int: " + resultInt + "\n";
            trace("HALF_GREEN", HALF_GREEN, HALF_GREEN == ane.runColorTests(GREEN, HALF_GREEN) ? "✅" : "❌");

            var resultObject:Person = ane.runObjectTests(person) as Person;
            if (resultObject) {
                textField.text += "Person.age: " + resultObject.age.toString() + "\n";
            }

            var inRect:Rectangle = new Rectangle(50.1, 60, 70.2, 80);
            var resultRectangle:Rectangle = ane.runExtensibleTests(inRect) as Rectangle;
            trace("resultRectangle", resultRectangle);

            var myArray:Array = [];
            myArray.push(3, 1, 4, 2, 6, 5);

            var myVector:Vector.<String> = new <String>[];
            myVector.push("abc", "xyz");

            var resultArray:Array = ane.runArrayTests(myArray, myVector);
            if (resultArray) {
                textField.text += "Array: " + resultArray.toString() + "\n";
            }

            const IMAGE_URL:String = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Kotlin-logo.svg/600px-Kotlin-logo.svg.png";

            var ldr:Loader = new Loader();
            ldr.contentLoaderInfo.addEventListener(Event.COMPLETE, ldr_complete);
            ldr.load(new URLRequest(IMAGE_URL));


            function ldr_complete(evt:Event):void {
                var bmp:Bitmap = ldr.content as Bitmap;
                ane.runBitmapTests(bmp.bitmapData);
                bmp.x = 20;
                bmp.y = 600;
                addChild(bmp);
            }

            try {
                ane.runErrorTests(person, "Test String");
            } catch (e:ANEError) {
                trace("Error captured in AS");
                trace("e.message:", e.message);
                trace("e.errorID:", e.errorID);
                trace("e.type:", e.type);
                trace("e.source:", e.source);
                trace("e.getStackTrace():", e.getStackTrace());
            }

            var testDate:Date = new Date(1990, 5, 13, 8, 59, 3);
            trace("Date returned is same", testDate.time == ane.runDateTests(testDate).time ? "✅" : "❌");

            var myByteArray:ByteArray = new ByteArray();
            myByteArray.writeUTFBytes("Kotlin in an ANE. Say whaaaat!");
            ane.runByteArrayTests(myByteArray);


            addChild(textField);
        }
        hasActivated = true;
    }

    private function onExiting(event:Event):void {
        ane.dispose();
    }
}
}
