# jimgui

[![Download][badge-img]][badge-link]
 
  [badge-img]: https://img.shields.io/bintray/v/ice1000/ice1000/jimgui.svg
  [badge-link]: https://bintray.com/ice1000/ice1000/jimgui

Linux Build | Windows Build
:----------:|:-------------:
[![CCI][0]][1]|[![AV][2]][3]

  [0]: https://circleci.com/gh/ice1000/jimgui.svg?style=svg
  [1]: https://circleci.com/gh/ice1000/jimgui
  [2]: https://ci.appveyor.com/api/projects/status/le5v5lne7au0lnn2?svg=true
  [3]: https://ci.appveyor.com/project/ice1000/jimgui

Cross-platform pure Java binding for [dear-imgui](https://github.com/ocornut/imgui), Kotlin is used as code generation tool.

This project is created for a code editor which is another project currently not open-sourced.

# Demo

+ [Partly re-implementation of imgui_demo.cpp](core/test/org/ice1000/jimgui/tests/Demo.java).
+ [A sample text editor](core/test/org/ice1000/jimgui/tests/EditorTest.java)

# Progress

+ [X] `ImGui` namespace getter/setter/function/javadoc generation
+ [X] `ImGuiIO` properties getter/setter/function/javadoc generation
+ [X] `ImGuiFont` properties getter/setter/function/javadoc generation
+ [X] `ImGuiStyle` properties getter/setter/function/javadoc generation
+ [X] `ImGuiFontAtlas` properties getter/setter/function/javadoc generation
+ [X] `ImGui*Flags` copy-pasted constant/javadoc
+ [X] `ImStyleVar` keys using generic parameter as type constraint (type safe!)
+ [X] Use `MagicConstant` annotation to specify where the constant parameters are from (IntelliJ IDEA understands this!)
  + [X] Generate functions with `MagicConstant` annotation
+ [X] Native value pointer (`bool *`, `int *`, `float *`) wrappers, providing `accessValue` and `modifyValue`
+ [X] `ImVec4` wrapper with optional mutability
+ [X] [Critical Native](https://stackoverflow.com/a/36309652/7083401) function generations
+ [X] `ImTextureID` wrapper with platform-dependent implementations
  + [X] `LPDIRECT3DTEXTURE9` on WindowsXP+
  + [X] `GLuint` on MacOS/Linux
+ [ ] Linux native library with glfw3 + opengl3 implementation
  + [ ] 32-bit hosted on ?
  + [X] 64-bit hosted on CircleCI
+ [X] WindowsXP+ native library
  + [X] with glfw + opengl3 implementation
    + [ ] 32-bit hosted on ?
    + [X] 64-bit hosted on my laptop
  + [X] with directX9 implementation
    + [X] 32-bit hosted on AppVeyor
    + [X] 64-bit hosted on AppVeyor
+ [X] MacOS native library with Cocoa, glut as additions to Linux implementation
  + [X] hosted on @zxj5470 's Mac laptop

# Usage

Remember to add jcenter to your repositories.

## Gradle

```groovy
dependencies {
  String jimgui_version = 'v0.4'
  compile "org.ice1000.jimgui:core:$jimgui_version" // basic functionality
  compile "org.ice1000.jimgui:kotlin-dsl:$jimgui_version" // kotlin dsl wrapper
}
```

## Gradle Kotlin DSL

```scala
dependencies {
  val jimguiVersion = "v0.4"
  compile("org.ice1000.jimgui:core:$jimguiVersion") // basic functionality
  compile("org.ice1000.jimgui:kotlin-dsl:$jimguiVersion") // kotlin dsl wrapper
}
```

## Maven

```xml
<dependency>
  <groupId>org.ice1000.jimgui</groupId>
  <!-- basic functionality -->
  <artifactId>core</artifactId>
  <version>v0.4</version>
  <type>pom</type>
</dependency>
```

# Build

First you need to make sure you have `cmake` newer than 3.5 and the following software installed:

+ For Linux
	+ `make`
	+ `pkg-config`
	+ `libglfw3-dev`
+ For Windows (\>= XP)
	+ Visual Studio 2017 with `msbuild`
	+ DirectX 9 Libraries (should be pre-installed on Windows or with Visual Studio)
	+ DirectX SDK
+ For Mac OS X
	+ Everything needed on Linux
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
