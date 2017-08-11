@echo off
SET pathtome=%~dp0
SET SZIP="C:\Program Files\7-Zip\7z.exe"
SET AIR_PATH="D:\dev\sdks\AIR\AIRSDK_26\bin\"
SET projectName=FreKotlinExampleANE
SET swcName=frekotlin
IF NOT EXIST %pathtome%platforms\default  %pathtome%platforms\default
copy %pathtome%..\bin\%swcName%.swc %pathtome%

REM contents of SWC.
copy /Y %pathtome%%swcName%.swc %pathtome%%swcName%Extract.swc
ren %pathtome%%swcName%Extract.swc %swcName%Extract.zip
call %SZIP% e %pathtome%%swcName%Extract.zip -o%pathtome%
del %pathtome%%swcName%Extract.zip


Copy library.swf to folders.
echo Copying library.swf into place.
copy %pathtome%library.swf %pathtome%platforms\android
copy %pathtome%library.swf %pathtome%platforms\default

echo copy the aar into place
copy /Y %pathtome%..\..\native_library\android\%projectName%\FreKotlin\build\outputs\aar\FreKotlin-release.aar %pathtome%platforms\android\FreKotlin-release.aar
copy /Y %pathtome%..\..\native_library\android\%projectName%\FreKotlin\libs\kotlin-stdlib-1.1.3-2.jar %pathtome%platforms\android\kotlin-stdlib-1.1.3-2.jar


echo "GETTING ANDROID JAR"
call %SZIP% x %pathtome%platforms\android\FreKotlin-release.aar -o%pathtome%platforms\android\ classes.jar


echo "GENERATING ANE"
call %AIR_PATH%adt.bat -package -target ane %pathtome%%swcName%.ane extension_android.xml ^
-swc %swcName%.swc ^
-platform Android-ARM ^
-C platforms/android library.swf classes.jar ^
-platformoptions platforms/android/platform.xml ^
kotlin-stdlib-1.1.3-2.jar ^
-platform Android-x86 ^
-C platforms/android library.swf classes.jar ^
-platformoptions platforms/android/platform.xml ^
kotlin-stdlib-1.1.3-2.jar ^
-platform default -C platforms/default library.swf

ren %pathtome%platforms\\android\\classes.jar frekotlin-1.1.3-2.jar
copy /Y %pathtome%platforms\\android\\frekotlin-1.1.3-2.jar %pathtome%..\..\libs

del %pathtome%platforms\\android\\frekotlin-1.1.3-2.jar
del %pathtome%platforms\\android\\kotlin-stdlib-1.1.3-2.jar
del %pathtome%platforms\\android\\FreKotlin-release.aar
del %pathtome%platforms\\android\\library.swf

call DEL /F /Q /A %pathtome%library.swf
call DEL /F /Q /A %pathtome%catalog.xml
call DEL /F /Q /A %pathtome%%swcName%.swc

echo "DONE!"