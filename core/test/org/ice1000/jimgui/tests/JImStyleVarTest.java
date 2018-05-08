package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JImStyleVar;
import org.ice1000.jimgui.util.JImGuiUtil;

import static org.ice1000.jimgui.tests.JImGuiTest.useAlternativeJniLibAndCheckHeadless;
import static org.junit.Assert.assertEquals;

public class JImStyleVarTest {
	public static void main(String... args) {
		useAlternativeJniLibAndCheckHeadless();
		JImGuiUtil.run(imGui -> {
			imGui.pushStyleVar(JImStyleVar.WindowMinSize, 300, 300);
			imGui.pushStyleVar(JImStyleVar.Alpha, 0.5f);
			imGui.begin("What =_=?");
			if (imGui.button("I love Reiuji Utsuho forever QAQ")) imGui.text("Don't touch me there! >_<");
			imGui.end();
			imGui.popStyleVar();
			imGui.popStyleVar();
			assertEquals(1, 1);
		});
	}
}