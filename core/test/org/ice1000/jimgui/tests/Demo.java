package org.ice1000.jimgui.tests;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.NativeBool;
import org.ice1000.jimgui.NativeInt;
import org.ice1000.jimgui.cpp.DeallocatableObjectManager;
import org.ice1000.jimgui.flag.JImCondition;
import org.ice1000.jimgui.flag.JImDirection;
import org.ice1000.jimgui.flag.JImWindowFlags;
import org.ice1000.jimgui.util.JImGuiUtil;
import org.ice1000.jimgui.util.JniLoader;
import org.jetbrains.annotations.NotNull;

public class Demo {
	private static final float DISTANCE = 10.0f;
	private static int corner = 0;

	public static void main(String @NotNull ... args) {
		JniLoader.load();
		final String windowName = "Debug";
		NativeBool noTitlebar = new NativeBool();
		NativeBool noScrollbar = new NativeBool();
		NativeBool noMenu = new NativeBool();
		NativeBool noMove = new NativeBool();
		NativeBool noResize = new NativeBool();
		NativeBool noCollapse = new NativeBool();
		NativeBool noNav = new NativeBool();
		NativeBool showAppSimpleOverlay = new NativeBool();
		NativeBool showAppMainMenuBar = new NativeBool();
		NativeBool show_app_log = new NativeBool();
		NativeBool show_app_layout = new NativeBool();
		NativeBool show_app_property_editor = new NativeBool();
		NativeBool show_app_long_text = new NativeBool();
		NativeBool show_app_auto_resize = new NativeBool();
		NativeBool show_app_constrained_resize = new NativeBool();
		NativeBool showAppWindowTitles = new NativeBool();
		NativeBool show_app_custom_rendering = new NativeBool();
		NativeBool showAppStyleEditor = new NativeBool();
		NativeBool showAppMetrics = new NativeBool();
		NativeBool showAppAbout = new NativeBool();
		NativeBool pOpen = new NativeBool();

		NativeInt clicked = new NativeInt();
		NativeBool check = new NativeBool();
		check.modifyValue(true);
		NativeInt e = new NativeInt();
		DeallocatableObjectManager manager = new DeallocatableObjectManager(15);
		manager.add(showAppSimpleOverlay);
		manager.add(showAppMainMenuBar);
		manager.add(show_app_log);
		manager.add(show_app_layout);
		manager.add(show_app_property_editor);
		manager.add(show_app_long_text);
		manager.add(show_app_auto_resize);
		manager.add(show_app_constrained_resize);
		manager.add(showAppWindowTitles);
		manager.add(show_app_custom_rendering);
		manager.add(showAppStyleEditor);
		manager.add(showAppMetrics);
		manager.add(showAppAbout);
		manager.add(pOpen);
		manager.add(noTitlebar);
		manager.add(noScrollbar);
		manager.add(noMenu);
		manager.add(noMove);
		manager.add(noResize);
		manager.add(noCollapse);
		manager.add(noNav);
		manager.add(clicked);
		manager.add(check);
		manager.add(e);
		JImGuiUtil.runPer(15, imGui -> {
			if (showAppMetrics.accessValue()) imGui.showMetricsWindow(showAppMetrics);
			if (showAppStyleEditor.accessValue()) {
				imGui.begin("Style Editor", showAppStyleEditor);
				imGui.showStyleEditor();
				imGui.end();
			}
			if (showAppAbout.accessValue()) {
				imGui.begin("About Dear ImGui", showAppAbout, JImWindowFlags.AlwaysAutoResize);
				imGui.separator();
				imGui.text("By Omar Cornut and all dear imgui contributors.");
				imGui.text("Dear ImGui is licensed under the MIT License, see LICENSE for more information.");
				imGui.end();
			}

			// Demonstrate the various window flags. Typically you would just use the default.
			int windowFlags = JImWindowFlags.Nothing;
			if (noTitlebar.accessValue()) windowFlags |= JImWindowFlags.NoTitleBar;
			if (noScrollbar.accessValue()) windowFlags |= JImWindowFlags.NoScrollbar;
			if (!noMenu.accessValue()) windowFlags |= JImWindowFlags.MenuBar;
			if (noMove.accessValue()) windowFlags |= JImWindowFlags.NoMove;
			if (noResize.accessValue()) windowFlags |= JImWindowFlags.NoResize;
			if (noCollapse.accessValue()) windowFlags |= JImWindowFlags.NoCollapse;
			if (noNav.accessValue()) windowFlags |= JImWindowFlags.NoNav;
			imGui.setNextWindowSize(550, 680, JImCondition.FirstUseEver);
			imGui.begin(windowName, pOpen, windowFlags);

			//imGui.pushItemWidth(imGui.getWindowWidth() * 0.65f);    // 2/3 of the space for widget and 1/3 for labels
			imGui.pushItemWidth(-140);                                 // Right align, keep 140 pixels for labels

			imGui.text("dear imgui says hello. (1.61 WIP)");

			if (imGui.beginMenuBar()) {
				if (imGui.beginMenu("Menu")) {
					showExampleMenuFile(imGui);
					imGui.endMenu();
				}
				if (imGui.beginMenu("Examples")) {
					imGui.menuItem0("Main menu bar", null, showAppMainMenuBar);
					imGui.menuItem0("Log", null, show_app_log);
					imGui.menuItem0("Simple layout", null, show_app_layout);
					imGui.menuItem0("Property editor", null, show_app_property_editor);
					imGui.menuItem0("Long text display", null, show_app_long_text);
					imGui.menuItem0("Auto-resizing window", null, show_app_auto_resize);
					imGui.menuItem0("Constrained-resizing window", null, show_app_constrained_resize);
					imGui.menuItem0("Simple overlay", null, showAppSimpleOverlay);
					imGui.menuItem0("Manipulating window titles", null, showAppWindowTitles);
					imGui.menuItem0("Custom rendering", null, show_app_custom_rendering);
					imGui.endMenu();
				}
				if (imGui.beginMenu("Help")) {
					imGui.menuItem0("Metrics", null, showAppMetrics);
					imGui.menuItem0("Style Editor", null, showAppStyleEditor);
					imGui.menuItem0("About Dear Imgui", null, showAppAbout);
					imGui.endMenu();
				}
				imGui.endMenuBar();
			}

			imGui.spacing();
			if (imGui.collapsingHeader("Help")) {
				imGui.textWrapped(
						"This window is being created by the ShowDemoWindow() function. Please refer to the code in imgui_demo.cpp for reference.\n\n");
				imGui.text("USER GUIDE:");
				showUserGuide(imGui);
			}
			if (imGui.collapsingHeader("Window options")) {
				imGui.checkbox("No titlebar", noTitlebar);
				imGui.sameLine(150);
				imGui.checkbox("No scrollbar", noScrollbar);
				imGui.sameLine(300);
				imGui.checkbox("No menu", noMenu);
				imGui.checkbox("No move", noMove);
				imGui.sameLine(150);
				imGui.checkbox("No resize", noResize);
				imGui.sameLine(300);
				imGui.checkbox("No collapse", noCollapse);
				imGui.sameLine(150);
				imGui.checkbox("No nav", noNav);

				if (imGui.treeNode("Style")) {
					imGui.showStyleEditor();
					imGui.treePop();
				}

				if (imGui.treeNode("Capture/Logging")) {
					imGui.textWrapped(
							"The logging API redirects all text output so you can easily capture the content of a window or a block. Tree nodes can be automatically expanded. You can also call ImGui::LogText() to output directly to the log without a visual output.");
					imGui.logButtons();
					imGui.treePop();
				}
			}

			if (imGui.collapsingHeader("Widgets")) {
				if (imGui.treeNode("Basic")) {
					if (imGui.button("Button"))
						clicked.modifyValue(clicked.accessValue() + 1);
					if ((clicked.accessValue() & 1) > 0) {
						imGui.sameLine();
						imGui.text("Thanks for clicking me!");
					}

					imGui.checkbox("checkbox", check);

					imGui.radioButton("radio a", e, 0);
					imGui.sameLine();
					imGui.radioButton("radio b", e, 1);
					imGui.sameLine();
					imGui.radioButton("radio c", e, 2);
					imGui.sameLine();

					for (int i = 0; i < 7; i++) {
						if (i > 0) imGui.sameLine();
						// TODO
					}
					imGui.treePop();

					float spacingX = imGui.getStyle().getItemInnerSpacingX();
					if (imGui.arrowButton("##left", JImDirection.Left)) ;
					imGui.sameLine(0, spacingX);
					if (imGui.arrowButton("##left", JImDirection.Right)) ;

					imGui.text("Hover over me");
					if (imGui.isItemHovered())
						imGui.setTooltip("I am a tooltip");
				}
			}

			if (showAppMainMenuBar.accessValue()) showExampleAppMainMenuBar(imGui);
			if (showAppSimpleOverlay.accessValue()) showExampleAppFixedOverlay(imGui, showAppSimpleOverlay);
			if (showAppWindowTitles.accessValue()) showExampleAppWindowTitles(imGui);
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

	private static void wtf(@NotNull JImGui imGui, @NotNull String windowName) {
		if (imGui.button("200x200")) imGui.setWindowSize(windowName, 200, 200);
		imGui.sameLine();
		if (imGui.button("500x500")) imGui.setWindowSize(windowName, 500, 500);
		imGui.sameLine();
		if (imGui.button("800x200")) imGui.setWindowSize(windowName, 800, 200);
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

	private static void showExampleAppWindowTitles(@NotNull JImGui imGui) {
		// By default, Windows are uniquely identified by their title.
		// You can use the "##" and "###" markers to manipulate the display/ID.

		// Using "##" to display same title but have unique identifier.
		imGui.setNextWindowPos(100, 100, JImCondition.FirstUseEver);
		imGui.begin("Same title as another window##1");
		imGui.text("This is window 1.\nMy title is the same as window 2, but my identifier is unique.");
		imGui.end();

		imGui.setNextWindowPos(100, 200, JImCondition.FirstUseEver);
		imGui.begin("Same title as another window##2");
		imGui.text("This is window 2.\nMy title is the same as window 1, but my identifier is unique.");
		imGui.end();

		imGui.setNextWindowPos(100, 300, JImCondition.FirstUseEver);
		long millis = System.currentTimeMillis();
		// Using "###" to display a changing title but keep a static identifier "Animated title"
		imGui.begin("Animated title " +
				"|/-\\".charAt((int) (millis / 0.25f) & 3) +
				" " + millis + "###Animated title");
		imGui.text("This window has a changing title.");
		imGui.end();
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
