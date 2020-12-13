package dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.jetbrains.annotations.NotNull;

public class DrivingGame implements TestBed {
  private float mx = -1;

  @Override public void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 sz, ImVec4 m, float t) {
    if (mx == -1) mx = a.x + 160;
    float dy = 36, dt = 8;
    int i = (dt * t % 2) < 1 ? 1 : 0;
    float v = (dt * t % 1), y = a.y - dy + v * dy;
    for (float s = 1 + sz.y / dy; s > 0; --s, y += dy) {
      float c = sin(t + v / dy - s / dy) * 40;
      ImVec2 tl = new ImVec2(c + a.x + sz.x / 2 - 64, y);
      ImVec2 br = new ImVec2(c + a.x + sz.x / 2 + 64, y + dy);
      d.addRectFilled(tl.x, tl.y, br.x, br.y, ((++i & 1) != 0) ? 0xffffffff : 0xff0000ff);
      tl.x += 8;
      br.x -= 8;
      d.addRectFilled(tl.x, tl.y, br.x, br.y, 0xff3f3f3f);
    }
    if (m.z >= 0) mx--;
    if (m.w >= 0) mx++;
    d.addRectFilled(mx - 8, b.y - sz.y / 4 - 15, mx + 8, b.y - sz.y / 4 + 15, 0xff00ff00, 4);
    d.addRectFilled(mx - 7, b.y - sz.y / 4 - 8, mx + 7, b.y - sz.y / 4 + 12, 0xff007f00, 4);
    d.addRectFilled(mx - 6, b.y - sz.y / 4 + 12, mx - 2, b.y - sz.y / 4 + 14, 0xff0000ff);
    d.addRectFilled(mx + 2, b.y - sz.y / 4 + 12, mx + 6, b.y - sz.y / 4 + 14, 0xff0000ff);
  }
}
