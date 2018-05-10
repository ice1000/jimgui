package org.ice1000.jimgui.flags;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImHoveredFlags {
	int Default = 0;
	int ChildWindows = 1 << 0;
	int RootWindow = 1 << 1;
	int AnyWindow = 1 << 2;
	int AllowWhenBlockedByPopup = 1 << 3;
	// int AllowWhenBlockedByModal = 1 << 4;
	int AllowWhenBlockedByActiveItem = 1 << 5;
	int AllowWhenOverlapped = 1 << 6;
	int RectOnly = AllowWhenBlockedByPopup | AllowWhenBlockedByActiveItem | AllowWhenOverlapped;
	int RootAndChildWindows = RootWindow | ChildWindows;
}
