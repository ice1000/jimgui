# jimgui

Hand-written pure Java binding for dear-imgui.

This project is created for [DevKt](https://github.com/ice1000/dev-kt), a JVM-based lightweight IDE for experiment purpose.

# Build

```
$ bash gradlew javah
$ cd jni
$ cmake .
$ make
$ mv *.so ..
$ cd ..
$ bash gradlew test
```
