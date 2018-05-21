import de.undercouch.gradle.tasks.download.Download
import org.apache.tools.ant.taskdefs.condition.Os
import org.ice1000.gradle.*

plugins {
	java
	id("de.undercouch.download") version "3.4.2"
}

val isCI: Boolean by rootProject.extra

val classes = tasks["classes"]!!
val compileJava = tasks["compileJava"] as JavaCompile
val clean = tasks["clean"] as Delete
val processResources = tasks["processResources"]!!
val processTestResources = tasks["processTestResources"]!!
val downloadAll = task("downloadAll") {
	group = "download"
	description = "Virtual task representing all downloading tasks"
}
val compileCxx = task("compileCxx") {
	group = compileJava.group
	description = "Virtual task representing all C++ compilation tasks"
}

val jniDir = projectDir.resolve("jni").absoluteFile!!
val imguiDir = jniDir.resolve("imgui")
val implDir = jniDir.resolve("impl")
val `cmake-build-win64` = jniDir.resolve("cmake-build-win64")
val `cmake-build` = jniDir.resolve("cmake-build")
val javahDir = jniDir.resolve("javah")
val res = projectDir.resolve("res")

val nativeLibraryExtensions = listOf("so", "dll", "dylib")
fun Exec.configureCxxBuild(workingDir: File, vararg commandLine: String) {
	group = compileCxx.group
	workingDir(workingDir)
	commandLine(*commandLine)
	doLast {
		workingDir.walk()
				.filter { it.extension in nativeLibraryExtensions }
				.forEach {
					println("Found native library $it")
					it.copyTo(res.resolve("native").resolve(it.name), overwrite = true)
				}
	}
}

fun Exec.configureCMake(workingDir: File, generator: String) {
	group = compileCxx.group
	workingDir(workingDir)
	commandLine("cmake", "-G", generator, workingDir.parent)
	doFirst { workingDir.mkdirs() }
}

val genImguiIO = task<GenIOTask>("genImguiIO")
val genImgui = task<GenGenTask>("genImgui")
val genNativeTypes = task<GenNativeTypesTask>("genNativeTypes")
val genImguiStyleVar = task<GenStyleVarsTask>("genImguiStyleVar")
val genImguiStyleColor = task<GenStyleColorsTask>("genImguiStyleColor")
val genImguiDefaultKeys = task<GenDefaultKeysTask>("genImguiDefaultKeys")
val genImguiFont = task<GenFontTask>("genImguiFont")
val genImguiFontAtlas = task<GenFontAtlasTask>("genImguiFontAtlas")
val genImguiDrawList = task<GenDrawListTask>("genImguiDrawList")
val genImguiStyle = task<GenStyleTask>("genImguiStyle")

val imguiGitHub = "https://raw.githubusercontent.com/ocornut/imgui/master"
val covGitHub = "https://raw.githubusercontent.com/covscript/covscript-imgui/master"
val imguiExamples = "$imguiGitHub/examples"

val downloadImgui = task<Download>("downloadImgui") {
	group = downloadAll.group
	src("$imguiGitHub/imgui.cpp")
	src("$imguiGitHub/imgui.h")
	src("$imguiGitHub/imgui_draw.cpp")
	src("$imguiGitHub/imgui_demo.cpp")
	src("$imguiGitHub/imgui_internal.h")
	src("$imguiGitHub/stb_rect_pack.h")
	src("$imguiGitHub/stb_textedit.h")
	src("$imguiGitHub/stb_truetype.h")
	dest(imguiDir)
	overwrite(false)
}

val downloadImpl = task<Download>("downloadImpl") {
	group = downloadAll.group
	src("$imguiExamples/opengl3_example/imgui_impl_glfw_gl3.h")
	src("$imguiExamples/opengl3_example/imgui_impl_glfw_gl3.cpp")
	src("$imguiExamples/directx11_example/imgui_impl_dx11.h")
	src("$imguiExamples/directx11_example/imgui_impl_dx11.cpp")
	src("$covGitHub/src/gl3w.c")
	dest(implDir)
	overwrite(false)
}

val downloadImplGL = task<Download>("downloadImplGL") {
	group = downloadAll.group
	src("$covGitHub/include/GL/gl3w.h")
	src("$covGitHub/include/GL/glcorearb.h")
	dest(implDir.resolve("GL"))
	overwrite(false)
}

val downloadFiraCode = task<Download>("downloadFiraCode") {
	src("https://raw.githubusercontent.com/tonsky/FiraCode/master/distr/ttf/FiraCode-Regular.ttf")
	dest(file("testRes/font").apply { if (!exists()) mkdirs() })
	overwrite(false)
}

val isWindows = Os.isFamily(Os.FAMILY_WINDOWS)
val cmakeWin64 = task<Exec>("cmakeWin64") {
	configureCMake(`cmake-build-win64`, if (isWindows) "Visual Studio 15 2017 Win64" else "Unix Makefiles")
}

val cmake = task<Exec>("cmake") {
	configureCMake(`cmake-build`, if (isWindows) "Visual Studio 15 2017" else "Unix Makefiles")
}

val make = task<Exec>("make") { configureCxxBuild(`cmake-build`, "make", "-f", "Makefile") }
val msbuild = task<Exec>("msbuild") { configureCxxBuild(`cmake-build`, "msbuild", "jimgui.sln", "/t:Build", "/p:Configuration=Release") }
val msbuildWin64 = task<Exec>("msbuildWin64") { configureCxxBuild(`cmake-build-win64`, "msbuild", "jimgui.sln", "/t:Build", "/p:Configuration=Release") }

val clearGenerated = task<Delete>("clearGenerated") {
	group = clean.group
	delete(projectDir.resolve("gen"), javahDir, *jniDir.listFiles { f: File -> f.name.startsWith("generated") })
}

val clearCMake = task<Delete>("clearCMake") {
	group = clean.group
	delete(`cmake-build-win64`, `cmake-build`)
}

val clearDownloaded = task<Delete>("clearDownloaded") {
	group = clean.group
	delete(imguiDir, implDir)
}

compileJava.options.compilerArgs = listOf("-h", javahDir.toString())

genImgui.dependsOn(downloadImgui)
compileJava.dependsOn(genImguiIO, genImguiFont, genImguiStyle, genImgui, genImguiDrawList,
		genNativeTypes, genImguiStyleVar, genImguiDefaultKeys, genImguiStyleColor, genImguiFontAtlas)
clean.dependsOn(clearCMake, clearDownloaded, clearGenerated)
if (isWindows) compileCxx.dependsOn(msbuild, msbuildWin64)
else compileCxx.dependsOn(make)
make.dependsOn(cmake)
msbuild.dependsOn(cmake)
msbuildWin64.dependsOn(cmakeWin64)
cmake.dependsOn(compileJava, downloadImgui, downloadImpl, downloadImplGL)
cmakeWin64.dependsOn(compileJava, downloadImgui, downloadImpl, downloadImplGL)
processResources.dependsOn(compileCxx)
processTestResources.dependsOn(downloadFiraCode)

java.sourceSets {
	"main" {
		java.setSrcDirs(listOf("src", "gen"))
		resources.setSrcDirs(listOf(res))
	}

	"test" {
		java.setSrcDirs(listOf("test"))
		resources.setSrcDirs(listOf("testRes"))
	}
}

dependencies {
	compile(group = "org.jetbrains", name = "annotations", version = "16.0.1")
	testCompile(group = "junit", name = "junit", version = "4.12")
}
