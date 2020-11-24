package dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.ice1000.jimgui.JImGuiGen;
import org.ice1000.jimgui.util.ColorUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static java.lang.Math.min;

public class ThunderStorm extends JImGuiGen implements TestBed {
	private float fl;
	private final Random random = new Random(System.currentTimeMillis());

	@Override
	public void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 sz, ImVec4 mouse, float t) {
		if (random.nextInt(500) == 0) fl = t;
		if (t - fl > 0) {
			float ft = 0.25f;
			d.addRectFilled(a.x, a.y, b.x, b.y, white((ft - (t - fl)) / ft));
		}

		for (int i = 0; i < 2000; ++i) {
			int h = getID(String.valueOf(i + (int) (t / 4)));
			float f = (t + ((h / 777.f) % 99)) % 99;
			int tx = h % (int) sz.x;
			int ty = h % (int) sz.y;
			if (f < 1) {
				float py = ty - 1000 * (1 - f);
				d.addLine(a.x + tx, a.y + py, a.x + tx, a.y + min(py + 10, ty), -1);
			} else if (f < 1.2f)
				d.addCircle(a.x + tx, a.y + ty, (f - 1) * 10 + h % 5, white(1 - (f - 1) * 5.f));
		}
	}

	public static int white(float wh) {
		return ColorUtil.colorU32(255, 255, 255, (int) (255 * Math.max(wh, 0)));
	}

	public static void main(String[] args) {
		new ThunderStorm().launch();
	}
}
