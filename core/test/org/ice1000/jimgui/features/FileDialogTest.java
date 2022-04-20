package org.ice1000.jimgui.features;

import org.ice1000.jimgui.*;
import org.ice1000.jimgui.flag.JImFDStyleFlags;
import org.ice1000.jimgui.tests.Sandbox;
import org.ice1000.jimgui.util.JniLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FileDialogTest {
  public static void main(String... args) {
    JniLoader.load();
    List<NativeString> strings = new ArrayList<>();
    try (JImGui imGui = new JImGui(); NativeBool modal = new NativeBool()) {
      imGui.getIO().getFonts().addFontDefault();
      JImFileDialog.loadIcons(15);
      JImStr key = new JImStr("deep_dark_fantasy");
      JImStr title = new JImStr(JImFileDialog.Icons.FOLDER_OPEN + " Choose a Java file");
      JImStr filter = new JImStr(".md");
      JImStr pwd = new JImStr(".");
      JImFileDialog instance = new JImFileDialog();
      while (!imGui.windowShouldClose()) {
        imGui.initNewFrame();
        imGui.checkbox("Use modal dialog", modal);
        instance.setFileStyle(JImFDStyleFlags.ByExtension, ".java", Sandbox.fromAWT(Color.ORANGE), "[Java]");
        instance.setFileStyle(JImFDStyleFlags.ByExtension, ".md", Sandbox.fromAWT(Color.BLUE), JImFileDialog.Icons.FILE_PIC.toString());
        if (imGui.button("Open dialog")) {
          if (modal.accessValue()) instance.openModal(key, title, filter, pwd, 2);
          else instance.openDialog(key, title, filter, pwd, 2);
        }
        if (instance.display(key)) {
          if (instance.isOk()) try (NativeString currentPath = instance.currentPath();
                                    NativeStrings selectedFiles = instance.selections()) {
            imGui.text(currentPath);
            imGui.text("Size: " + selectedFiles.size());
            if (!selectedFiles.isEmpty()) {
              for (NativeString string : strings) string.deallocateNativeObject();
              strings.clear();
              for (int i = 0, size = selectedFiles.size(); i < size; i++) strings.add(selectedFiles.get(i));
            }
          }
          instance.close();
        }
        imGui.text("Selections:");
        for (NativeString string : strings) {
          imGui.text(string);
          imGui.sameLine();
          imGui.text(", size = " + string.length());
        }
        // instance.drawBookmarkPane(100, 100);
        imGui.render();
      }
      instance.deallocateNativeObject();
    }
  }
}
