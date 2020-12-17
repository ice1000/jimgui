package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.NativeBool;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;

public class Issue66 {
  public static void main(String[] args) {
    JniLoader.load();
    JImGuiUtil.cacheStringToBytes();
    try (JImGui imgui = new JImGui(); NativeBool bool = new NativeBool()) {
      byte[] b = new byte[100];
      imgui.initBeforeMainLoop();
      while (!imgui.windowShouldClose()) {
        imgui.initNewFrame();
        imgui.checkbox("Check to test input, uncheck to test ID", bool);
        if (imgui.button("System.gc()")) System.gc();
        for (int i = 0; i < 150; i++) {
          if (bool.accessValue()) {
            imgui.inputText("Label " + i, b);
          } else {
            imgui.pushID("btn" + i);
            imgui.button("OK");
            imgui.popID();
          }
        }
        imgui.render();
      }
    }
  }
}
