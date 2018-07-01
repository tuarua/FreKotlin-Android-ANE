### 1.4.0
- Upgraded to Kotlin 1.2.50
- Upgraded to AIR 30
- Added iterator for FREArray i.e. for (fre: FREObject? in airArray) { }
- Added subscript setter# for FREObject i.e. myFreObject["name"] = myFREObject

### 1.3.0
- Upgraded to Kotlin 1.2.41
- Upgraded to AIR 29
- Extensions for LongArray
- Deprecate ArgCountException
- Deprecate ArgException
- Deprecate NullArgsException
- Added FreArgException
- Added FreConversionException
- Added generic catch for FREFunction calls

### 1.2.0
- Upgraded to Kotlin 1.2.30
- Upgraded to AIR 29

### 1.1.0
- Improved performance for conversions

### 1.0.0
- Added conversion from ByteArray to FreByteArrayKotlin
- Added ByteArray.toFREObject()
- Added Bitmap.toFREObject()
- Fix bug converting to Long
- Handle ARGB in toColor(hasAlpha: Boolean) method
- Upgraded to Kotlin 1.2.10
- Add Dokka docs

### 0.0.12
- Fix bug converting null String to Kotlin String
- Upgraded to Kotlin 1.1.51

### 0.0.11
- Added correct conversion from Bitmap to FreBitmapKotlin
- Minor corrections to FREArray
- Upgraded to Kotlin 1.1.50

### 0.0.10
- Extensions for conversion to ArrayList, Map
- Extensions for FREObject, FREArray

### 0.0.9  
- Extensions for conversion to IntArray, DoubleArray, BooleanArray

### 0.0.8  
- Extensions for conversion to String, Int, Boolean, Double, Float, Date, Rect, Point

### 0.0.7  
- Upgraded to Kotlin 1.1.4-3
- Improve FreKotlinController interface

### 0.0.6  
- Support conversion to/from Date, Long, Short
- Improve FreArrayKotlin

### 0.0.5  
- Improve FreException

### 0.0.4  
- Upgraded to Kotlin 1.1.4-2
- Change minimum SDK to 19 (KitKat 4.4)
- Added toColor method

### 0.0.3  
- Upgraded to Kotlin 1.1.4
