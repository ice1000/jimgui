# jimgui

[![CircleCI](https://circleci.com/gh/ice1000/jimgui.svg?style=svg)](https://circleci.com/gh/ice1000/jimgui)
[![Build status](https://ci.appveyor.com/api/projects/status/9dn7mora07srvvis?svg=true)](https://ci.appveyor.com/project/ice1000/jimgui)

Pure Java binding for dear-imgui, Kotlin is used as code generation tool.

This project is created for [DevKt](https://github.com/ice1000/dev-kt), a JVM-based lightweight IDE for experiment purpose.

# Demo

See [this java file](test/org/ice1000/jimgui/tests/Demo.java).

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
