package org.ice1000.jimgui.dlparty;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImStr;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

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
		try (JImGui imGui = new JImGui(800, 600, "FX")) {
			List<Case> toys = Arrays.asList(
					new Case(new Curves()),
					new Case(new Circles()),
					new Case(new MatrixEffect()),
					new Case(new Squares()),
					new Case(new ThunderStorm()),
					new Case(new TinyLoadingScreen())
			);
			imGui.initBeforeMainLoop();
			while (!imGui.windowShouldClose()) {
				imGui.initNewFrame();
				for (Case toy : toys) toy.work(imGui);
				imGui.render();
			}
		}
	}
}
