package org.ice1000.jimgui.flag;

/**
 * @author ice1000
 * @since v0.1
 */
public interface JImWindowFlags {
	int Nothing = 0;
	/** Disable title-bar */
	int NoTitleBar = 1;
	/** Disable user resizing with the lower-right grip */
	int NoResize = 1 << 1;
	/** Disable user moving the window */
	int NoMove = 1 << 2;
	/** Disable scrollbars (window can still scroll with mouse or programatically) */
	int NoScrollbar = 1 << 3;
	/** Disable user vertically scrolling with mouse wheel. On child window, mouse wheel will be forwarded to the parent unless NoScrollbar is also set. */
	int NoScrollWithMouse = 1 << 4;
	/** Disable user collapsing window by double-clicking on it */
	int NoCollapse = 1 << 5;
	/** Resize every window to its content every frame */
	int AlwaysAutoResize = 1 << 6;
	//** Show borders around windows and items (OBSOLETE! Use e.g. style.FrameBorderSize=1.0f to enable borders). */
	//@Deprecated
	//int ShowBorders = 1 << 7;
	/** Never load/save settings in .ini file */
	int NoSavedSettings = 1 << 8;
	/** Disable catching mouse or keyboard inputs, hovering test with pass through. */
	int NoInputs = 1 << 9;
	/** Has a menu-bar */
	int MenuBar = 1 << 10;
	/** Allow horizontal scrollbar to appear (off by default). You may use SetNextWindowContentSize(ImVec2(width,0.0f)); prior to calling Begin() to specify width. Read code in imgui_demo in the "Horizontal Scrolling" section. */
	int HorizontalScrollbar = 1 << 11;
	/** Disable taking focus when transitioning from hidden to visible state */
	int NoFocusOnAppearing = 1 << 12;
	/** Disable bringing window to front when taking focus (e.g. clicking on it or programatically giving it focus) */
	int NoBringToFrontOnFocus = 1 << 13;
	/** Always show vertical scrollbar (even if ContentSize.y < Size.y) */
	int AlwaysVerticalScrollbar = 1 << 14;
	/** Always show horizontal scrollbar (even if ContentSize.x < Size.x) */
	int AlwaysHorizontalScrollbar = 1 << 15;
	/** Ensure child windows without border uses style.WindowPadding (ignored by default for non-bordered child windows, because more convenient) */
	int AlwaysUseWindowPadding = 1 << 16;
	/** [BETA] Enable resize from any corners and borders. Your back-end needs to honor the different values of io.MouseCursor set by imgui. */
	int ResizeFromAnySide = 1 << 17;
	/** No gamepad/keyboard navigation within the window */
	int NoNavInputs = 1 << 18;
	/** No focusing toward this window with gamepad/keyboard navigation (e.g. skipped by CTRL+TAB) */
	int NoNavFocus = 1 << 19;
	int NoNav = NoNavInputs | NoNavFocus;

	/**
	 * [BETA] Allow gamepad/keyboard navigation to cross over parent border to this child
	 *
	 * @apiNote (only use on child that have no scrolling !)
	 */
	int NavFlattened = 1 << 23;
}
