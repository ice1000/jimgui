package org.ice1000.jimgui.features;

import org.ice1000.jimgui.JImStr;
import org.ice1000.jimgui.JImVec4;
import org.ice1000.jimgui.MutableJImVec4;
import org.ice1000.jimgui.NativeBool;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;

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
		final int totalMillis = 8000;
		NativeBool pOpen = new NativeBool();
		JImStr ok = new JImStr("Checked");
		JImStr not = new JImStr("Unchecked");
		JImVec4 color = JImVec4.fromAWT(Color.GREEN);
		pOpen.modifyValue(false);
		JImGuiUtil.runWithinPer(totalMillis, 15, imGui -> {
			imGui.spinner(80, 10, 10, color);
			imGui.toggleButton("my_id", pOpen);
			imGui.text(pOpen.accessValue() ? ok : not);
		});
		pOpen.deallocateNativeObject();
	}
}
