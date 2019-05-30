plugins { java }
sourceSets {
	main {
		java.setSrcDirs(listOf("src"))
		resources.setSrcDirs(listOf("res"))
	}
	test { java.setSrcDirs(listOf("test")) }
}
repositories { jcenter() }
dependencies {
	compile(project(":core"))
	testCompile(group = "junit", name = "junit", version = "4.12")
}
