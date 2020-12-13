package dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.jetbrains.annotations.NotNull;

public class TixyLand implements TestBed {
  @Override public void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 sz, ImVec4 mouse, float t) {
    int i = 0;
    for (int y = 0; y <= sz.y * 0.2; y++) {
      for (int x = 0; x <= sz.x * 0.2; x++, i++) {
        //float v = cos(t * cos(i * 2)) * cos(i * cos(x * 2));
        float v = (float) (Math.tan(t * cos(i * 2)) * sin(i * cos(x * 2)));
        v = ImClamp(v, -1.f, 1.f);
        d.addCircleFilled(x * 10 + a.x, y * 10 + a.y, 5 * Math.abs(v), (v > 0.f) ? 0xFFFFFFFF : 0xFF0000FF, 12);
      }
    }
  }
}
