package dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class InterwebBlogosphere implements TestBed {
  float l2(ImVec2 x) {
    return x.x * x.x + x.y * x.y;
  }

  private final Random random = new Random(System.currentTimeMillis());
  private final Pair<ImVec2, ImVec2>[] v = new Pair[300];

  {
    for (int i = 0, vLength = v.length; i < vLength; i++) {
      ImVec2 vec2 = newRandom();
      v[i] = new Pair<>(vec2, new ImVec2(vec2));
    }
  }

  @Contract(" -> new") @NotNull private ImVec2 newRandom() {
    return new ImVec2(random.nextInt(320), random.nextInt(180));
  }

  @Override public void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 s, ImVec4 mouse, float t) {
    float D, T;
    for (Pair<ImVec2, ImVec2> p : v) {
      D = (float) Math.sqrt(l2(p.first.sub(p.second)));
      if (D > 0) {
        ImVec2 sub = p.second.sub(p.first);
        p.first.x += sub.x / D;
        p.first.y += sub.y / D;
      }
      if (D < 4) p.second = newRandom();
    }
    for (int i = 0; i < v.length; i++) {
      for (int j = i + 1; j < v.length; j++) {
        D = l2(v[i].first.sub(v[j].first));
        T = l2(v[i].first.add(v[j].first).sub(s)) / 200;
        if (T > 255) T = 255;
        if (D < 400) {
          ImVec2 aa = a.add(v[i].first);
          ImVec2 bb = a.add(v[j].first);
          d.addLine(aa.x, aa.y, bb.x, bb.y, IM_COL32(T, 255 - T, 255, 70), 2);
        }
      }
    }
  }

  public static void main(String[] args) {
    new InterwebBlogosphere().launch();
  }
}
