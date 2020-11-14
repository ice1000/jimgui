import de.undercouch.gradle.tasks.download.Download
import org.apache.tools.ant.taskdefs.condition.Os
import org.ice1000.gradle.*

plugins {
	java
	id("de.undercouch.download")
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
	outputs.dir(workingDir)
	inputs.files(jniDir.listFiles().filter { it.name.endsWith("cpp") })
	inputs.dir(jniDir.resolve("imgui"))
	inputs.dir(jniDir.resolve("impl"))
	doLast {
		workingDir.walk()
				.filter { it.extension in nativeLibraryExtensions }
				.forEach {
					println("Found native library $it")
					it.copyTo(res.resolve("native").resolve(it.name), overwrite = true)
				}
	}
}

fun Exec.configureCMake(workingDir: File, generator: String, vararg additional: String) {
	group = compileCxx.group
	workingDir(workingDir)
	outputs.dir(workingDir)
	inputs.dir(jniDir.resolve("imgui"))
	inputs.dir(jniDir.resolve("impl"))
	inputs.file(jniDir.resolve("CMakeLists.txt"))
	commandLine("cmake", "-DCMAKE_BUILD_TYPE=Release", "-G", generator, *additional, workingDir.parent)
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
val genImguiFontConfig = task<GenFontConfigTask>("genImguiFontConfig")
val genImguiDrawList = task<GenDrawListTask>("genImguiDrawList")
val genImguiStyle = task<GenStyleTask>("genImguiStyle")

val github = "https://raw.githubusercontent.com"
/// It was my own fork, but now I'm using the official one
val coding = "https://coding.net/u/ice1000/p"
val imguiCoding = "$github/ocornut/imgui/master"
val imguiExamples = "$imguiCoding/backends"

val downloadImgui = task<Download>("downloadImgui") {
	group = downloadAll.group
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

val downloadImpl = task<Download>("downloadImpl") {
	group = downloadAll.group
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

val downloadImplGL = task<Download>("downloadImplGL") {
	group = downloadAll.group
	src("$github/covscript/covscript-imgui/master/include/GL/gl3w.h")
	src("$github/covscript/covscript-imgui/master/include/GL/glcorearb.h")
	dest(implDir.resolve("GL"))
	overwrite(false)
}

val downloadFiraCode = task<Download>("downloadFiraCode") {
	group = downloadAll.group
	src("$github/tonsky/FiraCode/master/distr/ttf/FiraCode-Regular.ttf")
	dest(file("testRes/font/FiraCode-Regular.ttf"))
	overwrite(false)
}

val downloadIce1000 = task<Download>("downloadIce1000") {
	group = downloadAll.group
	src("https://pic4.zhimg.com/61984a25d44df15b857475e7f7b1c7e3_xl.jpg")
	dest(file("testRes/pics/ice1000.png"))
	overwrite(false)
}

val isWindows = Os.isFamily(Os.FAMILY_WINDOWS)
val cmakeWin64 = task<Exec>("cmakeWin64") {
	if (isWindows)
		configureCMake(`cmake-build-win64`, "Visual Studio 16 2019", "-A", "x64")
	else configureCMake(`cmake-build-win64`, "Unix Makefiles")
}

val cmake = task<Exec>("cmake") {
	if (isWindows)
		configureCMake(`cmake-build`, "Visual Studio 16 2019", "-A", "Win32")
	else configureCMake(`cmake-build`, "Unix Makefiles")
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
downloadAll.dependsOn(downloadImplGL, downloadImpl, downloadImgui)
compileJava.dependsOn(genImguiIO, genImguiFont, genImguiStyle, genImgui, genImguiDrawList,
		genNativeTypes, genImguiStyleVar, genImguiDefaultKeys,
		genImguiStyleColor, genImguiFontAtlas, genImguiFontConfig)
clean.dependsOn(clearCMake, clearDownloaded, clearGenerated)
if (isWindows) compileCxx.dependsOn(msbuild, msbuildWin64)
else compileCxx.dependsOn(make)
make.dependsOn(cmake)
msbuild.dependsOn(cmake)
msbuildWin64.dependsOn(cmakeWin64)
cmake.dependsOn(compileJava, downloadAll)
cmakeWin64.dependsOn(compileJava, downloadAll)
processResources.dependsOn(compileCxx)
processTestResources.dependsOn(downloadFiraCode, downloadIce1000)

sourceSets {
	main {
		java.setSrcDirs(listOf("src", "gen"))
		resources.setSrcDirs(listOf(res))
	}

	test {
		java.setSrcDirs(listOf("test"))
		resources.setSrcDirs(listOf("testRes"))
	}
}

dependencies {
	implementation(group = "org.jetbrains", name = "annotations", version = "20.1.0")
	testImplementation(group = "junit", name = "junit", version = "4.12")
}
