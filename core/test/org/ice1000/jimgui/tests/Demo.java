package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;

public class Demo {
	public static void main(String @NotNull ... args) {
		JniLoader.load();
		final String windowName = "Debug";
		JImGuiUtil.runPer(15, imGui -> {
			imGui.begin(windowName);
			showUserGuide(imGui);
			showExampleAppMainMenuBar(imGui);
			if (imGui.button("200x200")) imGui.setWindowSize(windowName, 200, 200);
			imGui.sameLine();
			if (imGui.button("500x500")) imGui.setWindowSize(windowName, 500, 500);
			imGui.sameLine();
			if (imGui.button("800x200")) imGui.setWindowSize(windowName, 800, 200);
			showExampleAppFixedOverlay(imGui);
			if (imGui.treeNode("This is a tree node")) {
				imGui.text("Which?");
				if (imGui.treeNode("This is a child node")) {
					imGui.text("What?");
					imGui.treePop();
				}
				imGui.treePop();
			}
			imGui.end();
		});
	}

	private static void showExampleAppFixedOverlay(@NotNull JImGui imGui) {
		imGui.setNextWindowBgAlpha(.3f);
		if (imGui.begin("Example: Fixed Overlay")) {
			imGui.text("Simple overlay\nin the corner of the screen.\n(right-click to change position)");
			imGui.sameLine();
			showHelpMarker(imGui, "This is a help.");
			imGui.separator();
			if (imGui.isMousePosValid())
				imGui.text("Mouse Position: (" + imGui.getIO().getMousePosX() + ", " + imGui.getIO().getMousePosY() + ")");
			else imGui.text("Mouse Position: <invalid>");
			imGui.end();
		}
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

	private static void showHelpMarker(@NotNull JImGui imGui, @NotNull String description) {
		imGui.textDisabled("(?)");
		if (imGui.isItemHovered()) {
			imGui.beginTooltip();
			imGui.pushTextWrapPos(imGui.getFontSize() * 35.0f);
			imGui.text(description);
			imGui.popTextWrapPos();
			imGui.endTooltip();
		}
	}
}
