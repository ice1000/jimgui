package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Paths;

public class Demo {
	public static void main(String... args) {
		JniLoader.jniLibraryPath = Paths.get("jni", "cmake-build-debug", "libjimgui.so").toAbsolutePath().toString();
		JniLoader.load();
		JImGuiUtil.runPer(15, imGui -> {
			showUserGuide(imGui);
			showExampleAppMainMenuBar(imGui);
			if (imGui.button("200x200")) imGui.setWindowSize(200, 200);
			imGui.sameLine();
			if (imGui.button("500x500")) imGui.setWindowSize(500, 500);
			imGui.sameLine();
			if (imGui.button("800x200")) imGui.setWindowSize(800, 200);
			imGui.text("Simple overlay\nin the corner of the screen.\n(right-click to change position)");
			imGui.separator();
			if (imGui.isMousePosValid()) ;
//			ImGui.text("Mouse Position: (%.1f,%.1f)", , );
			else imGui.text("Mouse Position: <invalid>");
		});
	}

	private static void showExampleAppMainMenuBar(@NotNull JImGui imGui) {
		if (imGui.beginMainMenuBar()) {
			if (imGui.beginMenu("File")) {
				showExampleMenuFile(imGui);
				imGui.endMenu();
			}
			if (imGui.beginMenu("Edit")) {
				imGui.menuItem("Undo", "CTRL+Z");
				imGui.menuItem("Redo", "CTRL+Y", false, false);
				imGui.separator();
				imGui.menuItem("Cut", "CTRL+X");
				imGui.menuItem("Copy", "CTRL+C");
				imGui.menuItem("Paste", "CTRL+V");
				imGui.endMenu();
			}
			imGui.endMainMenuBar();
		}
	}

	private static void showExampleMenuFile(@NotNull JImGui imGui) {
		imGui.menuItem("(dummy menu)");
		imGui.menuItem("New");
		imGui.menuItem("Open", "Ctrl+O");
		if (imGui.beginMenu("Open Recent")) {
			imGui.menuItem("fish_hat.c");
			imGui.menuItem("fish_hat.inl");
			imGui.menuItem("fish_hat.h");
			if (imGui.beginMenu("More..")) {
				imGui.menuItem("Hello");
				imGui.menuItem("Sailor");
				if (imGui.beginMenu("Recurse..")) {
					showExampleMenuFile(imGui);
					imGui.endMenu();
				}
				imGui.endMenu();
			}
			imGui.endMenu();
		}
		imGui.menuItem("Save", "Ctrl+S");
		imGui.menuItem("Save As..");
		imGui.separator();
		imGui.beginMenu("Disabled", false);
		imGui.menuItem("Checked");
		imGui.menuItem("Quit", "Alt+F4");
	}


	private static void showUserGuide(@NotNull JImGui imGui) {
		imGui.bulletText("Double-click on title bar to collapse window.");
		imGui.bulletText(
				"Click and drag on lower right corner to resize window\n(double-click to auto fit window to its contents).");
		imGui.bulletText("Click and drag on any empty space to move window.");
		imGui.bulletText("TAB/SHIFT+TAB to cycle through keyboard editable fields.");
		imGui.bulletText("CTRL+Click on a slider or drag box to input value as text.");
		if (imGui.getIO().isFontAllowUserScaling()) imGui.bulletText("CTRL+Mouse Wheel to zoom window contents.");
		imGui.bulletText("Mouse Wheel to scroll.");
		imGui.bulletText("While editing text:\n");
		imGui.indent();
		imGui.bulletText("Hold SHIFT or use mouse to select text.");
		imGui.bulletText("CTRL+Left/Right to word jump.");
		imGui.bulletText("CTRL+A or double-click to select all.");
		imGui.bulletText("CTRL+X,CTRL+C,CTRL+V to use clipboard.");
		imGui.bulletText("CTRL+Z,CTRL+Y to undo/redo.");
		imGui.bulletText("ESCAPE to revert.");
		imGui.bulletText("You can apply arithmetic operators +,*,/ on numerical values.\nUse +- to subtract.");
		imGui.unindent();
	}
}
