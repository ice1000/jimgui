package dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static dlparty.Plasma.PI;

/**
 * Named after the user who created it.
 */
public class PossiblyAShrub implements TestBed {
	private final ImVec2[] bs = new ImVec2[1000];

	{
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < 1000; ++i) {
			bs[i] = new ImVec2(
					random.nextFloat() * PI / 2 + PI / 4,
					random.nextFloat() * 50 + 5);
		}
	}

	@Override
	public void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 s, ImVec4 mouse, float t) {
		for (int i = 0; i < 1000; ++i) {
			float
					g = bs[i].x,
					r = bs[i].y;
			r += sin(t + i) * 100;
			ImVec2 center = a
					.add(s.div(2))
					.add(new ImVec2(cos(g) * r, sin(g) * r))
					.add(new ImVec2(r * cos(t), 0));
			d.addCircleFilled(center.x, center.y, i % 2 + 1, IM_COL32(r + 100, 50, Math.abs(r) + 100, 200));
		}
	}

	public static void main(String[] args) {
		new PossiblyAShrub().launch();
	}
}
