@echo off
SET pathtome=%~dp0
SET SZIP="C:\Program Files\7-Zip\7z.exe"
SET AIR_PATH="D:\dev\sdks\AIR\AIRSDK_26\bin\"
SET ANDROID_STUDIO_REPO="D:\dev\Android-Studio-Preview\gradle\m2repository\"

SET KOTLIN_VERSION="1.1.4-3"
SET PROJECT_NAME=FreKotlinExampleANE
SET SWC_NAME=DummyANE
SET ANE_NAME=com.tuarua.frekotlin

IF NOT EXIST %pathtome%platforms\default_ane  %pathtome%platforms\default_ane
copy %pathtome%..\bin\%SWC_NAME%.swc %pathtome%

REM contents of SWC.
copy /Y %pathtome%%SWC_NAME%.swc %pathtome%%SWC_NAME%Extract.swc
ren %pathtome%%SWC_NAME%Extract.swc %SWC_NAME%Extract.zip
call %SZIP% e %pathtome%%SWC_NAME%Extract.zip -o%pathtome%
del %pathtome%%SWC_NAME%Extract.zip


REM copy library.swf to folders.
echo Copying library.swf into place.
copy %pathtome%library.swf %pathtome%platforms\android
copy %pathtome%library.swf %pathtome%platforms\default_ane 

echo copy the aar into place
copy /Y %pathtome%..\..\native_library\android\%PROJECT_NAME%\FreKotlin\build\outputs\aar\FreKotlin-release.aar %pathtome%platforms\android\FreKotlin-release.aar
copy /Y %ANDROID_STUDIO_REPO%org\jetbrains\kotlin\kotlin-stdlib\%KOTLIN_VERSION%\kotlin-stdlib-%KOTLIN_VERSION%.jar %pathtome%platforms\android\kotlin-stdlib-%KOTLIN_VERSION%.jar

echo "GETTING ANDROID JAR"
call %SZIP% x %pathtome%platforms\android\FreKotlin-release.aar -o%pathtome%platforms\android\ classes.jar

echo "GENERATING ANE"
call %AIR_PATH%adt.bat -package -target ane %pathtome%%ANE_NAME%.ane extension_frekotlin.xml ^
-swc %SWC_NAME%.swc ^
-platform Android-ARM ^
-C platforms/android library.swf classes.jar ^
-platformoptions platforms/android/platform_frekotlin.xml ^
kotlin-stdlib-%KOTLIN_VERSION%.jar ^
-platform Android-x86 ^
-C platforms/android library.swf classes.jar ^
-platformoptions platforms/android/platform_frekotlin.xml ^
kotlin-stdlib-%KOTLIN_VERSION%.jar ^
-platform default -C platforms/default_ane library.swf

ren %pathtome%platforms\\android\\classes.jar frekotlin-%KOTLIN_VERSION%.jar
copy /Y %pathtome%platforms\\android\\frekotlin-%KOTLIN_VERSION%.jar %pathtome%..\..\libs

del %pathtome%platforms\\android\\frekotlin-%KOTLIN_VERSION%.jar
del %pathtome%platforms\\android\\kotlin-stdlib-%KOTLIN_VERSION%.jar
del %pathtome%platforms\\android\\FreKotlin-release.aar
del %pathtome%platforms\\android\\library.swf

call DEL /F /Q /A %pathtome%library.swf
call DEL /F /Q /A %pathtome%catalog.xml
call DEL /F /Q /A %pathtome%%SWC_NAME%.swc

echo "DONE!"