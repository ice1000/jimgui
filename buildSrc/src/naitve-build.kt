package org.ice1000.gradle

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.tasks.Exec
import java.io.File

private val nativeLibraryExtensions = listOf("so", "dll", "dylib")
val isWindows = Os.isFamily(Os.FAMILY_WINDOWS)
const val Makefiles = "Unix Makefiles"
const val VS2019 = "Visual Studio 16 2019"

abstract class NativeBuildTask : Exec() {
	lateinit var jniDir: File
	lateinit var resDir: File

	init {
		group = "native compile"
	}
}

open class CMake : NativeBuildTask() {
	fun cmake(workingDir: File, generator: String, vararg additional: String) {
		workingDir(workingDir)
		outputs.dir(workingDir)
		inputs.dir(jniDir.resolve("imgui"))
		inputs.dir(jniDir.resolve("impl"))
		inputs.file(jniDir.resolve("CMakeLists.txt"))
		commandLine("cmake", "-DCMAKE_BUILD_TYPE=Release", "-G", generator, *additional, workingDir.parent)
		doFirst { workingDir.mkdirs() }
	}
}

open class CxxCompile : NativeBuildTask() {
	// @Finalize
	fun findNativeLibs() = workingDir.walk()
			.filter { it.extension in nativeLibraryExtensions }
			.forEach {
				println("Found native library $it")
				it.copyTo(resDir.resolve("native").resolve(it.name), overwrite = true)
			}

	fun cxx(workingDir: File, vararg commandLine: String) {
		workingDir(workingDir)
		commandLine(*commandLine)
		outputs.dir(workingDir)
		inputs.files(jniDir.listFiles().orEmpty().filter { it.name.endsWith("cpp") })
		inputs.dir(jniDir.resolve("imgui"))
		inputs.dir(jniDir.resolve("impl"))
		doLast { findNativeLibs() }
	}
}