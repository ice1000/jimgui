package org.ice1000.jimgui.features;

import org.ice1000.jimgui.JImStr;
import org.ice1000.jimgui.NativeBool;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.ice1000.jimgui.tests.JImGuiTest.useAlternativeJniLibAndCheckHeadless;

public class ToggleButtonTest {
	@BeforeClass
	public static void setup() {
		useAlternativeJniLibAndCheckHeadless();
	}

	@Test
	public void testSandbox() {
		main();
	}

	public static void main(String... args) {
		JniLoader.load();
		final int totalMillis = 6000;
		NativeBool pOpen = new NativeBool();
		JImStr ok = new JImStr("Checked");
		JImStr not = new JImStr("Unchecked");
		pOpen.modifyValue(false);
		JImGuiUtil.runWithinPer(totalMillis, 15, imGui -> {
			imGui.toggleButton("my_id", pOpen);
			imGui.text(pOpen.accessValue() ? ok : not);
		});
		pOpen.deallocateNativeObject();
	}
}
