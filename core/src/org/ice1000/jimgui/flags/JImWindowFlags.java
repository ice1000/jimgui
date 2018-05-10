package org.ice1000.jimgui.flags;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImWindowFlags {
	int NoTitleBar = 1 << 0;
	int NoResize = 1 << 1;
	int NoMove = 1 << 2;
	int NoScrollbar = 1 << 3;
	int NoScrollWithMouse = 1 << 4;
	int NoCollapse = 1 << 5;
	int AlwaysAutoResize = 1 << 6;
	// int ShowBorders = 1 << 7;
	int NoSavedSettings = 1 << 8;
	int NoInputs = 1 << 9;
	int MenuBar = 1 << 10;
	int HorizontalScrollbar = 1 << 11;
	int NoFocusOnAppearing = 1 << 12;
	int NoBringToFrontOnFocus = 1 << 13;
	int AlwaysVerticalScrollbar = 1 << 14;
	int AlwaysHorizontalScrollbar = 1 << 15;
	int AlwaysUseWindowPadding = 1 << 16;
	int ResizeFromAnySide = 1 << 17;
	int NoNavInputs = 1 << 18;
	int NoNavFocus = 1 << 19;
	int NoNav = NoNavInputs | NoNavFocus;
}
