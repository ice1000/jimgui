package org.ice1000.jimgui;

/**
 * @author ice1000
 * @since v0.1
 */
public class JImHoveredFlags {
	public static final int Default = 0;
	public static final int ChildWindows = 1 << 0;
	public static final int RootWindow = 1 << 1;
	public static final int AnyWindow = 1 << 2;
	public static final int AllowWhenBlockedByPopup = 1 << 3;
	//	public static final int AllowWhenBlockedByModal = 1 << 4;
	public static final int AllowWhenBlockedByActiveItem = 1 << 5;
	public static final int AllowWhenOverlapped = 1 << 6;
	public static final int RectOnly = AllowWhenBlockedByPopup | AllowWhenBlockedByActiveItem | AllowWhenOverlapped;
	public static final int RootAndChildWindows = RootWindow | ChildWindows;
}
