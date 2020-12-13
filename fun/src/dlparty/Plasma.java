package dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.jetbrains.annotations.NotNull;

public class Plasma implements TestBed {
  public static final float PI = (float) Math.PI;

  private int CO(float c, int b) {
    return ((int) (c * 255) << b);
  }

  private float CL(float x, float l, float h) {
    return x > h ? h : Math.max(x, l);
  }

  @Override public void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 s, ImVec4 m, float t) {
    t *= 3;
    float ix = s.x / 320;
    float iy = s.y / 180;
    float sz = s.x / 15;
    for (float x = a.x; x < b.x; x += ix)
      for (float y = a.y; y < b.y; y += iy) {
        float v = 0;
        v += sin(x / sz + t);
        v += sin((y / sz + t) / 2.0f);
        v += sin((x / sz + y / sz + t) / 2.0f);
        float cx = x / sz / 10 + 0.3f * sin(t / 3.0f);
        float cy = y / sz / 10 + 0.3f * cos(t / 2.0f);
        v += sin((float) (Math.sqrt(100 * (cx * cx + cy * cy + 1)) + t));
        v = CL(v / 2, 0, 1);
        float r = sin(v * PI) * .5f + .5f;
        float g = sin(v * PI + PI / 3) * .5f + .5f;
        float blue = sin(v * PI + PI) * .5f + .5f;
        d.addQuadFilled(x, y, x + ix, y, x + ix, y + iy, x, y + iy, 0xff000000 | CO(blue, 16) | CO(g, 8) | CO(r, 0));
      }
  }
}
