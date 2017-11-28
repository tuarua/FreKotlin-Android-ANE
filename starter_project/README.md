# Android Kotlin ANE

Example Android Studio project showing how to create Air Native Extensions for Android using Kotlin.  



-------------
  
https://kotlinlang.org/docs/reference/

HelloWorldANE.java is the entry point of the ANE. It acts as a thin layered API to your Kotlin controller.  
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


### Prerequisites

You will need

- Android Studio 3.0
- IntelliJ IDEA
- AIR 27+
