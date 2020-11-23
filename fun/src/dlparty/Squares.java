package dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.jetbrains.annotations.NotNull;

public class Squares implements TestBed {
	@Override
	public void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 sz, ImVec4 mouse, float t) {
		float sx = 1.f / 16.f;
		float sy = 1.f / 9.f;
		for (float ty = 0.0f; ty < 1.0f; ty += sy)
			for (float tx = 0.0f; tx < 1.0f; tx += sx) {
				ImVec2 c = new ImVec2((tx + 0.5f * sx), (ty + 0.5f * sy));
				float k = 0.45f;
				d.addRectFilled(
						a.x + (c.x - k * sx) * sz.x, a.y + (c.y - k * sy) * sz.y,
						a.x + (c.x + k * sx) * sz.x, a.y + (c.y + k * sy) * sz.y,
						IM_COL32(ty * 200, tx * 255, 100, 255));
			}
	}

	public static void main(String[] args) {
		new Squares().launch();
	}
}
