package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JImGuiIO;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.ice1000.jimgui.tests.JImGuiTest.useAlternativeJeniLibAndCheckHeadless;

public class JImGuiIOTest {
	@BeforeClass
	public static void setup() {
		useAlternativeJeniLibAndCheckHeadless();
	}

	@Test
	public void testBinding() {
		JImGuiUtil.runWithin(8000, imGui -> {
			JImGuiIO io = imGui.getIO();
			imGui.text("framerate: " + io.getFramerate());
			if (io.isKeyCtrl()) {
				imGui.text("[Ctrl]");
				imGui.sameLine();
			}
			if (io.isKeyAlt()) {
				imGui.text("[Alt]");
				imGui.sameLine();
			}
			if (io.isKeySuper()) {
				imGui.text("[Super]");
				imGui.sameLine();
			}
			if (io.isKeyShift()) {
				imGui.text("[Shift]");
				imGui.sameLine();
			}
		});
	}
}