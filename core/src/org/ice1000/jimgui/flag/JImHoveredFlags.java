package org.ice1000.jimgui.flag;

/**
 * Flags for {@link org.ice1000.jimgui.JImGuiGen#isItemHovered(int)}, {@link org.ice1000.jimgui.JImGuiGen#isWindowHovered(int)}
 *
 * @author ice1000
 * @apiNote if you are trying to check whether your mouse should be dispatched to imgui or to your app, you should use the 'io.WantCaptureMouse' boolean for that. Please read the FAQ!
 * @apiNote windows with the ImGuiWindowFlags_NoInputs flag are ignored by IsWindowHovered() calls.
 * @since v0.1
 */
public interface JImHoveredFlags {
	int Nothing = 0;
	/**
	 * Return true if directly over the item/window,
	 * not obstructed by another window,
	 * not obstructed by an active popup or modal blocking inputs under them.
	 */
	int Default = 0;
	/** IsWindowHovered() only: Return true if any children of the window is hovered */
	int ChildWindows = 1;
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
	/** Return true even if the item is disabled */
	int AllowWhenDisabled = 1 << 7;
	int RectOnly = AllowWhenBlockedByPopup | AllowWhenBlockedByActiveItem | AllowWhenOverlapped;
	int RootAndChildWindows = RootWindow | ChildWindows;

	enum Type implements Flag {
		/**
		 * Used for reverse lookup results and enum comparison.
		 * Return the Nothing or Default flag to prevent errors.
		 */
		NoSuchFlag(JImHoveredFlags.Nothing),
		Nothing(JImHoveredFlags.Nothing),
		/** @see JImHoveredFlags#Default */
		Default(JImHoveredFlags.Default),
		/** @see JImHoveredFlags#ChildWindows */
		ChildWindows(JImHoveredFlags.ChildWindows),
		/** @see JImHoveredFlags#RootWindow */
		RootWindow(JImHoveredFlags.RootWindow),
		/** @see JImHoveredFlags#AnyWindow */
		AnyWindow(JImHoveredFlags.AnyWindow),
		/** @see JImHoveredFlags#AllowWhenBlockedByPopup */
		AllowWhenBlockedByPopup(JImHoveredFlags.AllowWhenBlockedByPopup),
		/** @see JImHoveredFlags#AllowWhenBlockedByActiveItem */
		AllowWhenBlockedByActiveItem(JImHoveredFlags.AllowWhenBlockedByActiveItem),
		/** @see JImHoveredFlags#AllowWhenOverlapped */
		AllowWhenOverlapped(JImHoveredFlags.AllowWhenOverlapped),
		/** @see JImHoveredFlags#AllowWhenDisabled */
		AllowWhenDisabled(JImHoveredFlags.AllowWhenDisabled),
		/** @see JImHoveredFlags#RectOnly */
		RectOnly(JImHoveredFlags.RectOnly),
		/** @see JImHoveredFlags#RootAndChildWindows */
		RootAndChildWindows(JImHoveredFlags.RootAndChildWindows);

		public final int flag;

		Type(int flag) {
			this.flag = flag;
		}

		@Override
		public int get() {
			return flag;
		}
	}
}
