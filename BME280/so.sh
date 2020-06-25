gcc jni.h jni_md.h bme280.c BME280Jni.c -fPIC -shared -o a.so -lwiringPi

javac BME280Jni.java