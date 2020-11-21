package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.NativeFloat;
import org.ice1000.jimgui.NativeInt;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.ice1000.jimgui.tests.JImGuiTest.useAlternativeJniLibAndCheckHeadless;

public class SliderTest {
	@BeforeClass
	public static void setup() {
		useAlternativeJniLibAndCheckHeadless();
	}

	@Test
	public void testSandbox() {
		main();
	}

	public static void main(String @NotNull ... args) {
		JniLoader.load();
		final int totalMillis = 1006000;
		NativeInt nativeInt = new NativeInt();
		NativeFloat nativeFloat = new NativeFloat();
		nativeInt.modifyValue(20);
		nativeFloat.modifyValue(40);
		JImGuiUtil.runWithinPer(totalMillis, 15, imGui -> {
			imGui.sliderInt("TIZ", nativeInt, 0, 100);
			imGui.sliderFloat("TCL", nativeFloat, 0, 100);
			imGui.text("Int: " + nativeInt);
			imGui.text("Float: " + nativeFloat);
		});
		nativeInt.deallocateNativeObject();
	}
}
