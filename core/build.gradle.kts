import org.ice1000.gradle.*

plugins { java }

val isCI: Boolean by rootProject.extra

val classes = tasks["classes"]
val clean = tasks["clean"]

val genImGuiIO = task<GenIOTask>("genImGuiIO")
val genImGui = task<GenGenTask>("genImGui")
val genBindings = task("genBindings") {
	group = genImGui.group
	dependsOn(genImGuiIO)
	dependsOn(genImGui)
}

val javah = task<GenNativeHeaderTask>("javah") {
	classes(
			"org.ice1000.jimgui.JImGui",
			"org.ice1000.jimgui.JImGuiGen",
			"org.ice1000.jimgui.JImGuiIOGen",
			"org.ice1000.jimgui.JImVec4",
			"org.ice1000.jimgui.MutableJImVec4"
	)
	dependsOn(classes)
}

val clearGenerated = task<Delete>("clearGenerated") {
	group = clean.group
	delete(projectDir.resolve("gen"),
			projectDir.resolve("jni").resolve("generated.cpp"))
}

classes.dependsOn(genBindings)
clean.dependsOn(clearGenerated)

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
