package org.ice1000.jimgui.features;

import org.ice1000.jimgui.JImFileDialog;
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.util.JniLoader;

public class FileDialogTest {
  public static void main(String... args) {
    JniLoader.load();
    try (JImGui imGui = new JImGui()) {
      imGui.initBeforeMainLoop();
      JImFileDialog.loadIcons(16);
      while (!imGui.windowShouldClose()) {
        imGui.initNewFrame();
        if (imGui.button("Open dialog"))
          JImFileDialog.INSTANCE.openDialog("deep_dark_fantasy", "Choose a Java file", ".java", ".");
        if (JImFileDialog.INSTANCE.fileDialog("deep_dark_fantasy")) {
        }
        imGui.render();
      }
    }
  }
}
