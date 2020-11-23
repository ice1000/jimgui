package org.ice1000.jimgui.dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.jetbrains.annotations.NotNull;

public class Blobs implements TestBed {
	@Override
	public void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 s, ImVec4 m, float t) {
		int N = 25;
		float sp = s.y / N, y, st = (float) (sin(t) * 0.5 + 0.5);
		float[] r = {1500, 1087 + 200 * st, 1650};
		float[][] ctr = {{150, 140}, {s.x * m.x, s.y * m.y},
				{40 + 200 * st, 73 + 20 * sin(st * 5)}};
		for (int i = 0; i < N; i++) {
			y = a.y + sp * (i + .5f);
			for (int x = (int) a.x; x <= b.x; x += 2) {
				float D = 0, o = 255;
				for (int k = 0; k < 3; k++)
					D += r[k] / (pow(x - a.x - ctr[k][0], 2) + pow(y - a.y - ctr[k][1], 2));
				if (D < 1) continue;
				if (D > 2.5f) D = 2.5f;
				if (D < 1.15f) o /= 2;
				d.addLine(x, y, x + 2, y, IM_COL32(239, 129, 19, o), D + sin(2.3f * t + 0.3f * i));
			}
		}
	}

	public static void main(String[] args) {
		new Blobs().launch();
	}
}
