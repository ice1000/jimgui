package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.*;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.ice1000.jimgui.tests.JImGuiTest.useAlternativeJniLibAndCheckHeadless;

public class JImFontTest {
  @BeforeClass public static void setup() {
    useAlternativeJniLibAndCheckHeadless();
  }

  @Test public void testSandbox() throws InterruptedException {
    main();
  }

  public static void main(String @NotNull ... args) throws InterruptedException {
    JniLoader.load();
    long initialTime = System.currentTimeMillis();
    try (NativeFloat fontSize = new NativeFloat(); JImGui imGui = new JImGui()) {
      fontSize.modifyValue(18);
      JImFontAtlas fonts = imGui.getIO().getFonts();
      fonts.addFontDefault();
      JImFont fontFromFile = fonts.addFontFromFile("core/testRes/font/FiraCode-Regular.ttf", 18);
      if (fontFromFile.isLoaded()) System.out.println(fontFromFile.getDebugName());
      long latestRefresh = System.currentTimeMillis();
	    while (!imGui.windowShouldClose()) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - initialTime > 8000) break;
        long deltaTime = currentTimeMillis - latestRefresh;
        if (deltaTime > (long) 15) {
          imGui.initNewFrame();
          JImFont font = imGui.findFont();
          if (font != null) {
            font.setFontSize(fontSize.accessValue());
            final NativeString string = font.debugName();
            if (string.isNull()) imGui.text("Dunno");
            else imGui.text(string);
          } else imGui.text("Not found");
          imGui.dragFloat("Font size", fontSize);
          imGui.showFontSelector("Wtf");
          imGui.pushFont(imGui.getIO().getFontDefault());
          imGui.text("Default font");
          imGui.popFont();
          imGui.pushFont(fontFromFile);
          imGui.text("FiraCode font");
          imGui.popFont();
          imGui.render();
          latestRefresh = currentTimeMillis;
        }
      }
    }
  }
}
