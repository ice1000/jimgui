import de.undercouch.gradle.tasks.download.Download
import org.ice1000.gradle.*

plugins {
	java
	id("de.undercouch.download") version "3.4.2"
}

val isCI: Boolean by rootProject.extra

val classes = tasks["classes"]!!
val compileJava = tasks["compileJava"]!!
val clean = tasks["clean"]!!
val jar = tasks["jar"]!!

val genImguiIO = task<GenIOTask>("genImguiIO")
val genImgui = task<GenGenTask>("genImgui")

val jniDir = projectDir.resolve("jni").absoluteFile!!
val imguiDir = jniDir.resolve("imgui")
val implDir = jniDir.resolve("impl")
val `cmake-build-debug` = jniDir.resolve("cmake-build-debug")

val imguiGitHub = "https://raw.githubusercontent.com/ocornut/imgui/master"
val covGitHub = "https://raw.githubusercontent.com/covscript/covscript-imgui/master"

val downloadImgui = task<Download>("downloadImgui") {
	src("$imguiGitHub/imgui.cpp")
	src("$imguiGitHub/imgui.h")
	src("$imguiGitHub/imgui_draw.cpp")
	src("$imguiGitHub/imgui_internal.h")
	src("$imguiGitHub/imconfig.h")
	src("$imguiGitHub/stb_rect_pack.h")
	src("$imguiGitHub/stb_textedit.h")
	src("$imguiGitHub/stb_truetype.h")
	dest(imguiDir)
	overwrite(false)
}

val downloadImpl = task<Download>("downloadImpl") {
	src("$imguiGitHub/examples/opengl3_example/imgui_impl_glfw_gl3.h")
	src("$imguiGitHub/examples/opengl3_example/imgui_impl_glfw_gl3.cpp")
	src("$covGitHub/src/gl3w.c")
	dest(implDir)
	overwrite(false)
}

val downloadImplGL = task<Download>("downloadImplGL") {
	src("$covGitHub/include/GL/gl3w.h")
	src("$covGitHub/include/GL/glcorearb.h")
	dest(implDir.resolve("GL"))
	overwrite(false)
}

val cmake = task<Exec>("cmake") {
	`cmake-build-debug`.mkdirs()
	workingDir(`cmake-build-debug`)
	commandLine("cmake", `cmake-build-debug`.parent)
}

val make = task<Exec>("make") {
	workingDir(`cmake-build-debug`)
	commandLine("make")
}

val javah = task<GenNativeHeaderTask>("javah") {
	classes(
			"org.ice1000.jimgui.JImGui",
			"org.ice1000.jimgui.JImGuiGen",
			"org.ice1000.jimgui.JImGuiIOGen",
			"org.ice1000.jimgui.JImVec4",
			"org.ice1000.jimgui.MutableJImVec4"
	)
}

val clearGenerated = task<Delete>("clearGenerated") {
	group = clean.group
	delete(projectDir.resolve("gen"),
			jniDir.resolve("generated.cpp"))
}

val clearCMake = task<Exec>("clearCMake") {
	group = clean.group
	workingDir(`cmake-build-debug`)
	commandLine("cmake", "clean")
}

val clearDownloaded = task<Delete>("clearDownloaded") {
	group = clean.group
	delete(imguiDir, implDir)
}

compileJava.dependsOn(genImguiIO)
compileJava.dependsOn(genImgui)
clean.dependsOn(clearGenerated)
clean.dependsOn(clearDownloaded)
javah.dependsOn(classes)
make.dependsOn(cmake)
cmake.dependsOn(javah)
cmake.dependsOn(downloadImgui)
cmake.dependsOn(downloadImpl)
cmake.dependsOn(downloadImplGL)
jar.dependsOn(make)

java.sourceSets {
	"main" {
		java.setSrcDirs(listOf("src", "gen"))
		resources.setSrcDirs(listOf("res"))
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
