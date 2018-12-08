@echo off
SET pathtome=%~dp0
SET SZIP="C:\Program Files\7-Zip\7z.exe"
SET AIR_PATH="D:\dev\sdks\AIR\AIRSDK_31\bin\"

SET projectName=HelloWorldANE

REM Copy SWC into place.
copy %pathtome%..\bin\%projectName%.swc %pathtome%

REM Extract contents of SWC.
ren %pathtome%%projectName%Extract.swc %projectName%Extract.zip
call %SZIP% e %pathtome%%projectName%Extract.zip -o%pathtome%
del %pathtome%%projectName%Extract.zip

REM Copy library.swf to folders.
copy %pathtome%library.swf %pathtome%platforms\android

REM Copying Android aars into place
copy /Y %pathtome%..\..\native_library\android\%projectName%\app\build\outputs\aar\app-release.aar %pathtome%platforms\android\app-release.aar


REM Getting Android jars
call %SZIP% x %pathtome%platforms\android\app-release.aar -o%pathtome%platforms\android\ classes.jar
call %SZIP% x %pathtome%platforms\android\app-release.aar -o%pathtome%platforms\android\ res
ren %pathtome%platforms\android\res com.mycompany.%projectName%-res

REM Building ANE.
call %AIR_PATH%adt.bat -package -target ane %pathtome%%projectName%.ane extension_android.xml ^
-swc %projectName%.swc ^
-platform Android-ARM ^
-C platforms/android library.swf classes.jar ^
com.mycompany.%projectName%-res/. ^
-platformoptions platforms/android/platform.xml ^
-platform Android-x86 ^
-C platforms/android library.swf classes.jar ^
com.mycompany.%projectName%-res/. ^
-platformoptions platforms/android/platform.xml

REM Clean up.
del %pathtome%platforms\\android\\classes.jar
del %pathtome%platforms\\android\\app-release.aar
del %pathtome%platforms\\android\\library.swf
call DEL /F /Q /A %pathtome%library.swf
call DEL /F /Q /A %pathtome%catalog.xml
call DEL /F /Q /A %pathtome%%projectName%.swc
call rmdir /Q /S %pathtome%platforms\android\com.mycompany.%projectName%-res

echo "DONE!"
