# Android Kotlin ANE

Example Android Studio project showing how to create Air Native Extensions for iOS using Kotlin.  
  
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

### Prerequisites

You will need

- Android Studio 3.0 Canary
- IntelliJ IDEA
- AIR 25+