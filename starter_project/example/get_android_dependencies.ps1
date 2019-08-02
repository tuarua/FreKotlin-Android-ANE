$currentDir = (Get-Item -Path ".\" -Verbose).FullName
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
Invoke-WebRequest -OutFile "$currentDir\android_dependencies\com.tuarua.frekotlin-1.8.0.ane" -Uri https://github.com/tuarua/Android-ANE-Dependencies/blob/master/anes/kotlin/com.tuarua.frekotlin-1.8.0.ane?raw=true
