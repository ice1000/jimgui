package dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImGuiIO;
import org.ice1000.jimgui.JImStr;
import org.ice1000.jimgui.flag.JImMouseButton;
import org.ice1000.jimgui.flag.JImWindowFlags;
import org.ice1000.jimgui.util.ColorUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface TestBed {
  void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 sz, ImVec4 mouse, float t);
  default float sin(float x) {
    return (float) StrictMath.sin(x);
  }
  default float cos(float x) {
    return (float) StrictMath.cos(x);
  }
  default float pow(float x, float y) {
    return (float) StrictMath.pow(x, y);
  }
  default int IM_COL32(int red, int green, int blue, int alpha) {
    return ColorUtil.colorU32(red, green, blue, alpha);
  }
  default int IM_COL32(float red, float green, float blue, float alpha) {
    return ColorUtil.colorU32((int) red, (int) green, (int) blue, (int) alpha);
  }
  default int IM_COL32(double red, double green, double blue, double alpha) {
    return ColorUtil.colorU32((int) red, (int) green, (int) blue, (int) alpha);
  }
  default ImVec2 ImClamp(ImVec2 v, ImVec2 mn, ImVec2 mx) {
    return new ImVec2((v.x < mn.x) ? mn.x : Math.min(v.x, mx.x), (v.y < mn.y) ? mn.y : Math.min(v.y, mx.y));
  }
  default float ImLerp(float a, float b, float t) {
    return a + (b - a) * t;
  }
  default float ImClamp(float v, float mn, float mx) {
    return (v < mn) ? mn : Math.min(v, mx);
  }
  default ImVec2 ImLerp(ImVec2 a, ImVec2 b, float t) {
    return new ImVec2(a.x + (b.x - a.x) * t, a.y + (b.y - a.y) * t);
  }
  default ImVec2 ImLerp(ImVec2 a, ImVec2 b, ImVec2 t) {
    return new ImVec2(a.x + (b.x - a.x) * t.x, a.y + (b.y - a.y) * t.y);
  }
  default ImVec4 ImLerp(ImVec4 a, ImVec4 b, float t) {
    return new ImVec4(a.x + (b.x - a.x) * t, a.y + (b.y - a.y) * t, a.z + (b.z - a.z) * t, a.w + (b.w - a.w) * t);
  }
  final class Pair<A, B> {
    public A first;
    public B second;

    public Pair(A first, B second) {
      this.first = first;
      this.second = second;
    }
  }

  final class ImVec2 {
    public float x;
    public float y;

    @Contract(pure = true) public ImVec2(@NotNull ImVec2 vec2) {
      this(vec2.x, vec2.y);
    }

    @Contract(pure = true) public ImVec2(float x, float y) {
      this.x = x;
      this.y = y;
    }

    public ImVec2 add(ImVec2 o) {
      return new ImVec2(x + o.x, y + o.y);
    }

    public ImVec2 sub(ImVec2 o) {
      return new ImVec2(x - o.x, y - o.y);
    }

    public ImVec2 mul(float i) {
      return new ImVec2(x * i, y * i);
    }

    public ImVec2 div(float i) {
      return new ImVec2(x / 2, y / 2);
    }
  }

  final class ImVec4 {
    public float x;
    public float y;
    public float z;
    public float w;

    @Contract(pure = true) public ImVec4(float x, float y, float z, float w) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.w = w;
    }
  }
  static void launch(TestBed bed) {
    bed.launch();
  }
  default void launch() {
    JniLoader.load();
    try (JImGui imGui = new JImGui(sizeX + 80, sizeY + 120, "FX")) {
      JImStr str = new JImStr("FX");
      imGui.initBeforeMainLoop();
      while (!imGui.windowShouldClose()) {
        imGui.initNewFrame();
        imGui.setNextWindowPos(0, 0);
        testBed(imGui, str);
        imGui.render();
      }
    }
  }
  int sizeX = 320;
  int sizeY = 180;
  @SuppressWarnings("AccessStaticViaInstance") default void testBed(@NotNull JImGui imGui, @NotNull JImStr title) {
    if (imGui.begin(title, JImWindowFlags.NoResize)) {
      JImGuiIO io = imGui.getIO();
      JImDrawList drawList = imGui.getWindowDrawList();
      imGui.invisibleButton("canvas", sizeX, sizeY);
      float minX = imGui.getItemRectMinX();
      float minY = imGui.getItemRectMinY();
      float maxX = imGui.getItemRectMaxX();
      float maxY = imGui.getItemRectMaxY();
      drawList.pushClipRect(minX, minY, maxX, maxY);
      ImVec4 mouse = new ImVec4((io.getMousePosX() - minX) / sizeX,
          (io.getMousePosY() - minY) / sizeY,
          io.mouseDownDurationAt(JImMouseButton.Left),
          io.mouseDownDurationAt(JImMouseButton.Right));
      fx(drawList, new ImVec2(minX, minY), new ImVec2(maxX, maxY), new ImVec2(sizeX, sizeY), mouse, imGui.getTime());
      imGui.end();
    }
  }
}
