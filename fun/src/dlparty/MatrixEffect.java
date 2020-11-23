package dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.jetbrains.annotations.NotNull;

public class MatrixEffect implements TestBed {
	static int S = 0x1234;
	static float t0 = -1;

	static class MM {
		int y, h, c;
		float t, s;
	}

	MM[] m = new MM[40];

	{
		for (int i = 0; i < m.length; i++) m[i] = new MM();
	}

	@Override
	public void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 sz, ImVec4 mouse, float t) {
		if (t0 == -1) t0 = t;
		float ZI = t * .07f, Z = ZI + 1.f;
		for (int x = 0; x < 40; x++) {
			MM M = m[x];
			boolean i = x >= 15 && x < 25;
			if (M.s == 0.f || M.y > 16) {
				M.h = b2i(t < 7.f || i) * ((int) (2 + t * .5f) + S % (int) (6 + (t * 0.3f)));
				M.y = b2i(M.s == 0.f) * -(S % 15) - M.h;
				M.c += S;
				M.s = (5 + (S % 14)) * (.01f - t * .001f);
				if (t > 5.f && i) {
					M.c = (int) ((340003872375972L >> (x * 5 - 75)) & 31);
					M.h = b2i(x != 19);
				}
			}
			if ((M.t -= t - t0) < 0.f) {
				if (t < 6.f || !i || M.y != 6) M.y++;
				M.t += M.s;
			}
			char c = (char) (64 | M.c % 42);
			for (int j = 0; j < M.h; j++, c = (char) (64 | (c ^ M.c + M.h ^ j) % 42))
				for (int f = (j + 1 == M.h) ? 13 : 4 + (M.c & 1); f-- != 0; )
					d.addText(13 * (i ? Z : -Z), a.x - (sz.x * .5f * ZI) + x * 8 * Z + sin(j + t * f), a.y - (sz.y * .5f * ZI) + (M.y + j) * 13 * Z + (float) cos(x * f - t), 0x3c68bb5b, String.valueOf(c));
			S |= ((S & 1) ^ ((S & 8) >> 2)) << 16;
			S >>= 1;
		}
		t0 = t;
	}

	private int b2i(boolean b) {
		return b ? 1 : 0;
	}

	public static void main(String[] args) {
		new MatrixEffect().launch();
	}
}
