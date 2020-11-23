package dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntBinaryOperator;

public class ThreeDCube implements TestBed {
	@Override
	public void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 s, ImVec4 m, float t) {
		a.x += s.x / 2;
		a.y += s.y / 2;
		float S = sin(m.x);
		float C = cos(m.x);
		float x = 50;
		float y;
		AtomicReference<Float> z = new AtomicReference<>((m.y * 2 - 1) * x);
		float[][] v = {{x, x, z.get() + x}, {x, -x, z.get() + x}, {-x, -x, z.get() + x}, {-x, x, z.get() + x},
				{x, x, z.get() - x}, {x, -x, z.get() - x}, {-x, -x, z.get() - x}, {-x, x, z.get() - x}};
		for (int i = 0; i < 8; i++) {
			x = v[i][0] * C - v[i][1] * S;
			y = v[i][0] * S + v[i][1] * C + 120;
			z.set(v[i][2]);
			v[i][0] = x / y * 50;
			v[i][1] = z.get() / y * 50;
			v[i][2] = y;
		}
		IntBinaryOperator L = (A, B) -> {
			z.set(500 / (v[A][2] + v[B][2]));
			d.addLine(a.x + v[A][0], a.y + v[A][1], a.x + v[B][0], a.y + v[B][1], -1, z.get());
			return 0;
		};
		L.applyAsInt(0, 1);
		L.applyAsInt(1, 2);
		L.applyAsInt(2, 3);
		L.applyAsInt(0, 3);
		L.applyAsInt(4, 5);
		L.applyAsInt(5, 6);
		L.applyAsInt(6, 7);
		L.applyAsInt(4, 7);
		L.applyAsInt(0, 4);
		L.applyAsInt(1, 5);
		L.applyAsInt(2, 6);
		L.applyAsInt(3, 7);
	}
}
