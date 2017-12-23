# Android Kotlin ANE

Example Android Studio project showing how to create Air Native Extensions for Android using Kotlin.  
  
This project is used as the basis for the following ANEs   
[Google Maps ANE](https://github.com/tuarua/Google-Maps-ANE)   
[AdMob ANE](https://github.com/tuarua/AdMob-ANE)  
[WebViewANE](https://github.com/tuarua/WebViewANE )


-------------
  

### Getting Started

A basic Hello World [starter project](/starter_project) is included 


### How to use 
###### Converting from FREObject args into Kotlin types, returning FREObjects
The following table shows the primitive as3 types which can easily be converted to/from Kotlin types


| AS3 type | Kotlin type | AS3 param->Kotlin | return Kotlin->AS3 |
|:--------:|:--------:|:--------------|:-----------|
| String | String | `val s = String(argv[0])` | `return s.toFREObject()`|
| int | Int | `val i = Int(argv[0])` | `return i.toFREObject()`|
| Boolean | Boolean | `val b = Boolean(argv[0])` | `return b.toFREObject()`|
| Number | Double | `val d = Double(argv[0])` | `return d.toFREObject()`|
| Number | Float | `val fl = Float(argv[0])` | `return fl.toFREObject()`|
| Date | Date | `val dt = Date(argv[0])` | `return dt.toFREObject()`|
| Rectangle | Rect | `val r = Rect(argv[0])` | `return r.toFREObject()`|
| Point | Point | `val pnt = Point(argv[0])` | `return pnt.toFREObject()`|
| Vector Int | IntArray | `val arr = IntArray(argv[0])` | `return arr.toFREArray()`|
| Vector Boolean | BooleanArray | `val arr = BooleanArray(argv[0])` | `return arr.toFREArray()`|
| Vector Number | DoubleArray | `val arr = DoubleArray(argv[0])` | `return arr.toFREArray()`|
| Vector String | List | `val al = List<String>(argv[0])` | `return al.toFREArray()`|
| Object | Map<String, Any>? | `val dict: Map<String, Any>? = Map(argv[0])` | TODO |


Example

```` Kotlin
val airString: String? = String(argv[0])
trace("String passed from AIR:", airString) //As3 style trace!

val kotlinString = "I am a string from Kotlin"
return kotlinString.toFREObject()
`````

Example - Call a method on an FREObject

```` Kotlin
val person = argv[0]
val addition = person.call("add", 100, 31)
if (addition != null) {
    trace("addition result: ${Int(addition)}")
}
`````

Example - Get a property of a FREObject

```` Kotlin
val person = argv[0]
val age = Int(person["age"])
if (age != null) {
    trace("person is: $age" years old)
}
`````

Example - Set a property of a FREObject

```` Kotlin
val person = argv[0]
try {
     person.setProp("age", 32)
} catch (e: FreException) {

}
`````

Example - Convert a FREObject Object into a Map

```` Kotlin
val person = argv[0]
val dictionary: Map<String, Any>? = Map(person)
trace("keys: ${dictionary?.keys.toString()} values: ${dictionary?.values.toString()}")
`````

Example - Create a new FREObject

```` Kotlin
val newPerson = FREObject("com.tuarua.Person")
trace("We created a new person. type = ${newPerson.type}")
`````

Example - Sending events back to AIR (replaces dispatchStatusEventAsync)

```` Kotlin
sendEvent("MY_EVENT", "this is a test")
`````

Example - Error handling
```` kotlin
try {
    person.getProp("doNotExist")
} catch (e: FreException) {
    return e.getError(Thread.currentThread().stackTrace) //return the error as an actionscript error
}
`````

Advanced Example - Extending. Convert to/from LatLng
```` kotlin
package com.tuarua.frekotlin

import com.adobe.fre.FREObject
import com.google.android.gms.maps.model.LatLng

class FreCoordinateKotlin() : FreObjectKotlin() {
    private var TAG = "com.tuarua.FreCoordinateKotlin"

    constructor(value: LatLng) : this() {
        rawValue = FREObject("com.tuarua.googlemaps.Coordinate", value.longitude, value.latitude)
    }

    constructor(freObject: FREObject?) : this() {
        rawValue = freObject
    }

    override val value: LatLng
        @Throws(FreException::class)
        get() {
            var lat = 0.0
            var lng = 0.0

            val rv = rawValue
            if (rv == null) {
                return LatLng(lat, lng)
            } else {
                try {
                    val latFre = Double(rv["latitude"])
                    val lngFre = Double(rv["longitude"])
                    if (latFre != null && lngFre != null) {
                        lat = latFre
                        lng = lngFre
                    }
                } catch (e: FreException) {
                    throw e
                } catch (e: Exception) {
                    throw FreException(e)
                }
                return LatLng(lat, lng)
            }

        }
}

fun LatLng(freObject: FREObject?): LatLng = FreCoordinateKotlin(freObject = freObject).value
`````

### Prerequisites

You will need

- Android Studio 3.0
- IntelliJ IDEA
- AIR 27+
