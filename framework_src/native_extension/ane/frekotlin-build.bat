@echo off
SET pathtome=%~dp0
SET SZIP="C:\Program Files\7-Zip\7z.exe"
SET AIR_PATH="D:\dev\sdks\AIR\AIRSDK_33\bin\"

SET KOTLIN_VERSION="1.3.40"
SET ANE_VERSION="1.8.0"
SET PROJECT_NAME=FreKotlinExampleANE
SET SWC_NAME=FreKotlinANE
SET ANE_NAME=com.tuarua.frekotlin

copy %pathtome%..\bin\%SWC_NAME%.swc %pathtome%

REM contents of SWC.
copy /Y %pathtome%%SWC_NAME%.swc %pathtome%%SWC_NAME%Extract.swc
ren %pathtome%%SWC_NAME%Extract.swc %SWC_NAME%Extract.zip
call %SZIP% e %pathtome%%SWC_NAME%Extract.zip -o%pathtome%
del %pathtome%%SWC_NAME%Extract.zip


REM copy library.swf to folders.
copy %pathtome%library.swf %pathtome%platforms\android
copy /Y %pathtome%..\..\native_library\android\%PROJECT_NAME%\FreKotlin\build\outputs\aar\FreKotlin-release.aar %pathtome%platforms\android\FreKotlin-release.aar

call %SZIP% x %pathtome%platforms\android\FreKotlin-release.aar -o%pathtome%platforms\android\ classes.jar

echo "GENERATING ANE"
call %AIR_PATH%adt.bat -package -target ane %pathtome%%ANE_NAME%-%ANE_VERSION%.ane extension_frekotlin.xml ^
-swc %SWC_NAME%.swc ^
-platform Android-x86 ^
-C platforms/android library.swf classes.jar ^
-platformoptions platforms/android/platform_frekotlin.xml ^
kotlin-stdlib-%KOTLIN_VERSION%.jar ^
kotlin-stdlib-common-%KOTLIN_VERSION%.jar ^
-platform Android-ARM ^
-C platforms/android library.swf classes.jar ^
-platformoptions platforms/android/platform_frekotlin.xml ^
kotlin-stdlib-%KOTLIN_VERSION%.jar ^
kotlin-stdlib-common-%KOTLIN_VERSION%.jar ^
-platform Android-ARM64 ^
-C platforms/android library.swf classes.jar ^
-platformoptions platforms/android/platform_frekotlin.xml ^
kotlin-stdlib-%KOTLIN_VERSION%.jar ^
kotlin-stdlib-common-%KOTLIN_VERSION%.jar ^
-platform default -C platforms/android library.swf

ren %pathtome%platforms\\android\\classes.jar frekotlin-%KOTLIN_VERSION%.jar
copy /Y %pathtome%platforms\\android\\frekotlin-%KOTLIN_VERSION%.jar %pathtome%..\..\libs

del %pathtome%platforms\\android\\frekotlin-%KOTLIN_VERSION%.jar
del %pathtome%platforms\\android\\FreKotlin-release.aar
del %pathtome%platforms\\android\\library.swf

call DEL /F /Q /A %pathtome%library.swf
call DEL /F /Q /A %pathtome%catalog.xml
call DEL /F /Q /A %pathtome%%SWC_NAME%.swc

copy %pathtome%%ANE_NAME%-%ANE_VERSION%.ane %pathtome%..\..\..\starter_project\example\android_dependencies

echo "DONE!"
