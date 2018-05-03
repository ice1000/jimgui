# jimgui

[![CircleCI](https://circleci.com/gh/ice1000/jimgui.svg?style=svg)](https://circleci.com/gh/ice1000/jimgui)

Pure Java binding for dear-imgui, Kotlin is used as code generation tool.

This project is created for [DevKt](https://github.com/ice1000/dev-kt), a JVM-based lightweight IDE for experiment purpose.

# Build

To compile a jar library, run:

```
$ bash gradlew genBindings javah
$ cmake jni
$ make
$ mkdir -p res/native
$ mv *.so res/native
$ bash gradlew jar
```

To run tests, run:

```
$ bash gradlew genBindings javah
$ cd jni
$ mkdir -p cmake-build-debug
$ cd cmake-build-debug
$ cmake ..
$ make
$ cd ../..
$ bash gradlew test
```
