package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.*;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;

import java.net.URISyntaxException;

public class EditorTest {
	@SuppressWarnings("AccessStaticViaInstance")
	public static void main(String @NotNull ... args) throws InterruptedException, URISyntaxException {
		JniLoader.load();
		StringBuilder builder = new StringBuilder("输入一些文本");
		int cursor = builder.length();
		try (JImGui gui = new JImGui()) {
			JImGuiIO io = gui.getIO();
			JImFontAtlas fonts = io.getFonts();
			String fontPath = EditorTest.class.getResource("/font/sarasa-gothic-sc-regular.ttf").toURI().getPath();
			JImFont sarasaGothic = fonts.addFontFromFile(fontPath, 20, fonts.getGlyphRangesForChineseSimplifiedCommon());
			int texHeight = 20;
			JImStyle style = gui.getStyle();
			style.setItemSpacingX(1f);
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
				if (gui.isKeyPressed(io.keyMapAt(JImDefaultKeys.Enter))) builder.insert(cursor++, '\n');
				if (gui.isKeyPressed(io.keyMapAt(JImDefaultKeys.Backspace)) && cursor > 0) builder.deleteCharAt(--cursor);
				gui.begin("Editor");
				int length = builder.length();
				for (int i = 0; i <= length; i++) {
					if (i == cursor && (System.currentTimeMillis() / 1500) % 2 == 1) {
						float cursorPosX = gui.getCursorPosX() + gui.getWindowPosX() - 2;
						float cursorPosY = gui.getCursorPosY() + gui.getWindowPosY();
						gui.getWindowDrawList().addLine(cursorPosX,
								cursorPosY,
								cursorPosX,
								cursorPosY + texHeight,
								0xffffffff,
								2);
					}
					if (i == length) break;
					char c = builder.charAt(i);
					if (c != '\n') {
						gui.text(String.valueOf(c));
						gui.sameLine();
					} else gui.newLine();
				}
				gui.end();
				gui.render();
			}
		}
	}
}
