## Unreleased

+ Fix Linux build
+ Add `JniLoader.loadDX9` and `JniLoader.loadDX11`
+ Adapt latest imgui and FD changes
+ Fix DX11 rendering CPU usage issue

## v0.14.0

+ Render with DX11 on Windows7, 8, 10 now.
+ Deprecated `JImGui.initBeforeMainLoop` -- you no longer need to call it.
+ \[Internal] `jimgui[32].dll` is now split into `jimgui[32].dll` and `jimgui[32]-dx9.dll`
+ Fix the constructor of `JImFontConfig`
+ Suspend the development of the `extension` subproject

## v0.13.1

+ Enable `USE_IMGUI_TABLES` and `IMGUI_DISABLE_OBSOLETE_FUNCTIONS` back
+ Add bookmarks and extension-info support from file dialog
+ Add `JImGuiUtil.cacheStringToBytes()` from #29

## v0.13.0

+ Implement and test `NativeString`
+ Generate `inputText`, `inputTextMultiline` and `inputTextWithHint`!
+ Delete deprecated functions for platform windows
+ Improve `plotLines` and `plotHistogram`
+ Implement simple support for `JImFileDialog` based on https://github.com/aiekick/ImGuiFileDialog
  + Icons are not yet working -- figuring out why

## v0.12.0

+ Link CoreFoundation on macOS
+ Tweak code style, use 2-spaces instead of tabs
+ New functions: `pushFont`
+ Adapt official TableFlags changes
+ Allow deallocating native types multiple times
+ Generate overloads for `JImStr`
+ Download imgui_std for `NativeString` in the future

## v0.11.3

+ Fixed completely broken `NativeDouble`, `NativeShort` and `NativeLong`
+ Remove `setPlatformWindowSize[XY]` and `setPlatformWindowPos[XY]`,
  use the two-arg version `setPlatformWindowSize` and `setPlatformWindowPos` instead
+ Fix a bug in Windows' `setPlatformWindowSize`
+ Sync to the latest imgui repo
  + Generate flags for tables
  + Add most tables API

## v0.11.2

+ Added `imgui.toggleButton`
+ Added `imgui.text(JImStr)`
+ Fixed a serious problem with nullable `pOpen`s
+ Fix a lot of problems with my own extensions

## v0.11.1

Note that this is a pre-release created just for fun.

+ Few more variations of `imGui.begin`
+ A new subproject `fun`

## v0.11

(thanks very much again to @newk5)

+ Kotlin 1.4.20
+ Now the flags classes are generated, and updates on flags are addressed
+ Added a tabButton API
+ Addressed the SliderFlags change, added Popup flags
+ Removed TextEdit flags because they are actually just InputText flags
+ Fixed sliderInt not working issue
+ Some `Nothing` are renamed to `None`
+ `MouseIndexes` are renamed to `MouseButton`

## v0.10

+ Kotlin 1.4.10
+ Gradle and gradle plugins upgrade
+ Many polishing towards the library
+ Minor new APIs added
+ The library now works on Ubuntu 16.04 (thanks to @newk5)

## v0.9

+ Kotlin 1.3.40
+ Update flags API (by @Mr00Anderson)

## v0.8

+ `JImStr`
+ Tab API, and more from most recent imgui

## v0.7

+ Upgrade Windows build to Visual Studio 2019
+ Make native build gradle tasks incremental
+ Added accessors to platform window size/pos
+ Added new color styles
+ Support `Ctrl` which works on Windows
+ Added my custom extensions (shape widgets, bufferingBar, spinner, dragVec4, sliderVec4)
+ Customizable highlight

## v0.6 (accidentally deleted)

+ Added `imGui.initBeforeMainLoop`
+ Updated enums and flags
+ Used a more complete list of keys
+ Migrated to my fork of imgui's newest release

## v0.5

+ Added `org.ice1000.jimgui.util.JniLoaderEx`, allowing Windows to use the glfw implementation
+ Added `JImTexture.fromBytes` for in-memory image loading
+ Added factory for `JImGui` which takes a `long` as the native object pointer
  + This requires you to manually manage the release and initialization of GLFW/DirectX9
+ Renamed some factories of `JImTexture`
+ Added `windowDrawListAddLine`, `windowDrawListAddImage`
+ Added `inputText` with its flags class
+ Added support for co-working with other standalone GLFW applications (currently not working on Windows)
+ Added `org.ice1000.jimgui.glfw.GlfwUtil`

## v0.4

+ Migrated to the newest imgui code structure
+ Internal refactoring about `JImStyle`
+ Removed `showDemoWindow` and `showUserGuide`
+ Added `ImFontConfig` bindings
+ More constructors for `JImGui`, added constructor for `JImFontAtlas`

## v0.3

+ Replaced windows dx11 implementation with dx9
+ Added `ImTextureID` binding for Windows
+ Fixed countless bugs of function name mangling

## v0.2

+ Added `ImTextureID` binding for Linux/MacOS
+ Added `NativeDouble`, `NativeShort` , `NativeLong`
+ Added Critical Native JNI function generation
+ Made some non-static functions static
+ Bug fixes

## v0.1

+ Basic functionality
