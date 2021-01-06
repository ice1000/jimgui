package dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.jetbrains.annotations.NotNull;

public class Guitar implements TestBed {
  private int pk = 0;
  public static final int C1 = 0xA7A830FF;
  public static final int C2 = 0x775839FF;
  private final float[] amp = new float[6];
  private float ml;

  @Override public void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 s, ImVec4 m, float t) {
    if (m.z > -1) pk |= 2;
    m.y *= s.y;
    m.x *= s.x;
    m.y += a.y;
    m.x += a.x;
    float st = sin(10 * t), th, y, w;
    d.addRectFilledMultiColor(a.x, a.y + 40, b.x, b.y - 40, C1, C1, C2, C2);
    for (float i = a.x + 10, j = 20; i < b.x; i += (j += 10))
      d.addRectFilled(i, a.y + 38, i + 8, b.y - 38, 0xFF888888, 8);
    for (int i = 0; i < 6; i++) {
      y = a.y + 48 + i * 16.6f;
      th = 4 - i * .5f;
      w = 1;
      int N = 10;
      for (int j = 0; j < N; j++) {
        float x = a.x + j * s.x / N, k = (w *= -1) * amp[i] * st;
        d.addBezierCubic(x, y + k, x + 10, y + k, x + s.x / N - 10, y - k, x + s.x / N, y - k, -1, th);
      }
      amp[i] *= 0.9;
      if (pk == 3) {
        float A = ml, B = m.y;
        if ((A <= y && B > y) || (A >= y && B < y)) amp[i] += A - B;
      }
    }
    ml = m.y;
    if ((pk >>= 1) != 0) d.addTriangleFilled(m.x, m.y, m.x - 15, m.y - 15, m.x + 10, m.y - 25, 0xFF0000FF);
  }
}
