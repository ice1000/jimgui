package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.glfw.GlfwUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.ice1000.jimgui.tests.JImGuiTest.useAlternativeJniLibAndCheckHeadless;
import static org.junit.Assume.assumeTrue;

public class MultiWindowTest {
  @BeforeClass public static void setup() {
    useAlternativeJniLibAndCheckHeadless();
    assumeTrue(JniLoader.OS.Current == JniLoader.OS.Linux || JniLoader.OS.Current == JniLoader.OS.MacOS);
  }

  @Test public void testSandbox() throws @NotNull NoSuchFieldException, @NotNull IllegalAccessException {
    main();
  }

  public static void main(String @NotNull ... args) throws @NotNull NoSuchFieldException, @NotNull IllegalAccessException {
    JniLoader.load();
    JImGui gui = new JImGui();
    Field declaredField = JImGui.class.getDeclaredField("nativeObjectPtr");
    declaredField.setAccessible(true);
    long nativeObjectPtr = (Long) declaredField.get(gui);
    JImGui gui1 = JImGui.fromExistingPointer(GlfwUtil.createWindowPointer(500, 500, "Hello World", nativeObjectPtr));
	  while (!gui.windowShouldClose() || !gui1.windowShouldClose()) {
      gui.initNewFrame();
      gui.text("This is gui");
      gui.render();
      gui1.initNewFrame();
      gui1.text("This is gui1");
      gui1.render();
    }
  }
}
