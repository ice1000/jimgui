package dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DoomFire implements TestBed {
	private final Random random = new Random(System.currentTimeMillis());
	private final byte[][] T = new byte[90 + 2][163];
	private int n = 0;
	private final int[] C = new int[666];

	{
		int[] init = new int[]{
				0xFFFFFF, 0xC7EFEF, 0x9FDFDF, 0x6FCFCF, 0x37B7B7, 0x2FB7B7, 0x2FAFB7, 0x2FAFBF, 0x27A7BF, 0x27A7BF, 0x1F9FBF, 0x1F9FBF, 0x1F97C7, 0x178FC7, 0x1787C7, 0x1787CF, 0xF7FCF, 0xF77CF, 0xF6FCF, 0xF67D7, 0x75FD7, 0x75FD7, 0x757DF, 0x757DF, 0x74FDF, 0x747C7, 0x747BF, 0x73FAF, 0x72F9F, 0x7278F, 0x71F77, 0x71F67, 0x71757, 0x70F47, 0x70F2F, 0x7071F, 0x70707
		};
		System.arraycopy(init, 0, C, 0, init.length);
	}

	@Override
	public void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 s, ImVec4 mouse, float t) {
		if (20 * t > n) n++;
		else t = 0;
		for (int x = 0; x < 160; x++)
			for (int y = 0; y < 90; y++) {
				d.addRectFilled(a.x + (+x) * s.x / 160, b.y - (+y) * s.y / 90, a.x + (1 + x) * s.x / 160, b.y - (1 + y) * s.y / 90, C[T[y + 1][x]] | 255 << 24);
				int r = 3 & random.nextInt();
				if (t != 0) {
					byte value = (byte) (T[y][x + r - (r > 1 ? 1 : 0)] + (r & 1));
					T[y + 1][x] = value;
				}
			}
	}
}
