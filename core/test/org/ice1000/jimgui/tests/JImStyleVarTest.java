package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JImStyleVars;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.ice1000.jimgui.tests.JImGuiTest.useAlternativeJniLibAndCheckHeadless;
import static org.junit.Assert.assertNotNull;

public class JImStyleVarTest {
  @BeforeClass public static void setup() {
    useAlternativeJniLibAndCheckHeadless();
  }

  @Test public void testSandbox() {
    main();
  }

  public static void main(@NotNull String @NotNull ... args) {
    JniLoader.load();
    JImGuiUtil.runWithinPer(2000, 15, imGui -> {
      imGui.pushStyleVar(JImStyleVars.WindowMinSize, 300, 300);
      imGui.pushStyleVar(JImStyleVars.Alpha, 0.5f);
      imGui.begin("What =_=?");
      if (imGui.button("I love Reiuji Utsuho forever QAQ")) imGui.text("Don't touch me there! >_<");
      imGui.end();
      imGui.popStyleVar(2);
      assertNotNull(imGui.findStyle());
    });
  }
}
