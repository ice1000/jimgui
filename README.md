# jimgui

[ ![Download](https://api.bintray.com/packages/ice1000/ice1000/jimgui/images/download.svg?version=v0.1) ](https://bintray.com/ice1000/ice1000/jimgui/v0.1/link)

Linux Build | Windows Build
:----------:|:-------------:
[![CCI][0]][1]|[![AV][2]][3]

  [0]: https://circleci.com/gh/ice1000/jimgui.svg?style=svg
  [1]: https://circleci.com/gh/ice1000/jimgui
  [2]: https://ci.appveyor.com/api/projects/status/le5v5lne7au0lnn2?svg=true
  [3]: https://ci.appveyor.com/project/ice1000/jimgui

Cross-platform pure Java binding for [dear-imgui](https://github.com/ocornut/imgui), Kotlin is used as code generation tool.

This project is created for [DevKt](https://github.com/ice1000/dev-kt), a JVM-based lightweight IDE for experiment purpose.

# Demo

See [this java file](core/test/org/ice1000/jimgui/tests/Demo.java).

# Progress

+ [X] `ImGui` namespace function/javadoc generation
+ [X] `ImGuiIO` properties getter/setter/javadoc generation
+ [X] `ImGuiFont` properties getter/setter/javadoc generation
+ [X] `ImGuiFontAtlas` properties getter/setter/javadoc generation
+ [X] `ImGui*Flags` copy-pasted constant/javadoc
+ [X] `ImStyleVar` keys using generic parameter as type constraint (type safe!)
+ [X] Use `MagicConstant` annotation to specify where the constant parameters are from (IntelliJ IDEA understands this!)
  + [X] Generate functions with `MagicConstant` annotation
+ [X] Native value pointer (`bool *`, `int *`, `float *`) wrappers, providing `accessValue` and `modifyValue`
+ [X] `ImVec4` wrapper with optional mutability
+ [ ] `ImTextureID` wrapper (with `ID3D11ShaderResourceView *` on Windows7+ and `GLuint` on Linux)
+ Linux native library with glfw3 + opengl3 implementation
  + [ ] 32-bit hosted on ?
  + [X] 64-bit hosted on CircleCI
+ Windows7+ native library with directX11 implementation
  + [X] 32-bit hosted on AppVeyor
  + [X] 64-bit hosted on AppVeyor
+ [ ] WindowsXP native library with directX9 implementation, hosted on ?
+ MacOS native library with Cocoa, glut as additional to Linux implementation
  + [X] hosted on @zxj5470 's Mac laptop

# Usage

Gradle:

```groovy
repositories { jcenter() }
dependencies {
  String jimgui_version = "v0.1"
  compile "org.ice1000.jimgui:core:$jimgui_version" // basic functionality
  compile "org.ice1000.jimgui:kotlin-dsl:$jimgui_version" // kotlin dsl wrapper
}
```

If it doesn't work, replace `jcenter()` with `maven { url 'https://dl.bintray.com/ice1000/ice1000' }`.

# Build

First you need to make sure you have these software installed:

+ For Linux
	+ `cmake` version \> 3.5
	+ `make`
	+ `pkg-config`
	+ `libglfw3-dev`
+ For Windows (\> 7)
	+ `cmake` version \> 3.5
	+ Visual Studio 2017 with `msbuild`
	+ `d3d11`
	+ `d3dcompiler`
+ For Mac OS X
	+ Everything needed on Linux is needed on MacOS
	+ `Cocoa`
	+ `GLUT`
	+ `OpenGL`
	+ Run with JVM Argument: `-XstartOnFirstThread`
		+ You can use `export _JAVA_OPTIONS='-XstartOnFirstThread'`.

To compile a jar library, run:

```
$ ./gradlew assemble
```

To run tests, run:

```
$ ./gradlew test
```
