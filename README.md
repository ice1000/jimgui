# jimgui

[![Join the chat at https://gitter.im/imgui-java/community](https://badges.gitter.im/imgui-java/community.svg)](https://gitter.im/imgui-java/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Version][badge-img]][badge-link]
[![Bintray](https://img.shields.io/bintray/dt/ice1000/ice1000/jimgui)][badge-link]
[![Maven Central](https://img.shields.io/maven-central/v/org.ice1000.jimgui/core)][maven-link]

  [badge-img]: https://img.shields.io/bintray/v/ice1000/ice1000/jimgui.svg
  [badge-link]: https://bintray.com/ice1000/ice1000/jimgui
  [maven-link]: https://mvnrepository.com/artifact/org.ice1000.jimgui/core

Linux Build | Windows Build
:----------:|:-------------:
[![CCI][0]][1]|[![AV][2]][3]

  [0]: https://circleci.com/gh/ice1000/jimgui.svg?style=svg
  [1]: https://circleci.com/gh/ice1000/jimgui
  [2]: https://ci.appveyor.com/api/projects/status/le5v5lne7au0lnn2?svg=true
  [3]: https://ci.appveyor.com/project/ice1000/jimgui

To jcenter users: as the deprecation of jcenter, this library is now published to maven central since `v0.20`.
Please update your repository setting to reflect the change.

Cross-platform efficient pure Java binding for [dear-imgui](https://github.com/ocornut/imgui), Kotlin is used as code generation tool.

This binding is rather bare-metal, that reflects imgui's API directly. I think it's good enough, but you may expect some other styles of bindings.

There is a declarative wrapper of jimgui, namely [flui](https://github.com/newk5/flui) available.

## Features
Can be considered as both advantages and disadvantages.

### Java
It is Java-only with an optional Kotlin DSL wrapper.

### Pure
It hides everything about rendering behind-the-scene,
so you don't need to worry about GLFW, OpenGL or DirectX stuffs
(speaking of lwjgl or jogl integration -- see [#18], it's hard).

Also, it doesn't separate jars for different platforms. One jar works on all supported platforms.

### Usability
It is well-known that dear imgui doesn't have image loading out-of-the-box,
but this library has, and it even has a wrapper for [aiekick/ImGuiFileDialog]
and [Flix01/imguidatechooser] and some other minor widgets.

### Efficiency
This is twofolded.

+ JNI efficiency. It exploits [Critical Native] and it avoids accessing Java from C++.
  Only arrays and primitive types are passed from Java to C++,
  and only primitive types are returned.
+ Optimization for strings. That jimgui by default uses an inefficient way to convert `java.lang.String` into byte arrays that C++ is happy with.
  You can customize the string-to-bytes function yourself by using `org.ice1000.jimgui.util.JImGuiUtil.setStringToBytes`
  the default caching `JImGuiUtil.cacheStringToBytes()`, or use the more efficient alternative to `java.lang.String` --
  `org.ice1000.jimgui.JImStr`, which is supposed to be created as global constants.

### IDE-friendliness
It exploits [JetBrains annotations], particularly with `MagicConstant`,
`NotNull`, `Nullable` and `Contract`.

`MagicConstant` annotation enables IntelliJ IDEA to provide completion for `int flags` arguments
with only the flags needed:

![image](https://user-images.githubusercontent.com/16398479/102420233-9c558d80-403c-11eb-921c-9c68003fe923.png)

 [#18]: https://github.com/ice1000/jimgui/issues/18
 [JetBrains annotations]: https://www.jetbrains.com/help/idea/annotating-source-code.html

This project was created for a code editor and a game engine, both dead.

For macOS users, make sure you add `-XstartOnFirstThread` JVM argument when running applications built with jimgui.

# Demo

+ [Partial re-implementation of imgui_demo.cpp](core/test/org/ice1000/jimgui/tests/Demo.java).
+ [A sample text editor](core/test/org/ice1000/jimgui/tests/EditorTest.java)
+ [DrawList party](fun/src/dlparty/BigCollection.java), with a [YouTube video](https://youtu.be/ojgWnelaRfw)

# Contents

## Bindings to official features

+ [X] `ImGui` namespace getter/setter/function/javadoc generation
+ [X] `ImGuiFontAtlas`/`ImGuiStyle`/`ImGuiFont`/`ImGuiIO`/`ImGuiDrawList`
       properties getter/setter/function/javadoc generation
+ [X] `ImGui*Flags` constant/javadoc generation
+ [X] `ImStyleVar` keys using generic parameter as type constraint (type safe!)

## Extra convenience provided

+ [X] Functions to access and modify platform window size/pos
+ [X] Use `MagicConstant` annotation to specify where the constant parameters are from (IntelliJ IDEA understands this!)
  + [X] Generate functions with `MagicConstant` annotation
+ [X] [Critical Native] function generations
+ [X] A few extensions, including `emptyButton`, `dragVec4`, `sliderVec4`, `lineTo`, `circle`,
      `bufferingBar`, `dialogBox`, `spinner` (Android style!), `toggleButton` (iOS style!),
      mostly from the issues and the communities.
+ [X] Integration of [aiekick/ImGuiFileDialog] as `JImFileDialog`
+ [X] Integration of [Flix01/imguidatechooser] as `imgui.dateChooser`

 [aiekick/ImGuiFileDialog]: https://github.com/aiekick/ImGuiFileDialog
 [Flix01/imguidatechooser]: https://github.com/Flix01/imgui/tree/imgui_with_addons/addons/imguidatechooser
 [Critical Native]: https://stackoverflow.com/a/36309652/7083401

## C++ interoperability

+ [X] Native value pointer (`bool *`, `int *`, `float *`) wrappers, providing `accessValue` and `modifyValue`
+ [X] `ImVec4` wrapper with optional mutability
+ [X] `ImTextureID` wrapper with platform-dependent implementations
  + [X] `LPDIRECT3DTEXTURE9` on WindowsXP+
  + [X] `ID3D11ShaderResourceView*` on Windows7+
  + [X] `GLuint` on MacOS/Linux

## Backends and platforms

+ Linux native library with glfw3 + opengl3 implementation
  + [ ] 32-bit hosted on ?
  + [X] 64-bit amd64 hosted on CircleCI (Ubuntu 20.04)
  + [X] 64-bit aarch64, loongarch64 built by @Glavo
+ WindowsXP+ native library
  + with glfw + opengl3 implementation (no longer maintained)
    + [ ] 32-bit hosted on ?
    + [ ] 64-bit hosted on ?
  + with directX9 implementation
    + [X] 32-bit hosted on my laptop
    + [X] 64-bit hosted on my laptop
+ Windows7+ native library with directX11 implementation
  + [X] 32-bit hosted on my laptop
  + [X] 64-bit hosted on my laptop
+ MacOS native library with Cocoa, glut as additions to Linux implementation
  + [X] built by @imkiva (built by @newk5 's VM in the past)

# Usage

## Code example

```java
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.util.JniLoader;

public class Main {
  public static void main(String... args){
    JniLoader.load();
    try (JImGui imGui = new JImGui()) {
      // load fonts, global initializations, etc.
      while (!imGui.windowShouldClose()) {
        // some drawing-unrelated initializations
        // mostly do nothing here
        imGui.initNewFrame();
        // draw your widgets here, like this
        imGui.text("Hello, World!");
        imGui.render();
        // mostly do nothing here
      }
    }
  }
}
```

Kotlin DSL:

```kotlin
runPer(10) {
  "Window with Tabs" {
    tabBar("tab bar id") {
      tabItem("Tab One") { text("I am in Tab one!") }
      tabItem("Tab Two") { button("I am in Tab two!") }
      tabItem("Tab Three") { bulletText("I am in Tab three!") }
    }

    treeNode("PsiClassBody") {
      treeNode("PsiConstructor") {
        text("PsiIdentifier")
      }
      treeNode("PsiMethod") {
        text("PsiAnnotation")
        text("PsiLeafElement")
      }
    }
  }
}
```

## Using Unicode strings

You can use `ImGuiFontAtlas` to extend glyph ranges for your font, which is needed if you want to display Unicode characters.
You can find more info about glyph ranges at the [dear-imgui repository](https://github.com/ocornut/imgui).

Notice that to display Unicode characters you need to have your Java sources encoded and compiled as UTF-8. To compile the sources as UTF-8, add the following line to your `build.gradle`:

```groovy
compileJava.options.encoding = 'UTF-8'
```

## Gradle

```groovy
import org.apache.tools.ant.taskdefs.condition.Os
// ...
dependencies {
  String jimguiVersion = 'v0.20.3'
  implementation "org.ice1000.jimgui:core:$jimguiVersion" // basic functionality
  implementation "org.ice1000.jimgui:kotlin-dsl:$jimguiVersion" // kotlin dsl wrapper
}
// ...
tasks.withType(JavaExec).configureEach {
  if (Os.isFamily(Os.FAMILY_MAC)) jvmArgs "-XstartOnFirstThread"
}
```

## Gradle Kotlin DSL

```kotlin
import org.apache.tools.ant.taskdefs.condition.Os
dependencies {
  val jimguiVersion = "v0.20.3"
  implementation("org.ice1000.jimgui:core:$jimguiVersion") // basic functionality
  implementation("org.ice1000.jimgui:kotlin-dsl:$jimguiVersion") // kotlin dsl wrapper
}

tasks.withType<JavaExec>().configureEach {
  if (Os.isFamily(Os.FAMILY_MAC)) jvmArgs("-XstartOnFirstThread")
}
```

## Maven

```xml
<dependency>
  <groupId>org.ice1000.jimgui</groupId>
  <!-- basic functionality -->
  <artifactId>core</artifactId>
  <version>v0.20.3</version>
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
+ For macOS
  + Everything needed on Linux
  + Frameworks including `Cocoa`, `GLUT`, `OpenGL`
  + Run with JVM Argument: `-XstartOnFirstThread`
    (You can use `export _JAVA_OPTIONS='-XstartOnFirstThread'`)

To compile a jar library, run
(you need to use the developer command prompt for this on Windows):

```
$ ./gradlew jar
```

To run tests, run:

```
$ ./gradlew test
```
