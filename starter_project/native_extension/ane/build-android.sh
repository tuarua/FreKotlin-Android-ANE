#!/bin/sh

#Get the path to the script and trim to get the directory.
pathtome=$0
pathtome="${pathtome%/*}"
AIR_SDK="/Users/eoinlandy/SDKs/AIRSDK_33.1.1.259"

PROJECTNAME=HelloWorldANE

# Copy SWC into place.
cp "$pathtome/../bin/$PROJECTNAME.swc" "$pathtome/"

# Extract contents of SWC.
unzip "$pathtome/$PROJECTNAME.swc" "library.swf" -d "$pathtome"

# Copy library.swf to folders.
cp "$pathtome/library.swf" "$pathtome/platforms/android"

# Copying Android aars into place.
cp "$pathtome/../../native_library/android/$PROJECTNAME/app/build/outputs/aar/app-release.aar" "$pathtome/platforms/android/app-release.aar"

# Getting Android jars.
unzip "$pathtome/platforms/android/app-release.aar" "classes.jar" -d "$pathtome/platforms/android"
unzip "$pathtome/platforms/android/app-release.aar" "res/*" -d "$pathtome/platforms/android"
mv "$pathtome/platforms/android/res" "$pathtome/platforms/android/com.mycompany.$PROJECTNAME-res"

#Run the build command.
"$AIR_SDK"/bin/adt -package \
-target ane "$pathtome/$PROJECTNAME.ane" "$pathtome/extension_android.xml" \
-swc "$pathtome/$PROJECTNAME.swc" \
-platform Android-x86 \
-C "$pathtome/platforms/android" "library.swf" "classes.jar" \
com.mycompany.$PROJECTNAME-res/. \
-platformoptions "$pathtome/platforms/android/platform.xml" \
-platform Android-ARM \
-C "$pathtome/platforms/android" "library.swf" "classes.jar" \
com.mycompany.$PROJECTNAME-res/. \
-platformoptions "$pathtome/platforms/android/platform.xml" \
-platform Android-ARM64 \
-C "$pathtome/platforms/android" "library.swf" "classes.jar" \
com.mycompany.$PROJECTNAME-res/. \
-platformoptions "$pathtome/platforms/android/platform.xml" \
-platform default -C "$pathtome/platforms/android" "library.swf"

#Clean up.
rm "$pathtome/platforms/android/classes.jar"
rm "$pathtome/platforms/android/app-release.aar"
rm "$pathtome/platforms/android/library.swf"
rm "$pathtome/$PROJECTNAME.swc"
rm "$pathtome/library.swf"
rm -r "$pathtome/platforms/android/com.mycompany.$PROJECTNAME-res"

echo "DONE!"
