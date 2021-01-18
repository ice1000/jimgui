package org.ice1000.jimgui.features;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.NativeTime;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;

public class DateChooserTest {
  public static void main(String... args) {
    JniLoader.load();
    JImGuiUtil.cacheStringToBytes();
    try (JImGui imGui = new JImGui(); NativeTime time = new NativeTime()) {
      while (!imGui.windowShouldClose()) {
        imGui.initNewFrame();
        imGui.text("Picked text: " + time.toAbsoluteSeconds() * 1000);
        imGui.text("Current time text: " + System.currentTimeMillis());
        if (imGui.dateChooser("Pick a time", time)) imGui.text("Text");
        imGui.render();
      }
    }
  }
}
