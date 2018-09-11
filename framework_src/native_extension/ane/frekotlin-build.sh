#!/bin/sh

#Get the path to the script and trim to get the directory.
echo "Setting path to current directory to:"
pathtome=$0
pathtome="${pathtome%/*}"

echo $pathtome

AIR_SDK="/Users/eoinlandy/SDKs/AIRSDK_31"

KOTLIN_VERSION="1.2.61"
PROJECTNAME=FreKotlinExampleANE
SWC_NAME=FreKotlinANE
ANE_NAME=com.tuarua.frekotlin

#Copy SWC into place.
echo "Copying SWC into place."
cp "$pathtome/../bin/$SWC_NAME.swc" "$pathtome/"

#Extract contents of SWC.
echo "Extracting files form SWC."
unzip "$pathtome/$SWC_NAME.swc" "library.swf" -d "$pathtome"


#Copy library.swf to folders.
echo "Copying library.swf into place."
cp "$pathtome/library.swf" "$pathtome/platforms/android"
cp "$pathtome/library.swf" "$pathtome/platforms/default_ane"

echo "Copying Android aars into place"
cp "$pathtome/../../native_library/android/$PROJECTNAME/FreKotlin/build/outputs/aar/FreKotlin-release.aar" "$pathtome/platforms/android/FreKotlin-release.aar"
echo "getting Android jars"
unzip "$pathtome/platforms/android/FreKotlin-release.aar" "classes.jar" -d "$pathtome/platforms/android"

#Run the build command.
echo "Building ANE."
"$AIR_SDK"/bin/adt -package \
-target ane "$pathtome/$ANE_NAME.ane" "$pathtome/extension_frekotlin.xml" \
-swc "$pathtome/$SWC_NAME.swc" \
-platform Android-ARM \
-C "$pathtome/platforms/android" "library.swf" "classes.jar" \
com.tuarua.$PROJECTNAME-res/. \
-platformoptions "$pathtome/platforms/android/platform_frekotlin.xml" \
"kotlin-stdlib-$KOTLIN_VERSION.jar" \
-platform Android-x86 \
-C "$pathtome/platforms/android" "library.swf" "classes.jar" \
com.tuarua.$PROJECTNAME-res/. \
-platformoptions "$pathtome/platforms/android/platform_frekotlin.xml" \
"kotlin-stdlib-$KOTLIN_VERSION.jar" \
-platform default -C "$pathtome/platforms/default_ane" "library.swf" \

if [ ! -d "$pathtome/../../libs" ]; then
mkdir "$pathtome/../../libs"
fi

mv "$pathtome/platforms/android/classes.jar" "$pathtome/../../libs/frekotlin-$KOTLIN_VERSION.jar"
rm "$pathtome/platforms/android/FreKotlin-release.aar"
rm "$pathtome/platforms/android/library.swf"
rm "$pathtome/$SWC_NAME.swc"
rm "$pathtome/library.swf"
