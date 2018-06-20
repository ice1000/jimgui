## Unreleased

+ Added `JImTexture.fromBytes` for in-memory image loading
+ Added constructor for `JImGui` which takes a `long` as the native object pointer
  + This requires you to manually manage the release and initialization of GLFW
+ Renamed some factories of `JImTexture`
+ Added `windowDrawListAddLine`, `windowDrawListAddImage`
+ Added `inputText` with its flags class

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
