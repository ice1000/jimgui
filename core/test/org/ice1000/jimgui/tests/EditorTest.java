package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.*;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;

import java.net.URISyntaxException;

public class EditorTest {
	public static void main(String @NotNull ... args) throws InterruptedException, URISyntaxException {
		JniLoader.load();
		StringBuilder builder = new StringBuilder("输入一些文本");
		int cursor = builder.length();
		float itemRectSizeX = 0;
		try (JImGui gui = new JImGui()) {
			JImGuiIO io = gui.getIO();
			JImFontAtlas fonts = io.getFonts();
			String fontPath = EditorTest.class.getResource("/font/sarasa-gothic-sc-regular.ttf").toURI().getPath();
			JImFont sarasaGothic = fonts.addFontFromFile(fontPath, 20, fonts.getGlyphRangesForChinese());
			while (!gui.windowShouldClose()) {
				float deltaTime = io.getDeltaTime() * 1000;
				Thread.sleep((long) Math.abs(20 - deltaTime));
				gui.initNewFrame();
				char[] inputString = io.getInputChars();
				io.clearInputCharacters();
				builder.insert(cursor, inputString);
				cursor += inputString.length;
				if (gui.isKeyPressed(io.keyMapAt(JImDefaultKeys.LeftArrow)) && cursor > 0) cursor--;
				if (gui.isKeyPressed(io.keyMapAt(JImDefaultKeys.RightArrow)) && cursor < builder.length()) cursor++;
				if (gui.isKeyPressed(io.keyMapAt(JImDefaultKeys.Backspace)) && cursor > 0) builder.deleteCharAt(--cursor);
				gui.pushStyleVar(JImStyleVars.ItemSpacing, -0.5f, 2f);
				gui.begin("Editor");
				gui.text(builder.substring(0, cursor));
				gui.sameLine();
				float cursorPosX = gui.getCursorPosX();
				if ((System.currentTimeMillis() / 200) % 2 == 1) {
					gui.text("|");
					itemRectSizeX = gui.getItemRectSizeX();
					gui.setCursorPosX(cursorPosX);
					gui.sameLine();
				} else gui.setCursorPosX(cursorPosX + itemRectSizeX);
				gui.text(builder.substring(cursor));
				gui.popStyleVar();
				gui.end();
				gui.render();
			}
		}
	}
}
