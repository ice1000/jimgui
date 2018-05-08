package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JImGuiIO;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.ice1000.jimgui.tests.JImGuiTest.useAlternativeJniLibAndCheckHeadless;

public class JImGuiIOTest {
	@BeforeClass
	public static void setup() {
		useAlternativeJniLibAndCheckHeadless();
	}

	@Test
	public void testBinding() {
		JImGuiUtil.runWithinPer(8000, 16, imGui -> {
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