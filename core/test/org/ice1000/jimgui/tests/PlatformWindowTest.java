package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.NativeFloat;
import org.ice1000.jimgui.util.JImGuiUtil;
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
		NativeFloat nativeFloat = new NativeFloat();
		JImGuiUtil.runWithinPer(10000, 16, gui -> {
			gui.sliderFloat("Value", nativeFloat, 0, 1000);
			if (gui.button("Set Window")) {
				gui.setPlatformWindowSize(nativeFloat.accessValue(), nativeFloat.accessValue());
			}
		});
		nativeFloat.deallocateNativeObject();
	}
}
