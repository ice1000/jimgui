package org.ice1000.jimgui;

/**
 * @author ice1000
 * @since v0.1
 */
public class JImWindowFlags {
	public static final int NoTitleBar = 1 << 0;
	public static final int NoResize = 1 << 1;
	public static final int NoMove = 1 << 2;
	public static final int NoScrollbar = 1 << 3;
	public static final int NoScrollWithMouse = 1 << 4;
	public static final int NoCollapse = 1 << 5;
	public static final int AlwaysAutoResize = 1 << 6;
	//	public static final int ShowBorders = 1 << 7;
	public static final int NoSavedSettings = 1 << 8;
	public static final int NoInputs = 1 << 9;
	public static final int MenuBar = 1 << 10;
	public static final int HorizontalScrollbar = 1 << 11;
	public static final int NoFocusOnAppearing = 1 << 12;
	public static final int NoBringToFrontOnFocus = 1 << 13;
	public static final int AlwaysVerticalScrollbar = 1 << 14;
	public static final int AlwaysHorizontalScrollbar = 1 << 15;
	public static final int AlwaysUseWindowPadding = 1 << 16;
	public static final int ResizeFromAnySide = 1 << 17;
	public static final int NoNavInputs = 1 << 18;
	public static final int NoNavFocus = 1 << 19;
	public static final int NoNav = NoNavInputs | NoNavFocus;
}
