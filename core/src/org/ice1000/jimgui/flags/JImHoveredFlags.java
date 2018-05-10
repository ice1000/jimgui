package org.ice1000.jimgui.flags;

/**
 * Flags for {@link org.ice1000.jimgui.JImGuiGen#isItemHovered(int)}, {@link org.ice1000.jimgui.JImGuiGen#isWindowHovered(int)}
 *
 * @author ice1000
 * @apiNote If you are trying to check whether your mouse should be dispatched to imgui or to your app, you should use the 'io.WantCaptureMouse' boolean for that. Please read the FAQ!
 * @since v0.1
 */
public interface JImHoveredFlags {
	/**
	 * Return true if directly over the item/window,
	 * not obstructed by another window,
	 * not obstructed by an active popup or modal blocking inputs under them.
	 */
	int Default = 0;
	/** IsWindowHovered() only: Return true if any children of the window is hovered */
	int ChildWindows = 1 << 0;
	/** isWindowHovered() only: Test from root window (top most parent of the current hierarchy) */
	int RootWindow = 1 << 1;
	/** isWindowHovered() only: Return true if any window is hovered */
	int AnyWindow = 1 << 2;
	/** Return true even if a popup window is normally blocking access to this item/window */
	int AllowWhenBlockedByPopup = 1 << 3;
	//** Return true even if a modal popup window is normally blocking access to this item/window. FIXME-TODO: Unavailable yet. */
	// int AllowWhenBlockedByModal = 1 << 4;
	/** Return true even if an active item is blocking access to this item/window. Useful for Drag and Drop patterns. */
	int AllowWhenBlockedByActiveItem = 1 << 5;
	/** Return true even if the position is overlapped by another window */
	int AllowWhenOverlapped = 1 << 6;
	int RectOnly = AllowWhenBlockedByPopup | AllowWhenBlockedByActiveItem | AllowWhenOverlapped;
	int RootAndChildWindows = RootWindow | ChildWindows;
}
