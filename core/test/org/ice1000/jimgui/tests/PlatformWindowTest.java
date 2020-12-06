package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.NativeFloat;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.ice1000.jimgui.tests.JImGuiTest.useAlternativeJniLibAndCheckHeadless;

@TestOnly
public class PlatformWindowTest {
	@BeforeClass
	public static void setup() {
		useAlternativeJniLibAndCheckHeadless();
	}

	@Test
	public void testPlatformWindow() {
		main();
	}

	public static void main(String @NotNull ... args) {
		JniLoader.load();
		NativeFloat size = new NativeFloat();
		NativeFloat pos = new NativeFloat();
		final boolean[] init = {false};
		JImGuiUtil.runWithinPer(50000, 16, gui -> {
			if (!init[0]) {
				init[0] = true;
				size.modifyValue(Math.min(900, gui.getPlatformWindowSizeX()));
				pos.modifyValue(Math.min(900, gui.getPlatformWindowPosX()));
			}
			gui.sliderFloat("Size", size, 0, 1000);
			gui.sliderFloat("Pos", pos, 0, 1000);
			if (gui.button("Set Window Size")) {
				float value = size.accessValue();
				gui.setPlatformWindowSize(value, value);
			}
			if (gui.button("Set Window Pos")) {
				float value = pos.accessValue();
				gui.setPlatformWindowSize(value, value);
			}
		});
		size.deallocateNativeObject();
	}
}
