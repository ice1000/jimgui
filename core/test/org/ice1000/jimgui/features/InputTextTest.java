package org.ice1000.jimgui.features;

import org.ice1000.jimgui.*;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.junit.BeforeClass;

import static org.ice1000.jimgui.tests.JImGuiTest.useAlternativeJniLibAndCheckHeadless;

public class InputTextTest {
  @BeforeClass public static void setup() {
    useAlternativeJniLibAndCheckHeadless();
  }

  public static void main(String... args) {
    JniLoader.load();
    JImGuiUtil.cacheStringToBytes();
    try (JImGui imGui = new JImGui();
         NativeString multiline = new NativeString();
         NativeFloat width = new NativeFloat();
         NativeFloat height = new NativeFloat();
         NativeBool bool = new NativeBool();
         NativeString withHint = new NativeString()) {
      width.modifyValue(300);
      height.modifyValue(200);
      try (JImFontConfig config = new JImFontConfig()) {
        JImFontAtlas fontAtlas = imGui.getIO().getFonts();
        fontAtlas.clearFonts();
        config.setSizePixels(26);
        fontAtlas.addDefaultFont(config);
      }
      imGui.getStyle().scaleAllSizes(2);
	    while (!imGui.windowShouldClose()) {
        imGui.initNewFrame();
        imGui.sliderFloat("Width", width, 100, 500);
        imGui.sliderFloat("Height", height, 100, 300);
        bool.modifyValue(imGui.inputTextMultiline("InputTextMultiline",
            multiline,
            width.accessValue(),
            height.accessValue()));
        imGui.checkbox("Return value of InputTextMultiline", bool);
        imGui.text("Text length: " + multiline.length());
        if (multiline.length() != 0) {
          imGui.text("First char: " + multiline.charAt(0));
          if (imGui.button("Upshift the first character")) multiline.setByteAt(0, (byte) (multiline.byteAt(0) + 1));
          if (imGui.button("Downshift the first character")) multiline.setByteAt(0, (byte) (multiline.byteAt(0) - 1));
        }
        if (imGui.button("Clear multiline text")) multiline.clear();
        imGui.inputTextWithHint("InputTextWithHint", "This is the hint", withHint);
        imGui.render();
      }
    }
  }
}
