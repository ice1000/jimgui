package dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Mosaic implements TestBed {
	private final Random random = new Random(System.currentTimeMillis());

	private float hash(long x) {
		random.setSeed(x);
		return ImLerp(random.nextFloat() * 32767.f, random.nextFloat() * 32767.f, 0.5f);
	}

	private float noise(float x) {
		return ImLerp(hash((long) x), hash((long) (x + 2)), x - (int) x);
	}

	@Override
	public void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 sz, ImVec4 mouse, float t) {
		for (int x = 0; x < sz.x / 4; ++x)
			for (int y = 0; y < sz.y / 4; ++y) {
				float rx = x - 80;
				float ry = y - 45;
				double an = Math.atan2(rx, ry);
				an = an < 0 ? (Math.PI * 2 + an) : an;
				float len = (rx * rx + ry * ry + 0.1f) + t;
				float n0 = noise((float) an);
				float n1 = noise(len);
				float al = n0 + n1;
				ImVec2 v = a.add(new ImVec2(x, y).mul(4));
				int c = ThunderStorm.white(al);
				d.addRectFilled(v.x, v.y, v.x + 4, v.y + 4, c);
			}
	}
}
