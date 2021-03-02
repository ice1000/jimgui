package org.ice1000.jimgui.features;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.NativeString;
import org.ice1000.jimgui.util.JniLoader;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class InputNativeString {
  public static void main(String... args) {
    JniLoader.load();
    try (NativeString s = new NativeString();
         JImGui imGui = new JImGui()) {
      byte[] bytes = "não".getBytes(StandardCharsets.UTF_8);
      for (byte b : bytes) s.append(b);
      while (!imGui.windowShouldClose()) {
        imGui.initNewFrame();
        imGui.inputText("input", s);
        imGui.text(s);
        imGui.text("Size: " + s.length());
        final String str = s.toString();
        imGui.text("Content: " + str);
        imGui.text("Content-Length: " + str.length());
        imGui.text("Bytes: " + Arrays.toString(s.toBytes()));
        imGui.render();
      }
    }
  }
}
