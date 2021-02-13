./gradlew clean
./gradlew packageDebugPlugin

adb push build/plugin-debug.zip /data/local/tmp/plugin-debug.zip
adb push build/plugin2-debug.zip /data/local/tmp/plugin2-debug.zip
adb push build/pluginCommon-debug.zip /data/local/tmp/pluginCommon-debug.zip