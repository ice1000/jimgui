# jimgui

[![CircleCI](https://circleci.com/gh/ice1000/jimgui.svg?style=svg)](https://circleci.com/gh/ice1000/jimgui)
[![Build status](https://ci.appveyor.com/api/projects/status/9dn7mora07srvvis?svg=true)](https://ci.appveyor.com/project/ice1000/jimgui)

Pure Java binding for [dear-imgui](https://github.com/ocornut/imgui), Kotlin is used as code generation tool.

This project is created for [DevKt](https://github.com/ice1000/dev-kt), a JVM-based lightweight IDE for experiment purpose.

# Demo

See [this java file](core/test/org/ice1000/jimgui/tests/Demo.java).

# Build

First you need to make sure you have these software installed:

+ `cmake`
+ `make`
+ `pkg-config`
+ `libglfw3-dev`

To compile a jar library, run:

```
$ bash gradlew assemble
```

To run tests, run:

```
$ bash gradlew test
```
