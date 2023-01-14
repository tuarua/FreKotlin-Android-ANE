#!/bin/sh

#Get the path to the script and trim to get the directory.
echo "Setting path to current directory to:"
pathtome=$0
pathtome="${pathtome%/*}"

AIR_SDK="/Users/eoinlandy/SDKs/AIRSDK_50.1.1"

KOTLIN_VERSION="1.7.21"
ANE_VERSION="1.72.1"
PROJECTNAME=FreKotlinExampleANE
SWC_NAME=FreKotlinANE
ANE_NAME=com.tuarua.frekotlin

#Copy SWC into place.
cp "$pathtome/../bin/$SWC_NAME.swc" "$pathtome/"

#Extract contents of SWC.
unzip "$pathtome/$SWC_NAME.swc" "library.swf" -d "$pathtome"

#Copy library.swf to folders.
cp "$pathtome/library.swf" "$pathtome/platforms/android"

# Copying Android aars into place
cp "$pathtome/../../../../FreKotlin/library/build/outputs/aar/library-release.aar" "$pathtome/platforms/android/FreKotlin-release.aar"
unzip "$pathtome/platforms/android/FreKotlin-release.aar" "classes.jar" -d "$pathtome/platforms/android"

#Run the build command.
echo "Building ANE."
"$AIR_SDK"/bin/adt -package \
-target ane "$pathtome/$ANE_NAME-$ANE_VERSION.ane" "$pathtome/extension_frekotlin.xml" \
-swc "$pathtome/$SWC_NAME.swc" \
-platform Android-x86 \
-C "$pathtome/platforms/android" "library.swf" "classes.jar" \
com.tuarua.$PROJECTNAME-res/. \
-platformoptions "$pathtome/platforms/android/platform_frekotlin.xml" \
"kotlin-stdlib-$KOTLIN_VERSION.jar" \
"kotlin-stdlib-common-$KOTLIN_VERSION.jar" \
-platform Android-ARM \
-C "$pathtome/platforms/android" "library.swf" "classes.jar" \
com.tuarua.$PROJECTNAME-res/. \
-platformoptions "$pathtome/platforms/android/platform_frekotlin.xml" \
"kotlin-stdlib-$KOTLIN_VERSION.jar" \
"kotlin-stdlib-common-$KOTLIN_VERSION.jar" \
-platform Android-ARM64 \
-C "$pathtome/platforms/android" "library.swf" "classes.jar" \
com.tuarua.$PROJECTNAME-res/. \
-platformoptions "$pathtome/platforms/android/platform_frekotlin.xml" \
"kotlin-stdlib-$KOTLIN_VERSION.jar" \
"kotlin-stdlib-common-$KOTLIN_VERSION.jar" \
-platform default -C "$pathtome/platforms/android" "library.swf" \

if [ ! -d "$pathtome/../../libs" ]; then
mkdir "$pathtome/../../libs"
fi

mv "$pathtome/platforms/android/classes.jar" "$pathtome/../../libs/frekotlin-$KOTLIN_VERSION.jar"
rm "$pathtome/platforms/android/FreKotlin-release.aar"
rm "$pathtome/platforms/android/library.swf"
rm "$pathtome/$SWC_NAME.swc"
rm "$pathtome/library.swf"

cp "$pathtome/$ANE_NAME-$ANE_VERSION.ane" "$pathtome/../../../starter_project/example/android_dependencies"
echo "DONE!"
