package dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.ice1000.jimgui.flag.JImDrawCornerFlags;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class QuickSortVisualization implements TestBed {
  private int N = 64, S = 0, J = 0;
  private final int[] v = new int[N];
  private final Random random = new Random(System.currentTimeMillis());
  private final Deque<int[]> st = new ArrayDeque<>();

  {
    for (; J < N; J++) v[J] = random.nextInt(180);
    st.addLast(new int[]{0, N - 1, 0, 0});
  }

  private int I() {
    return st.getLast()[2];
  }

  private void swap(int[] v, int x, int y) {
    int t = v[x];
    v[x] = v[y];
    v[y] = t;
  }

  @Override public void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 s, ImVec4 mouse, float t) {
    float bs = s.x / N, y, c;
    for (int i = 0; i < N; i++) {
      y = a.y + v[i];
      c = 70 + v[i];
      d.addRectFilled(a.x + bs * i, y, a.x + bs * (i + 1), b.y, IM_COL32(c, 255 - c, 255, 255));
    }
    d.addText(a.x, a.y, -1, "Quicksort");
    if (st.isEmpty()) return;
    d.addRect(a.x + bs * st.getLast()[0],
        a.y,
        a.x + bs * (st.getLast()[1] + 1),
        b.y,
        0xFF00FF00,
        8,
        JImDrawCornerFlags.Top,
        2);
    switch (S) {
      case 0:
      case 5:
        if (st.getLast()[0] >= st.getLast()[1]) {
          st.removeLast();
          S += 3;
        } else {
          st.getLast()[2] = J = st.getLast()[0];
          S++;
        }
        break;
      case 1:
      case 6:
        if (v[J] > v[st.getLast()[1]]) {
          swap(v, I(), J);
          st.getLast()[2]++;
        }
        if (++J > st.getLast()[1]) {
          swap(v, I(), st.getLast()[1]);
          S++;
        }
        break;
      case 2:
      case 7:
        st.addLast(new int[]{st.getLast()[0], I() - 1, st.getLast()[0], 3});
        S = 0;
        break;
      case 3:
        st.addLast(new int[]{I() + 1, st.getLast()[1], st.getLast()[0], 8});
        S = 5;
        break;
      case 8:
        S = st.getLast()[3];
        st.removeLast();
    }
  }

  public static void main(String[] args) {
    new QuickSortVisualization().launch();
  }
}
