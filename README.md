# Android Kotlin ANE

Example Android Studio project showing how to create Air Native Extensions for Android using Kotlin.  
  
This project is used as the basis for the following ANEs   
[Google Maps ANE](https://github.com/tuarua/Google-Maps-ANE)   
[AdMob ANE](https://github.com/tuarua/AdMob-ANE)  


-------------
  
https://kotlinlang.org/docs/reference/

FreKotlinExampleANE.java is the entry point of the ANE. It acts as a thin layered API to your Kotlin controller.  
Add the number of methods here 

```` Java
public static final String[] FUNCTIONS = {
    "sayHello",
    "isKotlinGreat"
};
`````


KotlinController.kt   
Add Kotlin function(s) here   

```` Kotlin
fun sayHello(ctx: FREContext, argv: FREArgv): FREObject? {
    //your code here
    return null  
}

fun isKotlinGreat(ctx: FREContext, argv: FREArgv): FREObject? {
    //your code here
    return null  
}
`````

----------

### How to use 

Example - Convert a FREObject into a String, and String into FREObject

```` Kotlin
val airString: String? = String(argv[0])
trace("String passed from AIR:", airString) //As3 style trace!

val kotlinString = "I am a string from Kotlin"
return kotlinString.toFREObject()
`````

Example - Call a method on an FREObject

```` Kotlin
val addition: FreObjectKotlin? = person.callMethod("add", 100, 31)
if (addition is FreObjectKotlin) {
    val sum = addition.value
    if (sum is Int) {
       trace("addition result:", sum)
    }
}
`````

Example - Error handling
```` kotlin
try {
    person.getProperty("doNotExist")
} catch (e: FreException) {
    return e.getError(Thread.currentThread().stackTrace) //return the error as an actionscript error
}
`````

Example - Extending. Convert to/from LatLng
```` kotlin
package com.tuarua.frekotlin

import com.adobe.fre.FREObject
import com.google.android.gms.maps.model.LatLng

class FreCoordinateKotlin() : FreObjectKotlin() {
    private var TAG = "com.tuarua.FreCoordinateKotlin"

    constructor(value: LatLng) : this() {
        rawValue = FreObjectKotlin("com.tuarua.googlemaps.Coordinate", value.longitude, value.latitude).rawValue
    }

    constructor(freObjectKotlin: FreObjectKotlin?) : this() {
        rawValue = freObjectKotlin?.rawValue
    }

    constructor(freObject: FREObject?) : this() {
        rawValue = freObject
    }

    override val value: LatLng
        @Throws(FreException::class)
        get() {
            var lat = 0.0
            var lng = 0.0
            if (this.rawValue == null) return LatLng(lat, lng)
            try {
                val latFre = Double(this.getProperty("latitude"))
                val lngFre = Double(this.getProperty("longitude"))
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

fun LatLng(freObject: FREObject?): LatLng = FreCoordinateKotlin(freObject = freObject).value
fun LatLng(freObjectKotlin: FreObjectKotlin?): LatLng = FreCoordinateKotlin(freObjectKotlin = freObjectKotlin).value
`````

### Prerequisites

You will need

- Android Studio 3.0 Beta
- IntelliJ IDEA
- AIR 25+
