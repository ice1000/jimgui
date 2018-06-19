package test

import org.ice1000.jimgui.JImGui
import org.ice1000.jimgui.JImGui.getWindowHeight
import org.ice1000.jimgui.JImGui.getWindowWidth
import org.ice1000.jimgui.JImTextureID
import org.ice1000.jimgui.util.JniLoader

class PlaceHolder

fun main(args: Array<String>) {
	JniLoader.load()
	JImGui().use { imGui ->
		var latestRefresh = System.currentTimeMillis()
		val texture = JImTextureID.fromBytes(PlaceHolder::class.java.getResourceAsStream("/pics/ice1000.png").readBytes())
		while (!imGui.windowShouldClose()) {
			val currentTimeMillis = System.currentTimeMillis()
			val deltaTime = currentTimeMillis - latestRefresh
			Thread.sleep(deltaTime * 2 / 3)
			if (deltaTime > 16.toLong()) {
				imGui.initNewFrame()
				if (System.currentTimeMillis() % 1500 < 750)
					imGui.image(texture, texture.width.toFloat(), texture.height.toFloat())
				else
					imGui.image(texture, getWindowWidth(), getWindowHeight())
				imGui.render()
				latestRefresh = currentTimeMillis
			}
		}
	}
}
