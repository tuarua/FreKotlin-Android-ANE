#!/bin/sh

#Get the path to the script and trim to get the directory.
echo "Setting path to current directory to:"
pathtome=$0
pathtome="${pathtome%/*}"
AIR_SDK="/Users/eoinlandy/SDKs/AIRSDK_31"

sh ./frekotlin-build.sh

PROJECTNAME=FreKotlinExampleANE
ANENAME=com.tuarua.frekotlin.example

#Copy SWC into place.
echo "Copying SWC into place."
cp "$pathtome/../bin/$PROJECTNAME.swc" "$pathtome/"

#Extract contents of SWC.
echo "Extracting files form SWC."
unzip "$pathtome/$PROJECTNAME.swc" "library.swf" -d "$pathtome"

#Copy library.swf to folders.
echo "Copying library.swf into place."
cp "$pathtome/library.swf" "$pathtome/platforms/android"

echo "Copying Android aars into place"
cp "$pathtome/../../native_library/android/$PROJECTNAME/app/build/outputs/aar/app-release.aar" "$pathtome/platforms/android/app-release.aar"
echo "getting Android jars"
unzip "$pathtome/platforms/android/app-release.aar" "classes.jar" -d "$pathtome/platforms/android"


#Run the build command.
echo "Building ANE."
"$AIR_SDK"/bin/adt -package \
-target ane "$pathtome/$ANENAME.ane" "$pathtome/extension.xml" \
-swc "$pathtome/$PROJECTNAME.swc" \
-platform Android-ARM \
-C "$pathtome/platforms/android" "library.swf" "classes.jar" \
com.tuarua.$PROJECTNAME-res/. \
-platformoptions "$pathtome/platforms/android/platform.xml" \

rm "$pathtome/platforms/android/classes.jar"
rm "$pathtome/platforms/android/app-release.aar"
rm "$pathtome/platforms/android/library.swf"
rm "$pathtome/$PROJECTNAME.swc"
rm "$pathtome/library.swf"
echo "DONE!"
