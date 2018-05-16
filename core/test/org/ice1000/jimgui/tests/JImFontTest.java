package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JImFont;
import org.ice1000.jimgui.JImFontAtlas;
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.NativeFloat;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

public class JImFontTest {
	public static void main(String @NotNull ... args) throws InterruptedException {
		JniLoader.load();
		try (JImGui imGui = new JImGui()) {
			JImFontAtlas fonts = imGui.getIO().getFonts();
			fonts.addFontDefault();
			JImFont fontFromFile = fonts.addFontFromFile("testRes/font/FiraCode-Regular.ttf", 18);
			System.out.println(fontFromFile.getDebugName());
			NativeFloat fontSize = new NativeFloat();
			long latestRefresh = System.currentTimeMillis();
			while (!imGui.windowShouldClose()) {
				long currentTimeMillis = System.currentTimeMillis();
				long deltaTime = currentTimeMillis - latestRefresh;
				Thread.sleep(deltaTime / 2);
				if (deltaTime > (long) 15) {
					imGui.initNewFrame();
					JImFont font = imGui.findFont();
					if (font != null) {
						font.setFontSize(fontSize.accessValue());
						imGui.text(font.getDebugName());
					} else imGui.text("Not found");
					imGui.dragFloat("Font size", fontSize);
					imGui.showFontSelector("Wtf");
					imGui.render();
					latestRefresh = currentTimeMillis;
				}
			}
		}
	}
}
