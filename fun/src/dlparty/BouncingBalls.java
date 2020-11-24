package dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static java.lang.Math.sqrt;

public class BouncingBalls implements TestBed {
	public static final int N = 400;
	private int cu = 1;
	ImVec4[] c = new ImVec4[N];
	ImVec2[] v = new ImVec2[N];
	ImVec2[] P = new ImVec2[N];
	float[] r = new float[400];
	private float a = .02f, t = 10;
	private final Random random = new Random(System.currentTimeMillis());

	private float R(float t) {
		return random.nextFloat() * t;
	}

	{
		r[0] = 10;
		for (int i = 0; i < N; i++) {
			c[i] = new ImVec4(0, 0, 0, 0);
			v[i] = new ImVec2(0, 0);
			P[i] = new ImVec2(0, 0);
		}
		P[0] = new ImVec2(100, 80);
	}

	@Override
	public void fx(@NotNull JImDrawList d, ImVec2 p, ImVec2 bbb, ImVec2 s, ImVec4 m, float tt) {
		int n = -1;
		for (int i = 0; i < cu; i++) {
			if (m.z < 0) {
				v[i].y = v[i].y + a * r[i];
				P[i].y = P[i].y + v[i].y;
				P[i].x = P[i].x + v[i].x;
				if (P[i].y + r[i] > s.y) {
					v[i].y = -v[i].y;
					P[i].y = s.y - r[i];
					n = i;
				}
				if (P[i].x + r[i] > s.x || P[i].x - r[i] <= 0) {
					v[i].x = -v[i].x;
				}
			}
			float e = P[i].x - m.x * s.x, f = P[i].y - m.y * s.y;
			int ii = (int) Math.max(255 - sqrt(e * e + f * f), 0);
			for (int j = 0; j <= t; j++) {
				float sc = j / t;
				int color = IM_COL32(c[i].x * ii, c[i].y * ii, c[i].z * ii, sc * 255);
				d.addCircleFilled(P[i].x + p.x + v[i].x * sc * 4, P[i].y + p.y + v[i].y * sc * 4, r[i], color);
			}
		}
		if (n > -1 && (cu < N - 1)) {
			v[cu].x = v[n].x + R(8) - 4;
			v[cu].y = v[n].y;
			P[cu] = new ImVec2(P[n]);
			r[cu] = R(10) + 2;
			c[cu] = new ImVec4(R(1), R(1), R(1), 0);
			cu++;
		}
	}

	public static void main(String[] args) {
		new BouncingBalls().launch();
	}
}
