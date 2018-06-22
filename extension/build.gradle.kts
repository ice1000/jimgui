plugins { java }
java.sourceSets {
	"main" {
		java.setSrcDirs(listOf("src"))
		resources.setSrcDirs(listOf("res"))
	}
}
repositories { jcenter() }
dependencies { compile(project(":core")) }
