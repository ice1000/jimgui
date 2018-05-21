package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JImDrawList;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;

import static org.ice1000.jimgui.tests.JImGuiTest.useAlternativeJniLibAndCheckHeadless;
import static org.junit.Assert.*;

public class JImDrawListTest {
	@BeforeClass
	public static void setup() {
		useAlternativeJniLibAndCheckHeadless();
	}

	@Test
	public void testSandbox() {
		main();
	}

	public static void main(@NotNull String @NotNull ... args) {
		JniLoader.load();
		JImGuiUtil.runWithinPer(5000, 15, imGui -> {
			JImDrawList windowDrawList = imGui.findWindowDrawList();
			assertNotNull(windowDrawList);
			JImDrawList overlayDrawList = imGui.findOverlayDrawList();
			assertNotNull(overlayDrawList);
			float cursorPosX = imGui.getWindowX();
			float cursorPosY = imGui.getWindowY();
			windowDrawList.addText(30,
					cursorPosX + 122,
					cursorPosY + 22,
					Color.BLUE.getRGB(), "I XiHuan Reiuji Utsuho forever\nQAQ");
			int rgb = Color.GREEN.getRGB();
			windowDrawList.addCircle(
					cursorPosX + 250,
					cursorPosY + 150,
					50, rgb);
			windowDrawList.addText(72,
					cursorPosX + 230,
					cursorPosY + 120,
					rgb, "6");
		});
	}
}
