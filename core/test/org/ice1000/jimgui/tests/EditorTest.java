package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.*;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;

import java.net.URISyntaxException;

public class EditorTest {
  public static final int LIMIT = 3;

  @SuppressWarnings("AccessStaticViaInstance")
  public static void main(String @NotNull ... args) throws InterruptedException, URISyntaxException {
    JniLoader.load();
    StringBuilder builder = new StringBuilder("输入一些文本");
    int cursor = builder.length();
    try (JImGui gui = new JImGui()) {
      JImGuiIO io = gui.getIO();
      JImFontAtlas fonts = io.getFonts();
      String fontPath = EditorTest.class.getResource("/font/sarasa-gothic-sc-regular.ttf").toURI().getPath();
      int texHeight = 20;
      JImFont sarasaGothic = fonts.addFontFromFile(fontPath,
          texHeight,
          fonts.getGlyphRangesForChineseSimplifiedCommon());
      System.out.println(texHeight);
      long lastMovedTime = 0;
      JImStyle style = gui.getStyle();
      style.setItemSpacingX(2f);
      gui.initBeforeMainLoop();
      while (!gui.windowShouldClose()) {
        long deltaTime = (long) (io.getDeltaTime() * 1000);
        Thread.sleep(LIMIT - deltaTime < 0 ? 0 : (LIMIT - deltaTime));
        gui.initNewFrame();
        char[] inputString = io.getInputChars();
        io.clearInputCharacters();
        builder.insert(cursor, inputString);
        cursor += inputString.length;
        if (gui.isKeyPressed(io.keyMapAt(JImDefaultKeys.LeftArrow)) && cursor > 0) {
          lastMovedTime = System.currentTimeMillis();
          cursor--;
        }
        if (gui.isKeyPressed(io.keyMapAt(JImDefaultKeys.RightArrow)) && cursor < builder.length()) {
          lastMovedTime = System.currentTimeMillis();
          cursor++;
        }
        if (gui.isKeyPressed(io.keyMapAt(JImDefaultKeys.Enter))) builder.insert(cursor++, '\n');
        if (gui.isKeyPressed(io.keyMapAt(JImDefaultKeys.Backspace)) && cursor > 0) builder.deleteCharAt(--cursor);
        gui.begin("Editor");
        int length = builder.length();
        for (int i = 0; i <= length; i++) {
          if (i == cursor && ((System.currentTimeMillis() - lastMovedTime) / 500) % 2 == 0) {
            float cursorPosX = gui.getCursorPosX() + gui.getWindowPosX() - 1;
            float cursorPosY = gui.getCursorPosY() + gui.getWindowPosY();
            gui.windowDrawListAddLine(cursorPosX, cursorPosY, cursorPosX, cursorPosY + texHeight, 0xffffffff, 1.5f);
          }
          if (i == length) break;
          char c = builder.charAt(i);
          if (c != '\n') {
            gui.text(String.valueOf(c));
            gui.sameLine();
          } else gui.newLine();
        }
        gui.end();
        gui.render();
      }
    }
  }
}
