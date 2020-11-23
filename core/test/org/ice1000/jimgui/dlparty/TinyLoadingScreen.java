package org.ice1000.jimgui.dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.jetbrains.annotations.NotNull;

import static java.lang.Math.*;

public class TinyLoadingScreen implements TestBed {
	private float z = 0.f;

	@Override
	public void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 s, ImVec4 mouse, float t) {
		if (t > z + 2) z += 2;
		int c = C(z + 2);
		d.addRectFilled(a.x, a.y, b.x, b.y, C(z));
		for (float B = b.x, i = 0.f, o = s.y / 8; i < 8; ++i, B = b.x) {
			float w = (i / 7) * .2f, x = max(t - z - w, 0.f);
			if (t - z < w + 1)
				B = a.x + (x < .5f ? 8 * x * x * x * x : min(1 - pow(-2 * x + 2, 4.f) / 2, 1.f)) * s.x;
			d.addRectFilled(a.x, a.y + o * i, B, a.y + o * i + o, c);
		}
	}

	private int C(float x) {
		return IM_COL32((sin(x) + 1) * 255 / 2, (cos(x) + 1) * 255 / 2, 99, 255);
	}

	public static void main(String[] args) {
		new TinyLoadingScreen().launch();
	}
}
