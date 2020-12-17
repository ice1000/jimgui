package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JImFont;
import org.ice1000.jimgui.JImFontAtlas;
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.NativeFloat;
import org.ice1000.jimgui.util.JniLoader;

import java.net.URISyntaxException;

public class Issue14 {
  public static void main(String... args) throws InterruptedException, URISyntaxException {
    JniLoader.load();
    long initialTime = System.currentTimeMillis();
    try (NativeFloat fontSize = new NativeFloat(); JImGui imGui = new JImGui()) {
      fontSize.modifyValue(18);
      JImFontAtlas fonts = imGui.getIO().getFonts();
      //fonts.addFontDefault();
      JImFont fontFromFile = fonts.addFontFromFile(Issue14.class.getResource("/font/sarasa-gothic-sc-regular.ttf")
          .toURI()
          .getPath(), 18, fonts.getGlyphRangesForChineseSimplifiedCommon());
      long latestRefresh = System.currentTimeMillis();
	    while (!imGui.windowShouldClose()) {
        long currentTimeMillis = System.currentTimeMillis();
        //if (currentTimeMillis - initialTime > 8000) break;
        long deltaTime = currentTimeMillis - latestRefresh;
        Thread.sleep(deltaTime / 2);
        if (deltaTime > (long) 15) {
          imGui.initNewFrame();
          JImFont font = imGui.findFont();
          if (font != null) {
            font.setFontSize(fontSize.accessValue());
            imGui.text("卧槽" + "哈哈");
          } else imGui.text("Not found");
          imGui.dragFloat("Font size", fontSize);
          imGui.showFontSelector("Wtf");
          //imGui.showStyleSelector("asd");
          imGui.render();
          latestRefresh = currentTimeMillis;
        }
      }
    }
  }
}
