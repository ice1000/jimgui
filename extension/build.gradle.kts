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
	implementation(project(":core"))
	testImplementation(group = "junit", name = "junit", version = "4.12")
}
