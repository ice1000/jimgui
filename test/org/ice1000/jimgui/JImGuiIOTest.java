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
		JImGuiUtil.runWithin(8000, imGui -> {
			imGui.text("framerate: " + imGui.getIO().getFramerate());
			if (imGui.getIO().isKeyCtrl()) {
				imGui.text("[Ctrl]");
				imGui.sameLine();
			}
			if (imGui.getIO().isKeyAlt()) {
				imGui.text("[Alt]");
				imGui.sameLine();
			}
			if (imGui.getIO().isKeySuper()) {
				imGui.text("[Super]");
				imGui.sameLine();
			}
			if (imGui.getIO().isKeyShift()) {
				imGui.text("[Shift]");
				imGui.sameLine();
			}
		});
	}
}