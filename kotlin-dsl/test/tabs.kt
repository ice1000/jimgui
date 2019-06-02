package test

import org.ice1000.jimgui.dsl.runPer
import org.ice1000.jimgui.dsl.tabBar
import org.ice1000.jimgui.dsl.tabItem
import org.ice1000.jimgui.util.JniLoader

fun main() {
	JniLoader.load()
	runPer(10) {
		"Window with Tabs" {
			tabBar("tab bar id") {
				tabItem("Tab One") { text("I am in Tab one!") }
				tabItem("Tab Two") { button("I am in Tab two!") }
				tabItem("Tab Three") { bulletText("I am in Tab three!") }
			}
		}
	}
}
