import de.undercouch.gradle.tasks.download.Download
import org.apache.tools.ant.taskdefs.condition.Os
import org.ice1000.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	java
	id("de.undercouch.download")
}

val jni = projectDir.resolve("jni").absoluteFile
val imguiDir = jni.resolve("imgui")
val implDir = jni.resolve("impl")
val `cmake-build-win64` = jni.resolve("cmake-build-win64")
val `cmake-build` = jni.resolve("cmake-build")
val javahDir = jni.resolve("javah")
val res = projectDir.resolve("res")

fun NativeBuildTask.preconfigure() {
	jniDir = jni
	resDir = res
}

tasks.compileJava {
	options.compilerArgs = listOf("-h", "$javahDir")
}

val clean = tasks.named<Delete>("clean")
val cleanGroup = clean.get().group
val downloadGroup = "download"
val downloadAll = tasks.register("downloadAll") {
	group = downloadGroup
	description = "Virtual task representing all downloading tasks"
}
val compileCxx = tasks.register("compileCxx") {
	group = "native compile"
	description = "Virtual task representing all C++ compilation tasks"
}

val genImgui = tasks.register<GenGenTask>("genImgui") {
	dependsOn(downloadImgui)
}

val genImguiIO = tasks.register<GenIOTask>("genImguiIO")
val genNativeTypes = tasks.register<GenNativeTypesTask>("genNativeTypes")
val genImguiStyleVar = tasks.register<GenStyleVarsTask>("genImguiStyleVar")
val genImguiStyleColor = tasks.register<GenStyleColorsTask>("genImguiStyleColor")
val genImguiDefaultKeys = tasks.register<GenDefaultKeysTask>("genImguiDefaultKeys")
val genImguiFont = tasks.register<GenFontTask>("genImguiFont")
val genImguiFontAtlas = tasks.register<GenFontAtlasTask>("genImguiFontAtlas")
val genImguiFontConfig = tasks.register<GenFontConfigTask>("genImguiFontConfig")
val genImguiDrawList = tasks.register<GenDrawListTask>("genImguiDrawList")
val genImguiStyle = tasks.register<GenStyleTask>("genImguiStyle")

val github = "https://raw.githubusercontent.com"
/// It was my own fork, but now I'm using the official one
val coding = "https://coding.net/u/ice1000/p"
val imguiCoding = "$github/ocornut/imgui/master"
val imguiExamples = "$imguiCoding/backends"

val downloadImgui = tasks.register<Download>("downloadImgui") {
	group = downloadGroup
	src("$imguiCoding/imgui.cpp")
	src("$imguiCoding/imgui.h")
	src("$imguiCoding/imgui_draw.cpp")
	src("$imguiCoding/imgui_demo.cpp")
	src("$imguiCoding/imgui_widgets.cpp")
	src("$imguiCoding/imgui_internal.h")
	src("$imguiCoding/imstb_rectpack.h")
	src("$imguiCoding/imstb_textedit.h")
	src("$imguiCoding/imstb_truetype.h")
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
	src("$imguiExamples/imgui_impl_dx9.cpp")
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
	preconfigure()
	if (isWindows)
		cmake(`cmake-build-win64`, "Visual Studio 16 2019", "-A", "x64")
	else cmake(`cmake-build-win64`, "Unix Makefiles")
	dependsOn(tasks.compileJava, downloadAll)
}

val cmake = tasks.register<CMake>("cmake") {
	preconfigure()
	if (isWindows)
		cmake(`cmake-build`, "Visual Studio 16 2019", "-A", "Win32")
	else cmake(`cmake-build`, "Unix Makefiles")
	dependsOn(tasks.compileJava, downloadAll)
}

val make = tasks.register<CxxCompile>("make") {
	preconfigure()
	dependsOn(cmake)
	cxx(`cmake-build`, "make", "-f", "Makefile")
}

val msbuild = tasks.register<CxxCompile>("msbuild") {
	preconfigure()
	dependsOn(cmake)
	cxx(`cmake-build`, "msbuild", "jimgui.sln", "/t:Build", "/p:Configuration=Release")
}

val msbuildWin64 = tasks.register<CxxCompile>("msbuildWin64") {
	preconfigure()
	dependsOn(cmakeWin64)
	cxx(`cmake-build-win64`, "msbuild", "jimgui.sln", "/t:Build", "/p:Configuration=Release")
}

val clearGenerated = tasks.register<Delete>("clearGenerated") {
	group = cleanGroup
	delete(projectDir.resolve("gen"), javahDir, *jni.listFiles { f: File -> f.name.startsWith("generated") }.orEmpty())
}

val clearCMake = tasks.register<Delete>("clearCMake") {
	group = cleanGroup
	delete(`cmake-build-win64`, `cmake-build`)
}

val clearDownloaded = tasks.register<Delete>("clearDownloaded") {
	group = cleanGroup
	delete(imguiDir, implDir)
}

downloadAll.configure {
	dependsOn(downloadImplGL, downloadImpl, downloadImgui)
}
tasks.compileJava {
	dependsOn(
			genImguiIO,
			genImguiFont,
			genImguiStyle,
			genImgui,
			genImguiDrawList,
			genNativeTypes,
			genImguiStyleVar,
			genImguiDefaultKeys,
			genImguiStyleColor,
			genImguiFontAtlas,
			genImguiFontConfig
	)
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
	implementation(group = "org.jetbrains", name = "annotations", version = "20.1.0")
	testImplementation(group = "junit", name = "junit", version = "4.12")
}
