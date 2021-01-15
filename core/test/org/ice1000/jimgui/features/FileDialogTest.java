package org.ice1000.jimgui.features;

import org.ice1000.jimgui.*;
import org.ice1000.jimgui.tests.Sandbox;
import org.ice1000.jimgui.util.JniLoader;

import java.awt.*;

public class FileDialogTest {
  public static void main(String... args) {
    JniLoader.load();
    try (JImGui imGui = new JImGui(); NativeBool modal = new NativeBool()) {
	    imGui.getIO().getFonts().addFontDefault();
      JImFileDialog.loadIcons(15);
      JImStr key = new JImStr("deep_dark_fantasy");
      JImStr title = new JImStr(JImFileDialog.Icons.FOLDER_OPEN + " Choose a Java file");
      JImStr filter = new JImStr(".java");
      JImStr pwd = new JImStr(".");
      JImFileDialog instance = new JImFileDialog();
      while (!imGui.windowShouldClose()) {
        imGui.initNewFrame();
        imGui.checkbox("Use modal dialog", modal);
        instance.setExtensionInfo(".java", Sandbox.fromAWT(Color.ORANGE), "[Java]");
        if (imGui.button("Open dialog")) {
          if (modal.accessValue()) instance.openModal(key, title, filter, pwd);
          else instance.openDialog(key, title, filter, pwd);
        }
        if (instance.display(key)) {
          if (instance.isOk()) {
            try (NativeString currentPath = instance.currentPath()) {
              System.out.println(currentPath);
            }
          }
          instance.close();
        }
        // instance.drawBookmarkPane(100, 100);
        imGui.render();
      }
      instance.deallocateNativeObject();
    }
  }
}
