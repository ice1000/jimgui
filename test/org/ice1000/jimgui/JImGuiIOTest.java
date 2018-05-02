package org.ice1000.jimgui;

import org.ice1000.jimgui.tests.JImGuiTest;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.junit.BeforeClass;
import org.junit.Test;

public class JImGuiIOTest {
	@BeforeClass
	public static void setup() {
		JImGuiTest.useAlternativeJeniLib();
	}

	@Test
	public void testBinding() {
		JImGuiUtil.runWithin(5000, imGui -> {
			imGui.text("framerate: " + imGui.getIO().getFramerate());
			imGui.text("isKeyCtrl: " + imGui.getIO().isKeyCtrl());
			imGui.text("isKeyAlt: " + imGui.getIO().isKeyAlt());
			imGui.text("isKeyShift: " + imGui.getIO().isKeyShift());
			imGui.text("isKeySuper: " + imGui.getIO().isKeySuper());
		});
	}
}