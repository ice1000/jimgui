plugins { java }
sourceSets.main { java.srcDir("src") }
repositories { jcenter() }
dependencies {
	implementation(project(":core"))
}
