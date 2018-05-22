package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.*;
import org.ice1000.jimgui.flag.JImConfigFlags;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;

import java.net.URISyntaxException;

public class EditorTest {
	public static void main(String @NotNull ... args) throws InterruptedException, URISyntaxException {
		JniLoader.load();
		StringBuilder builder = new StringBuilder("输入一些文本");
		int cursorPos = builder.length();
		try (JImGui gui = new JImGui()) {
			JImGuiIO io = gui.getIO();
			JImFontAtlas fonts = io.getFonts();
			String fontPath = EditorTest.class.getResource("/font/sarasa-gothic-sc-regular.ttf").toURI().getPath();
			JImFont sarasaGothic = fonts.addFontFromFile(fontPath, 20, fonts.getGlyphRangesForChinese());
			io.setConfigFlags(io.getConfigFlags() | JImConfigFlags.NavEnableKeyboard);
			while (!gui.windowShouldClose()) {
				gui.text(sarasaGothic.getDebugName());
				float deltaTime = io.getDeltaTime() * 1000;
				Thread.sleep((long) Math.abs(20 - deltaTime));
				gui.initNewFrame();
				String inputString = io.getInputString();
				builder.insert(cursorPos, inputString);
				cursorPos += inputString.length();
				if (gui.isKeyPressed(io.keyMapAt(JImDefaultKeys.LeftArrow)) && cursorPos > 0) cursorPos--;
				if (gui.isKeyPressed(io.keyMapAt(JImDefaultKeys.RightArrow)) && cursorPos < builder.length()) cursorPos++;
				gui.pushStyleVar(JImStyleVars.ItemSpacing, -0.5f, 2f);
				gui.begin("Editor");
				gui.text(builder.substring(0, cursorPos));
				gui.sameLine();
				gui.text((System.currentTimeMillis() / 500) % 2 == 1 ? "|" : " ");
				gui.sameLine();
				gui.text(builder.substring(cursorPos));
				gui.popStyleVar();
				gui.end();
				gui.render();
			}
		}
	}
}
