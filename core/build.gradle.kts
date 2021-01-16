@file:Suppress("PropertyName")

import de.undercouch.gradle.tasks.download.Download
import org.ice1000.gradle.*
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil.escapeStringCharacters

plugins {
  java
  `java-library`
  id("de.undercouch.download")
}

val jni = projectDir.resolve("jni").absoluteFile
val imguiDir = jni.resolve("imgui")
val fdDir = jni.resolve("fd")
val implDir = jni.resolve("impl")
val genDir = jni.resolve("gen")
val `cmake-build-win64` = jni.resolve("cmake-build-win64")
val `cmake-build` = jni.resolve("cmake-build")
val javahDir = jni.resolve("javah")
val res = projectDir.resolve("res")

fun NativeBuildTask.preconfigure(vararg deps: TaskProvider<*>) {
  jniDir = jni
  resDir = res
  listOf(fdDir, genDir, imguiDir, implDir, jni.resolve("config"), jni.resolve("project")).map(inputs::dir)
  inputs.file(jni.resolve("CMakeLists.txt"))
  dependsOn(*deps)
}

val clean = tasks.named<Delete>("clean")
val cleanGroup = "build"
val downloadGroup = "download"
val downloadAll = tasks.register("downloadAll") {
  group = downloadGroup
  description = "Virtual task representing all downloading tasks"
  val log = projectDir.resolve("javadoc.log")
  outputs.file(log)
  doLast { log.writeText(GenGenTask.parser.map.entries.joinToString("\n") { (k, v) -> "$k:${escapeStringCharacters(v)}" }) }
}
val compileCxx = tasks.register("compileCxx") {
  group = "native compile"
  description = "Virtual task representing all C++ compilation tasks"
}

inline fun <reified Task : GenJavaTask> g() =
    tasks.register<Task>(Task::class.simpleName!!.decapitalize()) {
      dependsOn(downloadImgui)
    }

val generations = arrayOf(
    g<GenIOTask>(),
    g<GenNativeTypesTask>(),
    g<GenStyleVarsTask>(),
    g<GenStyleColorsTask>(),
    g<GenDefaultKeysTask>(),
    g<GenFontTask>(),
    g<GenFDTask>(),
    g<GenFontAtlasTask>(),
    g<GenFontConfigTask>(),
    g<GenDrawListTask>(),
    g<GenStyleTask>(),
    g<GenTabBarFlags>(),
    g<GenBackendFlags>(),
    g<GenColorEditFlags>(),
    g<GenMouseButton>(),
    g<GenTableBgTarget>(),
    g<GenSortDirection>(),
    g<GenComboFlags>(),
    g<GenSliderFlags>(),
    g<GenFontAtlasFlags>(),
    g<GenWindowFlags>(),
    g<GenInputTextFlags>(),
    g<GenTreeNodeFlags>(),
    g<GenSelectableFlags>(),
    g<GenPopupFlags>(),
    g<GenFocusedFlags>(),
    g<GenHoveredFlags>(),
    g<GenConfigFlags>(),
    g<GenButtonFlags>(),
    g<GenTabItemFlags>(),
    g<GenTableFlags>(),
    g<GenTableColumnFlags>(),
    g<GenTableRowFlags>(),
    g<GenCond>(),
    g<GenGenTask>())

val github = "https://raw.githubusercontent.com"
/// It was my own fork, but now I'm using the official one
val coding = "https://coding.net/u/ice1000/p"
val imguiCoding = "$github/ocornut/imgui/master"
val imguiFD = "$github/aiekick/ImGuiFileDialog"
val imguiExamples = "$imguiCoding/backends"

val downloadImgui = tasks.register<Download>("downloadImgui") {
  group = downloadGroup
  src("$imguiCoding/imgui.cpp")
  src("$imguiCoding/imgui.h")
  src("$imguiCoding/imgui_draw.cpp")
  src("$imguiCoding/imgui_demo.cpp")
  src("$imguiCoding/imgui_widgets.cpp")
  src("$imguiCoding/imgui_tables.cpp")
  src("$imguiCoding/imgui_internal.h")
  src("$imguiCoding/imstb_rectpack.h")
  src("$imguiCoding/imstb_textedit.h")
  src("$imguiCoding/imstb_truetype.h")
  src("$imguiCoding/misc/cpp/imgui_stdlib.cpp")
  src("$imguiCoding/misc/cpp/imgui_stdlib.h")
  src("$github/nothings/stb/master/stb_image.h")
  dest(imguiDir)
  overwrite(false)
}

