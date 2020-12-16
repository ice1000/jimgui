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
        JImFileDialog instance = JImFileDialog.INSTANCE;
        if (imGui.button("Open dialog"))
          instance.openDialog("deep_dark_fantasy", "Choose a Java file", ".java", ".");
        if (instance.fileDialog("deep_dark_fantasy")) {
          if (instance.isOk()) System.out.println("Ok!");
          instance.closeDialog("deep_dark_fantasy");
        }
        imGui.render();
      }
    }
  }
}
