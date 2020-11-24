package dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class AbstractFireworks implements TestBed {
	public static class P {
		float x, y, vx, vy, a;
		int l, s, f, d;
	}

	private final Random random = new Random(System.currentTimeMillis());
	private final P[] p = new P[8192];

	{
		for (int i = 0; i < p.length; i++) p[i] = new P();
	}

	void A(ImVec2 o, int f) {
		P t = new P();
		t.x = o.x;
		t.y = o.y;
		t.vx = (random.nextFloat() * 32767) * 3 - 1.5f;
		t.vy = (random.nextFloat() * 32767) * 2.5f - (f != 0 ? 4 : 1);
		t.l = random.nextInt(f != 0 ? 50 : 99);
		t.s = random.nextInt();
		t.f = f;
		for (int j = 0; j < 9; j++) {
			t.d = j;
			t.a = 1 - (float) j / 9;
			for (int i = 0; i < 8192; i++)
				if (0 == p[i].l) {
					p[i] = t;
					break;
				}
		}
	}

	@Override
	public void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 sz, ImVec4 mouse, float t0) {
		int g = random.nextInt();
		for (int i = 200; i > 0; --i)
			d.addLine(a.x, a.y + i, b.x, a.y + i, IM_COL32(0, 0, i, 255));
		int e = 0;
		for (int i = 0; i < 8192; i++) {
			P c = p[i];
			if (c.l != 0) {
				if (c.d != 0) c.d--;
				else {
					random.setSeed(c.s);
					c.x += c.vx;
					c.y += c.vy;
					c.vy += .04;
					c.l -= (c.vy > 0) ? 1 : 0;
					float s = ((random.nextFloat() * 32767) * 3 + .1f) * (c.f + 1);
					float n = (t0 * (c.l < 0 ? 0 : 1)) + (i * (random.nextFloat() * 32767) * 2.5f - 1.5f);
					float sa = sin(n) * s;
					float ca = cos(n) * s;
					ImVec2[] p = new ImVec2[4];
					for (int j = 0; j < 4; j++) {
						int bla = (j & 1 ^ j >> 1) != 0 ? 1 : -1;
						p[j] = new ImVec2(0, 0);
						p[j].x = (sa * bla) + (ca * ((j > 1) ? 1 : -1)) + c.x + a.x;
						p[j].y = (ca * bla) + (-sa * ((j > 1) ? 1 : -1)) + c.y + a.y;
					}
					int red = 128 + (random.nextInt() & 127);
					int green = 128 + (random.nextInt() & 127);
					int blue = 128 + (random.nextInt() & 127);
					// d.addConvexPolyFilled(p, c.f != 0 ? 4 : 3, IM_COL32(red, green, blue, (c.f != 0 ? 255 : c.l) * c.a));
					if (0 == c.l && 0 != c.f && c.a == 1) {
						int l = random.nextInt(40) + 15;
						for (int j = 0; j < l; j++)
							A(new ImVec2(c.x, c.y), 0);
					}
				}
				if (c.f != 0)
					e++;
			}
		}
		random.setSeed(g);
		if (e < 512) A(new ImVec2((random.nextFloat() * 32767) * 420 - 50, 200), 1);
	}
}
