import de.undercouch.gradle.tasks.download.Download
import org.apache.tools.ant.taskdefs.condition.Os
import org.ice1000.gradle.*

plugins {
	java
	id("de.undercouch.download")
}

val jniDir = projectDir.resolve("jni").absoluteFile!!
val imguiDir = jniDir.resolve("imgui")
val implDir = jniDir.resolve("impl")
val `cmake-build-win64` = jniDir.resolve("cmake-build-win64")
val `cmake-build` = jniDir.resolve("cmake-build")
val javahDir = jniDir.resolve("javah")
val res = projectDir.resolve("res")

tasks.compileJava {
	options.compilerArgs = listOf("-h", "$javahDir")
}

val clean = tasks.named<Delete>("clean")
val cleanGroup = clean.get().group
val downloadGroup = "download"
val compileGroup = "native compile"
val downloadAll = tasks.register("downloadAll") {
	group = downloadGroup
	description = "Virtual task representing all downloading tasks"
}
val compileCxx = tasks.register("compileCxx") {
	group = compileGroup
	description = "Virtual task representing all C++ compilation tasks"
}

val nativeLibraryExtensions = listOf("so", "dll", "dylib")
fun Exec.configureCxxBuild(workingDir: File, vararg commandLine: String) {
	group = compileGroup
	workingDir(workingDir)
	commandLine(*commandLine)
	outputs.dir(workingDir)
	inputs.files(jniDir.listFiles().orEmpty().filter { it.name.endsWith("cpp") })
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
	group = compileGroup
	workingDir(workingDir)
	outputs.dir(workingDir)
	inputs.dir(jniDir.resolve("imgui"))
	inputs.dir(jniDir.resolve("impl"))
	inputs.file(jniDir.resolve("CMakeLists.txt"))
	commandLine("cmake", "-DCMAKE_BUILD_TYPE=Release", "-G", generator, *additional, workingDir.parent)
	doFirst { workingDir.mkdirs() }
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

val isWindows = Os.isFamily(Os.FAMILY_WINDOWS)
val cmakeWin64 = tasks.register<Exec>("cmakeWin64") {
	if (isWindows)
		configureCMake(`cmake-build-win64`, "Visual Studio 16 2019", "-A", "x64")
	else configureCMake(`cmake-build-win64`, "Unix Makefiles")
	dependsOn(tasks.compileJava, downloadAll)
}

val cmake = tasks.register<Exec>("cmake") {
	if (isWindows)
		configureCMake(`cmake-build`, "Visual Studio 16 2019", "-A", "Win32")
	else configureCMake(`cmake-build`, "Unix Makefiles")
	dependsOn(tasks.compileJava, downloadAll)
}

val make = tasks.register<Exec>("make") {
	dependsOn(cmake)
	configureCxxBuild(`cmake-build`, "make", "-f", "Makefile")
}

val msbuild = tasks.register<Exec>("msbuild") {
	dependsOn(cmake)
	configureCxxBuild(`cmake-build`, "msbuild", "jimgui.sln", "/t:Build", "/p:Configuration=Release")
}

val msbuildWin64 = tasks.register<Exec>("msbuildWin64") {
	dependsOn(cmakeWin64)
	configureCxxBuild(`cmake-build-win64`, "msbuild", "jimgui.sln", "/t:Build", "/p:Configuration=Release")
}

val clearGenerated = tasks.register<Delete>("clearGenerated") {
	group = cleanGroup
	delete(projectDir.resolve("gen"), javahDir, *jniDir.listFiles { f: File -> f.name.startsWith("generated") }.orEmpty())
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
	dependsOn(genImguiIO, genImguiFont, genImguiStyle, genImgui, genImguiDrawList,
		genNativeTypes, genImguiStyleVar, genImguiDefaultKeys,
		genImguiStyleColor, genImguiFontAtlas, genImguiFontConfig)
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
