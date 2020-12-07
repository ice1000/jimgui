package org.ice1000.jimgui.features;

import org.ice1000.jimgui.flag.JImTableFlags;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.ice1000.jimgui.tests.JImGuiTest.useAlternativeJniLibAndCheckHeadless;

public class Tables {
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
		JImGuiUtil.runWithinPer(114514, 15, imGui -> {
			if (imGui.beginTable("Table1", 2,
					JImTableFlags.Borders | JImTableFlags.Resizable | JImTableFlags.Reorderable)) {
				imGui.tableNextRow();
				imGui.tableNextColumn();
				imGui.text("Upper Left");
				imGui.tableNextColumn();
				imGui.text("Upper Right");

				imGui.tableNextColumn();
				imGui.text("Lower Left");
				imGui.tableNextColumn();
				imGui.text("Lower Right");
				imGui.endTable();
			}
		});
	}
}
