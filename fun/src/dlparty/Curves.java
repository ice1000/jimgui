package dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.jetbrains.annotations.NotNull;

public class Curves implements TestBed {
	@Override
	public void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 sz, ImVec4 mouse, float t0) {
		for (float t = t0; t < t0 + 1.0f; t += 1.0f / 100.0f) {
			ImVec2 cp0 = new ImVec2(a.x, b.y);
			ImVec2 cp1 = new ImVec2(b);
			float ts = t - t0;
			cp0.x += (0.4f + sin(t) * 0.3f) * sz.x;
			cp0.y -= (0.5f + cos(ts * ts) * 0.4f) * sz.y;
			cp1.x -= (0.4f + cos(t) * 0.4f) * sz.x;
			cp1.y -= (0.5f + sin(ts * t) * 0.3f) * sz.y;
			d.addBezierCurve(a.x, b.y, cp0.x, cp0.y, cp1.x, cp1.y, b.x, b.y, IM_COL32(100 + ts * 150, 255 - ts * 150, 60, ts * 200), 5.0f);
		}
	}

	public static void main(String[] args) {
		new Curves().launch();
	}
}
