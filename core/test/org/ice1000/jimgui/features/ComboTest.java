package org.ice1000.jimgui.features;

import org.ice1000.jimgui.NativeInt;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.ice1000.jimgui.tests.JImGuiTest.useAlternativeJniLibAndCheckHeadless;

public class ComboTest {
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
		final int totalMillis = 6000;
		NativeInt currentItem = new NativeInt();
		JImGuiUtil.runWithinPer(totalMillis, 15, imGui -> {
			imGui.combo("Wtf", currentItem, "Java\0Kotlin\0Clojure\0Ceylon\0Scala\0");
			imGui.text("Selected: " + currentItem);
		});
		currentItem.deallocateNativeObject();
	}
}