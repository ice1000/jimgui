package test

import org.ice1000.jimgui.dsl.*
import org.ice1000.jimgui.util.JniLoader

fun main(args: Array<String>) {
	JniLoader.load()
	runPer(15) {
		"Window 2" {
			menuBar {
				menu("What?") {
					menuItem("Secret places, we don't fight fair~")
				}
			}
		}
		mainMenuBar {
			menu("File") {
				menu("New") {
					menuItem("Kotlin File")
					menuItem("Script")
				}
				menuItem("Open...")
			}
			menu("Edit") {
				menuItem("Copy", "Ctrl+C")
			}
		}
		treeNode("Platforms") {
			treeNode("JVM") {
				treeNode("Kotlin") {
					text("Kotlin JVM")
					text("Kotlin Native")
					text("Kotlin JavaScript")
				}
				treeNode("Scala") {
					text("Scala JVM")
					text("Scala Native")
					text("Scala JavaScript")
				}
				text("Java")
			}
			treeNode("CLR") {
				text("Visual C#")
				text("Visual F#")
				text("Visual Basic .NET")
				text("IronRuby")
				text("IronPython")
			}
		}
	}
}
