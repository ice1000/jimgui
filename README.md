# jimgui

Linux Build | Windows Build
:----------:|:-------------:
[![CCI][0]][1]|[![AV][2]][3]

  [0]: https://circleci.com/gh/ice1000/jimgui.svg?style=svg
  [1]: https://circleci.com/gh/ice1000/jimgui
  [2]: https://ci.appveyor.com/api/projects/status/le5v5lne7au0lnn2?svg=true
  [3]: https://ci.appveyor.com/project/ice1000/jimgui

Pure Java binding for [dear-imgui](https://github.com/ocornut/imgui), Kotlin is used as code generation tool.

This project is created for [DevKt](https://github.com/ice1000/dev-kt), a JVM-based lightweight IDE for experiment purpose.

# Demo

See [this java file](core/test/org/ice1000/jimgui/tests/Demo.java).

# Progress

+ [X] `ImGui` namespace function/javadoc generation
+ [X] `ImGuiIO` properties getter/setter/javadoc generation
+ [X] `ImGuiFont` properties getter/setter/javadoc generation
+ [X] `ImGui*Flags` copy-pasted constant/javadoc
+ [X] `ImStyleVar` keys using generic parameter as type constraint (type safe!)
+ [X] Linux native library with glfw3 + opengl3 implementation, hosted on CircleCI
+ [X] Windows7+ native library with directX11 implementation, hosted on AppVeyor
+ [ ] WindowsXP native library with directX9 implementation, hosted on ?
+ [ ] MacOS native library with ? implementation, hosted on ?

# Build

First you need to make sure you have these software installed:

+ For Linux
    + `cmake` version \> 3.5
    + `make`
    + `pkg-config`
    + `libglfw3-dev`
+ For Windows (\> 7)
    + `cmake` version \> 3.5
    + `ming32-make`
    + `d3d11`
    + `d3dx11`
    + `d3dcompiler`
    + `dxguid`

To compile a jar library, run:

```
$ bash gradlew assemble
```

To run tests, run:

```
$ bash gradlew test
```
