# jimgui

[![Join the chat at https://gitter.im/imgui-java/community](https://badges.gitter.im/imgui-java/community.svg)](https://gitter.im/imgui-java/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Version][badge-img]][badge-link]

  [badge-img]: https://img.shields.io/bintray/v/ice1000/ice1000/jimgui.svg
  [badge-link]: https://bintray.com/ice1000/ice1000/jimgui

Linux Build | Windows Build
:----------:|:-------------:
[![CCI][0]][1]|[![AV][2]][3]

  [0]: https://circleci.com/gh/ice1000/jimgui.svg?style=svg
  [1]: https://circleci.com/gh/ice1000/jimgui
  [2]: https://ci.appveyor.com/api/projects/status/le5v5lne7au0lnn2?svg=true
  [3]: https://ci.appveyor.com/project/ice1000/jimgui

Cross-platform efficient pure Java binding for [dear-imgui](https://github.com/ocornut/imgui), Kotlin is used as code generation tool.

This project is created for a code editor and a game engine, both not open-sourced currently.
You can find jimgui on [Maven Repositories](https://mvnrepository.com/artifact/org.ice1000.jimgui).

For macOS users, make sure you add `-XstartOnFirstThread` JVM argument when running applications built with jimgui.
Note that jimgui uses a not-very-efficient way to convert `java.lang.String` into byte arrays that C++ is happy with.
You can customize the string-to-bytes function yourself by using `org.ice1000.jimgui.util.JImGuiUtil.setStringToBytes`, or use the more efficient alternative to `java.lang.String` -- `org.ice1000.jimgui.JImStr`, which is supposed to be created as global constants.

# Demo

+ [Partly re-implementation of imgui_demo.cpp](core/test/org/ice1000/jimgui/tests/Demo.java).
+ [A sample text editor](core/test/org/ice1000/jimgui/tests/EditorTest.java)

# Progress

+ [X] `ImGui` namespace getter/setter/function/javadoc generation
+ [X] `ImGuiFontAtlas`/`ImGuiStyle`/`ImGuiFont`/`ImGuiIO`/`ImGuiDrawList`
       properties getter/setter/function/javadoc generation
+ [X] `ImGui*Flags` copy-pasted constant/javadoc
+ [X] `ImStyleVar` keys using generic parameter as type constraint (type safe!)
+ [X] Functions to access and modify platform window size/pos
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
    + [ ] 64-bit hosted on ?
  + [X] with directX9 implementation
    + [X] 32-bit hosted on my laptop
    + [X] 64-bit hosted on my laptop
+ [X] MacOS native library with Cocoa, glut as additions to Linux implementation
  + [X] hosted on @newk5 's VM

# Usage

Remember to add jcenter to your repositories.

## Code example

```java
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.util.JniLoader;

public class Main {
	public static void main(String... args){
		JniLoader.load();
		try (JImGui imGui = new JImGui()) {
			// load fonts, global initializations, etc.
			imGui.initBeforeMainLoop();
			while (!imGui.windowShouldClose()) {
				// some drawing-unrelated initializations
				// mostly do nothing here
				imGui.initNewFrame();
				// draw your widgets here, like this
				imGui.text("Hello, World!");
				imGui.render();
			}
		}
	}
}
```

## Using Unicode strings

You can use `ImGuiFontAtlas` in order to extend glyph ranges for your font, which is needed if you want to display Unicode characters.
You can find more info about glyph ranges at the [dear-imgui repository](https://github.com/ocornut/imgui).

Notice that in order to display Unicode characters you need to have your Java sources encoded and compiled as UTF-8. To compile the sources as UTF-8, add the following line to your `build.gradle`:
```java
compileJava.options.encoding = 'UTF-8'
```

## Gradle

```groovy
import org.gradle.internal.os.OperatingSystem
plugins {
  // needed for configuring `run`
  id 'application'
}
// ...
repositories {
  // ...
  jcenter()
}
// ...
dependencies {
  String jimguiVersion = 'v0.11'
  implementation "org.ice1000.jimgui:core:$jimguiVersion" // basic functionality
  implementation "org.ice1000.jimgui:kotlin-dsl:$jimguiVersion" // kotlin dsl wrapper
}
// ...
run {
  if (OperatingSystem.current() == OperatingSystem.MAC_OS) {
    jvmArgs "-XstartOnFirstThread"
  }
}
```

## Gradle Kotlin DSL

```scala
dependencies {
  val jimguiVersion = "v0.11"
  implementation("org.ice1000.jimgui:core:$jimguiVersion") // basic functionality
  implementation("org.ice1000.jimgui:kotlin-dsl:$jimguiVersion") // kotlin dsl wrapper
}
```

## Maven

```xml
<repositories>
  <repository>
    <id>jcenter</id>
    <url>https://jcenter.bintray.com</url>
  </repository>
</repositories>

<dependency>
  <groupId>org.ice1000.jimgui</groupId>
  <!-- basic functionality -->
  <artifactId>core</artifactId>
  <version>v0.11</version>
  <type>pom</type>
</dependency>
```

# Build

First you need to make sure you have `cmake` newer than 3.14 and the following software installed:

+ For Linux
	+ `make`
	+ `pkg-config`
	+ `libglfw3-dev`
+ For Windows (\>= XP)
	+ Visual Studio 2019 with `msbuild` (needs to be on PATH)
	+ DirectX 9 Libraries (should be pre-installed on Windows or with Visual Studio)
	+ [DirectX SDK](https://www.microsoft.com/en-us/download/details.aspx?id=6812)
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
