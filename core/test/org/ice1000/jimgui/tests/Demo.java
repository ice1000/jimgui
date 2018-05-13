package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.NativeBool;
import org.ice1000.jimgui.cpp.DeallocatableObjectManager;
import org.ice1000.jimgui.flag.JImCondition;
import org.ice1000.jimgui.flag.JImWindowFlags;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;

public class Demo {
	private static final float DISTANCE = 10.0f;
	private static int corner = 0;
	private static boolean noTitlebar = false;
	private static boolean noScrollbar = false;
	private static boolean noMenu = false;
	private static boolean noMove = false;
	private static boolean noResize = false;
	private static boolean noCollapse = false;
	private static boolean noNav = false;

	public static void main(String @NotNull ... args) {
		JniLoader.load();
		final String windowName = "Debug";
		NativeBool showAppSimpleOverlay = new NativeBool();
		NativeBool pOpen = new NativeBool();
		DeallocatableObjectManager manager = new DeallocatableObjectManager(5);
		manager.add(showAppSimpleOverlay);
		manager.add(pOpen);
		JImGuiUtil.runPer(15, imGui -> {
			// Demonstrate the various window flags. Typically you would just use the default.
			@MagicConstant(flagsFromClass = JImWindowFlags.class) int windowFlags = 0;
			if (noTitlebar) windowFlags |= JImWindowFlags.NoTitleBar;
			if (noScrollbar) windowFlags |= JImWindowFlags.NoScrollbar;
			if (!noMenu) windowFlags |= JImWindowFlags.MenuBar;
			if (noMove) windowFlags |= JImWindowFlags.NoMove;
			if (noResize) windowFlags |= JImWindowFlags.NoResize;
			if (noCollapse) windowFlags |= JImWindowFlags.NoCollapse;
			if (noNav) windowFlags |= JImWindowFlags.NoNav;
			imGui.setNextWindowSize(550, 680, JImCondition.FirstUseEver);
			imGui.begin(windowName, pOpen, windowFlags);
			//imGui.pushItemWidth(imGui.getWindowWidth() * 0.65f);    // 2/3 of the space for widget and 1/3 for labels
			imGui.pushItemWidth(-140);                                 // Right align, keep 140 pixels for labels
			imGui.text("dear imgui says hello. (1.61 WIP)");
			if (imGui.collapsingHeader("Help")) {
				imGui.textWrapped("This window is being created by the ShowDemoWindow() function. Please refer to the code in imgui_demo.cpp for reference.\n\n");
				imGui.text("USER GUIDE:");
				showUserGuide(imGui);
			}
			showExampleAppMainMenuBar(imGui);
			if (imGui.button("200x200")) imGui.setWindowSize(windowName, 200, 200);
			imGui.sameLine();
			if (imGui.button("500x500")) imGui.setWindowSize(windowName, 500, 500);
			imGui.sameLine();
			if (imGui.button("800x200")) imGui.setWindowSize(windowName, 800, 200);
			if (showAppSimpleOverlay.accessValue()) showExampleAppFixedOverlay(imGui, showAppSimpleOverlay);
			if (imGui.beginMenuBar()) {
				if (imGui.beginMenu("Menu")) {
					showExampleMenuFile(imGui);
					imGui.endMenu();
				}
				if (imGui.beginMenu("Examples")) {
					imGui.menuItem0("Simple overlay", null, showAppSimpleOverlay);
					imGui.endMenu();
				}
				imGui.endMenuBar();
			}
			imGui.end();
		});
		manager.deallocateAll();
	}

	private static void showExampleAppFixedOverlay(@NotNull JImGui imGui, @NotNull NativeBool openPtr) {
		float windowPosX = (corner & 1) > 0 ? imGui.getIO().getDisplaySizeX() - DISTANCE : DISTANCE;
		float windowPosY = (corner & 2) > 0 ? imGui.getIO().getDisplaySizeY() - DISTANCE : DISTANCE;
		float windowPosPivotX = (corner & 1) > 0 ? 1.0f : 0.0f;
		float windowPosPivotY = (corner & 2) > 0 ? 1.0f : 0.0f;
		if (corner != -1)
			imGui.setNextWindowPos(windowPosX, windowPosY, JImCondition.Always, windowPosPivotX, windowPosPivotY);
		imGui.setNextWindowBgAlpha(.3f);
		if (imGui.begin("Example: Fixed Overlay",
				openPtr,
				(corner != -1 ? JImWindowFlags.NoMove : 0) |
						JImWindowFlags.NoTitleBar |
						JImWindowFlags.NoResize |
						JImWindowFlags.AlwaysAutoResize |
						JImWindowFlags.NoSavedSettings |
						JImWindowFlags.NoFocusOnAppearing |
						JImWindowFlags.NoNav)) {
			imGui.text("Simple overlay\nin the corner of the screen.\n(right-click to change position)");
			imGui.sameLine();
			showHelpMarker(imGui, "This is another help.");
			imGui.separator();
			if (imGui.isMousePosValid())
				imGui.text("Mouse Position: (" + imGui.getIO().getMousePosX() + ", " + imGui.getIO().getMousePosY() + ")");
			else imGui.text("Mouse Position: <invalid>");
			if (imGui.beginPopupContextWindow()) {
				if (imGui.menuItem("Custom", null, corner == -1)) corner = -1;
				if (imGui.menuItem("Top-left", null, corner == 0)) corner = 0;
				if (imGui.menuItem("Top-right", null, corner == 1)) corner = 1;
				if (imGui.menuItem("Bottom-left", null, corner == 2)) corner = 2;
				if (imGui.menuItem("Bottom-right", null, corner == 3)) corner = 3;
				if (openPtr.accessValue() && imGui.menuItem("Close")) openPtr.modifyValue(false);
				imGui.endPopup();
			}
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
		imGui.menuItem("(dummy menu)", null, false, false);
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