val downloadImpl = tasks.register<Download>("downloadImpl") {
  group = downloadGroup
  src("$imguiExamples/imgui_impl_glfw.h")
  src("$imguiExamples/imgui_impl_glfw.cpp")
  src("$imguiExamples/imgui_impl_opengl3.h")
  src("$imguiExamples/imgui_impl_opengl3.cpp")
  src("$imguiExamples/imgui_impl_dx9.h")
  src("$imguiExamples/imgui_impl_dx11.h")
  src("$imguiExamples/imgui_impl_dx9.cpp")
  src("$imguiExamples/imgui_impl_dx11.cpp")
  src("$imguiExamples/imgui_impl_win32.h")
  src("$imguiExamples/imgui_impl_win32.cpp")
  src("$github/covscript/covscript-imgui/master/src/gl3w.c")
  dest(implDir)
  overwrite(false)
}

val downloadImplGL = tasks.register<Download>("downloadImplGL") {
  group = downloadGroup
  src("$github/covscript/covscript-imgui/master/include/GL/gl3w.h")
  src("$github/covscript/covscript-imgui/master/include/GL/glcorearb.h")
  dest(implDir.resolve("GL"))
  overwrite(false)
}

val downloadDirent = tasks.register<Download>("downloadDirent") {
  src("$github/tronkko/dirent/master/include/dirent.h")
  dest(fdDir.resolve("dirent").resolve("dirent.h"))
  overwrite(false)
}

val downloadFileDialog = tasks.register<Download>("downloadFileDialog") {
  group = downloadGroup
  if (isWindows) dependsOn(downloadDirent)
  src("$imguiFD/master/CustomFont.h")
  src("$imguiFD/master/CustomFont.cpp")
  src("$imguiFD/Lib_Only/ImGuiFileDialog.h")
  src("$imguiFD/Lib_Only/ImGuiFileDialog.cpp")
  dest(fdDir)
  overwrite(false)
}

val downloadFiraCode = tasks.register<Download>("downloadFiraCode") {
  group = downloadGroup
  src("$github/tonsky/FiraCode/master/distr/ttf/FiraCode-Regular.ttf")
  dest(file("testRes/font/FiraCode-Regular.ttf"))
  overwrite(false)
}

val downloadIce1000 = tasks.register<Download>("downloadIce1000") {
  group = downloadGroup
  src("https://pic4.zhimg.com/61984a25d44df15b857475e7f7b1c7e3_xl.jpg")
  dest(file("testRes/pics/ice1000.png"))
  overwrite(false)
}

val cmakeWin64 = tasks.register<CMake>("cmakeWin64") {
  preconfigure(tasks.compileJava, downloadAll)
  simple(`cmake-build-win64`, "x64")
}

val cmake = tasks.register<CMake>("cmake") {
  preconfigure(tasks.compileJava, downloadAll)
  simple(`cmake-build`, "Win32")
}

val make = tasks.register<CxxCompile>("make") {
  preconfigure(cmake)
  cxx(`cmake-build`, "make", "-f", "Makefile")
}

val msbuild = tasks.register<CxxCompile>("msbuild") {
  preconfigure(cmake)
  cxx(`cmake-build`, "msbuild", "jimgui.sln", "/t:Build", "/p:Configuration=Release")
}

val msbuildWin64 = tasks.register<CxxCompile>("msbuildWin64") {
  preconfigure(cmakeWin64)
  cxx(`cmake-build-win64`, "msbuild", "jimgui.sln", "/t:Build", "/p:Configuration=Release")
}

val clearGenerated = tasks.register<Delete>("clearGenerated") {
  group = cleanGroup
  delete(projectDir.resolve("gen"), javahDir, genDir)
}

val clearCMake = tasks.register<Delete>("clearCMake") {
  group = cleanGroup
  delete(`cmake-build-win64`, `cmake-build`)
}

val clearDownloaded = tasks.register<Delete>("clearDownloaded") {
  group = cleanGroup
  delete(imguiDir, implDir, fdDir)
}

downloadAll.configure {
  dependsOn(downloadImplGL, downloadImpl, downloadImgui, downloadFileDialog)
}
tasks.compileJava {
  options.compilerArgs = listOf("-h", "$javahDir")
  dependsOn(*generations)
}

clean.configure {
  dependsOn(clearCMake, clearDownloaded, clearGenerated)
}
compileCxx.configure {
  if (isWindows) dependsOn(msbuild, msbuildWin64)
  else dependsOn(make)
}
tasks.named("processResources") {
  dependsOn(compileCxx)
}
tasks.named("processTestResources") {
  dependsOn(downloadFiraCode, downloadIce1000)
}

sourceSets {
  main {
    java.srcDirs("src", "gen")
    resources.srcDir(res)
  }

  test {
    java.srcDir("test")
    resources.srcDir("testRes")
  }
}

dependencies {
  api(group = "org.jetbrains", name = "annotations", version = "20.1.0")
  testImplementation(group = "junit", name = "junit", version = "4.12")
}
