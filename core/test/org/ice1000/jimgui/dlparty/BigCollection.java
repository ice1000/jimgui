package org.ice1000.jimgui.dlparty;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImStr;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.ice1000.jimgui.dlparty.TestBed.sizeX;
import static org.ice1000.jimgui.dlparty.TestBed.sizeY;

public class BigCollection {
	public static class Case {
		private final JImStr str;
		private final TestBed bed;

		public Case(@NotNull TestBed bed) {
			this.bed = bed;
			this.str = new JImStr(bed.getClass().getSimpleName());
		}

		public void work(JImGui imGui) {
			bed.testBed(imGui, str);
		}
	}

	public static void main(String[] args) {
		JniLoader.load();
		try (JImGui imGui = new JImGui(1200, 1000, "FX")) {
			List<Case> toys = Stream.of(
					new Curves(),
					new Circles(),
					new MatrixEffect(),
					new Squares(),
					new ThunderStorm(),
					new TinyLoadingScreen(),
					new Blobs(),
					new ThreeDCube(),
					new InterwebBlogosphere(),
					new QuickSortVisualization()
			).map(Case::new).collect(Collectors.toList());
			imGui.initBeforeMainLoop();
			while (!imGui.windowShouldClose()) {
				imGui.initNewFrame();
				float x = 0, y = 0;
				float windowSizeX = imGui.getPlatformWindowSizeX();
				for (Case toy : toys) {
					if (x + sizeX > windowSizeX) {
						x = 0;
						y += sizeY + 20;
					}
					imGui.setNextWindowPos(x, y);
					toy.work(imGui);
					x += sizeX + 10;
				}
				imGui.render();
			}
		}
	}
}
