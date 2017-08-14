# Android Kotlin ANE

Example Android Studio project showing how to create Air Native Extensions for Android using Kotlin.  
  
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
val airString: String = FreObjectKotlin(freObject = inFRE0).value as String
trace("String passed from AIR:", airString) //As3 style trace!

val kotlinString: String = "I am a string from Kotlin"
return FreObjectKotlin(kotlinString).rawValue.guard { return null }
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
import android.util.Log
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
        get() {
            var lat = 0.0
            var lng = 0.0
            try {
                val latFre = this.getProperty("latitude")?.value
                val lngFre = this.getProperty("longitude")?.value
                lat = (latFre as? Int)?.toDouble() ?: latFre as Double
                lng = (lngFre as? Int)?.toDouble() ?: lngFre as Double
            } catch (e: Exception) {
                Log.e(TAG, e.message)
            }
            return LatLng(lat, lng)
        }
}
`````

### Prerequisites

You will need

- Android Studio 3.0 Beta1
- IntelliJ IDEA
- AIR 25+
