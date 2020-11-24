package dlparty;

import org.ice1000.jimgui.JImDrawList;
import org.jetbrains.annotations.NotNull;

public class RaceTrack implements TestBed {
	private float cz = 0;

	@Override
	public void fx(@NotNull JImDrawList d, ImVec2 a, ImVec2 b, ImVec2 sz, ImVec4 mouse, float t) {
		ImVec2 bl = new ImVec2(160, 90);
		ImVec2 br = new ImVec2(bl);
		ImVec2 bri = new ImVec2(bl);
		ImVec2 bli = new ImVec2(bl);
		cz += 0.5f;
		d.addQuadFilled(a.x, a.y, b.x, a.y, b.x, a.y + 30, a.x, a.y + 30, 0xffffff00);
		d.addQuadFilled(a.x, a.y + 30, b.x, a.y + 30, b.x, b.y, a.x, b.y, 0xff007f00);
		for (int s = 300; s > 0; s--) {
			float c = sin((cz + s) * 0.1f) * 500;
			float f = cos((cz + s) * 0.02f) * 1000;
			ImVec2 tl = new ImVec2(bl);
			ImVec2 tr = new ImVec2(br);
			ImVec2 tli = new ImVec2(bli);
			ImVec2 tri = new ImVec2(bri);
			tli.y--;
			tri.y--;
			float ss = 0.003f / s;
			float w = 2000 * ss * 160;
			float px = a.x + 160 + (f * ss * 160);
			float py = a.y + 30 - (ss * (c * 2 - 2500) * 90);
			bl = new ImVec2(px - w, py);
			br = new ImVec2(px + w, py);
			w = 1750 * ss * 160;
			bli = new ImVec2(px - w, py);
			bri = new ImVec2(px + w, py);
			if (s != 300) {
				boolean j = ((cz + s) % 10) < 5;
				d.addQuadFilled(tl.x, tl.y, tr.x, tr.y, br.x, br.y, bl.x, bl.y, j ? 0xffffffff : 0xff0000ff);
				d.addQuadFilled(tli.x, tli.y, tri.x, tri.y, bri.x, bri.y, bli.x, bli.y, j ? 0xff2f2f2f : 0xff3f3f3f);
			}
		}
	}

	public static void main(String[] args) {
		new RaceTrack().launch();
	}
}
