package org.ice1000.jimgui.features;

import org.ice1000.jimgui.NativeString;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.ice1000.jimgui.tests.JImGuiTest.useAlternativeJniLibAndCheckHeadless;

public class SetWindowTitle {
  @BeforeClass public static void setup() {
    useAlternativeJniLibAndCheckHeadless();
  }
  @Test public void test() {
    main();
  }

  public static void main(String... args) {
    JniLoader.load();
    NativeString string = new NativeString();
    JImGuiUtil.runPer(15, imgui -> {
      if (imgui.button("Hey Jude")) imgui.setWindowTitle("Don't make it bad");
      imgui.inputText("Sally can wait", string);
      if (imgui.button("She knows it's too late")) imgui.setWindowTitle(string);
    });
  }
}
